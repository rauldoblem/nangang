package com.taiji.emp.zn.controller;

import com.taiji.emp.zn.feign.IDateStatRestService;
import com.taiji.emp.zn.service.DateStatService;
import com.taiji.emp.zn.vo.EventAndAlertDateStatVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/dateStat")
public class DateStatController implements IDateStatRestService{

    DateStatService service;

    /**
     * 按时间统计突发事件(事发时间--occurTime)总数和预警(创建时间--createTime)总数
     *
     * @param params statDate: List<String>  -- example:{'2018-02','2018-03',...}
     *               alertStatus : List<String> 预警状态 -- example:{'2','3',...}
     * @return ResponseEntity<EventAndAlertDateStatVo>
     */
    @Override
    public ResponseEntity<EventAndAlertDateStatVo> statEventAndAlertDate(
            @RequestParam MultiValueMap<String, Object> params) {
        EventAndAlertDateStatVo resultVo = service.statEventAndAlertDate(params);
        return ResponseEntity.ok(resultVo);
    }
}
