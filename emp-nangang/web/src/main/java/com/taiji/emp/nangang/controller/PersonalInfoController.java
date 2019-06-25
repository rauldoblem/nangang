package com.taiji.emp.nangang.controller;

import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.nangang.service.PersonalInfoService;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/appNg")
public class PersonalInfoController extends BaseController{

    PersonalInfoService userService;

    /**
     * 获取单个用户信息
     * @return
     */
    @GetMapping(path = "/getUser")
    public ResultEntity findUserById(OAuth2Authentication principal){
        UserVo userVo = userService.findById(principal);
        return ResultUtils.success(userVo);
    }
}
