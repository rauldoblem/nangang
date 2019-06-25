package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.NoticeFeedBack;
import com.taiji.emp.base.vo.NoticeFeedBackVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知公告反馈 NoticeFeedBackMapper
 * @author qzp-pc
 * @date 2018年10月23日15:16:20
 */
@Mapper(componentModel = "spring")
public interface NoticeFeedBackMapper extends BaseMapper<NoticeFeedBack,NoticeFeedBackVo> {

    default RestPageImpl<NoticeFeedBackVo> entityPageToVoPage(Page<NoticeFeedBack> entityPage, Pageable page){
        if ( entityPage == null || page == null) {
            return null;
        }

        List<NoticeFeedBack> content = entityPage.getContent();
        List<NoticeFeedBackVo> list = new ArrayList<>(content.size());
        for (NoticeFeedBack entity :content){
            list.add(entityToVoForList(entity));
        }
        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
        return voPage;
    }

    default NoticeFeedBackVo entityToVoForList(NoticeFeedBack entity){
        if (entity == null){
            return null;
        }
        NoticeFeedBackVo noticeFeedBackVo = new NoticeFeedBackVo();
        noticeFeedBackVo.setId(entity.getId());
        noticeFeedBackVo.setNoticeRecId(entity.getNoticeReceiveId());
        noticeFeedBackVo.setFeedbackBy(entity.getFeedbackBy());
        noticeFeedBackVo.setContent(entity.getContent());
        noticeFeedBackVo.setFeedbackTime(entity.getFeedBackTime());
        noticeFeedBackVo.setNotes(entity.getNotes());
        return noticeFeedBackVo;
    }

    @Override
    default NoticeFeedBack voToEntity(NoticeFeedBackVo vo){
        NoticeFeedBack entity = new NoticeFeedBack();
        entity.setId(vo.getId());
        entity.setContent(vo.getContent());
        entity.setFeedbackBy(vo.getFeedbackBy());
        entity.setFeedBackTime(vo.getFeedbackTime());
        entity.setNotes(vo.getNotes());
        entity.setNoticeReceiveId(vo.getNoticeRecId());
        return entity;
    }

    @Override
    default NoticeFeedBackVo entityToVo(NoticeFeedBack entity) {
        if (null == entity){
            return null;
        }
        NoticeFeedBackVo entityVo = new NoticeFeedBackVo();
        entityVo.setId(entity.getId());
        entityVo.setContent(entity.getContent());
        entityVo.setFeedbackBy(entity.getFeedbackBy());
        entityVo.setFeedbackTime(entity.getFeedBackTime());
        entityVo.setNotes(entity.getNotes());
        entityVo.setNoticeRecId(entity.getNoticeReceiveId());
        return entityVo;
    }
}
