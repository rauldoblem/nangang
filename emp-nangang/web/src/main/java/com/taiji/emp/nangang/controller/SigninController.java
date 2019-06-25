package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.searchVo.signin.SigninListVo;
import com.taiji.emp.nangang.searchVo.signin.SigninPageVo;
import com.taiji.emp.nangang.service.SigninService;
import com.taiji.emp.nangang.vo.SignInStatusVo;
import com.taiji.emp.nangang.vo.SigninVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/signins")
public class SigninController extends BaseController {
    @Autowired
    SigninService signinService;

    // 新增
    @PostMapping("/add")
    public ResultEntity addSignin(
            @Validated
            @NotNull(message = "SigninVo不能为null")
            @RequestBody SigninVo signinVo, Principal principal){
        String status = signinService.create(signinVo, principal);
        return ResultUtils.success(status);
    }
    //分页
    @PostMapping(path = "/search")
    public ResultEntity findSignin(@RequestBody SigninPageVo signinPageVo){
        RestPageImpl<SigninVo> pageVo = signinService.findPage(signinPageVo);
        return ResultUtils.success(pageVo);
    }

    //获取签入签出状态
    @PostMapping(path = "/signinStatus")
    public ResultEntity getSigninStatus(@RequestBody SigninListVo signinListVo){
        List<SigninVo> listVo = signinService.findList(signinListVo);
        if (!CollectionUtils.isEmpty(listVo)){
            SigninVo signinVo = listVo.get(0);
            SignInStatusVo signInStatusVo = new SignInStatusVo();
            signInStatusVo.setId(signinVo.getId());
            signInStatusVo.setSignStatus(signinVo.getSignStatus());
            signInStatusVo.setDutyPersonName(signinVo.getDutyPersonName());
            signInStatusVo.setDutyDate(signinListVo.getDutyDate());
            signInStatusVo.setDutyPersonId(signinListVo.getDutyPersonId());
            signInStatusVo.setDutyShiftPattern(signinListVo.getDutyShiftPattern());
            return ResultUtils.success(signInStatusVo);
        }else{
            SignInStatusVo signInStatusVo = new SignInStatusVo();
            signInStatusVo.setDutyDate(signinListVo.getDutyDate());
            signInStatusVo.setDutyPersonId(signinListVo.getDutyPersonId());
            signInStatusVo.setDutyShiftPattern(signinListVo.getDutyShiftPattern());
            return ResultUtils.success(signInStatusVo);
        }
    }

    /**
     * 获取当前班次人员的签入状态
     */
    @GetMapping(path = "/getSigninStatus")
    public ResultEntity getSigninStatus(Principal principal){
        String flag = signinService.getSigninStatus(principal);
        return ResultUtils.success(flag);
    }
}
