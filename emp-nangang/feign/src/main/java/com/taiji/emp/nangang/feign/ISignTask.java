package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.vo.SigninVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "micro-nangang-task-signin")
public interface ISignTask {
    /**
     * 新增传真SigninVo,SigninVo不能为空
     * @param vo
     * @return ResponseEntity<FaxVo></>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<SigninVo> create(@RequestBody SigninVo vo);

    @RequestMapping(method = RequestMethod.GET,path = "/findDailyLogId/{dailyCheckItemsVoId}")
    @ResponseBody
    ResponseEntity<String> findTaskDailyLogId(@PathVariable(value = "dailyCheckItemsVoId") String dailyCheckItemsVoId);

}
