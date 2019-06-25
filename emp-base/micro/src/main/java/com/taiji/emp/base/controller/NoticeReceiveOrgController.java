package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.NoticeReceiveOrg;
import com.taiji.emp.base.feign.INoticeReceiveOrgRestService;
import com.taiji.emp.base.mapper.NoticeReceiveOrgMapper;
import com.taiji.emp.base.searchVo.NoticeReceiveVo;
import com.taiji.emp.base.service.NoticeReceiveOrgService;
import com.taiji.emp.base.vo.NoticeReceiveOrgVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/noticeReceiveOrg")
public class NoticeReceiveOrgController extends BaseController implements INoticeReceiveOrgRestService {

    NoticeReceiveOrgMapper mapper;

    NoticeReceiveOrgService service;

    /**
     * 通知公告发送 NoticeReceiveOrgVo 不能为空
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<NoticeReceiveOrgVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody NoticeReceiveOrgVo vo) {
        NoticeReceiveOrg entity = mapper.voToEntity(vo);
        NoticeReceiveOrg result = service.create(entity);
        NoticeReceiveOrgVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 通知公告接受状态查看
     * @param id id不能为空
     * @return
     */
    @Override
    public ResponseEntity<NoticeReceiveOrgVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        NoticeReceiveOrg result = service.findOne(id);
        NoticeReceiveOrgVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新通知公告反馈关系信息
     * @param vo
     * @param id 要更新 NoticeReceiveOrgVo id
     * @return
     */
    @Override
    public ResponseEntity<NoticeReceiveOrgVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody NoticeReceiveOrgVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        NoticeReceiveOrg entity = mapper.voToEntity(vo);
        NoticeReceiveOrg result = service.update(entity);
        NoticeReceiveOrgVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 接受通知条件查询列表----分页
     * @param noticeReceiveVo
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<NoticeReceiveOrgVo>> findNoticeReceivePage(
            @RequestBody NoticeReceiveVo noticeReceiveVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("page",noticeReceiveVo.getPage());
        params.add("size",noticeReceiveVo.getSize());
        Pageable page = PageUtils.getPageable(params);

        Page<NoticeReceiveOrg> pageResult = service.findNoticeReceivePage(noticeReceiveVo,page);
        RestPageImpl<NoticeReceiveOrgVo> voPage = mapper.entityPageToVoPage(pageResult, page);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 根据 noticeRecId 查询通知公告反馈信息
     * @param noticeRecId
     * @return
     */
    @Override
    public ResponseEntity<NoticeReceiveOrgVo> findRecOne(
            @NotEmpty(message = "noticeRecId不能为空")
            @PathVariable(value = "noticeRecId")String noticeRecId) {
        NoticeReceiveOrg result = service.findRecOne(noticeRecId);
        NoticeReceiveOrgVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    @Override
    public ResponseEntity<NoticeReceiveOrgVo> findByNoticeRecId(
            @NotEmpty(message = "noticeRecId不能为空")
            @PathVariable(value = "noticeRecId")String noticeRecId) {
        NoticeReceiveOrg result = service.findByNoticeRecId(noticeRecId);
        NoticeReceiveOrgVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 通知公告接受状态查看
     * @param noticeId
     * @return
     */
    @Override
    public ResponseEntity<List<NoticeReceiveOrgVo>> findList(
            @NotEmpty(message = "noticeId不能为空")
            @PathVariable(value = "noticeId")String noticeId) {
        List<NoticeReceiveOrg> list = service.findList(noticeId);
        List<NoticeReceiveOrgVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

}
