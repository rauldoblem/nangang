package com.taiji.emp.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.duty.entity.dailyShift.DailyShift;
import com.taiji.emp.duty.entity.dailyShift.QDailyShift;
import com.taiji.emp.duty.searchVo.DailyShiftPageVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


@Repository
@Transactional(
        readOnly = true
)
public class DailyShiftRepository extends BaseJpaRepository<DailyShift,String> {

    //带分页信息，查询
    public Page<DailyShift> findPage(DailyShiftPageVo dailyShiftPageVo, Pageable pageable){
        JPQLQuery<DailyShift> query = buildQuery(dailyShiftPageVo);
        return findAll(query,pageable);
    }

    private JPQLQuery<DailyShift> buildQuery(DailyShiftPageVo dailyShiftPageVo){
        QDailyShift dailyShift = QDailyShift.dailyShift;
        JPQLQuery<DailyShift> query = from(dailyShift);

        BooleanBuilder builder = new BooleanBuilder();
        String fromWatcherName = dailyShiftPageVo.getFromWatcherName();
        String title = dailyShiftPageVo.getTitle();
        String toWatcherName = dailyShiftPageVo.getToWatcherName();
        if(StringUtils.hasText(fromWatcherName)){
            builder.and(dailyShift.fromWatcherName.contains(fromWatcherName));
        }

        if(StringUtils.hasText(toWatcherName)){
            builder.and(dailyShift.toWatcherName.contains(toWatcherName));
        }

        if(StringUtils.hasText(title)){
            builder.and(dailyShift.title.contains(title));
        }

        query.where(builder)
                .orderBy(dailyShift.createTime.desc());
        return query;
    }

    @Override
    @Transactional
    public DailyShift save(DailyShift entity){
        Assert.notNull(entity,"Contact 对象不能为 null");

        DailyShift result;
        if(null == entity.getId()){
            result = super.save(entity);
        }else{
            DailyShift temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }
}
