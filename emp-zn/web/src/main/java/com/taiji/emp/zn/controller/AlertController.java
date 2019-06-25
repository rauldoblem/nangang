package com.taiji.emp.zn.controller;

import com.taiji.emp.zn.service.AlertService;
import com.taiji.emp.zn.vo.PushAlertVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/22 20:22
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertService service;
    /**
     * 保存外部传过来的预警数据
     */
    @PostMapping("/pushAlert")
    public ResultEntity savePushAlert(
            @Validated
            @NotNull(message = "pushAlertVo不能为null")
            @RequestBody PushAlertVo pushAlertVo) {
        service.savePushAlert(pushAlertVo);
        return ResultUtils.success();
    }
}
