package com.taiji.emp.zn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.emp.zn.common.constant.ZNGlobal;
import com.taiji.emp.zn.util.HttpClientUtil;
import com.taiji.emp.zn.vo.ResultVo;
import com.taiji.emp.zn.vo.ShipInfoVo;
import com.taiji.emp.zn.vo.ShipVo;
import com.taiji.emp.zn.vo.TotalShipVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/21 17:31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/ships")
public class ShipController {

    @Autowired
    ObjectMapper mapper;

    /**
     * 获取船舶列表 httpGet
     */
    @GetMapping("/getShips")
    public ResultVo getShips() throws IOException {

        String url = ZNGlobal.GET_SHIPS_URL;
        String result = HttpClientUtil.httpGet(url);
        ResultVo<List<ShipVo>> resultVo = mapper.readValue(result, ResultVo.class);
        return resultVo;
    }

    /**
     * 根据mmsi查询船舶信息 查不到返回null。httpGet
     * 2019-1-20 新改返回信息
     * @param
     */
    @GetMapping("/findOne/{mmsi}")
    public TotalShipVo findOne(
            @Validated
            @NotNull(message = "mmsi不能为null")
            @PathVariable(value = "mmsi")String mmsi) throws IOException {

        String url = ZNGlobal.FINDONE_SHIP_URL + mmsi;
        String result = HttpClientUtil.httpGet(url);
        TotalShipVo resultVo = mapper.readValue(result, TotalShipVo.class);
        //在下边做一系列判断操作
        if(null != resultVo){
            List<ShipInfoVo> data = resultVo.getData();
            if(!CollectionUtils.isEmpty(data)){
                String shiptype ;
                String navistat ;
                String length1 ;
                String width ;
                String draught ;
                String sog ;
                String etaStd ;
                String lasttime ;
                for (ShipInfoVo datum : data) {
                    if(null != datum){
                        shiptype = datum.getShiptype();//船舶类型 判断
                        if(!StringUtils.isEmpty(shiptype)){
                            if("100".equals(shiptype)) datum.setShiptype("集装箱");
                            if(shiptype.startsWith("2")) datum.setShiptype("地效应船");
                            if("30".equals(shiptype)) datum.setShiptype("捕捞");
                            if("31".equals(shiptype)) datum.setShiptype("拖引");
                            if("32".equals(shiptype)) datum.setShiptype("拖引并且船长>200m 或船宽>25m");
                            if("33".equals(shiptype)) datum.setShiptype("疏浚或水下作业");
                            if("34".equals(shiptype)) datum.setShiptype("潜水作业");
                            if("35".equals(shiptype)) datum.setShiptype("参与军事行动");
                            if("36".equals(shiptype)) datum.setShiptype("帆船航行");
                            if("37".equals(shiptype)) datum.setShiptype("娱乐船");
                            if(shiptype.startsWith("4")) datum.setShiptype("高速船");
                            if("50".equals(shiptype)) datum.setShiptype("引航船");
                            if("51".equals(shiptype)) datum.setShiptype("搜救船");
                            if("52".equals(shiptype)) datum.setShiptype("拖轮");
                            if("53".equals(shiptype)) datum.setShiptype("港口供应船");
                            if("54".equals(shiptype)) datum.setShiptype("载有防污染装置和设备的船舶");
                            if("55".equals(shiptype)) datum.setShiptype("执法艇");
                            if("56".equals(shiptype) || "57".equals(shiptype)) datum.setShiptype("备用-用于当地船舶的任务分配");
                            if("58".equals(shiptype)) datum.setShiptype("医疗船（如 1949 年日内瓦公约及附加条款所规定）");
                            if("59".equals(shiptype)) datum.setShiptype("符合 18 号决议（Mob-83）的船舶");
                            if(shiptype.startsWith("6")) datum.setShiptype("客船");
                            if(shiptype.startsWith("7")) datum.setShiptype("货船");
                            if(shiptype.startsWith("8")) datum.setShiptype("油轮");
                            else datum.setShiptype("其他类型的船舶");
                        }
                        navistat = datum.getNavistat();//船舶航行状态 判断
                        switch (navistat){
                            case "0":datum.setNavistat("在航（主机推动）");break;
                            case "1":datum.setNavistat("锚泊");break;
                            case "2":datum.setNavistat("失控");break;
                            case "3":datum.setNavistat("操纵受限");break;
                            case "4":datum.setNavistat("吃水受限");break;
                            case "5":datum.setNavistat("靠泊");break;
                            case "6":datum.setNavistat("搁浅");break;
                            case "7":datum.setNavistat("捕捞作业");break;
                            case "8":datum.setNavistat("靠帆船提供动力");break;
                            case "9":datum.setNavistat("HSC 航行状态修正");break;
                            case "10":datum.setNavistat("WIG 航行状态修正");break;
                            default:datum.setNavistat("未定义");break;
                        }

                        length1 = datum.getLength1();//船长 分米 转 米
                        datum.setLength1(deFormat(length1, 0));
                        width = datum.getWidth();//船宽 分米 转 米
                        datum.setWidth(deFormat(width, 0));
                        draught = datum.getDraught();//吃水 毫米转米
                        datum.setDraught(deFormat(draught, 1));
                        sog = datum.getSog();//速度  毫米转节
                        datum.setSog(deFormat(sog, 2));

                        etaStd = datum.getEtaStd();//预到时间
                        datum.setEtaStd(timeFormat(etaStd));
                        lasttime = datum.getLasttime();//更新时间
                        datum.setLasttime(timeFormat(lasttime));
                    }
                }
            }
        }
        return resultVo;
    }
    private String timeFormat(String milliseconds){
        String format = "";
        if(!StringUtils.isEmpty(milliseconds)){
            char[] chars = milliseconds.toCharArray();
            boolean digit;
            for (char aChar : chars) {
                digit = Character.isDigit(aChar);
                if(!('.'== aChar) && (false == digit)){
                    return format;
                }
            }
            long longTime = Long.parseLong(milliseconds);
            Date date = new Date(longTime);
            DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format = instance.format(date);
        }
        return format;
    }
    private String deFormat(String decimetreFormat ,Integer flag){
        String format = "";
        if(!StringUtils.isEmpty(decimetreFormat)){
            char[] chars = decimetreFormat.toCharArray();
            boolean digit;
            for (char aChar : chars) {
                digit = Character.isDigit(aChar);
                if(!('.'== aChar) && (false == digit)){
                    return format;
                }
            }
            double doubleDe = Double.parseDouble(decimetreFormat);
            double doubleFormat = 0;
            if(0 == flag){//0是分米转米
                doubleFormat = doubleDe / 10;
            }else if(1 == flag){//1是毫米转米
                doubleFormat = doubleDe / 1000;
            }else{ //毫米转节
                doubleFormat = doubleDe * 3.6 / 1852;
            }
            NumberFormat instance = NumberFormat.getInstance();
            instance.setMaximumFractionDigits(2);
            format = String.valueOf(instance.format(doubleFormat));
        }
        return format;
    }
}
