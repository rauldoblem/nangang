package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.DocAttachment;
import com.taiji.emp.base.entity.QDocAttachment;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class DocAttRepository extends BaseJpaRepository<DocAttachment,String> {

    //根据业务id查找附件list
    public List<DocAttachment> findList(String entityId){
        Assert.hasText(entityId,"业务id不能为空字符串");
        QDocAttachment docAttachment = QDocAttachment.docAttachment;
        JPQLQuery<DocAttachment> query = from(docAttachment);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(docAttachment.ywid.eq(entityId));
        builder.and(docAttachment.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(docAttachment.uploadTime.desc());
        return findAll(query);  //按上传时间倒叙排列
    }

    //给附件list赋值业务id
    @Transactional
    public void saveDocEntity(DocEntityVo docEntityVo){

        String entityId =docEntityVo.getEntityId();
        Assert.hasText(entityId,"业务id不能为空字符串");

        List<String> docAttIds = docEntityVo.getDocAttIds();
        List<String> docAttDelIds = docEntityVo.getDocAttDelIds();
//        Assert.notNull(docAttIds,"附件list不能为null");

        //删除部分
        if(null!=docAttDelIds&&docAttDelIds.size()>0){
            for(String docAttDelId : docAttDelIds){
                DocAttachment docDel= super.findOne(docAttDelId);
                if(null!=docDel){
                    docDel.setDelFlag(DelFlagEnum.DELETE.getCode());
                    super.save(docDel);
                }
            }
        }

        //新增编辑部分
        if(null!=docAttIds&&docAttIds.size()>0){
            for(String docAttId : docAttIds){
                DocAttachment doc= super.findOne(docAttId);
                if(null!=doc&&StringUtils.isEmpty(doc.getYwid())){  //这里的赋值只需要给新添加的附件赋值
                    doc.setYwid(entityId);
                    super.save(doc);
                }
            }
        }

    }

}
