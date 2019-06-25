package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.Notice;
import com.taiji.emp.base.entity.NoticeReceiveOrg;
import com.taiji.emp.base.vo.NoticeReceiveOrgVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知公告反馈关系 NoticeReceiveOrgMapper
 * @author qzp-pc
 * @date 2018年10月22日17:51:20
 */
@Mapper(componentModel = "spring")
public interface NoticeReceiveOrgMapper extends BaseMapper<NoticeReceiveOrg,NoticeReceiveOrgVo> {


    @Override
    default RestPageImpl<NoticeReceiveOrgVo> entityPageToVoPage(Page<NoticeReceiveOrg> entityPage, Pageable page) {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<NoticeReceiveOrg> content = entityPage.getContent();
        List<NoticeReceiveOrgVo> list = new ArrayList<NoticeReceiveOrgVo>(content.size());
        for (NoticeReceiveOrg entity : content){
            list.add(entityToVo(entity));
        }
        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
        return voPage;
    }

    @Override
    default NoticeReceiveOrg voToEntity(NoticeReceiveOrgVo vo){
        NoticeReceiveOrg entity = new NoticeReceiveOrg();
        Notice notice = new Notice();
        notice.setId(vo.getNoticeId());
        entity.setId(vo.getId());
        entity.setNotice(notice);
        entity.setSendBy(vo.getSendBy());
        entity.setSendTime(vo.getSendTime());
        entity.setReceiveOrgId(vo.getReceiveOrgId());
        entity.setReceiveOrgName(vo.getReceiveOrgName());
        entity.setIsFeedback(vo.getIsFeedback());
        entity.setReceiveOrgName(vo.getReceiveOrgName());
        return entity;
    }

    @Override
    default NoticeReceiveOrgVo entityToVo(NoticeReceiveOrg entity){
        if (entity == null){
            return null;
        }
        NoticeReceiveOrgVo noticeReceiveOrgVo = new NoticeReceiveOrgVo();
        noticeReceiveOrgVo.setId(entity.getId());
        noticeReceiveOrgVo.setNoticeId(entity.getNotice().getId());
        noticeReceiveOrgVo.setTitle(entity.getNotice().getTitle());
        noticeReceiveOrgVo.setNoticeTypeId(entity.getNotice().getNoticeTypeId());
        noticeReceiveOrgVo.setNoticeTypeName(entity.getNotice().getNoticeTypeName());
        noticeReceiveOrgVo.setContent(entity.getNotice().getContent());
        noticeReceiveOrgVo.setBuildOrgId(entity.getNotice().getBuildOrgId());
        noticeReceiveOrgVo.setBuildOrgName(entity.getNotice().getBuildOrgName());
        noticeReceiveOrgVo.setSendBy(entity.getSendBy());
        noticeReceiveOrgVo.setReceiveOrgId(entity.getReceiveOrgId());
        noticeReceiveOrgVo.setIsFeedback(entity.getIsFeedback());
        noticeReceiveOrgVo.setSendTime(entity.getNotice().getSendTime());
        noticeReceiveOrgVo.setReceiveOrgName(entity.getReceiveOrgName());
        return noticeReceiveOrgVo;
    }
}
