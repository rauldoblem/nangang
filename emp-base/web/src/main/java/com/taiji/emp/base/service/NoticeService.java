package com.taiji.emp.base.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.common.constant.NoticeGlobal;
import com.taiji.emp.base.feign.DocAttClient;
import com.taiji.emp.base.feign.NoticeClient;
import com.taiji.emp.base.feign.UtilsFeignClient;
import com.taiji.emp.base.msgService.InfoMsgService;
import com.taiji.emp.base.vo.*;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService extends BaseService {

    @Autowired
    private NoticeClient noticeClient;

    @Autowired
    private UtilsFeignClient utilsFeignClient;

    @Autowired
    private InfoMsgService infoMsgService;

    @Autowired
    private DocAttClient docAttClient;

    /**
     * 新增通知公告信息
     * @param vo
     * @param principal
     */
    public void create(NoticeSaveVo vo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userVoProfile = userVo.getProfile();
        String userName = principal.getName();
        NoticeVo notice = vo.getNotice();
        //创建人
        notice.setCreateBy(userName);
        //更新人
        notice.setUpdateBy(userName);

        //下发状态 : 0未下发
        notice.setSendStatus(NoticeGlobal.INFO_STATUS_UNSEND);

        String noticeTypeId = notice.getNoticeTypeId();
        Assert.hasText(noticeTypeId,"通知类型ID 不能为空字符串");
        notice.setNoticeTypeName(getItemNameById(noticeTypeId));

        String orgId = userVoProfile.getOrgId();
        //创建单位ID
        notice.setBuildOrgId(orgId);
        //创建单位名称
        notice.setBuildOrgName(getOrgNameById(orgId).getOrgName());

        //通知公告实体入库
        ResponseEntity<NoticeVo> responseEntity = noticeClient.create(notice);
        NoticeVo noticeVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);

        //附件
        String id = noticeVo.getId();
        List<String> fileIds = vo.getFileIds();
        List<String> fileDeleteIds = vo.getFileDeleteIds();
        docAttClient.saveDocEntity(new DocEntityVo(id,fileIds,fileDeleteIds));
    }

    /**
     * 根据id逻辑删除某条通知公告信息
     * @param id
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = noticeClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 更新通知公告信息
     * @param vo
     * @param principal
     */
    public void update(NoticeSaveVo vo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        //获取用户姓名
        String userName = principal.getName();
        NoticeVo notice = vo.getNotice();
        //更新人
        notice.setUpdateBy(userName);
        //下发状态 : 0未下发
        notice.setSendStatus(NoticeGlobal.INFO_STATUS_UNSEND);
        String noticeTypeId = notice.getNoticeTypeId();
        Assert.hasText(noticeTypeId,"通知类型ID 不能为空字符串");
        NoticeVo tempVo = this.findOne(notice.getId());
        if (!noticeTypeId.equals(tempVo.getNoticeTypeId())){
            //通知类型有修改才替换
            notice.setNoticeTypeName(getItemNameById(noticeTypeId));
        }
        String orgId = userProfileVo.getOrgId();
        //创建单位ID
        notice.setBuildOrgId(orgId);
        //创建单位名称
        notice.setBuildOrgName(getOrgNameById(orgId).getOrgName());
        //更新通知公告实体入库
        ResponseEntity<NoticeVo> responseEntity = noticeClient.update(notice, notice.getId());
        NoticeVo noticeVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);

        //附件
        String id = noticeVo.getId();
        List<String> fileIds = vo.getFileIds();
        List<String> fileDeleteIds = vo.getFileDeleteIds();
        docAttClient.saveDocEntity(new DocEntityVo(id,fileIds,fileDeleteIds));
    }

    /**
     * 根据id获取某条通知公告信息
     * @param id
     * @return
     */
    public NoticeVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<NoticeVo> resultVo = noticeClient.findOne(id);
        NoticeVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

//        ResponseEntity<List<DocAttVo>> list = docAttClient.findList(id);
//        List<DocAttVo> docAttVos = ResponseEntityUtils.achieveResponseEntityBody(list);
//        List<String> filesList = docAttVos.stream().map(temp -> temp.getId()).collect(Collectors.toList());
//        vo.setFileIds(filesList);
        return vo;
    }

    /**
     * 根据条件查询通知公告列表-分页
     * params参数key为title,noticeTypeId,sendStartTime,sendEndTime,sendStatus,orgId(可选)
     * page,size
     * @param params
     * @return
     */
    public RestPageImpl<NoticeVo> findPage(Map<String,Object> params,Principal principal){
        Assert.notNull(params,"params 不能为空");
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userVoProfile = userVo.getProfile();
        String orgId = userVoProfile.getOrgId();
        params.put("buildOrgId",orgId);
        ResponseEntity<RestPageImpl<NoticeVo>> resultVo = noticeClient.findPage(convertMap2MultiValueMap(params));
        RestPageImpl<NoticeVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 通知公告发送
     * @param vo
     */
    public void updateSendStatus(NoticeVo vo){
        //下发时间
        vo.setSendTime(utilsFeignClient.now().getBody());
        //下发状态 : 1已下发
        vo.setSendStatus(NoticeGlobal.INFO_STATUS_SEND);
        //更新通知公告实体入库
        noticeClient.update(vo,vo.getId());
    }

    /**
     * 公告“发送”后,发送系统消息
     * @param voList
     */
    public void sendSystemMsg(List<NoticeReceiveOrgVo> voList) {
        Assert.notNull(voList,"voList不能为空");
        //发送系统消息
        infoMsgService.sendInfoMsg(voList);
    }

    /**
     * 反馈发送系统消息
     * @param vo
     * @param noticeFeedBackVo
     */
    public void sendSystemFeedBackMsg(NoticeReceiveOrgVo vo, NoticeFeedBackVo noticeFeedBackVo) {
        Assert.notNull(vo,"vo不能为空");
        Assert.notNull(noticeFeedBackVo,"noticeFeedBackVo不能为空");
        //反馈发送系统消息
        infoMsgService.returnInfoMsg(vo,noticeFeedBackVo);
    }
}
