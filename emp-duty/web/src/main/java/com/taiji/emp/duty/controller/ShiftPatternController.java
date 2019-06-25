package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.service.ShiftPatternService;
import com.taiji.emp.duty.vo.dailylog.ShiftPatternVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/patternsettings")
public class ShiftPatternController extends BaseController {

    @Autowired
    private ShiftPatternService service;


    /**
     * 新增班次设置
     * @param shiftPatternVo
     * @return
     */
    @PostMapping(path = "/shiftPatterns")
    public ResultEntity createShiftPattern(
            @Validated
            @NotNull(message = "shiftPatternVo不能为null")
            @RequestBody ShiftPatternVo shiftPatternVo,
            Principal principal){
        service.create(shiftPatternVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id删除某条班次设置信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/shiftPatterns/{id}")
    public ResultEntity deleteShiftPattern(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 修改班次设置信息
     * @param shiftPatternVo
     * @param principal
     * @param id
     * @return
     */
    @PutMapping("/shiftPatterns/{id}")
    public ResultEntity updateShiftPattern(
            @NotNull(message = "shiftPatternVo不能为null")
            @RequestBody ShiftPatternVo shiftPatternVo,
            Principal principal,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        service.update(shiftPatternVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id查询班次设置信息
     * @param id
     * @return
     */
    @GetMapping(path = "/shiftPatterns/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id){
       ShiftPatternVo vo = service.findOne(id);
       return ResultUtils.success(vo);
    }


    /**
     * 根据条件查询班次设置列表
     * @param id
     * @return
     */
    @GetMapping(path = "/searchShiftPatterns")
    public ResultEntity findAll(
            @NotEmpty(message = "patterId不能为空")
            @RequestParam(value = "patterId") String id){
        List<ShiftPatternVo>  shiftPatternVo = service.findList(id);
        return ResultUtils.success(shiftPatternVo);
    }
}
