package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.DocAttachment;
import com.taiji.emp.base.feign.IDocAttRestService;
import com.taiji.emp.base.mapper.DocAttMapper;
import com.taiji.emp.base.service.DocAttService;
import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/files")
public class DocAttController extends BaseController implements IDocAttRestService {

    DocAttService service;
    DocAttMapper mapper;

    /**
     * 根据 DocAttVo id获取单个附件对象
     * @param id
     * @return ResponseEntity<DocAttVo>
     */
    @Override
    public ResponseEntity<DocAttVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id")String id) {
        DocAttachment result = service.findOne(id);
        DocAttVo vo = mapper.entityToVo(result);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    /**
     * 根据业务id 获取附件清单list
     * @param entityId
     * @return ResponseEntity<List<DocAttVo>>
     */
    @Override
    public ResponseEntity<List<DocAttVo>> findList(
            @NotEmpty(message = "entityId不能为空")
            @PathVariable("entityId")String entityId) {
        List<DocAttachment> resultList = service.findList(entityId);
        List<DocAttVo> voList = mapper.entityListToVoList(resultList);
        return new ResponseEntity<>(voList,HttpStatus.OK);
    }

    /**
     * 给附件list赋予 业务id
     * @param docEntityVo
     * 包含 entityId（必须）,docAttIds（必须）
     * @return
     */
    @Override
    public ResponseEntity<Void> saveDocEntity(
            @Validated
            @RequestBody DocEntityVo docEntityVo) {
        service.saveDocEntity(docEntityVo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据id逻辑删除一条附件记录。
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 新增保存 DocAttVo 对象
     * @param vo
     * DocAttVo 对象 --- 上传文件时保存DocAttachment对象
     */
    @Override
    public ResponseEntity<DocAttVo> createDoc(
            @NotNull(message = "vo不能为null")
            @RequestBody DocAttVo vo) {
        DocAttachment doc = mapper.voToEntity(vo);
        DocAttachment temp = service.createDoc(doc);
        DocAttVo resultVo = mapper.entityToVo(temp);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

}
