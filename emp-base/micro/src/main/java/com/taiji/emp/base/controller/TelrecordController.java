package com.taiji.emp.base.controller;


import com.taiji.emp.base.entity.Telrecord;
import com.taiji.emp.base.feign.ITelrecordService;
import com.taiji.emp.base.mapper.TelrecordMapper;
import com.taiji.emp.base.searchVo.telrecord.TelrecordPageVo;
import com.taiji.emp.base.service.TelrecordService;
import com.taiji.emp.base.vo.TelrecordVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/telrecords")
@AllArgsConstructor
public class TelrecordController extends BaseController implements ITelrecordService {
    TelrecordService service;
    TelrecordMapper mapper;

    /**
     * 根据参数获取TelrecordVo多条记录，分页
     * @param参数key为caller(可选),callee(可选),fileName(可选),beginTime(可选)
     *      page,size
     * @param telrecordPageVo
     * @return ResponseEntity<RestPageImpl<TelrecordVo></>></>
     */
    @Override
    public ResponseEntity<RestPageImpl<TelrecordVo>> findPage(@RequestBody TelrecordPageVo telrecordPageVo){
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",telrecordPageVo.getPage());
        map.add("size",telrecordPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<Telrecord> pageresult = service.findPage(telrecordPageVo,page);
        RestPageImpl<TelrecordVo> voPage = mapper.entityPageToVoPage(pageresult,page);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }
}
