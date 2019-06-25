package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.Notice;
import com.taiji.emp.base.repository.NoticeRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

@Slf4j
@Service
@AllArgsConstructor
public class NoticeService extends BaseService<Notice,String> {

    @Autowired
    private NoticeRepository repository;

    /**
     * 新增通知公告信息
     * @param entity
     * @return
     */
    public Notice create(Notice entity){
        Assert.notNull(entity,"notice对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Notice result = repository.save(entity);
        return result;
    }

    /**
     * 删除某条通知公告信息
     * @param id
     * @param delFlagEnum
     */
    public void deleteLogic(String id,DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Notice entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    /**
     * 修改通知公告信息
     * @param entity
     * @return
     */
    public Notice update(Notice entity){
        Assert.notNull(entity,"notice对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Notice result = repository.save(entity);
        return result;
    }

    /**
     * 根据id查询某条通知公告信息
     * @param id
     * @return
     */
    public Notice findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        Notice result = repository.findOne(id);
        return result;
    }

    /**
     * 根据条件查询通知公告列表-分页
     * params参数key为title,noticeTypeId,sendStartTime,sendEndTime,sendStatus,orgId(可选)
     * @param params
     * @param pageable
     * @return
     */
    public Page<Notice> findPage(MultiValueMap<String,Object> params, Pageable pageable){
        Page<Notice> result = repository.findPage(params, pageable);
        return result;
    }

}
