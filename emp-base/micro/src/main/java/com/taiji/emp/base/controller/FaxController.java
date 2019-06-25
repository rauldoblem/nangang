package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.Fax;
import com.taiji.emp.base.feign.IFaxService;
import com.taiji.emp.base.mapper.FaxMapper;
import com.taiji.emp.base.searchVo.fax.FaxListVo;
import com.taiji.emp.base.searchVo.fax.FaxPageVo;
import com.taiji.emp.base.service.FaxService;
import com.taiji.emp.base.vo.FaxVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.Serializers;
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
@RequestMapping("/api/faxs")
public class FaxController extends BaseController implements IFaxService {
    FaxService service;
    FaxMapper mapper;
    /**
     * 根据参数获取FaxVo多条记录
     * 参数key为title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选),createOrgId
     *  @param faxListVo
     *  @return ResponseEntity<List<FaxVo>>
     */
    @Override
    public ResponseEntity<List<FaxVo>> findList(@RequestBody FaxListVo faxListVo) {
        List<Fax> list = service.findList(faxListVo);
        List<FaxVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取FaxVo多条记录,分页信息
     * 参数key为title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选),createOrgId
     *          page,size
     *  @param faxPageVo
     *  @return ResponseEntity<RestPageImpl<FaxVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<FaxVo>> findPage(@RequestBody FaxPageVo faxPageVo) {

            MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
            map.add("page",faxPageVo.getPage());
            map.add("size",faxPageVo.getSize());
            Pageable page = PageUtils.getPageable(map);

        Page<Fax> pageResult = service.findPage(faxPageVo,page);
        RestPageImpl<FaxVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 新增应急知识FaxVo，FaxVo不能为空
     * @param vo
     * @return ResponseEntity<FaxVo>
     */
    @Override
    public ResponseEntity<FaxVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody FaxVo vo) {
        Fax entity = mapper.voToEntity(vo);
        Fax result = service.create(entity);
        FaxVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新应急知识FaxVo，FaxVo不能为空
     * @param vo,
     * @param id 要更新FaxVo 的 id
     * @return ResponseEntity<FaxVo>
     */
    @Override
    public ResponseEntity<FaxVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody FaxVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Fax entity = mapper.voToEntity(vo);
        Fax result = service.update(entity);
        FaxVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id 获取应急知识FaxVo
     * @param id id不能为空
     * @return ResponseEntity<FaxVo>
     */
    @Override
    public ResponseEntity<FaxVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Fax result = service.findOne(id);
        FaxVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
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
        return ResponseEntity.ok().build();
    }

}
