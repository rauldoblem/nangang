package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.caseVo.CasePageVo;
import com.taiji.emp.res.service.CaseService;
import com.taiji.emp.res.vo.CaseEntityVo;
import com.taiji.emp.res.vo.CaseSaveVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/cases")
public class CasesController extends BaseController {

    private CaseService service;
    /**
     * 新增案例信息
     */
    @PostMapping
    public ResultEntity createCase(
            @Validated
            @NotNull(message = "CaseSaveVo 不能为null")
            @RequestBody CaseSaveVo caseSaveVo, Principal principal){
        service.create(caseSaveVo,principal);
        return ResultUtils.success();
    }


    /**
     * 修改案例信息
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateCase(
            @Validated
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            @NotNull(message = "CaseSaveVo 不能为null")
            @RequestBody CaseSaveVo caseSaveVo, Principal principal){
        service.update(caseSaveVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条案例信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findCaseById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        CaseEntityVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条案例信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteCase(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询案例信息列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(
            @RequestBody CasePageVo casePageVo){
            RestPageImpl<CaseEntityVo> pageVo = service.findPage(casePageVo);
            return ResultUtils.success(pageVo);
    }

    /**
     * 查询案例信息列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(
            @RequestBody CasePageVo casePageVo){
        List<CaseEntityVo> listvo = service.findList(casePageVo);
        return ResultUtils.success(listvo);
    }
}
