package com.taiji.emp.event.infoDispatch.service;

import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.infoDispatch.entity.Accept;
import com.taiji.emp.event.infoDispatch.entity.AcceptDeal;
import com.taiji.emp.event.infoDispatch.repository.AcceptDealRepository;
import com.taiji.emp.event.infoDispatch.searchVo.InfoPageVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AcceptDealService extends BaseService<AcceptDeal,String>{

    @Autowired
    private AcceptDealRepository repository;

    //分页查询AcceptDeal
    public Page<AcceptDeal> findPage(InfoPageVo infoPageVo, Pageable pageable){
        return repository.findPage(infoPageVo,pageable);
    }

    //操作：办理信息，包括发送、退回、办结、生成/更新事件
    public AcceptDeal operateInfo(AcceptDeal entity,String buttonFlag){
        if(EventGlobal.INFO_DISPATCH_BUTTON_SEND.equals(buttonFlag)){
            return repository.sendInfo(entity);
        }else{
            return repository.operateInfo(entity,buttonFlag);
        }
    }

    //根据初报Id 获取已生成事件的 eventId
    public String getEventIdByFirstReportId(String firstReportId){
        Assert.hasText(firstReportId,"初报Id 不能为空");
        return repository.getEventIdByFirstReportId(firstReportId);
    }

    //获取退回办理信息：查看退回原因
    public AcceptDeal checkReturnReason(String acceptDealId){
        Assert.hasText(acceptDealId,"acceptDealId不能为空");
        AcceptDeal result = repository.findOne(acceptDealId);
        result.setAcceptId(result.getImAccept().getId());
//        result.setImAccept(null); //不需要返回信息对象
        return result;
    }

    //通过eventId查询所有初报续报对象
    public List<Accept> findListByEventId(String eventId){
        Assert.hasText(eventId,"eventId不能为空");
        return repository.findAcceptListByEventId(eventId);
    }

    //通过eventId 将所有该事件下的报送信息置位为已办结
    public void finishInfosByEventId(String eventId){
        Assert.hasText(eventId,"eventId不能为空");
        repository.finishInfosByEventId(eventId);
    }

    public AcceptDeal findDealByDealId(String dealId) {
        Assert.hasText(dealId,"dealId不能为空");
        return repository.findOne(dealId);
    }

    public Page<AcceptDeal> findAllPage(InfoPageVo infoPageVo, Pageable page) {
        return repository.findAllPage(infoPageVo,page);
    }
}
