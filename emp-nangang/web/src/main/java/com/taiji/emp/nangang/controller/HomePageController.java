package com.taiji.emp.nangang.controller;

import com.taiji.emp.base.searchVo.NoticeReceiveVo;
import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.base.vo.NoticeReceiveOrgResultVo;
import com.taiji.emp.base.vo.NoticeReceiveOrgVo;
import com.taiji.emp.base.vo.NoticeVo;
import com.taiji.emp.duty.searchVo.SchedulingSearchVo;
import com.taiji.emp.duty.searchVo.SchedulingsListVo;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.emp.nangang.service.HomePageService;
import com.taiji.emp.nangang.vo.DocAttsVo;
import com.taiji.emp.nangang.vo.NoticesVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/appNg")
public class HomePageController extends BaseController {

    @Autowired
    private HomePageService service;

    /**
     * 根据时间获取对应的值班人员列表
     * @param searchDate
     * @return
     */
    @GetMapping(path = "/getDutysByDate")
    public ResultEntity findDutysByDate(
            @RequestParam(value = "searchDate")
            @NotEmpty(message = "searchDate不能为空")String searchDate,
            OAuth2Authentication principal){
        SchedulingSearchVo listVo = service.findDutysByDate(searchDate,principal);
        return ResultUtils.success(listVo);
    }

    /**
     * 按月份获取当前用户的值班信息
     * @param searchMonth
     * @return
     */
    @GetMapping(path = "/getMonthSchedulingsForPerson")
    public ResultEntity findMonthSchedulings(
            @RequestParam(value = "searchMonth")
            @NotEmpty(message = "searchMonth不能为空")String searchMonth,
            OAuth2Authentication principal){
        List<SchedulingsListVo> listVo = service.findMonthSchedulings(searchMonth,principal);
        return ResultUtils.success(listVo);
    }

    /**
     * 接受通知条件查询列表----分页
     * @param noticeReceiveVo
     * @return
     */
    @PostMapping(path = "/noticeRecSearch")
    public ResultEntity findNoticeReceivePage(@RequestBody NoticeReceiveVo noticeReceiveVo,OAuth2Authentication principal){
        RestPageImpl<NoticeReceiveOrgVo> pageVo = service.findNoticeReceivePage(noticeReceiveVo,principal);
        return ResultUtils.success(pageVo);
    }

    /**
     * 根据条件查询通知公告列表-分页
     * params参数key为title,noticeTypeId,sendStartTime,sendEndTime,sendStatus,orgId(可选)
     * page,size
     * @param params
     * @return
     */
    @PostMapping(path = "/noticesSearch")
    public ResultEntity findPage(@RequestBody Map<String,Object> params, OAuth2Authentication principal){
        //验证分页参数
        if (params.containsKey("page") && params.containsKey("size")){
            RestPageImpl<NoticeVo> pageVo = service.findPage(params,principal);
            return ResultUtils.success(pageVo);
        }else {
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
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
        NoticeReceiveOrgVo entityVo = service.findByNoticeRecId(noticeRecId);
        List<DocAttsVo> docAtts = new ArrayList<DocAttsVo>();
        //查询附件
        List<DocAttVo> listVos = service.findDocAtts(entityVo.getId());
        for (DocAttVo vos:listVos) {
            DocAttsVo docAttsVo = new DocAttsVo();
            docAttsVo.setId(vos.getId());
            docAttsVo.setFileName(vos.getName());
            docAttsVo.setFileType(vos.getType());
            docAttsVo.setLocation(vos.getLocation());
            docAtts.add(docAttsVo);
        }

        NoticesVo noticesVo = new NoticesVo();
        noticesVo.setNoticeRec(entityVo);
        noticesVo.setDocAtts(docAtts);

        return ResultUtils.success(noticesVo);
    }

    /**
     * 根据id获取某条通知公告信息
     * @param id
     * @return
     */
    @GetMapping(path = "/viewNotices/{id}")
    public ResultEntity findNoticeById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        NoticeVo vo = service.findOne(id);
        List<DocAttsVo> docAtts = new ArrayList<DocAttsVo>();
        //查询附件
        List<DocAttVo> listVos = service.findDocAtts(id);
        for (DocAttVo vos:listVos) {
            DocAttsVo docAttsVo = new DocAttsVo();
            docAttsVo.setId(vos.getId());
            docAttsVo.setFileName(vos.getName());
            docAttsVo.setFileType(vos.getType());
            docAttsVo.setLocation(vos.getLocation());
            docAtts.add(docAttsVo);
        }

        NoticesVo noticesVo = new NoticesVo();
        noticesVo.setNotice(vo);
        noticesVo.setDocAtts(docAtts);
        return ResultUtils.success(noticesVo);
    }

    /**
     *根据id获取单个事件信息
     * @param id
     * @return ResultEntity<EventVo>
     */
    @GetMapping(path = "/events/{id}")
    public ResultEntity<EventVo> findEventById(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id){
        EventVo resultVo = service.findEventById(id);
        return ResultUtils.success(resultVo);
    }

}
