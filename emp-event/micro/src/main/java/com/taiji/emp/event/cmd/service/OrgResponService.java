package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.OrgRespon;
import com.taiji.emp.event.cmd.entity.OrgResponDetail;
import com.taiji.emp.event.cmd.repository.OrgResDetailRepository;
import com.taiji.emp.event.cmd.repository.OrgResponRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrgResponService extends BaseService<OrgRespon,String> {

    @Autowired
    OrgResponRepository repository;
    @Autowired
    OrgResDetailRepository detailRepository;

    //新增单个应急责任单位/人
    public OrgRespon createOne(OrgRespon entity){
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        List<OrgResponDetail> details = entity.getDetails();
        entity.setDetails(null);
        OrgRespon result = repository.createOne(entity);
        if(null!=result){
            if(null!=details && details.size()>0){
                List<OrgResponDetail> detailResultList = new ArrayList<>();
                for(OrgResponDetail detail:details){
                    detail.setDelFlag(DelFlagEnum.NORMAL.getCode());
                    detail.setOrgResponId(result.getId());
                    //由于主表与从表同时维护，因此在这里将createBy、updateBy与主表设置成一致
                    detail.setCreateBy(result.getCreateBy());
                    detail.setUpdateBy(result.getUpdateBy());

                    OrgResponDetail detailResult = detailRepository.save(detail);
                    if(null!=detailResult){
                        detailResultList.add(detailResult);
                    }
                }
                result.setDetails(detailResultList);
            }
        }
        return result;
    }

    //获取单个应急责任单位/人
    public OrgRespon findOne(String id){
        Assert.hasText(id,"id 不能为空字符串");
        return repository.findOne(id);
    }

    //更新单个应急责任单位/人
    public OrgRespon update(OrgRespon entity,String id){
        Assert.hasText(id,"id 不能为空字符串");
        List<OrgResponDetail> details = entity.getDetails();
        OrgRespon oldOrgRespon = findOne(id);
        List<OrgResponDetail> oldDetails = oldOrgRespon.getDetails();
        Map<String,OrgResponDetail> map = oldDetails.stream().collect(Collectors.toMap(temp -> temp.getId(), temp -> temp));
        if(null!=details && details.size()>0){ //先保存从表记录
            for(OrgResponDetail detail:details){
                String detailId = detail.getId();
                if(StringUtils.isEmpty(detailId)){ //新增从表
                    detail.setDelFlag(DelFlagEnum.NORMAL.getCode());
                    detail.setOrgResponId(entity.getId());
                    detail.setCreateBy(entity.getUpdateBy());
                }else{
                    if(map.containsKey(detailId)){
                        map.remove(detailId); //编辑的detail信息去除掉，剩余的为需要清除的部分
                    }
                }
                detail.setUpdateBy(entity.getUpdateBy());
                detailRepository.save(detail);
            }
        }
        if (!CollectionUtils.isEmpty(map)) {
            for(Map.Entry<String,OrgResponDetail> entry : map.entrySet()){
                OrgResponDetail deleteDetail = entry.getValue();
                detailRepository.deleteLogic(deleteDetail, DelFlagEnum.DELETE);//批量清理需要删除的记录(逻辑删除)
            }
//            List<OrgResponDetail> mapEntryValues = (List<OrgResponDetail>) map.values();
            /*if (!CollectionUtils.isEmpty(mapEntryValues)) {
                for (OrgResponDetail deleteDetail : mapEntryValues) {
                    detailRepository.deleteLogic(deleteDetail, DelFlagEnum.DELETE);//批量清理需要删除的记录(逻辑删除)
                }
            }*/
        }

        OrgRespon result = repository.update(entity,id);
        return result;
    }

    //删除应急责任单位/人 -- 级联删除从表信息
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        OrgRespon entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
        detailRepository.deleteDetailByOrgResponId(id,delFlagEnum);
    }

    //查询应急责任单位/人
    //params 参数key:emgOrgId
    public List<OrgRespon> findList(MultiValueMap<String,Object> params){
        return repository.findList(params);
    }

    //新增关联应急责任单位/人--- 启动预案，根据数字化批量生成
    public List<OrgRespon> createList(List<OrgRespon> list){
        if(null!=list && list.size()>0){
            List<OrgRespon> resultList = new ArrayList<>();
            for(OrgRespon temp:list){
                OrgRespon result = this.createOne(temp);
                if(null!=result){
                    resultList.add(result);
                }
            }
            return resultList;
        }else{
            return null;
        }
    }

}
