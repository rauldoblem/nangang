package com.taiji.emp.event.infoDispatch.mapper;

import com.taiji.emp.event.infoDispatch.entity.Accept;
import com.taiji.emp.event.infoDispatch.entity.AcceptDeal;
import com.taiji.emp.event.infoDispatch.vo.AccDealVo;
import com.taiji.emp.event.infoDispatch.vo.AcceptVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 报送信息处理类mapper AcceptDealMapper
 * @author qizhijie-pc
 * @date 2018年10月24日10:33:07
 */
@Mapper(componentModel = "spring")
public interface AcceptDealMapper extends BaseMapper<AcceptDeal,AccDealVo>{

    //接报信息处理类分页List转化为接报信息类Vo分页List
    default RestPageImpl<AcceptVo> AcceptDealPageToAcceptPage(Page<AcceptDeal> entityPage, Pageable page){

        if ( entityPage == null || page == null) {
            return null;
        }

        List<AcceptDeal> content = entityPage.getContent();

        List<AcceptVo> list = new ArrayList<AcceptVo>(content.size());

        for ( AcceptDeal acceptDeal : content ) {
            list.add( acceptDealToAcceptVo(acceptDeal));
        }

        RestPageImpl<AcceptVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;

    }

    //接报信息处理类转化为接报信息类Vo
    default AcceptVo acceptDealToAcceptVo(AcceptDeal entity){
        if ( entity == null ) {
            return null;
        }
        Accept accept = entity.getImAccept();
        if ( accept == null ) {
            return null;
        }

        AcceptVo acceptVo = new AcceptVo();

        acceptVo.setId( accept.getId() );
        acceptVo.setCreateTime( accept.getCreateTime() );
        acceptVo.setUpdateTime( accept.getUpdateTime() );
        acceptVo.setCreateBy( accept.getCreateBy() );
        acceptVo.setUpdateBy( accept.getUpdateBy() );
        acceptVo.setEventName( accept.getEventName() );
        acceptVo.setPosition( accept.getPosition() );
        acceptVo.setLonAndLat( accept.getLonAndLat() );
        acceptVo.setOccurTime( accept.getOccurTime() );
        acceptVo.setEventTypeId( accept.getEventTypeId() );
        acceptVo.setEventTypeName( accept.getEventTypeName() );
        acceptVo.setEventGradeId( accept.getEventGradeId() );
        acceptVo.setEventGradeName( accept.getEventGradeName() );
        acceptVo.setReportOrgId( accept.getReportOrgId() );
        acceptVo.setReportOrgName( accept.getReportOrgName() );
        acceptVo.setReporter( accept.getReporter() );
        acceptVo.setReporterTel( accept.getReporterTel() );
        acceptVo.setMethodId( accept.getMethodId() );
        acceptVo.setMethodName( accept.getMethodName() );
        acceptVo.setReportTime( accept.getReportTime() );
        acceptVo.setEventCause( accept.getEventCause() );
        acceptVo.setEventDesc( accept.getEventDesc() );
        acceptVo.setProtreatment( accept.getProtreatment() );
        acceptVo.setDeathNum( accept.getDeathNum() );
        acceptVo.setWondedNum( accept.getWondedNum() );
        acceptVo.setRequest( accept.getRequest() );
        acceptVo.setIsFirst( accept.getIsFirst() );
        acceptVo.setFirstReportId( accept.getFirstReportId() );
        acceptVo.setCreateOrgId( accept.getCreateOrgId() );

        return acceptVo;

    }


}
