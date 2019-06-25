package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.NoticeFeedBack;
import com.taiji.emp.base.entity.QNoticeFeedBack;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class NoticeFeedBackRepository extends BaseJpaRepository<NoticeFeedBack,String> {

    /**
     * 通过receiveId查看反馈内容
     * @param receiveId
     * @return
     */
    public NoticeFeedBack findByReceiveId(String receiveId) {
        QNoticeFeedBack noticeFeedBack = QNoticeFeedBack.noticeFeedBack;
        JPQLQuery<NoticeFeedBack> query = from(noticeFeedBack);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(noticeFeedBack.noticeReceiveId.eq(receiveId));
        return query.where(builder).fetchOne();
    }
}
