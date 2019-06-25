package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.Fax;
import com.taiji.emp.base.repository.FaxRepository;
import com.taiji.emp.base.searchVo.fax.FaxListVo;
import com.taiji.emp.base.searchVo.fax.FaxPageVo;
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
public class FaxService extends BaseService<Fax,String > {
    @Autowired
    private FaxRepository faxRepository;

    public Fax create(Fax entity){
        Assert.notNull(entity,"Fax 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Fax result = faxRepository.save(entity);
        return result;
    }

    public Fax update(Fax entity){
        Assert.notNull(entity,"Fax 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Fax result = faxRepository.save(entity);
        return result;
    }

    public Fax findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        Fax result = faxRepository.findOne(id);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Fax entity = faxRepository.findOne(id);
        faxRepository.deleteLogic(entity,delFlagEnum);
    }

    //提供给controller使用的 分页list查询方法
    public Page<Fax> findPage(FaxPageVo faxPageVo, Pageable pageable){
        Page<Fax> result = faxRepository.findPage(faxPageVo,pageable);
        return result;
    }

    public List<Fax> findList(FaxListVo knowListVo){
        List<Fax> result = faxRepository.findList(knowListVo);
        return result;
    }

}
