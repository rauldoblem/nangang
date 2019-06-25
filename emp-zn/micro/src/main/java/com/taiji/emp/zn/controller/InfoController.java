package com.taiji.emp.zn.controller;

import com.taiji.emp.zn.entity.InfoStat;
import com.taiji.emp.zn.feign.IInfoRestService;
import com.taiji.emp.zn.mapper.InfoStatMapper;
import com.taiji.emp.zn.service.InfoService;
import com.taiji.emp.zn.vo.InfoStatVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/zn/info")
public class InfoController implements IInfoRestService{

    @Autowired
    InfoService service;
    @Autowired
    InfoStatMapper mapper;

    /**
     * 根据条件查询预警/事件信息列表，并按视图view_alarm_event中的report_time倒序排列
     * 参数：page,size(默认10条)
     *
     * @param params
     * @return ResponseEntity<RestPageImpl < InfoStatVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<InfoStatVo>> statInfo(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<InfoStat> result = service.statInfo(params,pageable);
        RestPageImpl<InfoStatVo> resultVo = mapper.entityPageToVoPage(result,pageable);
        return ResponseEntity.ok(resultVo);
    }
}
