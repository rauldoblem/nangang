package com.taiji.emp.res.controller;


import com.taiji.emp.res.entity.Law;
import com.taiji.emp.res.feign.ILawService;
import com.taiji.emp.res.mapper.LawMapper;
import com.taiji.emp.res.searchVo.law.LawListVo;
import com.taiji.emp.res.searchVo.law.LawPageVo;
import com.taiji.emp.res.service.LawService;
import com.taiji.emp.res.vo.LawVo;
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
@RestController
@RequestMapping("/api/law")
@AllArgsConstructor
public class LawController extends BaseController implements ILawService {
    LawService service;
    LawMapper mapper;

    /**
     * 根据参数获取LawVo多条记录
     * params参数key为title(可选),keyWord(可选),buildOrg(可选),eventypeIds(可选),lawTypeId(可选)
     * @param lawListVo
     * @return ResponseEntity<List<LawVo></>></>
    **/
    @Override
    public ResponseEntity<List<LawVo>> findList(@RequestBody LawListVo lawListVo) {
        List<Law> list = service.findList(lawListVo);
        List<LawVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 根据参数获取LawVo多条记录，分页
     * @param参数key为title(可选),keyWord(可选),buildOrg(可选),eventypeIds(可选),lawTypeId(可选)
     *      page,size
     * @param lawPageVo
     * @return ResponseEntity<RestPageImpl<KnowledgeVo></>></>
    **/
    @Override
    public ResponseEntity<RestPageImpl<LawVo>> findPage(@RequestBody LawPageVo lawPageVo){
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",lawPageVo.getPage());
        map.add("size",lawPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<Law> pageResult = service.findPage(lawPageVo,page);
        RestPageImpl<LawVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }

    /**
     * 新增法律法规LawVo,LawVo不能为空
     * @param vo
     * @return ResponseEntity<LawVo></>
    **/
    @Override
    public ResponseEntity<LawVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody LawVo vo) {
        Law entity = mapper.voToEntity(vo);
        Law result = service.create(entity);
        LawVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
    **/
    @Override
    public ResponseEntity<Void> deleteLogic (
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id
    ) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 更新法律法规LawVo,LawVo不能为空
     * @param vo
     * @param id 要更新LawVo的id
     * @return ResponseEntity<KnowledgeVo></>
    **/
    @Override
    public ResponseEntity<LawVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody LawVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Law entity = mapper.voToEntity(vo);
        Law result = service.update(entity);
        LawVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据LawVo id获取一条记录
     *
     * @param id id不能为空
     * @return ResponseEntity<LawVo>
    **/
    @Override
    public ResponseEntity<LawVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Law result = service.findOne(id);
        LawVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

}
