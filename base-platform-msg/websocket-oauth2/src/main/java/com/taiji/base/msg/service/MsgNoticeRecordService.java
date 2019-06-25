package com.taiji.base.msg.service;

import com.taiji.base.msg.feign.MsgNoticeRecordClient;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * <p>Title:MsgNoticeRecordService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 15:14</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@Service
public class MsgNoticeRecordService extends BaseService {

    MsgNoticeRecordClient client;

    /**
     * 获取个人消息记录列表
     * @param params
     * @return
     */
    public List<MsgNoticeRecordVo> findNoticeList(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<MsgNoticeRecordVo>> result = client.findAll(super.convertMap2MultiValueMap(params));
        List<MsgNoticeRecordVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 更新多条消息记录已读标识
     * @param vos
     */
    public void updateRecordList(List<MsgNoticeRecordVo> vos){
        Assert.notEmpty(vos,"vos不能为null!");

        ResponseEntity<Void> result = client.updateMsgNoticeRecordList(vos);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 更新单条消息记录已读标识
     * @param vo
     * @param id
     */
    public void updateRecord(MsgNoticeRecordVo vo,String id){
        Assert.notNull(vo,"vo不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<MsgNoticeRecordVo> result = client.updateMsgNoticeRecord(vo, id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }
}
