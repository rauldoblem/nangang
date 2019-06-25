package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.Sms;
import com.taiji.emp.base.repository.SmsRepository;
import com.taiji.emp.base.searchVo.sms.SmsListVo;
import com.taiji.emp.base.searchVo.sms.SmsPageVo;
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
public class SmsService extends BaseService<Sms,String> {
    @Autowired
    private SmsRepository smsRepository;

    public Sms create(Sms entity){
        Assert.notNull(entity,"Sms 对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Sms result = smsRepository.save(entity);
        return result;
    }

    public Sms findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串");
        Sms result = smsRepository.findOne(id);
        return result;
    }

    public Sms update(Sms entity){
        Assert.notNull(entity,"Sms对象不能为Null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Sms result = smsRepository.save(entity);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为Null或空字符串");
        Sms entity = smsRepository.findOne(id);
        smsRepository.deleteLogic(entity,delFlagEnum);
    }

    public Page<Sms> findPage(SmsPageVo smsPageVo, Pageable pageable){
        Page<Sms> result = smsRepository.findPage(smsPageVo,pageable);
        return result;
    }
    public List<Sms> findList(SmsListVo smsListVo){
        List<Sms> result = smsRepository.findList(smsListVo);
        return result;
    }
}
