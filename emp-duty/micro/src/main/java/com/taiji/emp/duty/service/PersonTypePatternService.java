package com.taiji.emp.duty.service;

import com.taiji.emp.duty.entity.PersonTypePattern;
import com.taiji.emp.duty.repository.PersonTypePatternRepository;
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
public class PersonTypePatternService extends BaseService<PersonTypePattern,String> {

    @Autowired
    private PersonTypePatternRepository repository;

    /**
     * 新增值班人员设置信息
     * @param entity
     * @return
     */
    public PersonTypePattern create(PersonTypePattern entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        PersonTypePattern result = repository.save(entity);
        return result;
    }

    /**
     * 修改值班人员设置信息
     * @param entity
     * @return
     */
    public PersonTypePattern update(PersonTypePattern entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        PersonTypePattern result = repository.save(entity);
        return result;
    }

    /**
     * 删除某条值班人员设置信息
     * @param id
     * @param delFlagEnum
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为null或空字符串");
        PersonTypePattern entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    /**
     * 根据id查询某条值班人员设置信息
     * @param id
     * @return
     */
    public PersonTypePattern findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串");
        PersonTypePattern result = repository.findOne(id);
        return result;
    }

    /**
     * 根据条件查询值班人员设置列表
     * @param patternId
     * @return
     */
    public List<PersonTypePattern> findList(String patternId) {
        List<PersonTypePattern> list = repository.findList(patternId);
        return list;
    }
}
