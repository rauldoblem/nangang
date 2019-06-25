package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.Analyse;
import com.taiji.emp.event.cmd.feign.IAnalyseRestService;
import com.taiji.emp.event.cmd.mapper.AnalyseMapper;
import com.taiji.emp.event.cmd.service.AnalyseService;
import com.taiji.emp.event.cmd.vo.AnalyseVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/analyses")
public class AnalyseController extends BaseController implements IAnalyseRestService{

    @Autowired
    AnalyseService service;
    @Autowired
    AnalyseMapper mapper;

    /**
     * 新增事件分析研判意见AnalyseVo
     *
     * @param vo
     * @return ResponseEntity<AnalyseVo>
     */
    @Override
    public ResponseEntity<AnalyseVo> create(
            @Validated
            @NotNull(message = "AnalyseVo 不能为null")
            @RequestBody AnalyseVo vo) {
        Analyse entity = mapper.voToEntity(vo);
        Analyse result = service.create(entity);
        AnalyseVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id 获取事件分析研判意见AnalyseVo
     *
     * @param id id不能为空
     * @return ResponseEntity<AnalyseVo>
     */
    @Override
    public ResponseEntity<AnalyseVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        Analyse result = service.findOne(id);
        AnalyseVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 更新事件分析研判意见AnalyseVo
     *
     * @param vo
     * @param id 要更新AnalyseVo id
     * @return ResponseEntity<AnalyseVo>
     */
    @Override
    public ResponseEntity<AnalyseVo> update(
            @Validated
            @NotNull(message = "AnalyseVo 不能为null")
            @RequestBody AnalyseVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        Analyse entity = mapper.voToEntity(vo);
        Analyse result = service.update(entity,id);
        AnalyseVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id删除一条事件分析研判意见。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据条件查询事件分析研判结果列表-不分页AnalyseVo list
     * 参数keys：eventId
     *
     * @param params
     * @return ResponseEntity<List < AnalyseVo>>
     */
    @Override
    public ResponseEntity<List<AnalyseVo>> searchAll(@RequestParam MultiValueMap<String,Object> params) {
        List<Analyse> list = service.findList(params);
        List<AnalyseVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList,HttpStatus.OK);
    }
}
