package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.Signin;
import com.taiji.emp.nangang.feign.ISignTask;
import com.taiji.emp.nangang.mapper.SigninMapper;
import com.taiji.emp.nangang.service.DailyCheckItemsService;
import com.taiji.emp.nangang.service.SigninService;
import com.taiji.emp.nangang.vo.SigninVo;
import com.taiji.micro.common.service.UtilsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/signins/task")
public class SignTaskController extends BaseController implements ISignTask {

    SigninService signinService;
    SigninMapper signinMapper;
    UtilsService utilsService;
    DailyCheckItemsService service;

    /**
     * 新增传真SigninVo,SigninVo不能为空
     * @param vo
     * @return ResponseEntity<FaxVo></>
     */
    @Override
    public ResponseEntity<SigninVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody SigninVo vo) {
        String signStatus = vo.getSignStatus();
        if (!StringUtils.isEmpty(signStatus)){
            if (signStatus.equals("1")){
                vo.setCheckInTime(utilsService.now());
            }else if(signStatus.equals("2")){
                vo.setCheckOutTime(utilsService.now());
            }
        }
        Signin entity = signinMapper.voToEntity(vo);
        Signin result = signinService.create(entity);
        SigninVo resultVo = signinMapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }


    /**
     * 根据dailyCheckItemsVoId查询对应的dailyLogId
     * （从中间表ed_dailycheck_dailylog查）
     */
    public ResponseEntity<String> findTaskDailyLogId(
            @Validated
            @NotEmpty(message = "dailyCheckItemsVoId不能为空")
            @PathVariable(value = "dailyCheckItemsVoId") String dailyCheckItemsVoId) {
        String dailyLogId = service.findDailyLogId(dailyCheckItemsVoId);
        return ResponseEntity.ok(dailyLogId);
    }
}
