package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.PlanOrgResponDetail;
import com.taiji.emp.res.repository.PlanOrgResponDetailRepository;
import com.taiji.emp.res.searchVo.planOrgResponDetail.PlanOrgResponDetailListVo;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PlanOrgResponDetailService extends BaseService<PlanOrgResponDetail,String>{

    @Autowired
    private PlanOrgResponDetailRepository repository;

    public PlanOrgResponDetail createOrUpdate(PlanOrgResponDetailListVo vo,String id){
        Assert.notNull(vo,"PlanOrgResponDetailListVo 对象不能为 null");
        return repository.save(vo,id);
    }

    public PlanOrgResponDetail findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deleteLogic(List<String> ids, DelFlagEnum delFlagEnum){
        Assert.notNull(ids,"ids 集合不能为 null");
        for (String id :ids) {
            PlanOrgResponDetail entity = repository.findOne(id);
            Assert.notNull(entity,"entity不能为null");
            repository.deleteLogic(entity,delFlagEnum);
        }

    }

    //不分页list查询
    public List<PlanOrgResponDetail> findList(PlanOrgResponDetailVo planOrgResponDetailVo){
        return repository.findList(planOrgResponDetailVo);
    }

    public List<PlanOrgResponDetail> findList(PlanOrgResponDetailListVo planOrgResponDetailListVo){
        return repository.findList(planOrgResponDetailListVo);
    }

//    @Transactional
    public List<PlanOrgResponDetail> updateDetail(List<PlanOrgResponDetail> planOrgResponDetail,String id){
        List<PlanOrgResponDetail> results = new ArrayList<>();

        PlanOrgResponDetail result = null;
        //根据传入orgResponId获取要修改详情的id，循环更新
        PlanOrgResponDetailVo detailVo = new PlanOrgResponDetailVo();
        detailVo.setOrgResponId(id);
        List<PlanOrgResponDetail> details = repository.findList(detailVo);

        List<String> detailIds = details.stream().map(temp -> temp.getId()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(planOrgResponDetail)) {
            for (PlanOrgResponDetail detail : planOrgResponDetail) {
                String detailId = detail.getId();
                if (!StringUtils.isEmpty(detailId)){
                    if(detailIds.contains(detailId)){
                        //删除掉编辑后的那条数据
                        detailIds.remove(detailId);
                    }
                }
                detail.setOrgResponId(id);
                detail.setDelFlag(DelFlagEnum.NORMAL.getCode());
                result = repository.save(detail);
                results.add(result);
            }
        }
        if (detailIds.size() > 0){
            //删除
            for (String delId:detailIds) {
                PlanOrgResponDetail tempDetail = repository.findOne(delId);
                tempDetail.setDelFlag(DelFlagEnum.DELETE.getCode());
                repository.save(tempDetail);
            }
        }
        return results;
    }

}
