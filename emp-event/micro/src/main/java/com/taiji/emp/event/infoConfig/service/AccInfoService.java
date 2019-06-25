package com.taiji.emp.event.infoConfig.service;

import com.taiji.emp.event.infoConfig.entity.AcceptInform;
import com.taiji.emp.event.infoConfig.repository.AccInfoRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AccInfoService extends BaseService<AcceptInform,String> {

    @Autowired
    AccInfoRepository accInfoRepository;

    //新增通知单位
    public AcceptInform create(AcceptInform entity){
        Assert.notNull(entity,"AcceptInform 对象不能为 null");
        return accInfoRepository.save(entity);
    }

    //通过id获取单个通知单位
    public AcceptInform findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return accInfoRepository.findOne(id);
    }

    //更新通知单位
    public AcceptInform update(AcceptInform entity,String id){
        Assert.notNull(entity,"AcceptInform 对象不能为 null");
        Assert.hasText(id,"id 不能为null或空字符串");
        return accInfoRepository.save(entity);
    }
    //删除通知单位
    public void delete(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        accInfoRepository.delete(id);
    }

    //根据条件查询通知单位list
    //key:eventTypeId
    public List<AcceptInform> searchAll(MultiValueMap<String,Object> params){
        return accInfoRepository.findList(params);
    }

}
