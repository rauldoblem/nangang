package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.searchVo.meteorological.MeteorologicalPageVo;
import com.taiji.emp.nangang.vo.MeteorologicalVo;
import com.taiji.emp.nangang.vo.SurfaceWaterVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "micro-nangang-meteorological")
public interface IMeteorologicalService {
    /**
     * 查看空气质量监测态势
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find")
    @ResponseBody
    ResponseEntity<MeteorologicalVo> find();

//    /**
//     * 查询最新7条空气质量监测态势
//     */
//    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
//    @ResponseBody
//    ResponseEntity<RestPageImpl<MeteorologicalVo>> findPage();
}
