package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.service.PersonTypePatternService;
import com.taiji.emp.duty.vo.dailylog.PersonTypePatternVo;
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
public class PersonTypePatternController extends BaseController {

    @Autowired
    private PersonTypePatternService service;


    /**
     * 新增值班人员设置
     * @param personTypePatternVo
     * @return
     */
    @PostMapping(path = "/ptypePatterns")
    public ResultEntity createPersonTypePattern(
            @Validated
            @NotNull(message = "personTypePatternVo不能为null")
            @RequestBody PersonTypePatternVo personTypePatternVo,
            Principal principal){
        service.create(personTypePatternVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id删除某条值班人员设置信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/ptypePatterns/{id}")
    public ResultEntity deletePersonTypePattern(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 修改值班人员设置信息
     * @param personTypePatternVo
     * @param principal
     * @param id
     * @return
     */
    @PutMapping("/ptypePatterns/{id}")
    public ResultEntity updatePersonTypePattern(
            @NotNull(message = "personTypePatternVo不能为null")
            @RequestBody PersonTypePatternVo personTypePatternVo,
            Principal principal,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        personTypePatternVo.setId(id);
        service.update(personTypePatternVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id查询值班人员设置信息
     * @param id
     * @return
     */
    @GetMapping(path = "/ptypePatterns/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id){
       PersonTypePatternVo vo = service.findOne(id);
       return ResultUtils.success(vo);
    }


    /**
     * 根据条件查询值班人员设置列表
     * @param id
     * @return
     */
    @GetMapping(path = "/searchPtypePatterns")
    public ResultEntity findAll(
            @NotEmpty(message = "patterId不能为空")
            @RequestParam(value = "patternId") String id){
        List<PersonTypePatternVo>  personTypePatternVo = service.findList(id);
        return ResultUtils.success(personTypePatternVo);
    }
}
