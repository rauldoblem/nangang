package com.taiji.emp.alarm.service;

import com.taiji.emp.alarm.entity.Alert;
import com.taiji.emp.alarm.repository.AlertRepository;
import com.taiji.emp.alarm.searchVo.AlertPageSearchVo;
import com.taiji.emp.alarm.vo.AlertVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@AllArgsConstructor
public class AlertService extends BaseService<Alert,String> {

    @Autowired
    AlertRepository repository;

    /**
     * 新增预警信息
     */
    public Alert create(Alert entity){
        Assert.notNull(entity,"Alert 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    /**
     * 根据id 获取预警信息
     */
    public Alert findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    /**
     * 更新预警信息
     */
    public Alert update(Alert entity,String id){
        Assert.notNull(entity,"Alert 对象不能为 null");
        Assert.hasText(id,"id 不能为null或空字符串");
        entity.setId(id);
        return repository.save(entity);
    }

    /**
     * 根据id删除一条预警信息
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空");
        Alert entity = repository.findOne(id);
        Assert.notNull(entity,"Alert 不能为空");
        repository.deleteLogic(entity,delFlagEnum);
    }

    /**
     * 根据参数获取AlertVo多条记录,分页信息
     */
    public Page<Alert> findPage(AlertPageSearchVo alertPageVo, Pageable pageable){
        return repository.findPage(alertPageVo,pageable);
    }

}
