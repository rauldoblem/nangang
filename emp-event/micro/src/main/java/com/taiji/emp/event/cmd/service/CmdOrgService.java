package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.CmdOrg;
import com.taiji.emp.event.cmd.repository.CmdOrgRepository;
import com.taiji.emp.event.cmd.repository.OrgResponRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class CmdOrgService extends BaseService<CmdOrg,String> {

    @Autowired
    private CmdOrgRepository repository;
    @Autowired
    private OrgResponRepository orgResponRepository;

    //新增单个应急组织机构
    public CmdOrg createOne(CmdOrg entity){
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
//        String planOrgId = entity.getPlanOrgId();
        CmdOrg result = repository.createOne(entity);
//        result.setPlanOrgId(planOrgId); //planOrgId赋值并回传  -- 用于预案数字化应急组织转化为处置方案应急组织
        return result;
    }

    //获取单个应急组织机构信息
    public CmdOrg findOne(String id){
        Assert.hasText(id,"id不能为空");
        return repository.findOne(id);
    }


    //更新单个应急组织机构
    public CmdOrg update(CmdOrg entity,String id){
        Assert.hasText(id,"id不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.update(entity,id);
    }

    //删除应急组织机构（含该机构下关联的责任单位/人员）
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为空");
        CmdOrg entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
        //级联删除该机构下关联的责任单位/人员
        orgResponRepository.deleteOrgResponByOrg(id,delFlagEnum);
    }

    //查询应急组织机构树
    public List<CmdOrg> findList(MultiValueMap<String, Object> params){
        return repository.findList(params);
    }

    //新增关联应急组织机构--- 启动预案，根据数字化批量生成
    public List<CmdOrg> createList(List<CmdOrg> list){
        List<CmdOrg> resultList = new ArrayList<>();
        List<CmdOrg> createList = new ArrayList<>();
        if(null!=list && list.size()>0){
            Map<String,String> planOrgIdCmdOrgIdMap = new HashMap<>();//Map<planOrgId,CmdOrgId> ,入库后产生CmdOrgId，需要更改 parentId
            for(CmdOrg temp : list){
                CmdOrg result = this.createOne(temp);
                if(null!=result){
                    planOrgIdCmdOrgIdMap.put(result.getPlanOrgId(),result.getId());
                    createList.add(result);
                }
            }
            for(CmdOrg createTemp : createList){
                String parentId = createTemp.getParentId();
                if(planOrgIdCmdOrgIdMap.containsKey(parentId)){
                    createTemp.setParentId(planOrgIdCmdOrgIdMap.get(parentId));  //重新复制parentId
                    CmdOrg updateResult = repository.save(createTemp);
                    resultList.add(updateResult);
                }else{
                    resultList.add(createTemp);
                }
            }
        }
        return resultList;
    }
}
