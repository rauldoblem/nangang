package com.taiji.emp.base.controller;

import com.taiji.emp.base.searchVo.fax.SendfaxPageVo;
import com.taiji.emp.base.service.SendfaxService;
import com.taiji.emp.base.vo.SendfaxVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sendfaxs")
public class SendfaxController extends BaseController{
    @Autowired
    SendfaxService sendfaxService;

    /**
     * 获取已发送传真列表——分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findSendfaxs(@RequestBody SendfaxPageVo sendfaxPageVo) {
        RestPageImpl<SendfaxVo> pageVo = sendfaxService.findPage(sendfaxPageVo);
        return ResultUtils.success(pageVo);
    }
}
