package com.taiji.emp.zn.service;

import com.taiji.emp.zn.repository.ResStatRepository;
import com.taiji.emp.zn.vo.EventTypeStatVo;
import com.taiji.emp.zn.vo.TargetTypeStatVo;
import com.taiji.emp.zn.vo.TeamTypeStatVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ResStatService {

    ResStatRepository repository;

    /**
     * 统计重点防护目标各大类数量 -- 目标类型
     */
    public List<TargetTypeStatVo> statTargetByType(){
        return repository.statTargetByType();
    }

    /**
     * 统计应急专家各大类数量 -- 事件类型
     */
    public List<EventTypeStatVo> statExpertByType(){
        return repository.statExpertByType();
    }

    /**
     * 统计应急队伍各大类数量 -- 队伍类型
     */
    public List<TeamTypeStatVo> statTeamByType(){
        return repository.statTeamByType();
    }
}
