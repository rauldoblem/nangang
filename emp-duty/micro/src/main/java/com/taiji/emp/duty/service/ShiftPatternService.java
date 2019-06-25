package com.taiji.emp.duty.service;

import com.taiji.emp.duty.entity.ShiftPattern;
import com.taiji.emp.duty.repository.ShiftPatternRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ShiftPatternService extends BaseService<ShiftPattern,String> {

    @Autowired
    private ShiftPatternRepository repository;

    /**
     * 新增班次设置信息
     * @param entity
     * @return
     */
    public ShiftPattern create(ShiftPattern entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        ShiftPattern result = repository.save(entity);
        return result;
    }

    /**
     * 修改班次设置信息
     * @param entity
     * @return
     */
    public ShiftPattern update(ShiftPattern entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        ShiftPattern result = repository.save(entity);
        return result;
    }

    /**
     * 删除某条班次设置信息
     * @param id
     * @param delFlagEnum
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为null或空字符串");
        ShiftPattern entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    /**
     * 根据id查询某条班次设置信息
     * @param id
     * @return
     */
    public ShiftPattern findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串");
        ShiftPattern result = repository.findOne(id);
        return result;
    }

    /**
     * 根据条件查询班次设置列表
     * @param patternId
     * @return
     */
    public List<ShiftPattern> findList(String patternId) {
        List<ShiftPattern> list = repository.findList(patternId);
        return list;
    }
}
