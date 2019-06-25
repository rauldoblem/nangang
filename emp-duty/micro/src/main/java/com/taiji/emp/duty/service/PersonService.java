package com.taiji.emp.duty.service;

import com.taiji.emp.duty.entity.Person;
import com.taiji.emp.duty.repository.PersonRepository;
import com.taiji.emp.duty.searchVo.PersonListVo;
import com.taiji.emp.duty.searchVo.PersonPageVo;
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
public class PersonService extends BaseService<Person,String> {

    @Autowired
    private PersonRepository repository;

//    @Autowired
//    private ContactRe

    /**
     * 新增值班人员信息
     * @param entity
     * @return
     */
    public Person create(Person entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Person result = repository.save(entity);
        return result;
    }

    /**
     * 修改值班人员信息
     * @param entity
     * @return
     */
    public Person update(Person entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Person result = repository.save(entity);
        return result;
    }

    /**
     * 删除某条值班人员信息
     * @param vo
     * @param delFlagEnum
     */
    public void deleteLogic(PersonListVo vo, DelFlagEnum delFlagEnum) {
        Assert.notNull(vo,"PersonListVo对象不能为空");
        //根据参数查询要删除的数据
        List<String> personIds = vo.getPersonIds();
        for (String personId:personIds) {
            Person entity = repository.findOne(personId);
            repository.deleteLogic(entity,delFlagEnum);
        }
    }

    /**
     * 根据id查询某条值班人员信息
     * @param id
     * @return
     */
    public Person findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串");
        Person result = repository.findOne(id);
        return result;
    }

    /**
     * 根据条件查询值班人员列表——分页
     * @param pageVo
     * @param pageable
     * @return
     */
    public Page<Person> findPage(PersonPageVo pageVo, Pageable pageable) {
        Page<Person> result = repository.findPage(pageVo,pageable);
        return result;
    }

    /**
     * 根据条件查询值班人员列表
     * @param listVo
     * @return
     */
    public List<Person> findList(PersonListVo listVo) {
        List<Person> list = repository.findList(listVo);
        return list;
    }

    /**
     * 获取分组下的人员信息
     * @param teamId
     * @return
     */
    public List<Person> findListByTeamId(String teamId) {
        List<Person> list = repository.findListByTeamId(teamId);
        return list;
    }
}
