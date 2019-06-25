package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Position;
import com.taiji.emp.res.feign.IPositionRestService;
import com.taiji.emp.res.mapper.PositionMapper;
import com.taiji.emp.res.service.PositionService;
import com.taiji.emp.res.vo.PositionVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/positions")
public class PositionController extends BaseController implements IPositionRestService {

    PositionService service;
    PositionMapper mapper;

    /**
     * 根据参数获取PositionVo多条记录
     *  @param positionVo
     *  @return ResponseEntity<List<PositionVo>>
     */
    @Override
    public ResponseEntity<List<PositionVo>> findList(@RequestBody PositionVo positionVo) {
        List<Position> result = service.findList(positionVo);
        List<PositionVo> resultVo = mapper.entityListToVoList(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

}
