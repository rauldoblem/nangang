package com.taiji.emp.drill.service;

import com.taiji.emp.drill.entity.DrillScheme;
import com.taiji.emp.drill.repository.DrillSchemeRepository;
import com.taiji.emp.drill.searchVo.DrillSchemeSearchVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@AllArgsConstructor
public class DrillSchemeService extends BaseService<DrillScheme,String> {

    DrillSchemeRepository repository;

    /**
     * 新增演练方案
     * @param entity
     * @return
     */
    public DrillScheme create(DrillScheme entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DrillScheme drillScheme = repository.save(entity);
        return drillScheme;
    }

    /**
     * 根据id删除演练方案信息
     * @param id
     * @param delFlagEnum
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为空或null");
        DrillScheme entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    /**
     * 根据id修改演练方案信息
     * @param entity
     * @return
     */
    public DrillScheme update(DrillScheme entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DrillScheme drillScheme = repository.save(entity);
        return drillScheme;
    }

    /**
     * 根据id获取一条演练方案信息
     * @param id
     * @return
     */
    public DrillScheme findOne(String id) {
        Assert.hasText(id,"id不能为空");
        DrillScheme drillScheme = repository.findOne(id);
        return drillScheme;
    }

    /**
     * 根据条件查询演练方案列表——分页
     * @param searchVo
     * @param page
     * @return
     */
    public Page<DrillScheme> findPage(DrillSchemeSearchVo searchVo, Pageable page) {
        Page<DrillScheme> result = repository.findPage(searchVo,page);
        return result;
    }

    /**
     * 上报/下发演练方案——更改状态
     * @param entity
     * @return
     */
    public DrillScheme updateStatusById(DrillScheme entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DrillScheme drillScheme = repository.save(entity);
        return drillScheme;
    }
}
