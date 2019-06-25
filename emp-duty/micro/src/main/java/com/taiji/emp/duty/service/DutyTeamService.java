package com.taiji.emp.duty.service;

import com.taiji.emp.duty.entity.DutyTeam;
import com.taiji.emp.duty.entity.Person;
import com.taiji.emp.duty.repository.DutyTeamRepository;
import com.taiji.emp.duty.repository.PersonRepository;
import com.taiji.emp.duty.vo.DutyTeamVo;
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
public class DutyTeamService extends BaseService<DutyTeam,String> {

    @Autowired
    private DutyTeamRepository repository;

    @Autowired
    private PersonRepository personRepository;

    /**
     * 新增值班人员分组信息
     * @param entity
     * @return
     */
    public DutyTeam create(DutyTeam entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DutyTeam result = repository.save(entity);
        return result;
    }

    /**
     * 修改值班人员分组信息
     * @param entity
     * @return
     */
    public DutyTeam update(DutyTeam entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DutyTeam result = repository.save(entity);
        return result;
    }

    /**
     * 删除某条值班人员分组信息
     * @param id
     * @param delFlagEnum
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为null或空字符串");
        DutyTeam entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
        //根据组id查询人员信息
        //删除
        List<Person> listByTeamId = personRepository.findListByTeamId(id);
        for (Person p:listByTeamId) {
            personRepository.deleteLogic(p,delFlagEnum);
        }
    }

    /**
     * 根据id查询某条值班人员分组信息
     * @param id
     * @return
     */
    public DutyTeam findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串");
        DutyTeam result = repository.findOne(id);
        return result;
    }

    /**
     * 根据条件查询值班人员分组列表
     * @param orgId
     * @return
     */
    public List<DutyTeam> findList(String orgId) {
        List<DutyTeam> list = repository.findList(orgId);
        return list;
    }

    /**
     * 根据条件查询值班人员分组列表
     * @param vo
     * @return
     */
    public List<DutyTeam> findDutyList(DutyTeamVo vo) {
        List<DutyTeam> list = repository.findDutyList(vo);
        return list;
    }

    /**
     * 自动排班的值班组信息
     * @param orgId
     * @return
     */
    public List<DutyTeam> findAutoList(String orgId) {
        List<DutyTeam> list = repository.findAutoList(orgId);
        return list;
    }
}
