package com.taiji.base.msg.controller;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.msg.enums.ReadFlagEnum;
import com.taiji.base.msg.service.MsgNoticeRecordService;
import com.taiji.base.msg.service.MsgNoticeService;
import com.taiji.base.msg.service.MsgTypeService;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * <p>Title:NoticeController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 14:06</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/notices")
public class NoticeController extends BaseController{

    MsgNoticeService noticeService;
    MsgNoticeRecordService recordService;
    MsgTypeService typeService;

    @GetMapping
    public ResultEntity findNotices(OAuth2Authentication principal){

        LinkedHashMap<String,Object> principalMap = SecurityUtils.getPrincipalMap(principal);
        String userId = (String)principalMap.get("id");

        Map<String, Object> params  = new HashMap<>();
        params.put("receiverId",userId);

        List<MsgNoticeRecordVo> list = recordService.findNoticeList(params);

        return ResultUtils.success(list);
    }

    /**

     {
     "title":"title",
     "sendOrg":"sendOrg",
     "orgName":"orgName",
     "msgContent":"msgContentAAAAAA",
     "msgType":"1",
     "typeName":"test-websocket",
     "icon":"icon",
     "path":"path",
     "entityId":"1",
     "receivers":[
     {
     "id":"1",
     "name":"god"
     }
     ]
     }

     * @param msgNoticeVo
     * @param principal
     * @return
     */
    @PostMapping
    public ResultEntity addNotice(@RequestBody MsgNoticeVo msgNoticeVo, OAuth2Authentication principal){

        noticeService.addNotice(msgNoticeVo);

        return ResultUtils.success();
    }

    @PostMapping(path = "/clear")
    public ResultEntity noticeClear(@RequestBody Map<String, Object> paramsMap){

        if (paramsMap.containsKey("recordIds")) {
            List<String> recordIds = (List<String>) paramsMap.get("recordIds");
            if(!CollectionUtils.isEmpty(recordIds)){
                List<MsgNoticeRecordVo> vos = new ArrayList<>();
                for (String recordId: recordIds) {
                    MsgNoticeRecordVo vo = new MsgNoticeRecordVo();
                    vo.setReadFlag(ReadFlagEnum.READ.getCode());
                    vo.setId(recordId);
                    vos.add(vo);
                }
                recordService.updateRecordList(vos);
            }

            return ResultUtils.success();
        } else {
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }
}
