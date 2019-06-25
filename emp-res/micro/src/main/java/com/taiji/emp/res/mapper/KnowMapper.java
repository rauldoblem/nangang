package com.taiji.emp.res.mapper;

import com.taiji.emp.res.entity.Knowledge;
import com.taiji.emp.res.vo.KnowledgeVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 应急知识mapper KnowMapper
 * @author qizhijie-pc
 * @date 2018年9月18日17:24:06
 */
@Mapper(componentModel = "spring")
public interface KnowMapper extends BaseMapper<Knowledge,KnowledgeVo>{

    @Override
    default RestPageImpl<KnowledgeVo> entityPageToVoPage(Page<Knowledge> entityPage, Pageable page){
        if ( entityPage == null || page == null) {
            return null;
        }

        List<Knowledge> content = entityPage.getContent();

        List<KnowledgeVo> list = new ArrayList<KnowledgeVo>(content.size());

        for ( Knowledge entity : content ) {
            list.add( entityToVoForList(entity) );
        }

        RestPageImpl<KnowledgeVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;
    }

    default KnowledgeVo entityToVoForList(Knowledge entity){
        if ( entity == null ) {
            return null;
        }

        KnowledgeVo knowledgeVo = new KnowledgeVo();

        knowledgeVo.setId( entity.getId() );
        knowledgeVo.setCreateTime( entity.getCreateTime() );
        knowledgeVo.setUpdateTime( entity.getUpdateTime() );
        knowledgeVo.setCreateBy( entity.getCreateBy() );
        knowledgeVo.setUpdateBy( entity.getUpdateBy() );
        knowledgeVo.setTitle( entity.getTitle() );
        knowledgeVo.setKnoTypeId( entity.getKnoTypeId() );
        knowledgeVo.setKnoTypeName( entity.getKnoTypeName() );
        knowledgeVo.setEventTypeId( entity.getEventTypeId() );
        knowledgeVo.setEventTypeName( entity.getEventTypeName() );
        knowledgeVo.setSource( entity.getSource() );
        knowledgeVo.setKeyWord( entity.getKeyWord() );

        String content = entity.getContent();
        if(StringUtils.hasText(content)){
            content = content.length()>300?content.substring(0,300):content;  //content超过300个字符就截取前300个字符
        }

        knowledgeVo.setContent(content);

        knowledgeVo.setCreateOrgId(entity.getCreateOrgId());
        knowledgeVo.setCreateOrgName(entity.getCreateOrgName());

        return knowledgeVo;
    }
}
