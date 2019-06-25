package com.taiji.emp.res.service;


import com.taiji.emp.res.entity.Repertory;
import com.taiji.emp.res.repository.RepeRepository;
import com.taiji.emp.res.searchVo.repertory.RepertoryPageVo;
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
public class RepeService extends BaseService<Repertory,String>{

    @Autowired
    private RepeRepository repository;

    public Repertory create(Repertory entity){
        Assert.notNull(entity,"Repertory 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
//        entity.setCreateTime(LocalDateTime.now());
//        entity.setUpdateTime(LocalDateTime.now());
        Repertory result = repository.save(entity);
        return result;
    }

    public Repertory update(Repertory entity){
        Assert.notNull(entity,"Repertory 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
//        entity.setUpdateTime(LocalDateTime.now());
        Repertory result = repository.save(entity);
        return result;
    }

    public Repertory findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        Repertory result = repository.findOne(id);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Repertory entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    //提供给controller使用的 分页list查询方法
    public Page<Repertory> findPage(RepertoryPageVo vo, Pageable pageable){
        Page<Repertory> result = repository.findPage(vo,pageable);
        return result;
    }

    /**
     * 根据条件查询物资库列表-不分页
     * @param vo
     * @return
     */
    public List<Repertory> findRepertoryList(RepertoryPageVo vo) {
        List<Repertory> list = repository.findRepertoryList(vo);
        return list;
    }
}
