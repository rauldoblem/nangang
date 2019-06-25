package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.PlantLayout;
import com.taiji.emp.base.repository.PlantLayoutRepository;
import com.taiji.emp.base.searchVo.PlantLayoutSearchVo;
import com.taiji.micro.common.enums.DelFlagEnum;
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
public class PlantLayoutService extends BaseService<PlantLayout,String> {
    @Autowired
    private PlantLayoutRepository repository;


    public PlantLayout create(PlantLayout entity) {
        Assert.notNull(entity,"entity对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    /**
     * 根据id删除一条记录
     * @param id
     * @param delFlagEnum
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为null或空字符串!");
        PlantLayout entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    /**
     * 修改厂区平面图信息
     * @param entity
     * @return
     */
    public PlantLayout update(PlantLayout entity) {
        Assert.notNull(entity,"entity对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    /**
     * 根据id获取某条厂区平面图信息
     * @param id
     * @return
     */
    public PlantLayout findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串!");
        return repository.findOne(id);
    }

    /**
     * 查询厂区平面图信息列表------分页
     * @param plantLayoutVo
     * @param page
     * @return
     */
    public Page<PlantLayout> findPage(PlantLayoutSearchVo plantLayoutVo, Pageable page) {
        return repository.findPage(plantLayoutVo, page);
    }

    /**
     * 查询厂区平面图信息列表------不分页
     * @param plantLayoutVo
     * @return
     */
    public List<PlantLayout> findList(PlantLayoutSearchVo plantLayoutVo) {
        return repository.findList(plantLayoutVo);
    }
}
