package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.CaseEntity;
import com.taiji.emp.res.repository.CasesRepository;
import com.taiji.emp.res.searchVo.caseVo.CasePageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class CaseService extends BaseService<CaseEntity,String> {

    private CasesRepository repository;

    public CaseEntity create(CaseEntity entity){
        Assert.notNull(entity,"Case 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        CaseEntity result = repository.save(entity);
        return result;
    }

    public CaseEntity update(CaseEntity entity){
        Assert.notNull(entity,"Case 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        CaseEntity result = repository.save(entity);
        return result;
    }

    public CaseEntity findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        CaseEntity result = repository.findOne(id);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        CaseEntity entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    public Page<CaseEntity> findPage(CasePageVo casePageVo, Pageable pageable){
        Page<CaseEntity> result = repository.findPage(casePageVo,pageable);
        return result;
    }

    public List<CaseEntity> findList(CasePageVo casePageVo){
        List<CaseEntity> result = repository.findList(casePageVo);
        return result;
    }

}
