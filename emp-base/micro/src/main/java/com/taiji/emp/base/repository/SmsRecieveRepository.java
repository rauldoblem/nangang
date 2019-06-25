package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.QSmsRecieve;
import com.taiji.emp.base.entity.SmsRecieve;
import com.taiji.emp.base.vo.SmsRecieveVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SmsRecieveRepository extends BaseJpaRepository<SmsRecieve,String> {

    //不分页
    public List<SmsRecieve> findList(SmsRecieveVo smsRecieveVo){
        QSmsRecieve smsRecieve = QSmsRecieve.smsRecieve;
        JPQLQuery<SmsRecieve> query = from(smsRecieve);

        BooleanBuilder builder = new BooleanBuilder();

        String smsId = smsRecieveVo.getSmsId();
        String receiverName = smsRecieveVo.getReceiverName();
        String receiverTel = smsRecieveVo.getReceiverTel();

        if (StringUtils.hasText(smsId)){
            builder.and(smsRecieve.smsId.eq(smsId));
        }

        if (StringUtils.hasText(receiverName)){
            builder.and(smsRecieve.receiverName.eq(receiverName));
        }

        if (StringUtils.hasText(receiverTel)){
            builder.and(smsRecieve.receiverTel.eq(receiverTel));
        }
        return findAll(builder);
    }

    @Override
    @Transactional
    public SmsRecieve save(SmsRecieve entity){
        Assert.notNull(entity,"sms 对象不能为Null");

        SmsRecieve result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            SmsRecieve temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return  result;
    }
}
