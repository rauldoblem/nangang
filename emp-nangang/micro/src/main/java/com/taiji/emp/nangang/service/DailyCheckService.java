package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.DailyCheck;
import com.taiji.emp.nangang.entity.DailyCheckDailyLog;
import com.taiji.emp.nangang.entity.DailyCheckItems;
import com.taiji.emp.nangang.repository.DailyCheckItemsRepository;
import com.taiji.emp.nangang.repository.DailyCheckRepository;
import com.taiji.emp.nangang.repository.DailyLogRepository;
import com.taiji.emp.nangang.searchVo.dailyCheck.DailyCheckPageVo;
import com.taiji.emp.nangang.vo.DailyCheckVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DailyCheckService {

    @Autowired
    private DailyCheckRepository repository;
    @Autowired
    private DailyCheckItemsRepository itemsRepository;
    @Autowired
    private DailyLogRepository dailyLogRepository;
    @PersistenceContext
    protected EntityManager em;

    public DailyCheck updateDailyCheck(String id) {
        boolean notBlank = StringUtils.isNotBlank(id);
        Assert.isTrue(notBlank , "id不能为null");
        DailyCheck dailyCheck = repository.findOne(id);
        dailyCheck.setIsShift("1");
        DailyCheck result = repository.save(dailyCheck);
        return result;
    }

    /**
     *
     * @param entity
     * @return
     */
    public List<DailyCheckItems> selectItem(DailyCheck entity) {
        Assert.notNull(entity,"DailyCheck对象不能为null");

        List<DailyCheck> dailyChecks = repository.findOne(entity);
        DailyCheck dailyCheck = dailyChecks.get(0);
        String dailyCheckId = dailyCheck.getId();
        List<DailyCheckItems> result = itemsRepository.findAll(dailyCheckId);
        return result;
    }

    /**
     * 分页查询dailyCheck
     * @param dailyCheckPageVo
     * @param pageable
     * @return
     */
    public Page<DailyCheck> findPage(DailyCheckPageVo dailyCheckPageVo, Pageable pageable) {
        Page<DailyCheck> result = repository.findPage(dailyCheckPageVo,pageable);
        return result;
    }

    /**
     * 根据条件判断是否存在dailyCheck
     * @param entity
     * @return
     */
    public DailyCheck exists(DailyCheck entity) {
        Assert.notNull(entity,"DailyCheck对象不能为null");
        List<DailyCheck> dailyChecks = repository.findOne(entity);
        if(!CollectionUtils.isEmpty(dailyChecks)){
            return dailyChecks.get(0);
        }
        return null;
    }

    /**
     * 新建一条dailyCheck保存到库
     * @param entity
     * @return
     */
    public DailyCheck save(DailyCheck entity) {
        Assert.notNull(entity,"DailyCheck对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DailyCheck result = repository.save(entity);
        return result;
    }

    /**
     * 根据条件查出dailyCheck
     * @param entity
     * @return result（id）
     */
    public DailyCheck findOne(DailyCheck entity) {
        Assert.notNull(entity,"DailyCheck对象不能为null");
        List<DailyCheck> one = repository.findOne(entity);
        DailyCheck result = one.get(0);
        return result;
    }

    /**
     * 批量保存DailyCheckItems
     * @param entityList
     * @return
     */
    public List<DailyCheckItems> saveByList(List<DailyCheckItems> entityList) {
        boolean empty = CollectionUtils.isEmpty(entityList);
        Assert.isTrue(!empty , "List<DailyCheckItems>对象不能为null");
        for (DailyCheckItems dailyCheckItems : entityList) {
            dailyCheckItems.setDelFlag(DelFlagEnum.NORMAL.getCode());
        }
        List<DailyCheckItems> result = itemsRepository.save(entityList);
        return result;
    }
    @Transactional(rollbackFor = Exception.class)
    public DailyCheckDailyLog addDailyLog(DailyCheckDailyLog entity) {
        Assert.notNull(entity,"DailyCheckDailyLog对象不能为null");
        Query query = this.em.createNativeQuery("delete from ED_DAILYCHECK_DAILYLOG where check_item_id = :checkItemId");
        query.setParameter("checkItemId",entity.getCheckItemId());
        query.executeUpdate();
        DailyCheckDailyLog result = dailyLogRepository.save(entity);
        return result;
    }
}
