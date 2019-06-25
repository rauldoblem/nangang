package com.taiji.base.msg.service;

import com.taiji.base.msg.feign.MsgNoticeClient;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * <p>Title:MsgNoticeService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 15:02</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@Service
public class MsgNoticeService extends BaseService {

    MsgNoticeClient client;

    /**
     * 创建消息信息
     * @param noticeVo
     */
    public void addNotice(MsgNoticeVo noticeVo){
        Assert.notNull(noticeVo, "noticeVo不能为null值。");

        ResponseEntity<MsgNoticeVo> result = client.create(noticeVo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }
}
