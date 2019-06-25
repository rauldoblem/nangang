package com.taiji.base.msg.service;

import com.querydsl.core.BooleanBuilder;
import com.taiji.base.msg.entity.MsgNotice;
import com.taiji.base.msg.entity.MsgType;
import com.taiji.base.msg.entity.QMsgType;
import com.taiji.base.msg.repository.MsgTypeRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>Title:MsgTypeService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/31 11:31</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class MsgTypeService extends BaseService<MsgType,String> {
    MsgTypeRepository repository;

    /**
     * 根据参数获取MsgType多条记录。
     *
     * @param moduleName 模块代码 （必选）
     * @param type   类型 0：通知，1：待办（可选）
     * @return List<MsgType>
     */
    public List<MsgType> findAllByModuleName(String moduleName, String type){
        return repository.findAllByModuleName(moduleName,type);
    }

    public MsgType findOneByCode(String code) {
        return repository.findOneByCode(code);
    }
}
