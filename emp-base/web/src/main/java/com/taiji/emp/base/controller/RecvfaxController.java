package com.taiji.emp.base.controller;

import com.taiji.emp.base.searchVo.fax.RecvfaxPageVo;
import com.taiji.emp.base.service.RecvfaxService;
import com.taiji.emp.base.vo.RecvfaxVo;
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
@RequestMapping("/recvfaxs")
public class RecvfaxController extends BaseController{
    @Autowired
    RecvfaxService recvfaxService;

    /**
     * 获取已接收传真列表——分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findRecvfaxs(@RequestBody RecvfaxPageVo recvfaxPageVo) {
        RestPageImpl<RecvfaxVo> pageVo = recvfaxService.findPage(recvfaxPageVo);
        return ResultUtils.success(pageVo);
    }
}
