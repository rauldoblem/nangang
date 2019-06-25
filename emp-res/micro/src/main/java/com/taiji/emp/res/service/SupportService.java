package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Support;
import com.taiji.emp.res.repository.SupportRepository;
import com.taiji.emp.res.searchVo.support.SupportListVo;
import com.taiji.emp.res.searchVo.support.SupportPageVo;
import com.taiji.emp.res.vo.SupportVo;
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
public class SupportService extends BaseService<Support,String>{

    @Autowired
    private SupportRepository repository;

    public Support createOrUpdate(Support entity){
        Assert.notNull(entity,"Support 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public Support findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Support entity = repository.findOne(id);
        Assert.notNull(entity,"entity不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }

    //提供给controller使用的 分页list查询方法
    public Page<Support> findPage(SupportPageVo supportPageVo, Pageable pageable){
        return repository.findPage(supportPageVo,pageable);
    }

    //不分页list查询
    public List<Support> findList(SupportListVo supportListVo){
        return repository.findList(supportListVo);
    }

    /**
     * 通过schemeId应急社会依托资源信息
     * @param schemeId
     * @return
     */
    public List<SupportVo> findBySchemeId(String schemeId) {
        return repository.findBySchemeId(schemeId);
    }
}
