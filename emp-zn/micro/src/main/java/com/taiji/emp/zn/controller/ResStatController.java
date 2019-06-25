package com.taiji.emp.zn.controller;

import com.taiji.emp.zn.feign.IResStatRestService;
import com.taiji.emp.zn.service.ResStatService;
import com.taiji.emp.zn.vo.EventTypeStatVo;
import com.taiji.emp.zn.vo.TargetTypeStatVo;
import com.taiji.emp.zn.vo.TeamTypeStatVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/resStat")
public class ResStatController implements IResStatRestService {

    ResStatService service;

    /**
     * 统计重点防护目标各大类数量 -- 目标类型
     *
     * @return ResponseEntity<List<TargetTypeStatVo>>
     */
    @Override
    public ResponseEntity<List<TargetTypeStatVo>> statTargetByType() {
        List<TargetTypeStatVo> resultList = service.statTargetByType();
        return ResponseEntity.ok(resultList);
    }

    /**
     * 统计应急专家各大类数量 -- 事件类型
     *
     * @return ResponseEntity<List<EventTypeStatVo>>
     */
    @Override
    public ResponseEntity<List<EventTypeStatVo>> statExpertByType() {
        List<EventTypeStatVo> resultList = service.statExpertByType();
        return ResponseEntity.ok(resultList);
    }

    /**
     * 统计应急队伍各大类数量 -- 队伍类型
     *
     * @return ResponseEntity<List<TeamTypeStatVo>>
     */
    @Override
    public ResponseEntity<List<TeamTypeStatVo>> statTeamByType() {
        List<TeamTypeStatVo> resultList = service.statTeamByType();
        return ResponseEntity.ok(resultList);
    }

}
