package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.Meteorological;
import com.taiji.emp.nangang.feign.IMeteorologicalService;
import com.taiji.emp.nangang.mapper.MeteorologicalMapper;
import com.taiji.emp.nangang.repository.MeteorologicalRepository;
import com.taiji.emp.nangang.service.MeteorologicalService;
import com.taiji.emp.nangang.vo.MeteorologicalVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/meteorologicals")
@AllArgsConstructor
public class MeteorologicalController extends BaseController implements IMeteorologicalService {
    MeteorologicalService meteorologicalService;
    MeteorologicalMapper meteorologicalMapper;
    /**
     * 获取一条记录
     **/
    @Override
    public ResponseEntity<MeteorologicalVo> find() {
        Meteorological result = meteorologicalService.getMeteorological();
        MeteorologicalVo resultVo = meteorologicalMapper.entityToVo(result);
        return new ResponseEntity(resultVo,HttpStatus.OK);
    }

//    /**
//     * 查询指定size条数据
//     * @return
//     */
//    @Override
//    public ResponseEntity<RestPageImpl<MeteorologicalVo>> findPage() {
//        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
//        map.add("page",0);
//        map.add("size",7);
//        Pageable page = PageUtils.getPageable(map);
//
//        Page<Meteorological> pageResult = meteorologicalService.findPage(page);
//        RestPageImpl<MeteorologicalVo> voPage = meteorologicalMapper.entityPageToVoPage(pageResult,page);
//        return new ResponseEntity<>(voPage,HttpStatus.OK);
//    }


}
