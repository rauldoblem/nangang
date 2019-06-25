package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.NoticeFeedBack;
import com.taiji.emp.base.feign.INoticeFeedBackRestService;
import com.taiji.emp.base.mapper.NoticeFeedBackMapper;
import com.taiji.emp.base.service.NoticeFeedBackService;
import com.taiji.emp.base.service.NoticeReceiveOrgService;
import com.taiji.emp.base.vo.NoticeFeedBackVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/noticeFeedBack")
public class NoticeFeedBackController extends BaseController implements INoticeFeedBackRestService{

    NoticeFeedBackMapper mapper;
    NoticeFeedBackService service;
    NoticeReceiveOrgService orgService;

    /**
     * 通知公告反馈
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<NoticeFeedBackVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody NoticeFeedBackVo vo) {
        NoticeFeedBack entity = mapper.voToEntity(vo);
        NoticeFeedBack result = service.create(entity);
        NoticeFeedBackVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 通过receiveId查看反馈内容
     * @param receiveId
     * @return
     */
    @Override
    public ResponseEntity<NoticeFeedBackVo> findByReceiveId(
            @NotEmpty(message = "receiveId不能为空")
            @PathVariable(value = "receiveId")String receiveId) {
        NoticeFeedBack noticeFeedBack = service.findByReceiveId(receiveId);
        NoticeFeedBackVo resultVo = mapper.entityToVo(noticeFeedBack);
        return ResponseEntity.ok(resultVo);
    }
}
