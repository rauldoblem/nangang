package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.searchVo.PersonListVo;
import com.taiji.emp.duty.searchVo.PersonPageVo;
import com.taiji.emp.duty.service.PersonService;
import com.taiji.emp.duty.vo.PersonVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/persons")
public class PersonController extends BaseController {

    @Autowired
    private PersonService service;

    /**
     * 新增值班人员
     * @param vo
     * @return
     */
    @PostMapping
    public ResultEntity createPerson(
            @Validated
            @NotNull(message = "PersonListVo不能为null")
            @RequestBody PersonListVo vo,
            Principal principal){
        service.create(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id删除某条值班人员信息
     * @param vo
     * @return
     */
    @PostMapping(path = "/deletePersons")
    public ResultEntity deletePerson(
            @NotNull(message = "PersonListVo不能为null")
            @RequestBody PersonListVo vo){
        service.deleteLogic(vo);
        return ResultUtils.success();
    }

    /**
     * 修改值班人员信息
     * @param personVo
     * @param principal
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResultEntity updatePerson(
            @NotNull(message = "personVo不能为null")
            @RequestBody PersonVo personVo,
            Principal principal,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        service.update(personVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id查询值班人员信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id){
       PersonVo vo = service.findOne(id);
       return ResultUtils.success(vo);
    }

    /**
     *  根据条件查询值班人员列表
     * @param vo
     * @return
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody PersonListVo vo){
        List<PersonVo> listVo = service.findList(vo);
        return ResultUtils.success(listVo);
    }

    /**
     *  根据条件查询值班人员列表换班
     * @param vo
     * @return
     */
    @PostMapping(path = "/searchExchangeAll")
    public ResultEntity findexchangeList(@RequestBody PersonListVo vo){
        List<PersonVo> listVo = service.findList(vo);
//            //去掉已排过的人员
//            List<String> personIds = vo.getPersonIds();
//            for (String str : personIds) {
//                for (PersonVo p : listVo) {
//                    if (p.getAddrId().equals(str)){
//                        p.setFlag("1");
//                    }
//                }
//        }

        return ResultUtils.success(listVo);
    }

    /**
     * 根据条件查询值班人员列表——分页
     * @param vo
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody PersonPageVo vo){
        RestPageImpl<PersonVo> pageVo = service.findPage(vo);
        return ResultUtils.success(pageVo);
    }
}
