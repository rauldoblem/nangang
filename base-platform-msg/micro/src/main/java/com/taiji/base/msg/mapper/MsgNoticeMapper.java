package com.taiji.base.msg.mapper;

import com.taiji.base.msg.entity.MsgNotice;
import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import com.taiji.base.msg.vo.MsgNoticeVo;
import org.mapstruct.*;

/**
 * <p>Title:MsgNoticeMapper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 18:46</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface MsgNoticeMapper {
    @Mappings({
                      @Mapping(source = "msgType.id", target = "msgType"),
                      @Mapping(source = "msgType.moduleName",target = "typeName"),
                      @Mapping(source = "msgType.icon",target = "icon"),
                      @Mapping(source = "msgType.path",target = "path")
              })
    @InheritInverseConfiguration
    MsgNoticeVo entityToVo(MsgNotice entity);

    @Mappings({
                      @Mapping(source = "msgType", target = "msgType.id"),
                      @Mapping(source = "typeName",target = "msgType.moduleName"),
                      @Mapping(source = "icon",target = "msgType.icon"),
                      @Mapping(source = "path",target = "msgType.path")
              })
    MsgNotice voToEntity(MsgNoticeVo vo);
}
