package com.taiji.emp.zn.service;

import com.taiji.emp.alarm.searchVo.AlertPageSearchVo;
import com.taiji.emp.alarm.vo.AlertVo;
import com.taiji.emp.zn.feign.AlertClient;
import com.taiji.emp.zn.feign.InfoClient;
import com.taiji.emp.zn.vo.InfoStatVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

@Service
public class InfoService {

    private static final Integer num_5 = 5;
    private static final Integer num_10 = 5;
    private static final Integer page = 0;

    @Autowired
    private AlertClient client;
    @Autowired
    private InfoClient infoClient;

    //根据条件查询未处理状态的预警信息列表
    public List<AlertVo> findAlerts(Map<String,Object> map){
        List<String> alertStatus =(List<String>)map.get("alertStatus");
        Integer topNum = null;
        if(!map.containsKey("topNum")|| null == map.get("topNum")){ //如果前台未传：默认5条
            topNum = num_5;
        }else{
            topNum = (Integer) map.get("topNum");
        }
        AlertPageSearchVo pageSearchVo = new AlertPageSearchVo();
        pageSearchVo.setPage(page);
        pageSearchVo.setSize(topNum);
        pageSearchVo.setNoticeFlags(alertStatus);

        ResponseEntity<RestPageImpl<AlertVo>> result = client.findPage(pageSearchVo);
        RestPageImpl<AlertVo> resultPage = ResponseEntityUtils.achieveResponseEntityBody(result);
        return resultPage.getContent();
    }

    //根据条件查询预警/事件信息列表-不分页，并按视图view_alarm_event中的report_time倒序排列
    public List<InfoStatVo> findInfos(Map<String,Object> map){
        Integer topNum = null;
        if(!map.containsKey("topNum")|| null == map.get("topNum")){ //如果前台未传：默认10条
            topNum = num_10;
        }else{
            topNum = (Integer) map.get("topNum");
        }
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("page",page);
        params.add("size",topNum);

        ResponseEntity<RestPageImpl<InfoStatVo>> result = infoClient.statInfo(params);
        RestPageImpl<InfoStatVo> resultPage = ResponseEntityUtils.achieveResponseEntityBody(result);
        return resultPage.getContent();
    }

}
