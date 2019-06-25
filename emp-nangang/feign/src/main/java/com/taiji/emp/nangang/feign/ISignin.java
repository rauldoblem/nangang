package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.searchVo.signin.SigninListVo;
import com.taiji.emp.nangang.searchVo.signin.SigninPageVo;
import com.taiji.emp.nangang.vo.SigninVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
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
     * 新增传真SigninVo,SigninVo不能为空
     * @param vo
     * @return ResponseEntity<FaxVo></>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<SigninVo> create(@RequestBody SigninVo vo);

    /**
     * 根据参数获取SigninVo多条记录
     * 查询参数 dutyDate(可选),dutyShiftPattern(可选),dutyPersonId(可选)
     *  @param signinListVo
     *  @return ResponseEntity<List<FaxVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<SigninVo>> findList(@RequestBody SigninListVo signinListVo);

    /**
     * 根据参数获取FaxVo多条记录,分页信息
     * 查询参数 dutyPersonName(可选),checkDateStart(可选),checkDateEnd(可选)
     *          page,size
     *  @param signinPageVo
     *  @return ResponseEntity<RestPageImpl<FaxVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<SigninVo>> findPage(@RequestBody SigninPageVo signinPageVo);
}
