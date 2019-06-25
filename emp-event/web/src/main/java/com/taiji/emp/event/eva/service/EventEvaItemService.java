package com.taiji.emp.event.eva.service;

import com.taiji.emp.event.eva.feign.EventEvaItemClient;
import com.taiji.emp.event.eva.vo.EventEvaItemVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class EventEvaItemService extends BaseService {

    @Autowired
    EventEvaItemClient client;

    /**
     * 新增事件评估项
     * @param vo
     * @param principal
     */
    public void create(EventEvaItemVo vo, Principal principal) {
        Assert.notNull(vo,"vo不能为空");
        vo.setCreateBy(principal.getName());
        vo.setUpdateBy(principal.getName());
        client.create(vo);
    }

    /**
     * 删除事件评估项信息
     * @param id
     */
    public void deleteLogic(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 修改事件评估项信息
     * @param id
     * @param principal
     * @param vo
     */
    public void update(String id, Principal principal, EventEvaItemVo vo) {
        Assert.hasText(id,"id不能为空");
        vo.setUpdateBy(principal.getName());
        client.update(vo,id);
    }

    /**
     * 获取单条事件评估项信息
     * @param id
     * @return
     */
    public EventEvaItemVo findOne(String id) {
        Assert.hasText(id,"id不能为空");
        ResponseEntity<EventEvaItemVo> resultVo = client.findOne(id);
        EventEvaItemVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据事件类型获取事件评估项列表
     * @param eventTypeId
     * @return
     */
    public List<EventEvaItemVo> findListByEventTypeId(String eventTypeId) {
        Assert.hasText(eventTypeId,"eventTypeId不能为空");
        ResponseEntity<List<EventEvaItemVo>> list = client.findListByEventTypeId(eventTypeId);
        List<EventEvaItemVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }
}
