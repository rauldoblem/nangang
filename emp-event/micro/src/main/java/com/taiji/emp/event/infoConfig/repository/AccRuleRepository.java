package com.taiji.emp.event.infoConfig.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.emp.event.infoConfig.entity.AcceptRule;
import com.taiji.emp.event.infoConfig.entity.QAcceptRule;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class AccRuleRepository extends BaseJpaRepository<AcceptRule,String> {

    @Transactional
    @Override
    public AcceptRule save(AcceptRule entity){
        Assert.notNull(entity,"AcceptRule对象不能为 null");
        AcceptRule result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){ //新增保存 --- 保证一个类型只能一个接报要求
            String eventTypeId = entity.getEventTypeId();
            Assert.hasText(eventTypeId,"eventTypeId 不能为空");
            AcceptRule exsitTemp = checkTypeUnique(eventTypeId);
            if(null==exsitTemp){ //库中不存在--- 新增操作
                result = super.save(entity);
            }else{//库中存在--- 编辑操作
                BeanUtils.copyNonNullProperties(entity,exsitTemp);
                result = super.save(exsitTemp);
            }
        }else {//编辑保存
            AcceptRule temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    //判断该类型下是否有接报要求
    private AcceptRule checkTypeUnique(String eventTypeId){
        QAcceptRule acceptRule = QAcceptRule.acceptRule;
        List<AcceptRule> list = findAll(acceptRule.eventTypeId.eq(eventTypeId));
        if(null!=list&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public AcceptRule getRuleSetting(MultiValueMap<String,Object> params){

        QAcceptRule acceptRule = QAcceptRule.acceptRule;

        BooleanBuilder builder = new BooleanBuilder();

        if(params.containsKey("eventTypeId")){
            builder.and(acceptRule.eventTypeId.eq(params.getFirst("eventTypeId").toString()));
        }else{
            return null;
        }

        return findOne(builder);

    }

    /**
     * 获取一键事故的描述信息
     * @param eventTypeId
     * @return
     */
    public List<AcceptRule> eventDesc(String eventTypeId) {
        QAcceptRule acceptRule = QAcceptRule.acceptRule;
        List<AcceptRule> list = findAll(acceptRule.eventTypeId.eq(eventTypeId));
        return list;
    }
}
