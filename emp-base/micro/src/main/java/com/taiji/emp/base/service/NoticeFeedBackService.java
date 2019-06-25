package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.NoticeFeedBack;
import com.taiji.emp.base.repository.NoticeFeedBackRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@AllArgsConstructor
public class NoticeFeedBackService extends BaseService<NoticeFeedBack,String> {

    @Autowired
    private NoticeFeedBackRepository repository;

    /**
     * 通知公告反馈
     * @param entity
     * @return
     */
    public NoticeFeedBack create(NoticeFeedBack entity){
        Assert.notNull(entity,"noticeFeedBack对象不能为null");
        NoticeFeedBack result = repository.save(entity);
        return result;
    }

    /**
     * 通过receiveId查看反馈内容
     * @param receiveId
     * @return
     */
    public NoticeFeedBack findByReceiveId(String receiveId) {
        Assert.hasText(receiveId,"receiveId不能为null或空字符串!");
        NoticeFeedBack entity = repository.findByReceiveId(receiveId);
        return entity;
    }
}
