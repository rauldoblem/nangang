package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.searchVo.CalenSettingListVo;
import com.taiji.emp.duty.service.CalenSettingService;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/calensettings")
public class CalenSettingController extends BaseController {

    @Autowired
    private CalenSettingService service;

    /**
     * 新增日历设置
     * @param calenSettingListVo
     * @return
     */
    @PostMapping(path = "/saveCalen")
    public ResultEntity createCalenSetting(
            @Validated
            @NotNull(message = "calenSettingListVo不能为null")
            @RequestBody CalenSettingListVo calenSettingListVo,
            OAuth2Authentication principal){
        service.create(calenSettingListVo,principal);
        return ResultUtils.success();
    }
    
    /**
     *  根据条件查询日历设置列表
     * @param calenSettingVo
     * @return
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody CalenSettingVo calenSettingVo, OAuth2Authentication principal){
        CalenSettingListVo listVo = service.findList(calenSettingVo,principal);
        return ResultUtils.success(listVo);
    }

}
