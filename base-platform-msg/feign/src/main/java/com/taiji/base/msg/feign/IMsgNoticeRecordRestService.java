package com.taiji.base.msg.feign;

import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IMsgNoticeRecordRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 9:39</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-msg-notice-record")
public interface IMsgNoticeRecordRestService {

    /**
     * 根据参数获取MsgNoticeRecordVo多条记录。
     * <p>
     * <p>
     * params参数key为receiverId 当前用户Id（必选）。
     *
     * @param params 查询参数集合
     *
     * @return ResponseEntity<List<MsgNoticeRecordVo>>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<MsgNoticeRecordVo>> findAll(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 更新多条MsgNoticeRecordVo的readFlag为已读，vos不能为空。
     *
     * @param vos 多条消息通知
     *
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/updateList")
    @ResponseBody
    ResponseEntity<Void> updateMsgNoticeRecordList(@RequestBody List<MsgNoticeRecordVo> vos);

    /**
     * 更新MsgNoticeRecordVo的readFlag为已读，vo不能为空。
     *
     * @param vo 单条消息通知
     * @param id 消息id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<MsgNoticeRecordVo> updateMsgNoticeRecord(@RequestBody MsgNoticeRecordVo vo, @PathVariable(value = "id") String id);
}
