package com.taiji.emp.alarm.service;

import com.taiji.emp.alarm.entity.Alert;
import com.taiji.emp.alarm.entity.AlertNotice;
import com.taiji.emp.alarm.repository.AlertNoticeRepository;
import com.taiji.emp.alarm.repository.AlertRepository;
import com.taiji.emp.alarm.repository.NoticeFbRepository;
import com.taiji.emp.alarm.searchVo.AlertNoticePageSearchVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AlertNoticeService extends BaseService<AlertNotice,String> {

    AlertNoticeRepository repository;
    NoticeFbRepository noticeFbRepository;
    AlertRepository alertRepository;

    /**
     * 新增单条 预警通知信息
     */
    public AlertNotice createOne(AlertNotice entity){
        String alertId = entity.getAlertId();
        Assert.hasText(alertId,"alertId 不能为空");
        Alert alert = alertRepository.findOne(alertId);
        Assert.notNull(alert,"alert 不能为null");
        entity.setAlert(alert);
        entity.setFeedbacks(null); //反馈List置null,避免错误
        return repository.create(entity);
    }

    /**
     * 根据id获取 单条预警通知信息
     */
    public AlertNotice findOne(String id){
        Assert.hasText(id,"id 不能为空");
        AlertNotice entity = repository.findOne(id);
        return entity;
    }

    /**
     * 更新单条 预警通知信息
     */
    public AlertNotice update(AlertNotice entity,String id){
        Assert.hasText(id,"id 不能为空");
        Assert.notNull(entity,"alert 不能为null");
        entity.setFeedbacks(null);//反馈List置null,避免错误
        return repository.update(entity);
    }

    /**
     * 新增多条 预警通知信息
     */
    public List<AlertNotice> createList(List<AlertNotice> entities){
        if(CollectionUtils.isEmpty(entities)){
            return null;
        }
        List<AlertNotice> resultList = new ArrayList<>();
        for(AlertNotice notice:entities){
            AlertNotice result = createOne(notice);
            if(null!=result){
                resultList.add(result);
            }
        }
        return resultList;
    }

    /**
     * 根据条件查询预警通知信息 -- 分页
     */
    public Page<AlertNotice> findPage(AlertNoticePageSearchVo alertNoticePageVo, Pageable pageable){
        return repository.findPage(alertNoticePageVo,pageable);
    }

    /**
     * 根据条件查询预警通知信息 -- 不分页
     */
    public List<AlertNotice> findList(MultiValueMap<String, Object> params){
        return repository.findList(params);
    }

}
