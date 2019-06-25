package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.dailyShift.DailyShift;
import com.taiji.emp.duty.feign.IDailyShiftRestService;
import com.taiji.emp.duty.mapper.DailyShiftMapper;
import com.taiji.emp.duty.searchVo.DailyShiftPageVo;
import com.taiji.emp.duty.service.DailyShiftService;
import com.taiji.emp.duty.vo.dailyShift.DailyShiftVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/dailyshift")
public class DailyShiftController extends BaseController implements IDailyShiftRestService {

    DailyShiftService service;
    DailyShiftMapper mapper;

    /**
     * 新增交接班 DailyShiftVo，DailyShiftVo 不能为空
     * 包含 值班日志ID集合
     * @param vo
     * @return ResponseEntity<DailyShiftVo>
     */
    @Override
    public ResponseEntity<DailyShiftVo> create(
            @NotNull(message = "vo不能为null")
            @RequestBody DailyShiftVo vo) {
        DailyShift entity = mapper.voToEntity(vo);
        DailyShift result = service.create(entity);
        DailyShiftVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id 获取交接班信息 DailyShiftVo
     * 其中包含 值班日志ID集合
     * @param id
     * @return ResponseEntity<ContactVo>
     */
    @Override
    public ResponseEntity<DailyShiftVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DailyShift result = service.findOne(id);
        DailyShiftVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据参数获取 DailyShiftVo 多条记录,分页信息
     * fromWatcherName(交班人姓名),toWatcherName（接班人姓名）,title（表题）
     * 包含值班日志ID集合
     * @return ResponseEntity<RestPageImpl<DailyShiftVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<DailyShiftVo>> findPage(@RequestBody DailyShiftPageVo dailyShiftPageVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        int page = dailyShiftPageVo.getPage();
        int size = dailyShiftPageVo.getSize();
        Assert.notNull(page,"page 不能为null或空字符串!");
        Assert.notNull(size,"size 不能为null或空字符串!");
        params.add("page",page);
        params.add("size",size);
        Pageable pageable = PageUtils.getPageable(params);
        Page<DailyShift> pageResult = service.findPage(dailyShiftPageVo,pageable);
        RestPageImpl<DailyShiftVo> voPage = mapper.entityPageToVoPage(pageResult,pageable);
        return ResponseEntity.ok(voPage);
    }
}
