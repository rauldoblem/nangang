package com.taiji.emp.base.controller;

import com.taiji.emp.base.searchVo.NoticeReceiveVo;
import com.taiji.emp.base.service.NoticeFeedBackService;
import com.taiji.emp.base.service.NoticeReceiveOrgService;
import com.taiji.emp.base.service.NoticeService;
import com.taiji.emp.base.vo.*;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/notices")
public class NoticeController extends BaseController {

    @Autowired
    NoticeService service;

    @Autowired
    NoticeReceiveOrgService noticeReceiveOrgService;

    @Autowired
    NoticeFeedBackService noticeFeedBackService;

    /**
     * 新增通知公告信息
     * @param noticeVo
     * @param principal
     * @return
     */
    @PostMapping
    public ResultEntity createNotice(
            @Validated
            @NotNull(message = "noticeVo不能为null")
            @RequestBody NoticeSaveVo noticeVo, Principal principal){
        service.create(noticeVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id删除某条通知公告信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteNotice(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 修改通知公告信息
     * @param noticeVo
     * @param principal
     * @return
     */
    @PutMapping("/{id}")
    public ResultEntity updateNotice(
            @NotNull(message = "noticeVo不能为null")
            @RequestBody NoticeSaveVo noticeVo
            , Principal principal,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        noticeVo.getNotice().setId(id);
        service.update(noticeVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取某条通知公告信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findNoticeById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        NoticeVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据条件查询通知公告列表-分页
     * params参数key为title,noticeTypeId,sendStartTime,sendEndTime,sendStatus,orgId(可选)
     * page,size
     * @param params
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody Map<String,Object> params,Principal principal){
        //验证分页参数
        if (params.containsKey("page") && params.containsKey("size")){
            RestPageImpl<NoticeVo> pageVo = service.findPage(params,principal);
            return ResultUtils.success(pageVo);
        }else {
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 通知公告发送
     * @param noticeReceiveOrgSaveVo
     * @param principal
     * @return
     */
    @PostMapping("/send")
    public ResultEntity createNoticeReceiveOrg(
            @NotNull(message = "noticeReceiveOrgSaveVo不能为null")
            @RequestBody NoticeReceiveOrgSaveVo noticeReceiveOrgSaveVo
            ,Principal principal){
        NoticeVo noticeVo = service.findOne(noticeReceiveOrgSaveVo.getNoticeId());
        if (null != noticeVo) {
            service.updateSendStatus(noticeVo);
            List<NoticeReceiveOrgVo> voList = noticeReceiveOrgService.create(noticeReceiveOrgSaveVo, principal);
            List<NoticeReceiveOrgVo> resultList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(voList)){
                for (NoticeReceiveOrgVo vo : voList){
                    String id = vo.getId();
                    NoticeReceiveOrgVo receiveOrgVo = noticeReceiveOrgService.findOne(id);
                    resultList.add(receiveOrgVo);
                }
            }
            //发送系统消息
            service.sendSystemMsg(resultList);
            return ResultUtils.success();
        }else {
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 接受通知条件查询列表----分页
     * @param noticeReceiveVo
     * @return
     */
    @PostMapping(path = "/recSearch")
    public ResultEntity findNoticeReceivePage(@RequestBody NoticeReceiveVo noticeReceiveVo){
        RestPageImpl<NoticeReceiveOrgVo> pageVo = noticeReceiveOrgService.findNoticeReceivePage(noticeReceiveVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 通知公告接受状态查看
     * @param noticeId
     * @return
     */
    @GetMapping(path = "/viewFeedbacks/{noticeId}")
    public ResultEntity findReceiveStatusByNoticeId(
            @NotEmpty(message = "noticeId不能为空")
            @PathVariable(name = "noticeId")String noticeId){
        List<NoticeReceiveOrgVo> voList = noticeReceiveOrgService.findList(noticeId);
        return ResultUtils.success(voList);
    }

    /**
     * 接受通知公告查看
     * @param noticeRecId
     * @return
     */
    @GetMapping(path = "/viewNoticeRec/{noticeRecId}")
    public ResultEntity viewNoticeRec(
            @NotEmpty(message = "noticeRecId不能为空")
            @PathVariable(value = "noticeRecId")
            String noticeRecId){
        //查询
        NoticeReceiveOrgResultVo entityVo = noticeReceiveOrgService.findByNoticeRecId(noticeRecId);
        return ResultUtils.success(entityVo);
    }

    /**
     * 查看通知公告反馈
     * @param noticeRecId
     * @return
     */
    @GetMapping(path = "/viewFeedback/{noticeRecId}")
    public ResultEntity viewNotice(
            @NotEmpty(message = "noticeRecId不能为空")
            @PathVariable(value = "noticeRecId")
            String noticeRecId){
        NoticeFeedBackVo entityVo = noticeFeedBackService.findByNoticeRecId(noticeRecId);
        return ResultUtils.success(entityVo);
    }

    /**
     * 通知公告反馈
     * @return
     */
    @PostMapping(path = "/feedback")
    public ResultEntity feedback(
            @NotNull(message = "noticeFeedBackVo不能为null")
            @RequestBody NoticeFeedBackVo noticeFeedBackVo,
            Principal principal){
        NoticeReceiveOrgVo vo = noticeReceiveOrgService.findOne(noticeFeedBackVo.getNoticeRecId());
        noticeReceiveOrgService.updateReceiveStatus(vo);
        noticeFeedBackService.create(noticeFeedBackVo,principal);
        //反馈发送系统消息
        service.sendSystemFeedBackMsg(vo,noticeFeedBackVo);
        return ResultUtils.success();
    }
}
