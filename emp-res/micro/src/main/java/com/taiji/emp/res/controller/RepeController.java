package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Repertory;
import com.taiji.emp.res.feign.IRepeRestService;
import com.taiji.emp.res.mapper.RepeMapper;
import com.taiji.emp.res.searchVo.repertory.RepertoryPageVo;
import com.taiji.emp.res.service.RepeService;
import com.taiji.emp.res.vo.RepertoryVo;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/repertorys")
public class RepeController extends BaseController implements IRepeRestService {

    RepeService service;
    RepeMapper mapper;

    /**
     * 根据参数获取RepertoryVo多条记录,分页信息
     *          page,size
     *  @param vo
     *  @return ResponseEntity<RestPageImpl<RepertoryVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<RepertoryVo>> findPage(@RequestBody RepertoryPageVo vo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",vo.getPage());
        map.add("size",vo.getSize());
        Pageable pageable = PageUtils.getPageable(map);

        Page<Repertory> pageResult = service.findPage(vo,pageable);
        RestPageImpl<RepertoryVo> voPage = mapper.entityPageToVoPage(pageResult,pageable);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }

    /**
     * 新增应急储备库RepertoryVo，RepertoryVo不能为空
     * @param vo
     * @return ResponseEntity<RepertoryVo>
     */
    @Override
    public ResponseEntity<RepertoryVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody RepertoryVo vo) {
        Repertory entity = mapper.voToEntity(vo);
        Repertory result = service.create(entity);
        RepertoryVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新应急储备库RepertoryVo，RepertoryVo不能为空
     * @param vo,
     * @param id 要更新RepertoryVo 的 id
     * @return ResponseEntity<RepertoryVo>
     */
    @Override
    public ResponseEntity<RepertoryVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody RepertoryVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Repertory entity = mapper.voToEntity(vo);
        Repertory result = service.update(entity);
        RepertoryVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取应急储备库RepertoryVo
     * @param id id不能为空
     * @return ResponseEntity<RepertoryVo>
     */
    @Override
    public ResponseEntity<RepertoryVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Repertory result = service.findOne(id);
        RepertoryVo resultVo = mapper.entityToVo(result);
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

    /**
     * 根据条件查询物资库列表-不分页
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<RepertoryVo>> findRepertoryList(@RequestBody RepertoryPageVo vo) {
        List<Repertory> result = service.findRepertoryList(vo);
        List<RepertoryVo> resultVo = mapper.entityListToVoList(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }
}
