package com.taiji.emp.base.controller;

import com.taiji.emp.base.searchVo.fax.FaxListVo;
import com.taiji.emp.base.searchVo.fax.FaxPageVo;
import com.taiji.emp.base.service.FaxService;
import com.taiji.emp.base.vo.FaxSaveVo;
import com.taiji.emp.base.vo.FaxVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.Value;
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
@RequestMapping("/faxs")

public class FaxController extends BaseController{
    @Autowired
    FaxService faxService;

    /**
     * 新增传真
     */
    @PostMapping
    public ResultEntity addFax(
            @Validated
            @NotNull(message = "FaxVo不能为null")
            @RequestBody FaxSaveVo faxSaveVo, Principal principal){
        faxService.create(faxSaveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改传真
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateFax(
            @NotNull(message = "FaxVo不能为null")
            @RequestBody FaxSaveVo faxSaveVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            Principal principal){
        faxService.update(faxSaveVo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条传真信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findFaxById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        FaxVo vo = faxService.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条传真信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteFax(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        faxService.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询应急传真列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findFaxs(@RequestBody FaxPageVo faxPageVo){
        RestPageImpl<FaxVo> pageVo = faxService.findPage(faxPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询应急传真列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findFaxsAll(@RequestBody FaxListVo faxListVo){
        List<FaxVo> listVo = faxService.findList(faxListVo);
        return ResultUtils.success(listVo);
    }
}
