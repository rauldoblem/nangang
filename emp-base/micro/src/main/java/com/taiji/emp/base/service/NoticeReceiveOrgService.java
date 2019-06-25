package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.NoticeReceiveOrg;
import com.taiji.emp.base.repository.NoticeReceiveOrgRepository;
import com.taiji.emp.base.searchVo.NoticeReceiveVo;
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
public class NoticeReceiveOrgService extends BaseService<NoticeReceiveOrg,String> {

    @Autowired
    private NoticeReceiveOrgRepository repository;

    /**
     * 通知公告发送
     * @param entity
     * @return
     */
    public NoticeReceiveOrg create(NoticeReceiveOrg entity){
        Assert.notNull(entity,"noticeReceiveOrg对象不能为null");
        NoticeReceiveOrg result = repository.save(entity);
        return result;
    }

    /**
     * 通知公告接受状态查看
     * @param id
     * @return
     */
    public NoticeReceiveOrg findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        NoticeReceiveOrg result = repository.findOne(id);
        return result;
    }

    /**
     * 修改通知公告反馈关系信息
     * @param entity
     * @return
     */
    public NoticeReceiveOrg update(NoticeReceiveOrg entity){
        Assert.notNull(entity,"entity对象不能为null");
        NoticeReceiveOrg result = repository.save(entity);
        return result;
    }

    /**
     * 接受通知条件查询列表----分页
     * @param noticeReceiveVo
     * @param pageable
     */
    public Page<NoticeReceiveOrg> findNoticeReceivePage(NoticeReceiveVo noticeReceiveVo, Pageable pageable){
        Page<NoticeReceiveOrg> result = repository.findNoticeReceivePage(noticeReceiveVo,pageable);
        return result;
    }

    /**
     * 根据 noticeRecId 查询通知公告反馈信息
     * @param noticeRecId
     * @return
     */
    public NoticeReceiveOrg findRecOne(String noticeRecId) {
        Assert.hasText(noticeRecId,"noticeRecId不能为null或空字符串!");
        NoticeReceiveOrg result = repository.findRecOne(noticeRecId);
        return result;
    }

    public NoticeReceiveOrg findByNoticeRecId(String noticeRecId) {
        Assert.hasText(noticeRecId,"noticeRecId不能为null或空字符串!");
        NoticeReceiveOrg result = repository.findByNoticeRecId(noticeRecId);
        return result;
    }

    public List<NoticeReceiveOrg> findList(String noticeId) {
        List<NoticeReceiveOrg> result = repository.finList(noticeId);
        return result;
    }

}
