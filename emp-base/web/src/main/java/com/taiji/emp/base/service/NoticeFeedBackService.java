package com.taiji.emp.base.service;

import com.taiji.emp.base.feign.NoticeFeedBackClient;
import com.taiji.emp.base.feign.UtilsFeignClient;
import com.taiji.emp.base.vo.NoticeFeedBackVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;

@Service
public class NoticeFeedBackService extends BaseService {

    @Autowired
    private NoticeFeedBackClient noticeFeedBackClient;

    @Autowired
    private UtilsFeignClient utilsFeignClient;

    /**
     * 通知公告反馈
     * @param vo
     * @param principal
     */
    public void create(NoticeFeedBackVo vo, Principal principal){
        //获取用户姓名
        String userName = principal.getName();
        //反馈人
        vo.setFeedbackBy(userName);
        //反馈时间
        vo.setFeedbackTime(utilsFeignClient.now().getBody());
        noticeFeedBackClient.create(vo);
    }

    /**
     * 查看通知公告反馈
     * @param receiveId
     * @return
     */
    public NoticeFeedBackVo findByNoticeRecId(String receiveId) {
        Assert.hasText(receiveId,"receiveId不能为空字符串");
        ResponseEntity<NoticeFeedBackVo> resultVo = noticeFeedBackClient.findByReceiveId(receiveId);
        NoticeFeedBackVo noticeFeedBackVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return noticeFeedBackVo;
    }
}
