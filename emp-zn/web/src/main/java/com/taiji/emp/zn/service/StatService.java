package com.taiji.emp.zn.service;

import com.taiji.emp.res.vo.HazardVo;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.zn.common.constant.StatGlobal;
import com.taiji.emp.zn.feign.DateStatClient;
import com.taiji.emp.zn.feign.HazardClient;
import com.taiji.emp.zn.feign.MaterialClient;
import com.taiji.emp.zn.feign.ResStatClient;
import com.taiji.emp.zn.vo.*;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class StatService extends BaseService{

    @Autowired
    private ResStatClient clent;
    @Autowired
    private DateStatClient dateStatClient;
    @Autowired
    private MaterialClient materialClient;
    @Autowired
    private HazardClient hazardClient;


    public List<TargetTypeStatVo> statTargets(){
        ResponseEntity<List<TargetTypeStatVo>> resultList = clent.statTargetByType();
        return ResponseEntityUtils.achieveResponseEntityBody(resultList);
    }

    public List<EventTypeStatVo> statExperts(){
        ResponseEntity<List<EventTypeStatVo>> resultList = clent.statExpertByType();
        return ResponseEntityUtils.achieveResponseEntityBody(resultList);
    }

    public List<TeamTypeStatVo> statTeams(){
        ResponseEntity<List<TeamTypeStatVo>> resultList = clent.statTeamByType();
        return ResponseEntityUtils.achieveResponseEntityBody(resultList);
    }

    /**
     * 事件，预警按日期统计接口
     * 参数：{
     "statYears": [
     0
     ],
     "statMonths": [
     0
     ],
     "alertStatus": [
     "string"
     ]
     }
     */
    public List<DateStatDTO> statEventAlert(Map<String,Object> map){
        List<Integer> statYears = (List<Integer>)map.get("statYears");
        List<Integer> statMonths = (List<Integer>)map.get("statMonths");
        List<String> alertStatus = (List<String>)map.get("alertStatus");
        if(null!=statYears&&null!=statMonths){ //年月不能为空
            if(statYears.size()>0&& statMonths.size()>0){
                List<String> dateList = new ArrayList<>();
                List<List<String>> yearLists = new ArrayList<>();//每年的月份集合
                for(Integer year : statYears){
                    List<String> yearList = new ArrayList<>();
                    for(Integer month : statMonths){
                        String monthStr = month<10?("0"+month):(""+month); //月份补0
                        String date = String.valueOf(year)+"-"+monthStr;
                        dateList.add(date);
                        yearList.add(date);
                    }
                    yearLists.add(yearList);
                }
                Map<String,Object> params = new HashMap<>();
                params.put("statDate",dateList);
                params.put("alertStatus",alertStatus);
                ResponseEntity<EventAndAlertDateStatVo> result = dateStatClient.statEventAndAlertDate(convertMap2MultiValueMap(params));
                EventAndAlertDateStatVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
                if(null!=resultVo){
                    Map<String,Integer> eventStatMap = resultVo.getEventStatMap(); //事件按月统计对象 example: {'2018-11':5,'2018-12':6}
                    Map<String,Integer> alertStatMap = resultVo.getAlertStatMap(); //预警信息按月统计对象 example: {'2018-11':5,'2018-12':6}
                    List<DateStatDTO> resultList = new ArrayList<>();
                    for(List<String> list:yearLists){ //每年的所有月份
                        List<Integer> eventTotalNums = new ArrayList<>();
                        List<Integer> alertTotalNums = new ArrayList<>();
                        for(String time:list){
                            Integer eventTotalNum = 0;
                            Integer alertTotalNum = 0;
                            if(eventStatMap.containsKey(time)){
                                eventTotalNum = eventStatMap.get(time);
                            }
                            eventTotalNums.add(eventTotalNum);
                            if(alertStatMap.containsKey(time)){
                                alertTotalNum = alertStatMap.get(time);
                            }
                            alertTotalNums.add(alertTotalNum);
                        }
                        resultList.add(new DateStatDTO(eventTotalNums,alertTotalNums));
                    }
                    return resultList;
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 统计各板块的应急物资各大类数量
     * @param vo
     * @return
     */
    public List<MaterialStatVo> statMaterials(MaterialSearchVo vo) {
        //List<MaterialStatVo> resultList = new ArrayList<>();

        //类型下的板块
        ResponseEntity<List<MaterialStatVo>> listVo = materialClient.findInfo(vo);
        List<MaterialStatVo> mapList = ResponseEntityUtils.achieveResponseEntityBody(listVo);
//        List<String> listCode = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(mapList)){
//            for (MaterialStatVo materialStatVo : mapList){
//                String resTypeId = materialStatVo.getResTypeId();
//                if (!listCode.contains(resTypeId)){
//                    listCode.add(resTypeId);
//                }
//            }
//        }
//
//        //类型
//        ResponseEntity<List<MaterialVo>> list = materialClient.findGroupList(listCode);
//        List<MaterialVo> materialVoList = ResponseEntityUtils.achieveResponseEntityBody(list);
//        Map<String, MaterialStatVo> mapEntity = new HashMap<>();//每个类型的数量(总数据)
//        for (MaterialVo materialVo : materialVoList) {
//            String resTypeId = materialVo.getResTypeId();
//            MaterialStatVo materialStatVo = new MaterialStatVo();
//            List<Integer> totalNum = new ArrayList<>();
//            Map<String, Integer> map = new HashMap<>();//记录板块的数量
//            if (!CollectionUtils.isEmpty(mapList)) {
//                int temp1 = 0;
//                int temp2 = 0;
//                int temp3 = 0;
//                int temp4 = 0;
//                int temp5 = 0;
//                int temp6 = 0;
//                int temp7 = 0;
//                int temp8 = 0;
//                for (MaterialStatVo statVo : mapList) {
//                    String resTypeId_ = statVo.getResTypeId();
//                    if (resTypeId.equals(resTypeId_)) {
//                        String orgCode = statVo.getOrgCode();
//                        if(!StringUtils.isEmpty(orgCode)) {
//                            orgCode = orgCode.substring(0, 2);
//                            if (StatGlobal.INFO_STAT_ONE.equals(orgCode)) {
//                                temp1++;
//                                map.put(StatGlobal.INFO_STAT_ONE, temp1);
//                            } else if (StatGlobal.INFO_STAT_TWO.equals(orgCode)) {
//                                temp2++;
//                                map.put(StatGlobal.INFO_STAT_TWO, temp2);
//                            } else if (StatGlobal.INFO_STAT_THREE.equals(orgCode)) {
//                                temp3++;
//                                map.put(StatGlobal.INFO_STAT_THREE, temp3);
//                            } else if (StatGlobal.INFO_STAT_FOUR.equals(orgCode)) {
//                                temp4++;
//                                map.put(StatGlobal.INFO_STAT_FOUR, temp4);
//                            } else if (StatGlobal.INFO_STAT_FIVE.equals(orgCode)) {
//                                temp5++;
//                                map.put(StatGlobal.INFO_STAT_FIVE, temp5);
//                            } else if (StatGlobal.INFO_STAT_SIX.equals(orgCode)) {
//                                temp6++;
//                                map.put(StatGlobal.INFO_STAT_SIX, temp6);
//                            } else if (StatGlobal.INFO_STAT_SEVEN.equals(orgCode)) {
//                                temp7++;
//                                map.put(StatGlobal.INFO_STAT_SEVEN, temp7);
//                            } else if (StatGlobal.INFO_STAT_EIGHT.equals(orgCode)) {
//                                temp8++;
//                                map.put(StatGlobal.INFO_STAT_EIGHT, temp8);
//                            }
//                        }
//                    }
//                }
//
//            }
//            //存放每个类型的板块数量
//            if (!CollectionUtils.isEmpty(map)) {
//                for (Map.Entry<String, Integer> entry : map.entrySet()) {
//                    Integer value = entry.getValue();
//                    totalNum.add(value);
//                }
//            }else {
//                totalNum.add(0);
//                totalNum.add(0);
//                totalNum.add(0);
//                totalNum.add(0);
//                totalNum.add(0);
//                totalNum.add(0);
//                totalNum.add(0);
//                totalNum.add(0);
//            }
//            materialStatVo.setResTypeId(resTypeId);
//            materialStatVo.setResTypeName(materialVo.getResTypeName());
//            materialStatVo.setTotalNum(totalNum);
//            mapEntity.put(resTypeId,materialStatVo);
//        }
//        //把最终数据存放集合
//        if (!CollectionUtils.isEmpty(mapEntity)) {
//            for (Map.Entry<String, MaterialStatVo> entry : mapEntity.entrySet()) {
//                MaterialStatVo value = entry.getValue();
//                resultList.add(value);
//            }
//        }
        return mapList;
    }

    /**
     * 统计各板块不同级别的重大风险源数量
     * @param vo
     * @return
     */
    public List<HazardStatVo> statHazards(MaterialSearchVo vo) {
        //List<HazardStatVo> resultList = new ArrayList<>();



        //类型下的板块
        ResponseEntity<List<HazardStatVo>> listVo = hazardClient.findInfo(vo);
        List<HazardStatVo> statVoList = ResponseEntityUtils.achieveResponseEntityBody(listVo);
//        List<String> listCode = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(statVoList)){
//            for (HazardStatVo hazardStatVo : statVoList){
//                String danGradeId = hazardStatVo.getDanGradeId();
//                if (!listCode.contains(danGradeId)){
//                    listCode.add(danGradeId);
//                }
//            }
//        }
//
//        //类型   危险源级别ID集合
//        ResponseEntity<List<HazardVo>> list = hazardClient.findGroupList(listCode);
//        List<HazardVo> hazardVoList = ResponseEntityUtils.achieveResponseEntityBody(list);
//
//        Map<String, HazardStatVo> mapEntity = new HashMap<>();//每个类型的数量(总数据)
//        for (HazardVo hazardVo : hazardVoList){
//            String danGradeId = hazardVo.getDanGradeId();
//            HazardStatVo hazardStatVo = new HazardStatVo();
//            List<Integer> totalNums = new ArrayList<>();
//            Map<String, Integer> map = new HashMap<>();//记录板块的数量
//            if (!CollectionUtils.isEmpty(statVoList)) {
//                int temp1 = 0;
//                int temp2 = 0;
//                int temp3 = 0;
//                int temp4 = 0;
//                int temp5 = 0;
//                int temp6 = 0;
//                int temp7 = 0;
//                int temp8 = 0;
//                for (HazardStatVo statVo : statVoList){
//                    String danGradeId_ = statVo.getDanGradeId();
//                    if (danGradeId.equals(danGradeId_)){
//                        String orgCode = statVo.getOrgCode();
//                        if(!StringUtils.isEmpty(orgCode)) {
//                            orgCode = orgCode.substring(0, 2);
//                            if (StatGlobal.INFO_STAT_ONE.equals(orgCode)) {
//                                temp1++;
//                                map.put(StatGlobal.INFO_STAT_ONE, temp1);
//                            } else if (StatGlobal.INFO_STAT_TWO.equals(orgCode)) {
//                                temp2++;
//                                map.put(StatGlobal.INFO_STAT_TWO, temp2);
//                            } else if (StatGlobal.INFO_STAT_THREE.equals(orgCode)) {
//                                temp3++;
//                                map.put(StatGlobal.INFO_STAT_THREE, temp3);
//                            } else if (StatGlobal.INFO_STAT_FOUR.equals(orgCode)) {
//                                temp4++;
//                                map.put(StatGlobal.INFO_STAT_FOUR, temp4);
//                            } else if (StatGlobal.INFO_STAT_FIVE.equals(orgCode)) {
//                                temp5++;
//                                map.put(StatGlobal.INFO_STAT_FIVE, temp5);
//                            } else if (StatGlobal.INFO_STAT_SIX.equals(orgCode)) {
//                                temp6++;
//                                map.put(StatGlobal.INFO_STAT_SIX, temp6);
//                            } else if (StatGlobal.INFO_STAT_SEVEN.equals(orgCode)) {
//                                temp7++;
//                                map.put(StatGlobal.INFO_STAT_SEVEN, temp7);
//                            } else if (StatGlobal.INFO_STAT_EIGHT.equals(orgCode)) {
//                                temp8++;
//                                map.put(StatGlobal.INFO_STAT_EIGHT, temp8);
//                            }
//                        }
//                    }
//                }
//            }
//            //存放每个类型的板块数量
//            if (!CollectionUtils.isEmpty(map)) {
//                for (Map.Entry<String, Integer> entry : map.entrySet()) {
//                    Integer value = entry.getValue();
//                    totalNums.add(value);
//                }
//            }else {
//                totalNums.add(0);
//                totalNums.add(0);
//                totalNums.add(0);
//                totalNums.add(0);
//                totalNums.add(0);
//                totalNums.add(0);
//                totalNums.add(0);
//                totalNums.add(0);
//            }
//            hazardStatVo.setDanGradeId(danGradeId);
//            hazardStatVo.setDanGradeName(hazardVo.getDanGradeName());
//            hazardStatVo.setTotalNums(totalNums);
//            mapEntity.put(danGradeId,hazardStatVo);
//        }
//        if (!CollectionUtils.isEmpty(mapEntity)) {
//            for (Map.Entry<String, HazardStatVo> entry : mapEntity.entrySet()) {
//                HazardStatVo value = entry.getValue();
//                resultList.add(value);
//            }
//        }
        return statVoList;
    }
}
