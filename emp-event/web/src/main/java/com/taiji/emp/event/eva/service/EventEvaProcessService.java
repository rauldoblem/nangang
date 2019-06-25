package com.taiji.emp.event.eva.service;

import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.event.eva.feign.DocAttClient;
import com.taiji.emp.event.eva.feign.EventEvaProcessClient;
import com.taiji.emp.event.eva.feign.EventEvaProcessNodeClient;
import com.taiji.emp.event.eva.vo.EventEvaProcessNodeVo;
import com.taiji.emp.event.eva.vo.EventEvaProcessSaveVo;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.event.redis.vo.ProcessRedisNodeVo;
import com.taiji.micro.common.utils.AssemblyTreeUtils;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventEvaProcessService extends BaseService {

    private EventEvaProcessClient processClient;
    private DocAttClient docAttClient;

    private EventEvaProcessNodeClient processNodeRedisClient;

    /**
     * 新增过程再现
     */
    public void create(EventEvaProcessSaveVo saveVo, Principal principal){
        EventEvaProcessVo vo = saveVo.getProcess();
        String userName  = principal.getName(); //获取用户姓名
        vo.setCreateBy(userName); //创建人
        if(vo.getProcessType().isEmpty()) {
            vo.setProcessType("0");//过程记录类型：0系统自动创建 1人工手动创建
        }
        //根据SecondNCode()二级节点ID 获取到二级节点信息
        ProcessRedisNodeVo nodeOne = processNodeRedisClient.findNodeOne(vo.getSecNodeCode()).getBody();
        Assert.notNull(nodeOne,"SecNodeCode不存在");
        vo.setSecNodeCode(nodeOne.getId());
        vo.setSecNodeName(nodeOne.getNodeName());
        //根据二级节点的parentId 得到父一级节点的信息
        ProcessRedisNodeVo parentNode = processNodeRedisClient.findNodeOne(nodeOne.getParentId()).getBody();
        vo.setFirstNodeCode(parentNode.getId());
        vo.setFirstNodeName(parentNode.getNodeName());

        ResponseEntity<EventEvaProcessVo> resultVo = processClient.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        fileSave(resultVo,saveVo);

    }

    /**
     * 更新单条过程再现
     */
    public void update(EventEvaProcessSaveVo saveVo, Principal principal, String id) {
        EventEvaProcessVo vo =saveVo.getProcess();
        String userName  = principal.getName(); //获取用户姓名
        vo.setUpdateBy(userName); //更新人
        //根据SecondNCode()二级节点ID 获取到二级节点信息
        ProcessRedisNodeVo nodeOne = processNodeRedisClient.findNodeOne(vo.getSecNodeCode()).getBody();
        vo.setSecNodeCode(nodeOne.getId());
        vo.setSecNodeName(nodeOne.getNodeName());
        //根据二级节点的parentId 得到父一级节点的信息
        ProcessRedisNodeVo parentNode = processNodeRedisClient.findNodeOne(nodeOne.getParentId()).getBody();
        vo.setFirstNodeCode(parentNode.getId());
        vo.setFirstNodeName(parentNode.getNodeName());
        ResponseEntity<EventEvaProcessVo> resultVo  = processClient.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        fileSave(resultVo,saveVo);
    }

    private void fileSave(ResponseEntity<EventEvaProcessVo> resultVo, EventEvaProcessSaveVo saveVo){
        String entityId = resultVo.getBody().getId();
        List<String> docAttIds = saveVo.getFileIds();
        List<String> docAttDelIds = saveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
    }

    /**
     * 根据id获取单条过程再现记录
     */
    public EventEvaProcessSaveVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<EventEvaProcessVo> resultVo = processClient.findOne(id);
        EventEvaProcessSaveVo eventEvaProcessSaveVo = new EventEvaProcessSaveVo();
        List<String> listStr = findDocAtt(id);
        EventEvaProcessVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        eventEvaProcessSaveVo.setProcess(vo);
        eventEvaProcessSaveVo.setFileIds(listStr);
        return eventEvaProcessSaveVo;
    }

    /**
     * 查询文件ID集合
     * @param id
     * @return
     */
    public  List<String> findDocAtt(String id) {
        List<DocAttVo> list = docAttClient.findList(id).getBody();
        List<String> listStr = new ArrayList<>();
        for (DocAttVo doc : list) {
            listStr.add(doc.getId());
        }
        return listStr;
    }

    /**
     * 根据id逻辑删除单条过程再现记录
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = processClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }



    public  List<EventEvaProcessNodeVo> findNodeListVo(String eventId) {
        //查询事件流程树
        List<EventEvaProcessNodeVo> listVo = findNodeList();
        List<EventEvaProcessNodeVo> listResult = new ArrayList<>();

        Map<String,EventEvaProcessNodeVo> nodeMap = listVo.stream().collect(Collectors.toMap(temp -> temp.getId(),temp ->temp));
        List<EventEvaProcessVo> list = findList(eventId);//业务节点信息
        for(EventEvaProcessVo process : list){
            String secNodeCode = process.getSecNodeCode();
            List<String> listStr = findDocAtt(process.getId());
            if(nodeMap.containsKey(secNodeCode)){
                EventEvaProcessNodeVo processNodeVo = new EventEvaProcessNodeVo();
                EventEvaProcessNodeVo temp = nodeMap.get(secNodeCode);
                listVo.remove(temp); //清理的剩餘項即為一級節點
                processNodeVo.setNodeName(temp.getNodeName());
                processNodeVo.setParentId(temp.getParentId());
                processNodeVo.setLeaf(temp.getLeaf());
                processNodeVo.setStatus(temp.getStatus());
                processNodeVo.setId(process.getId());
                processNodeVo.setProcess(process);
                processNodeVo.setFileIds(listStr);
                processNodeVo.setOrders(temp.getOrders());
                listResult.add(processNodeVo);
            }

        }
        listResult.addAll(listVo);

        EventEvaProcessNodeVo top = new EventEvaProcessNodeVo();
        top.setId("-1");
        top.setNodeName("事件流程");
        top.setParentId("-1");
        listResult.add(top);
        return AssemblyTreeUtils.assemblyTree(listResult);
    }
    /**
     * 获取过程再现list(不带分页)
     */
    public List<EventEvaProcessVo> findList(String eventId){
        Assert.notNull(eventId,"params 不能为空");
        ResponseEntity<List<EventEvaProcessVo>> resultVo = processClient.findList(eventId);
        List<EventEvaProcessVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取过程再现树
     */
    public List<EventEvaProcessNodeVo> findNodeList(){
        //查询事件流程树优先redis
        List<ProcessRedisNodeVo> listVo = processNodeRedisClient.findNodeList().getBody();
        List<EventEvaProcessNodeVo> list = getList(listVo);
        return ResponseEntityUtils.achieveResponseEntityBody(ResponseEntity.ok(list));
    }

    private List<EventEvaProcessNodeVo> getList(List<ProcessRedisNodeVo> listVo){
        List<EventEvaProcessNodeVo> list = new ArrayList<>();
        for(int i=0;i<listVo.size();i++){
            ProcessRedisNodeVo nodeVo = listVo.get(i);
            EventEvaProcessNodeVo vo = new EventEvaProcessNodeVo();
            vo.setId(nodeVo.getId());
            vo.setStatus(nodeVo.getStatus());
            vo.setOrders(nodeVo.getOrders());
            vo.setLeaf(nodeVo.getLeaf());
            vo.setParentId(nodeVo.getParentId());
            vo.setNodeName(nodeVo.getNodeName());
            list.add(vo);
        }
        return list;
    }

    public List<EventEvaProcessNodeVo> searchNodesAll(){
        //查询事件流程树优先redis
        List<ProcessRedisNodeVo> listVo = processNodeRedisClient.findNodeList().getBody();
        List<EventEvaProcessNodeVo> list = getList(listVo);
        EventEvaProcessNodeVo top = new EventEvaProcessNodeVo();
        top.setId("-1");
        top.setNodeName("事件流程");
        top.setParentId("-1");
        list.add(top);
        return AssemblyTreeUtils.assemblyTree(list);
    }
}
