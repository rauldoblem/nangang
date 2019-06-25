package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.vo.SigninListVo;
import com.taiji.emp.duty.vo.SigninVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@FeignClient(value = "micro-nangang-signin")
public interface ISignin {

    /**
     * 根据参数获取SigninVo多条记录
     * 查询参数 dutyDate(可选),dutyShiftPattern(可选),dutyPersonId(可选)
     *  @param signinListVo
     *  @return ResponseEntity<List<FaxVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<SigninVo>> findList(@RequestBody SigninListVo signinListVo);

}
