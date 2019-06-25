package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.NoticeFeedBack;
import com.taiji.emp.base.entity.Sms;
import com.taiji.emp.base.vo.NoticeFeedBackVo;
import com.taiji.emp.base.vo.SmsVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SmsMapper extends BaseMapper<Sms,SmsVo >{

//    default RestPageImpl<SmsVo> entityPageToVoPage(Page<Sms> entityPage, Pageable page){
//        if ( entityPage == null || page == null) {
//            return null;
//        }
//
//        List<Sms> content = entityPage.getContent();
//        List<SmsVo> list = new ArrayList<>(content.size());
//        for (Sms entity :content){
//            list.add(entityToVoForList(entity));
//        }
//        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
//        return voPage;
//    }
//
//    default SmsVo entityToVoForList(Sms entity){
//        if (entity == null){
//            return null;
//        }
//        SmsVo smsVo = new SmsVo();
//        smsVo.setId(entity.getId());
//        smsVo.setContent(entity.getContent());
//        smsVo.setBuildOrgId(entity.getBuildOrgId());
//        smsVo.setBuildOrgName(entity.getBuildOrgName());
//        smsVo.setSendStatus(entity.getSendStatus());
//        smsVo.setSendEndTime(entity.getSendTime());
//        return smsVo;
//    }
//
//    @Override
//    default Sms voToEntity(SmsVo vo){
//        Sms entity = new Sms();
//        entity.setId(vo.getId());
//        entity.setContent(vo.getContent());
//        entity.setBuildOrgId(vo.getBuildOrgId());
//        entity.setBuildOrgName(vo.getBuildOrgName());
//        entity.setSendStatus(vo.getSendStatus());
//        entity.setSendTime(vo.getSendEndTime());
//        return entity;
//    }
}
