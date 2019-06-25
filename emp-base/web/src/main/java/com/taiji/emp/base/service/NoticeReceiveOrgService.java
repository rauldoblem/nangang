package com.taiji.emp.base.service;

import com.taiji.emp.base.common.constant.NoticeGlobal;
import com.taiji.emp.base.feign.NoticeClient;
import com.taiji.emp.base.feign.NoticeFeedBackClient;
import com.taiji.emp.base.feign.NoticeReceiveOrgClient;
import com.taiji.emp.base.feign.UtilsFeignClient;
import com.taiji.emp.base.searchVo.NoticeReceiveVo;
import com.taiji.emp.base.vo.*;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeReceiveOrgService extends BaseService {

    @Autowired
    private NoticeReceiveOrgClient noticeReceiveOrgClient;

    @Autowired
    private UtilsFeignClient utilsFeignClient;

    @Autowired
    private NoticeFeedBackClient feedBackClient;

    @Autowired
    private NoticeClient noticeClient;

    /**
     * 通知公告发送
     * @param saveVo
     */
    public List<NoticeReceiveOrgVo> create(NoticeReceiveOrgSaveVo saveVo, Principal principal){
        List<NoticeReceiveOrgVo> voList = new ArrayList<>();
        NoticeReceiveOrgVo vo = saveVo;
        List<String> orgIds = saveVo.getOrgIds();
        //获取用户姓名
        String userName = principal.getName();
        //发送人
        vo.setSendBy(userName);
        vo.setSendTime(utilsFeignClient.now().getBody());

        for (String orgId : orgIds){
            vo.setReceiveOrgId(orgId);
            vo.setReceiveOrgName(getOrgNameById(orgId).getOrgName());
            //是否反馈：0未反馈，1已反馈
            vo.setIsFeedback(NoticeGlobal.INFO_STATUS_UNFEEDBACK);
            ResponseEntity<NoticeReceiveOrgVo> resultVo = noticeReceiveOrgClient.create(vo);
            NoticeReceiveOrgVo noticeReceiveOrgVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            voList.add(noticeReceiveOrgVo);
        }
        return voList;
    }

    /**
     * 通知公告接受状态查看
     * @param id
     * @return
     */
    public NoticeReceiveOrgVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<NoticeReceiveOrgVo> resultVo = noticeReceiveOrgClient.findOne(id);
        NoticeReceiveOrgVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 通知公告反馈
     * @param vo
     */
    public void updateReceiveStatus(NoticeReceiveOrgVo vo){
        //反馈状态
        vo.setIsFeedback(NoticeGlobal.INFO_STATUS_RECEIVE_FEEDBACK);
        noticeReceiveOrgClient.update(vo,vo.getId());
    }

    /**
     * 接受通知条件查询列表----分页
     * @param noticeReceiveVo
     * @return
     */
    public RestPageImpl<NoticeReceiveOrgVo> findNoticeReceivePage(NoticeReceiveVo noticeReceiveVo) {
        Assert.notNull(noticeReceiveVo,"noticeReceiveVo 不能为null");
        ResponseEntity<RestPageImpl<NoticeReceiveOrgVo>> resultVo = noticeReceiveOrgClient.findNoticeReceivePage(noticeReceiveVo);
        RestPageImpl<NoticeReceiveOrgVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据 noticeRecId 查询通知公告反馈信息
     * @param noticeRecId
     * @return
     */
    public NoticeReceiveOrgVo findRecOne(String noticeRecId) {
        Assert.hasText(noticeRecId,"noticeRecId不能为空字符串");
        ResponseEntity<NoticeReceiveOrgVo> resultVo = noticeReceiveOrgClient.findRecOne(noticeRecId);
        NoticeReceiveOrgVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public NoticeReceiveOrgResultVo findByNoticeRecId(String noticeRecId) {
        Assert.hasText(noticeRecId,"noticeRecId不能为空字符串");
        NoticeReceiveOrgResultVo orgResultVo = new NoticeReceiveOrgResultVo();
        ResponseEntity<NoticeReceiveOrgVo> resultVo = noticeReceiveOrgClient.findByNoticeRecId(noticeRecId);
        NoticeReceiveOrgVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        ResponseEntity<NoticeVo> entity = noticeClient.findOne(vo.getNoticeId());
        NoticeVo noticeVo = ResponseEntityUtils.achieveResponseEntityBody(entity);
        orgResultVo.setId(vo.getId());
        orgResultVo.setNoticeId(vo.getNoticeId());
        orgResultVo.setTitle(vo.getTitle());
        orgResultVo.setNoticeTypeId(vo.getNoticeTypeId());
        orgResultVo.setNoticeTypeName(vo.getNoticeTypeName());
        orgResultVo.setContent(vo.getContent());
        orgResultVo.setBuildOrgId(vo.getBuildOrgId());
        orgResultVo.setBuildOrgName(vo.getBuildOrgName());
        orgResultVo.setSendBy(vo.getSendBy());
        orgResultVo.setReceiveOrgId(vo.getReceiveOrgId());
        orgResultVo.setIsFeedback(vo.getIsFeedback());
        orgResultVo.setSendTime(vo.getSendTime());
        orgResultVo.setCreateTime(noticeVo.getCreateTime());
        return orgResultVo;
    }

    /**
     * 通知公告接受状态查看
     * @param noticeId
     * @return
     */
    public List<NoticeReceiveOrgVo> findList(String noticeId) {
        Assert.hasText(noticeId,"noticeId不能为空字符串");
        ResponseEntity<List<NoticeReceiveOrgVo>> resultVo = noticeReceiveOrgClient.findList(noticeId);
        List<NoticeReceiveOrgVo> voList = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        for (NoticeReceiveOrgVo vo : voList){
            String receiveId = vo.getId();
            ResponseEntity<NoticeFeedBackVo> feedBackVo = feedBackClient.findByReceiveId(receiveId);
            NoticeFeedBackVo entity = ResponseEntityUtils.achieveResponseEntityBody(feedBackVo);
            if(null != entity) {
                vo.setFeedbackContent(entity.getContent());
                vo.setFeedbackBy(entity.getFeedbackBy());
                vo.setFeedbackTime(entity.getFeedbackTime());
            }
        }
        return voList;
    }
}
