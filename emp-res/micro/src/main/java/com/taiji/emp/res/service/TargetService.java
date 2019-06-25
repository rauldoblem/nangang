package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Target;
import com.taiji.emp.res.repository.TargetRepository;
import com.taiji.emp.res.searchVo.target.TargetSearchVo;
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
public class TargetService extends BaseService<Target,String> {

    @Autowired
    private TargetRepository repository;

    /**
     * 新增
     * @param entity
     * @return
     */
    public Target create(Target entity){
        Assert.notNull(entity,"rcTarget对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Target result = repository.save(entity);
        return result;
    }

    /**
     * 修改
     * @param entity
     * @return
     */
    public Target update(Target entity){
        Assert.notNull(entity,"rcTarget对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Target result = repository.save(entity);
        return result;
    }

    /**
     * 查询某条信息
     * @param id
     * @return
     */
    public Target findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        Target result = repository.findOne(id);
        return result;
    }

    /**
     * 删除某条信息
     * @param id
     * @param delFlagEnum
     */
    public void deleteLogic(String id,DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Target entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    /**
     * 提供给controller使用的 分页list查询方法
     * @param searchVo
     * @param pageable
     * @return
     */
    public Page<Target> findPage(TargetSearchVo searchVo, Pageable pageable){
        Page<Target> result = repository.findPage(searchVo, pageable);
        return result;
    }

    /**
     * 不分页,查询防护目标列表
     * @param vo
     * @return
     */
    public List<Target> findList(TargetSearchVo vo){
        List<Target> list = repository.findList(vo);
        return list;
    }

}
