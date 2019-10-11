package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.SmsReceive;
import com.taiji.emp.base.repository.SmsReceiveRepository;
import com.taiji.emp.base.vo.SmsReceiveVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SmsReceiveService extends BaseService<SmsReceive,String> {
    @Autowired
    private SmsReceiveRepository smsReceiveRepository;

    public SmsReceive create(SmsReceive entity){
        Assert.notNull(entity,"Sms 对象不能为null");
        SmsReceive result = smsReceiveRepository.save(entity);
        return result;
    }

    public SmsReceive findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串");
        SmsReceive result = smsReceiveRepository.findOne(id);
        return result;
    }

    public SmsReceive update(SmsReceive entity){
        Assert.notNull(entity,"Sms对象不能为Null");
        SmsReceive result = smsReceiveRepository.save(entity);
        return result;
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为Null或空字符串");
        SmsReceiveVo vo = new SmsReceiveVo();
        vo.setSmsId(id);
        List<SmsReceive> result = smsReceiveRepository.findList(vo);
        smsReceiveRepository.delete(result);
    }

    public List<SmsReceive> findList(SmsReceiveVo smsReceiveVo){
        List<SmsReceive> result = smsReceiveRepository.findList(smsReceiveVo);
        return result;
    }
}
