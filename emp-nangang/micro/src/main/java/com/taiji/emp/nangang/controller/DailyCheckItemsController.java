package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.DailyCheckItems;
import com.taiji.emp.nangang.feign.IDailyCheckItemsService;
import com.taiji.emp.nangang.mapper.DailyCheckItemsMapper;
import com.taiji.emp.nangang.service.DailyCheckItemsService;
import com.taiji.emp.nangang.vo.DailyCheckItemsVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/dailyCheckItems")
public class DailyCheckItemsController extends BaseController implements IDailyCheckItemsService {

    @Autowired
    private DailyCheckItemsService service;
    @Autowired
    private DailyCheckItemsMapper mapper;

    /**
     * 根据参数获取DailyCheckItemsVo
     *  @param dailyCheckItemsVo
     *  @return ResponseEntity<DailyCheckItemsVo>
     */
    @Override
    public ResponseEntity<DailyCheckItemsVo> update(
            @Validated
            @NotNull(message = "DailyCheckItemsVo不能为null")
            @RequestBody DailyCheckItemsVo dailyCheckItemsVo) {
        DailyCheckItems entity = mapper.voToEntity(dailyCheckItemsVo);
        DailyCheckItems result = service.update(entity);
        DailyCheckItemsVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据参数获取DailyCheckItemsVo
     *  @param id
     *  @return ResponseEntity<DailyCheckItemsVo>
     */
    @Override
    public ResponseEntity<List<DailyCheckItemsVo>> findCheckId(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        List<DailyCheckItems> result = service.findCheckId(id);
        List<DailyCheckItemsVo> resultVo = mapper.entityListToVoList(result);
        for (DailyCheckItemsVo vo : resultVo){
            String checkItemId = vo.getId();
            String dailyLogId = service.findDailyLogId(checkItemId);
            vo.setDailyLogId(dailyLogId);
        }
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据dailyCheckItemsVoId查询对应的dailyLogId
     * （从中间表ed_dailycheck_dailylog查）
     * @param dailyCheckItemsVoId
     * @return
     */
    @Override
    public ResponseEntity<String> findDailyLogId(
            @NotEmpty(message = "dailyCheckItemsVoId不能为空")
            @PathVariable(value = "dailyCheckItemsVoId") String dailyCheckItemsVoId) {
        String dailyLogId = service.findDailyLogId(dailyCheckItemsVoId);
        return ResponseEntity.ok(dailyLogId);
    }

}
