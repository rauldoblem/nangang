package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.Notice;
import com.taiji.emp.base.feign.INoticeRestService;
import com.taiji.emp.base.mapper.NoticeMapper;
import com.taiji.emp.base.service.NoticeService;
import com.taiji.emp.base.vo.NoticeVo;
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
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/notice")
public class NoticeController extends BaseController implements INoticeRestService {

    NoticeMapper mapper;

    NoticeService service;

    /**
     * 新增通知公告信息 NoticeVo不能为空
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<NoticeVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody NoticeVo vo) {
        Notice entity = mapper.voToEntity(vo);
        Notice result = service.create(entity);
        NoticeVo voResult = mapper.entityToVo(result);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 根据id逻辑删除一条记录
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * 更新通知公告 NoticeVo
     * @param vo
     * @param id 要更新 NoticeVo id
     * @return ResponseEntity<NoticeVo>
     */
    @Override
    public ResponseEntity<NoticeVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody NoticeVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        Notice entity = mapper.voToEntity(vo);
        Notice result = service.update(entity);
        NoticeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取通知公告信息
     * @param id id不能为空
     * @return ResponseEntity<NoticeVo>
     */
    @Override
    public ResponseEntity<NoticeVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        Notice result = service.findOne(id);
        NoticeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据条件查询通知公告列表-分页
     * params参数key为title,noticeTypeId,sendStartTime,sendEndTime,sendStatus,orgId(可选)
     * page,size
     * @param params
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<NoticeVo>> findPage(
            @RequestParam MultiValueMap<String, Object> params) {
        Pageable page = PageUtils.getPageable(params);
        Page<Notice> pageResult = service.findPage(params, page);
        RestPageImpl<NoticeVo> voPage = mapper.entityPageToVoPage(pageResult, page);
        return ResponseEntity.ok(voPage);
    }
}
