package com.taiji.emp.base.feign;

import com.taiji.emp.base.searchVo.NoticeReceiveVo;
import com.taiji.emp.base.vo.NoticeReceiveOrgVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知公告反馈关系 feign 接口服务类
 * @author qzp-pc
 * @date 2018年10月22日17:36:37
 */
@FeignClient(value = "micro-base-noticeReceiveOrg")
public interface INoticeReceiveOrgRestService {
    /**
     * 通知公告发送 NoticeReceiveOrgVo 不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<NoticeReceiveOrgVo> create(@RequestBody NoticeReceiveOrgVo vo);

    /**
     * 通知公告查看反馈
     * @param id id不能为空
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<NoticeReceiveOrgVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新通知公告 NoticeReceiveOrgVo不能为空
     * @param vo
     * @param id 要更新 NoticeReceiveOrgVo id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<NoticeReceiveOrgVo> update(@RequestBody NoticeReceiveOrgVo vo, @PathVariable(value = "id") String id);

    /**
     * 接受通知条件查询列表----分页
     * @param noticeReceiveVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/receive/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<NoticeReceiveOrgVo>> findNoticeReceivePage(@RequestBody NoticeReceiveVo noticeReceiveVo);

    /**
     * 根据 noticeRecId 查询通知公告反馈信息
     * @param noticeRecId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/rec/one/{noticeRecId}")
    @ResponseBody
    ResponseEntity<NoticeReceiveOrgVo> findRecOne(@PathVariable(value = "noticeRecId") String noticeRecId);

    @RequestMapping(method = RequestMethod.GET,path = "/find/by/noticeRecId/{noticeRecId}")
    @ResponseBody
    ResponseEntity<NoticeReceiveOrgVo> findByNoticeRecId(@PathVariable(value = "noticeRecId") String noticeRecId);

    /**
     * 通知公告接受状态查看
     * @param noticeId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/list/{noticeId}")
    @ResponseBody
    ResponseEntity<List<NoticeReceiveOrgVo>> findList(@PathVariable(value = "noticeId") String noticeId);
}
