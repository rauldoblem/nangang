package com.taiji.emp.zn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.emp.zn.common.constant.ZNGlobal;
import com.taiji.emp.zn.util.HttpClientUtil;
import com.taiji.emp.zn.vo.*;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/21 17:30
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/typhoons")
public class TyphoonController {

    //private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    ObjectMapper mapper;

    /**
     * 获取台风万宜的详细信息forGIS（临时使用）
     * @return
     * @throws IOException
     */
    @GetMapping("/getDetailTyphoon")
    public ResultEntity getDetailTyphoon() throws IOException {

        String id = "2462919";
        String result = getTyphoonDetailCommon(id);
        ResultEntity resultVo = mapper.readValue(result, ResultEntity.class);
        return resultVo;
    }

    /**
     * 根据台风id获取台风详细信息forGIS
     * 配合预警推送过来的台风信息（包含id）使用
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/getDetailTyphoon/{id}")
    public ResultVo getDetailTyphoonById(
            @Validated
            @NotNull(message = "台风id不能为空")
            @PathVariable(value = "id") String id) throws IOException {

        String result = getTyphoonDetailCommon(id);
        ResultVo resultVo = mapper.readValue(result, ResultVo.class);
        return resultVo;
    }

    /**
     * 获取台风万宜的信息for首页（临时使用）
     * @return
     * @throws IOException
     */
    @GetMapping("/getTyphoon")
    public ResultEntity getTyphoon() throws IOException {

        //id 应该是从预警信息获取
        String id = "2462919";
        List<TyphoonInfoVo> typhoonCommon = getTyphoonCommon(id);
        return ResultUtils.success(typhoonCommon);
    }

    /**
     * 获取台风万宜的信息for首页
     * 配合预警推送过来的台风信息（包含id）使用
     * @return
     * @throws IOException
     */
    @GetMapping("/getTyphoon/{id}")
    public ResultEntity getTyphoonById(
            @Validated
            @NotNull(message = "台风id不能为空")
            @PathVariable(value = "id") String id) throws IOException {
        List<TyphoonInfoVo> typhoonCommon = getTyphoonCommon(id);
        return ResultUtils.success(typhoonCommon);
    }

    /**
     * 获取台风列表 调用平台接口
     * @return
     * @throws IOException
     */
    @GetMapping("/getTyphoonList")
    public ResultEntity getTyphoonList() throws IOException {

        //获取台风列表
        String url = ZNGlobal.GET_TYPHOON_LIST_URL;
        String result = HttpClientUtil.httpGet(url);
        TyphoonListVo resultVo = mapper.readValue(result, TyphoonListVo.class);
        List<ForTyphoonListVo> data = null;
        if(null != resultVo){
            data = resultVo.getData();
            List<ForTyphoonListVo> dataTemp = new ArrayList<>();
            //刚获得的data的台风列表是倒序的
            if(null != data){
                for (int i = data.size() - 1; i > 0 ; i--) {
                    dataTemp.add(data.get(i));
                }
                data = dataTemp;
            }
        }
        return ResultUtils.success(data);
    }

    /**
     * 获取台风详细信息的通用方法
     * @param id
     * @return
     * @throws IOException
     */
    private String getTyphoonDetailCommon(String id) throws IOException {
        String url = ZNGlobal.FINDONE_TYPHOON_BY_ID_URL + id;
        String result = HttpClientUtil.httpGet(url);
        return result;
    }

    /**
     * 获取台风信息的通用方法
     * @param id
     * @return
     * @throws IOException
     */
    private List<TyphoonInfoVo> getTyphoonCommon(String id) throws IOException {

        String[] yAxis = ZNGlobal.LON_LAT_AIRPRESSURE_WINDSPEED;
        String result = getTyphoonDetailCommon(id);
        TotalTyphoonVo resultVo = mapper.readValue(result, TotalTyphoonVo.class);

        List<TyphoonInfoVo> typhoonInfoVos = null;
        if(null != resultVo){
            List<TyphoonVo> typhoonVos = resultVo.getData();
            //取该台风的前三条信息
            int size = 0;
            if(typhoonVos.size() >= 3){
                size = 3;
            }else{
                size = typhoonVos.size();
            }
            TyphoonInfoVo vo;
            typhoonInfoVos = new ArrayList<>();
            for (int i = 0 ,length = yAxis.length; i < length; i++) {
                vo = new TyphoonInfoVo();
                vo.setName(yAxis[i]);
                typhoonInfoVos.add(vo);
            }
            //yAxis.length 恒等于4 对应typhoonVo的4个属性
            TyphoonVo typhoonVo ;
            for (int i = 0; i < size; i++) {
                typhoonVo = typhoonVos.get(i);
                if(null != typhoonVo){

                    Double lon = typhoonVo.getLon();
                    if(null != lon ) typhoonInfoVos.get(0).getValue().add(lon.toString());

                    Double lat = typhoonVo.getLat();
                    if(null != lat ) typhoonInfoVos.get(1).getValue().add(lat.toString());

                    Double airpressure = typhoonVo.getAirpressure();
                    if(null != airpressure ) typhoonInfoVos.get(2).getValue().add(airpressure.toString());

                    Double windspeed = typhoonVo.getWindspeed();
                    if(null != windspeed ) typhoonInfoVos.get(3).getValue().add(windspeed.toString());
                }
            }
        }
        return typhoonInfoVos;
    }
}
