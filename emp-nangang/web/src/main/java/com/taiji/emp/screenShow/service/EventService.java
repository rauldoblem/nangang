package com.taiji.emp.screenShow.service;

import com.taiji.emp.event.common.enums.RepStatusEnum;
import com.taiji.emp.event.infoDispatch.searchVo.EventPageVo;
import com.taiji.emp.event.infoDispatch.vo.AcceptIsFirstOrNotFirst;
import com.taiji.emp.event.infoDispatch.vo.AcceptVo;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.emp.nangang.feign.AcceptClient;
import com.taiji.emp.nangang.feign.EventClient;
import com.taiji.emp.nangang.service.BaseService;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService extends BaseService {

    @Autowired
    private AcceptClient acceptClient;
    @Autowired
    private EventClient eventClient;

    //根据条件查询事件列表-不分页
    public List<EventVo> findList(EventPageVo pageVo){
        ResponseEntity<List<EventVo>> resultVoList = eventClient.searchAll(pageVo);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVoList);
    }

    //根据eventId查询所有初报续报信息
    public AcceptIsFirstOrNotFirst findAccListByEventId(String eventId){
        Assert.hasText(eventId,"eventId 不能为空");
        ResponseEntity<List<AcceptVo>> resultVoList = acceptClient.searchInfoByEvent(eventId);
        List<AcceptVo> acceptVos = ResponseEntityUtils.achieveResponseEntityBody(resultVoList);
        //根据IsFirst 判断是初报还是续报
        AcceptIsFirstOrNotFirst acceptIsFirstOrNotFirst = new AcceptIsFirstOrNotFirst();
        List<AcceptVo> firstIMAccept = new ArrayList<>();
        List<AcceptVo> resubmits = new ArrayList<>();
        for (AcceptVo result:acceptVos) {
            String isFirst = result.getIsFirst();
            if(RepStatusEnum.FIRST.getCode().equals(isFirst)){
                firstIMAccept.add(result);
            }else {
                resubmits.add(result);
            }
        }
        acceptIsFirstOrNotFirst.setFirstIMAccept(firstIMAccept);
        acceptIsFirstOrNotFirst.setResubmits(resubmits);
        return acceptIsFirstOrNotFirst;
    }
}
