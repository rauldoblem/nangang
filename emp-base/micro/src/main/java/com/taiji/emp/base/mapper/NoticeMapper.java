package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.Notice;
import com.taiji.emp.base.vo.NoticeVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知公告 NoticeMapper
 * @author qzp-pc
 * @date 2018年10月21日14:36:20
 */
@Mapper(componentModel = "spring")
public interface NoticeMapper extends BaseMapper<Notice,NoticeVo> {

    @Override
    default RestPageImpl<NoticeVo> entityPageToVoPage(Page<Notice> entityPage, Pageable page) {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<Notice> content = entityPage.getContent();
        List<NoticeVo> list = new ArrayList<NoticeVo>(content.size());
        for (Notice entity : content){
            list.add(entityToVoForList(entity));
        }
        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
        return voPage;
    }

    default NoticeVo entityToVoForList(Notice entity){
        if (entity == null){
            return null;
        }

        NoticeVo noticeVo = new NoticeVo();
        noticeVo.setId(entity.getId());
        noticeVo.setCreateTime(entity.getCreateTime());
        noticeVo.setUpdateTime(entity.getUpdateTime());
        noticeVo.setCreateBy(entity.getCreateBy());
        noticeVo.setUpdateBy(entity.getUpdateBy());

        noticeVo.setTitle(entity.getTitle());
        noticeVo.setNoticeTypeId(entity.getNoticeTypeId());
        noticeVo.setNoticeTypeName(entity.getNoticeTypeName());
        noticeVo.setContent(entity.getContent());
        noticeVo.setBuildOrgId(entity.getBuildOrgId());
        noticeVo.setBuildOrgName(entity.getBuildOrgName());
        noticeVo.setSendStatus(entity.getSendStatus());
        noticeVo.setSendTime(entity.getSendTime());
        noticeVo.setNotes(entity.getNotes());
        return noticeVo;
    }

}
