package com.taiji.emp.alarm.service;

import com.taiji.emp.alarm.entity.NoticeFeedback;
import com.taiji.emp.alarm.repository.NoticeFbRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@AllArgsConstructor
public class NoticeFeedbackService extends BaseService<NoticeFeedback,String>{

    NoticeFbRepository repository;

    /**
     * 新增反馈
     */
    public NoticeFeedback create(NoticeFeedback entity){
        Assert.notNull(entity,"NoticeFeedback 不能为null");
        return repository.save(entity);
    }

}
