package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.DailyCheck;
import com.taiji.emp.nangang.entity.DailyCheckDailyLog;
import com.taiji.emp.nangang.entity.DailyCheckItems;
import com.taiji.emp.nangang.feign.IDailyCheckService;
import com.taiji.emp.nangang.mapper.DailyCheckDailyLogMapper;
import com.taiji.emp.nangang.mapper.DailyCheckItemsMapper;
import com.taiji.emp.nangang.mapper.DailyCheckMapper;
import com.taiji.emp.nangang.searchVo.dailyCheck.DailyCheckPageVo;
import com.taiji.emp.nangang.service.DailyCheckService;
import com.taiji.emp.nangang.vo.DailyCheckDailyLogVo;
import com.taiji.emp.nangang.vo.DailyCheckItemsVo;
import com.taiji.emp.nangang.vo.DailyCheckVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/dailyChecks")
public class DailyCheckController extends BaseController implements IDailyCheckService {

    @Autowired
    private DailyCheckService service;
    @Autowired
    private DailyCheckMapper mapper;
    @Autowired
    private DailyCheckItemsMapper itemsMapper;
    @Autowired
    private DailyCheckDailyLogMapper dailyCheckDailyLogMapper;

    /**
     * 根据参数获取DailyCheckVo
     *  @param id
     *  @return ResponseEntity<DailyCheckVo>
     */
    @Override
    public ResponseEntity<DailyCheckVo> updateDailyCheck(
            @Validated
            @NotNull(message = "DailyCheckVo不能为null")
            @RequestParam String id) {
        DailyCheck result = service.updateDailyCheck(id);
        DailyCheckVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据参数获取DailyCheckItemsVo
     *  @param dailyCheckVo
     *  @return ResponseEntity<DailyCheckItemsVo>
     */
    @Override
    public ResponseEntity<List<DailyCheckItemsVo>> selectItem(
            @Validated
            @NotNull(message = "DailyCheckVo不能为null")
            @RequestBody DailyCheckVo dailyCheckVo) {
        DailyCheck entity = mapper.voToEntity(dailyCheckVo);
        List<DailyCheckItems> result = service.selectItem(entity);
        //这边用list的to
        List<DailyCheckItemsVo> resultVo = itemsMapper.entityListToVoList(result);
        return ResponseEntity.ok(resultVo);
    }
    /**
     * 根据参数获取DailyCheckItemsVo
     *  @param dailyCheckPageVo
     *  @return ResponseEntity<RestPageImpl<DailyCheckVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<DailyCheckVo>> findPage(
            @Validated
            @RequestBody DailyCheckPageVo dailyCheckPageVo) {
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",dailyCheckPageVo.getPage());
        map.add("size",dailyCheckPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<DailyCheck> pageResult = service.findPage(dailyCheckPageVo,page);
        RestPageImpl<DailyCheckVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }

    /**
     * 根据条件查询表中是否有这条记录
     * @param dailyCheckVo
     * @return
     */
    @Override
    public DailyCheckVo exists(
            @Validated
            @NotNull(message = "DailyCheckVo不能为null")
            @RequestBody DailyCheckVo dailyCheckVo) {
        DailyCheck entity = mapper.voToEntity(dailyCheckVo);
        DailyCheck dailyCheck = service.exists(entity);
        if(dailyCheck==null){
            return null;
        }else
        return mapper.entityToVo(dailyCheck);
    }

    /**
     * 保存一条dailyCheck
     * @param dailyCheckVo
     * @return
     */
    @Override
    public ResponseEntity<DailyCheckVo> save(
            @Validated
            @NotNull(message = "DailyCheckVo不能为null")
            @RequestBody DailyCheckVo dailyCheckVo) {
        DailyCheck entity = mapper.voToEntity(dailyCheckVo);
        DailyCheck result = service.save(entity);
        DailyCheckVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);

    }

    /**
     * 根据条件 查询一条dailyCheck
     * @param dailyCheckVo
     * @return
     */
    @Override
    public ResponseEntity<DailyCheckVo> findOne(
            @Validated
            @NotNull(message = "DailyCheckVo不能为null")
            @RequestBody DailyCheckVo dailyCheckVo) {
        DailyCheck entity = mapper.voToEntity(dailyCheckVo);
        DailyCheck result = service.findOne(entity);
        DailyCheckVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 批量保存具有同一dailyCheckId的items
     * @param itemsVos
     * @return
     */
    @Override
    public ResponseEntity<List<DailyCheckItemsVo>> saveByList(
            @Validated
            @NotNull(message = "itemsVos不能为null")
            @RequestBody List<DailyCheckItemsVo> itemsVos) {
        List<DailyCheckItems> dailyCheckItems = itemsMapper.voListToEntityList(itemsVos);
        List<DailyCheckItems> result = service.saveByList(dailyCheckItems);
        List<DailyCheckItemsVo> resultVo = itemsMapper.entityListToVoList(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 保存checkItemId和dailyLogId到中间表
     * @param dailyCheckDailyLogVo
     * @return
     */
    @Override
    public ResponseEntity<DailyCheckDailyLogVo> addDailyLog(
            @Validated
            @NotNull(message = "checkItemId、dailyLogId不能为null")
            @RequestBody DailyCheckDailyLogVo dailyCheckDailyLogVo) {

        DailyCheckDailyLog dailyCheckDailyLog = dailyCheckDailyLogMapper.voToEntity(dailyCheckDailyLogVo);
        DailyCheckDailyLog entity = service.addDailyLog(dailyCheckDailyLog);
        DailyCheckDailyLogVo resultVo = dailyCheckDailyLogMapper.entityToVo(entity);
        return ResponseEntity.ok(resultVo);
    }
}
