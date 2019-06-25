package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Position;
import com.taiji.emp.res.repository.PositionRepository;
import com.taiji.emp.res.vo.PositionVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PositionService extends BaseService<Position,String>{

    @Autowired
    private PositionRepository repository;

    //不分页list查询
    public List<Position> findList(PositionVo positionVo){
        return repository.findList(positionVo);
    }

}
