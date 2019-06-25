package com.taiji.emp.base.service;

import com.taiji.emp.base.common.constant.FileGlobal;
import com.taiji.emp.base.entity.DocAttachment;
import com.taiji.emp.base.repository.DocAttRepository;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DocAttService extends BaseService<DocAttachment,String> {

    @Autowired
    private DocAttRepository repository;

    public DocAttachment findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        DocAttachment result = repository.findOne(id);
        return result;
    }

    public List<DocAttachment> findList(String entityId){
        Assert.hasText(entityId,"entityId不能为null或空字符串!");
        List<DocAttachment> resultList = repository.findList(entityId);
        return resultList;
    }

    public void saveDocEntity(DocEntityVo docEntityVo){
        repository.saveDocEntity(docEntityVo);
    }

    public void deleteLogic(String id,DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        DocAttachment doc = repository.findOne(id);
        repository.deleteLogic(doc,delFlagEnum);
    }

    public DocAttachment createDoc(DocAttachment doc){
        Assert.notNull(doc,"附件对象不能为null");
        doc.setDelFlag(DelFlagEnum.NORMAL.getCode());
        doc.setType(getDocTypeByExt(doc.getSuffix()));
        return repository.save(doc);
    }

    /**
     * 根据后缀名 得到媒体类型
     * @param fileExt
     * @return picture,audio,video,word,others
     */
    private String getDocTypeByExt(String fileExt){
        fileExt = fileExt.toLowerCase(); //转小写，方便匹配
        String type = "";
        if(FileGlobal.DOC_TYPE_MAP.containsKey(fileExt)){
            type = FileGlobal.DOC_TYPE_MAP.get(fileExt);
        }else{
            type = FileGlobal.DOC_OTHERS_TYPE;
        }
        return type;
    }

}
