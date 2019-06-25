package com.taiji.emp.drill.service;

import com.taiji.emp.drill.entity.DrillPlan;
import com.taiji.emp.drill.repository.DrillPlanRepository;
import com.taiji.emp.drill.searchVo.DrillPlanSearchVo;
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
public class DrillPlanService extends BaseService<DrillPlan,String> {

    @Autowired
    private DrillPlanRepository repository;

    /**
     * 新增演练计划
     * @param entity
     * @return
     */
    public DrillPlan create(DrillPlan entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DrillPlan drillPlan = repository.save(entity);
        return drillPlan;
    }

    /**
     * 根据id删除一条记录
     * @param id
     * @param delFlagEnum
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为null或空字符串");
        DrillPlan entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    /**
     * 更新演练计划
     * @param entity
     * @return
     */
    public DrillPlan update(DrillPlan entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DrillPlan drillPlan = repository.save(entity);
        return drillPlan;
    }

    /**
     * 根据id获取演练计划信息
     * @param id
     * @return
     */
    public DrillPlan findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串");
        DrillPlan drillPlan = repository.findOne(id);
        return drillPlan;
    }

    /**
     * 根据条件查询演练计划列表——分页
     * @param searchVo
     * @param pageable
     * @return
     */
    public Page<DrillPlan> findPage(DrillPlanSearchVo searchVo, Pageable pageable) {
        Page<DrillPlan> result = repository.findPage(searchVo,pageable);
        return result;
    }

    /**
     * 上报/下发演练计划——更改状态
     * @param entity
     * @return
     */
    public DrillPlan updateStatusById(DrillPlan entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DrillPlan result = repository.save(entity);
        return result;
    }
}
