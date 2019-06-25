package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Knowledge;
import com.taiji.emp.res.feign.IKnowRestService;
import com.taiji.emp.res.mapper.KnowMapper;
import com.taiji.emp.res.searchVo.knowledge.KnowListVo;
import com.taiji.emp.res.searchVo.knowledge.KnowPageVo;
import com.taiji.emp.res.service.KnowService;
import com.taiji.emp.res.vo.KnowledgeVo;
import com.taiji.emp.res.controller.BaseController;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/knowledges")
public class KnowController extends BaseController implements IKnowRestService{

    KnowService service;
    KnowMapper mapper;

    /**
     * 根据参数获取KnowledgeVo多条记录
     * 参数key为title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选),createOrgId
     *  @param knowListVo
     *  @return ResponseEntity<List<KnowledgeVo>>
     */
    @Override
    public ResponseEntity<List<KnowledgeVo>> findList(@RequestBody KnowListVo knowListVo) {
        List<Knowledge> list = service.findList(knowListVo);
        List<KnowledgeVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 根据参数获取KnowledgeVo多条记录,分页信息
     * 参数key为title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选),createOrgId
     *          page,size
     *  @param knowPageVo
     *  @return ResponseEntity<RestPageImpl<KnowledgeVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<KnowledgeVo>> findPage(@RequestBody KnowPageVo knowPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",knowPageVo.getPage());
        map.add("size",knowPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<Knowledge> pageResult = service.findPage(knowPageVo,page);
        RestPageImpl<KnowledgeVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }

    /**
     * 新增应急知识KnowledgeVo，KnowledgeVo不能为空
     * @param vo
     * @return ResponseEntity<KnowledgeVo>
     */
    @Override
    public ResponseEntity<KnowledgeVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody KnowledgeVo vo) {
        Knowledge entity = mapper.voToEntity(vo);
        Knowledge result = service.create(entity);
        KnowledgeVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新应急知识KnowledgeVo，KnowledgeVo不能为空
     * @param vo,
     * @param id 要更新KnowledgeVo 的 id
     * @return ResponseEntity<KnowledgeVo>
     */
    @Override
    public ResponseEntity<KnowledgeVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody KnowledgeVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Knowledge entity = mapper.voToEntity(vo);
        Knowledge result = service.update(entity);
        KnowledgeVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取应急知识KnowledgeVo
     * @param id id不能为空
     * @return ResponseEntity<KnowledgeVo>
     */
    @Override
    public ResponseEntity<KnowledgeVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Knowledge result = service.findOne(id);
        KnowledgeVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
