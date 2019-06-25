package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.OrgRespon;
import com.taiji.emp.event.cmd.entity.OrgResponDetail;
import com.taiji.emp.event.cmd.feign.IOrgResponRestService;
import com.taiji.emp.event.cmd.mapper.OrgResponMapper;
import com.taiji.emp.event.cmd.service.OrgResponService;
import com.taiji.emp.event.cmd.vo.OrgResponVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 *  应急处置--关联应急责任单位/人接口服务实现类
 * @author qizhijie-pc
 * @date 2018年11月5日16:49:18
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/orgrespons")
public class OrgResponController extends BaseController implements IOrgResponRestService{

    @Autowired
    OrgResponService service;
    @Autowired
    OrgResponMapper mapper;


    /**
     * 新增单个应急责任单位/人
     *
     * @param vo
     * @return ResponseEntity<OrgResponVo>
     */
    @Override
    public ResponseEntity<OrgResponVo> createOne(
            @Validated
            @NotNull(message = "OrgResponVo 不能为null")
            @RequestBody OrgResponVo vo) {
        OrgRespon entity = mapper.voToEntity(vo);
        OrgRespon result = service.createOne(entity);
        OrgResponVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 获取单个应急责任单位/人
     *
     * @param id 应急责任单位/人id
     * @return ResponseEntity<OrgResponVo>
     */
    @Override
    public ResponseEntity<OrgResponVo> findOne(
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id")String id) {
        OrgRespon result = service.findOne(id);
        result = getOrgRespon(result);
        OrgResponVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新单个应急责任单位/人
     *
     * @param vo
     * @param id
     * @return ResponseEntity<OrgResponVo>
     */
    @Override
    public ResponseEntity<OrgResponVo> update(
            @Validated
            @NotNull(message = "OrgResponVo 不能为null")
            @RequestBody OrgResponVo vo,
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id) {
        OrgRespon entity = mapper.voToEntity(vo);
        OrgRespon result = service.update(entity,id);
        OrgResponVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 删除应急责任单位/人 -- 级联删除从表信息
     *
     * @param id 应急责任单位/人id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 查询应急责任单位/人
     * @param params 参数key:emgOrgId
     * @return ResponseEntity<List < OrgResponVo>>
     */
    @Override
    public ResponseEntity<List<OrgResponVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        List<OrgRespon> resultList = service.findList(params);
        if (!CollectionUtils.isEmpty(resultList)){
            for (OrgRespon orgRespon : resultList){
                getOrgRespon(orgRespon);
            }
        }
        List<OrgResponVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }

    private OrgRespon getOrgRespon(OrgRespon result){
        if (null != result){
            List<OrgResponDetail> details = result.getDetails();
            List<OrgResponDetail> list = new ArrayList<>();
            if (!CollectionUtils.isEmpty(details)){
                for (OrgResponDetail entity : details){
                    String delFlag = entity.getDelFlag();
                    if (delFlag.equals(DelFlagEnum.NORMAL.getCode())){
                        list.add(entity);
                    }
                }
                result.setDetails(list);
            }
        }
        return result;
    }

    /**
     * 新增关联应急责任单位/人--- 启动预案，根据数字化批量生成
     *
     * @param vos
     * @return ResponseEntity<List < OrgResponVo>>
     */
    @Override
    public ResponseEntity<List<OrgResponVo>> createList(
            @NotNull(message = "List<OrgResponVo> 不能为null")
            @RequestBody List<OrgResponVo> vos) {
        List<OrgRespon> entityList = mapper.voListToEntityList(vos);
        List<OrgRespon> resultList = service.createList(entityList);
        List<OrgResponVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }
}
