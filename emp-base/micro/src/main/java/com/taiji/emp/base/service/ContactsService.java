package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.Contact;
import com.taiji.emp.base.repository.ContactRepository;
import com.taiji.emp.base.searchVo.contacts.ContactPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
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
public class ContactsService extends BaseService<Contact,String> {

    @Autowired
    private ContactRepository repository;

    public Contact create(Contact entity){
        Assert.notNull(entity,"Contact 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Contact result = repository.save(entity);
        return result;
    }

    public Contact update(Contact entity){
        Assert.notNull(entity,"Contact 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Contact result = repository.save(entity);
        return result;
    }

    public Contact findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        Contact result = repository.findOne(id);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Contact entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
}

    //提供给controller使用的 分页list查询方法
    public Page<Contact> findPage(ContactPageVo contactPageVo, Pageable pageable){
        Page<Contact> result = repository.findPage(contactPageVo,pageable);
        return result;
    }

    public List<Contact> findList(ContactPageVo contactPageVo){
        List<Contact> result = repository.findList(contactPageVo);
        return result;
    }

}
