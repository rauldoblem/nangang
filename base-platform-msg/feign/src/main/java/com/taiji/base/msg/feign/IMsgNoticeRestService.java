package com.taiji.base.msg.feign;

import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.MsgTypeVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IMsgNoticeRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 9:38</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-msg-notice")
public interface IMsgNoticeRestService {
    /**
     * 新增MsgNoticeVo，MsgNoticeVo不能为空。
     *
     * @param vo 消息vo
     *
     * @return ResponseEntity<MsgNoticeVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<MsgNoticeVo> create(@RequestBody MsgNoticeVo vo);
}
