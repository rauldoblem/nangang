package com.taiji.emp.base.feign;

import com.taiji.emp.base.vo.NoticeFeedBackVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 通知公告反馈 feign 接口服务类
 * @author qzp-pc
 * @date 2018年10月23日15:12:37
 */
@FeignClient(value = "micro-base-noticeFeedBack")
public interface INoticeFeedBackRestService {

    /**
     * 通知公告反馈 NoticeFeedBackVo 不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<NoticeFeedBackVo> create(@RequestBody NoticeFeedBackVo vo);

    /**
     * 通过receiveId查看反馈内容
     * @param receiveId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/by/receiveId/{receiveId}")
    @ResponseBody
    ResponseEntity<NoticeFeedBackVo> findByReceiveId(@PathVariable(value = "receiveId") String receiveId);


}
