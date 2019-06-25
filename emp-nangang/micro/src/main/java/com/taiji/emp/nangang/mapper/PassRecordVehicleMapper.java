package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.common.constant.NangangGlobal;
import com.taiji.emp.nangang.entity.PassRecordVehicle;
import com.taiji.emp.nangang.vo.*;
import org.mapstruct.Mapper;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.text.NumberFormat;
import java.util.*;

/**
 * @author yhcookie
 * @date 2018/12/6 15:13
 */
@Mapper(componentModel = "spring")
public interface PassRecordVehicleMapper {

    /**
     * 大屏页面三 各卡口车流量占总流量的比例
     * @param entitys
     * @return
     */
    default List<BayonetFlowProportionVo> entityToBayonetFlowProportionVoList(List<PassRecordVehicle> entitys){

        boolean empty = CollectionUtils.isEmpty(entitys);
        Assert.isTrue(!empty,"List<BayonetFlowProportionVo>不能为空");

        //拿到总量的总数 数据表没问题就不会空指针
        PassRecordVehicle passRecordVehicle = entitys.get(0);
        String totalVehicle = passRecordVehicle.getTotalVehicle();
        ArrayList<BayonetFlowProportionVo> vos = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            BayonetFlowProportionVo vo = new BayonetFlowProportionVo();
            vo.setBayonetName(NangangGlobal.BAYONET_NAME_LIST.get(i));
            vos.add(vo);
        }

        String flow0 = doubleToString(passRecordVehicle.getHongqiRoad(), totalVehicle);
        vos.get(0).setFlow(flow0);
        String flow1 = doubleToString(passRecordVehicle.getHaigangSouthRoad(), totalVehicle);
        vos.get(1).setFlow(flow1);
        String flow2 = doubleToString(passRecordVehicle.getHaigangNorthRoad(), totalVehicle);
        vos.get(2).setFlow(flow2);
        String flow3 = doubleToString(passRecordVehicle.getNantiRoad(), totalVehicle);
        vos.get(3).setFlow(flow3);
        String flow4 = doubleToString(passRecordVehicle.getHaifangNorthRoad(), totalVehicle);
        vos.get(4).setFlow(flow4);
        String flow5 = doubleToString(passRecordVehicle.getHaifangMiddleRoad(), totalVehicle);
        vos.get(5).setFlow(flow5);
        String flow6 = doubleToString(passRecordVehicle.getHaifangSouthRoad(), totalVehicle);
        vos.get(6).setFlow(flow6);
        String flow7 = doubleToString(passRecordVehicle.getChuangyeRoad(), totalVehicle);
        vos.get(7).setFlow(flow7);
        String flow8 = doubleToString(passRecordVehicle.getBinhaiNorthRoad(), totalVehicle);
        vos.get(8).setFlow(flow8);
        return vos;
    }

    /**
     * 大屏页面一 各卡口车辆的出量和入量
     * @param entitys
     * @return
     */
    default List<BayonetVehiclePassStateVo> entityToBayonetVehiclePassStateVoList(List<PassRecordVehicle> entitys){

        List<BayonetVehiclePassStateVo> results = new ArrayList<>();

        BayonetVehiclePassStateVo vo1 = new BayonetVehiclePassStateVo();
        vo1.setBayonetName(NangangGlobal.HONGQI_ROAD);
        vo1.setOutNumber(entitys.get(0).getHongqiRoad());
        vo1.setInNumber(entitys.get(1).getHongqiRoad());

        BayonetVehiclePassStateVo vo2 = new BayonetVehiclePassStateVo();
        vo2.setBayonetName(NangangGlobal.HAIGANG_SOUTH_ROAD);
        vo2.setOutNumber(entitys.get(0).getHaigangSouthRoad());
        vo2.setInNumber(entitys.get(1).getHaigangSouthRoad());

        BayonetVehiclePassStateVo vo3 = new BayonetVehiclePassStateVo();
        vo3.setBayonetName(NangangGlobal.HAIGANG_NORTH_ROAD);
        vo3.setOutNumber(entitys.get(0).getHaigangNorthRoad());
        vo3.setInNumber(entitys.get(1).getHaigangNorthRoad());

        BayonetVehiclePassStateVo vo4 = new BayonetVehiclePassStateVo();
        vo4.setBayonetName(NangangGlobal.NANTI_ROAD);
        vo4.setOutNumber(entitys.get(0).getNantiRoad());
        vo4.setInNumber(entitys.get(1).getNantiRoad());

        BayonetVehiclePassStateVo vo5 = new BayonetVehiclePassStateVo();
        vo5.setBayonetName(NangangGlobal.HAIGANG_NORTH_ROAD);
        vo5.setOutNumber(entitys.get(0).getHaifangNorthRoad());
        vo5.setInNumber(entitys.get(1).getHaifangNorthRoad());

        BayonetVehiclePassStateVo vo6 = new BayonetVehiclePassStateVo();
        vo6.setBayonetName(NangangGlobal.HAIFANG_MIDDLE_ROAD);
        vo6.setOutNumber(entitys.get(0).getHaifangMiddleRoad());
        vo6.setInNumber(entitys.get(1).getHaifangMiddleRoad());

        BayonetVehiclePassStateVo vo7 = new BayonetVehiclePassStateVo();
        vo7.setBayonetName(NangangGlobal.HAIGANG_SOUTH_ROAD);
        vo7.setOutNumber(entitys.get(0).getHaifangSouthRoad());
        vo7.setInNumber(entitys.get(1).getHaifangSouthRoad());

        BayonetVehiclePassStateVo vo8 = new BayonetVehiclePassStateVo();
        vo8.setBayonetName(NangangGlobal.CHUANGYE_ROAD);
        vo8.setOutNumber(entitys.get(0).getChuangyeRoad());
        vo8.setInNumber(entitys.get(1).getChuangyeRoad());

        BayonetVehiclePassStateVo vo9 = new BayonetVehiclePassStateVo();
        vo9.setBayonetName(NangangGlobal.BINHAI_NORTH_ROAD);
        vo9.setOutNumber(entitys.get(0).getBinhaiNorthRoad());
        vo9.setInNumber(entitys.get(1).getBinhaiNorthRoad());

        results.add(vo1);
        results.add(vo2);
        results.add(vo3);
        results.add(vo4);
        results.add(vo5);
        results.add(vo6);
        results.add(vo7);
        results.add(vo8);
        results.add(vo9);

        return results;
    }

    /**
     * 大屏页面二 不同类型车辆的出量、入量和现存量
     * @param entitys
     * @return
     */
    default TotalVehicleTypeAndFlowVo entityToVehicleTypeAndFlowVoList(List<PassRecordVehicle> entitys){
        //初始化指定格式的vo
        TotalVehicleTypeAndFlowVo totalVehicleTypeAndFlowVo = new TotalVehicleTypeAndFlowVo();
        ArrayList<String> yAxisData = new ArrayList();    //y轴 车辆类型List
        ArrayList<String> totalNumber = new ArrayList();  //存量list
        ArrayList<String> comeInNum = new ArrayList();    //进量list
        ArrayList<String> goOutNum = new ArrayList();     //出量list

        ArrayList<VehicleTypeAndFlowVo> vos = new ArrayList();
        //第一次先创建一个同等长度的vos，相同vehicleType的进和出分别在两个vo
        for (PassRecordVehicle entity : entitys) {
            VehicleTypeAndFlowVo vo = new VehicleTypeAndFlowVo();
            vo.setVehicleType(entity.getVehicleType());
            if(NangangGlobal.IN_OUT_FLAG_IN.equals(entity.getInOutFlag())){
                vo.setIn(entity.getTotalVehicle());
            }else if(NangangGlobal.IN_OUT_FLAG_OUT.equals(entity.getInOutFlag())){
                vo.setOut(entity.getTotalVehicle());
            }
            vos.add(vo);
        }
        //第二次把vos里边VehicleType相同的进的出的放到一个vo里
        String vehicleType = "";
        for (int i = 0; i < vos.size(); i++) {
            VehicleTypeAndFlowVo voi = vos.get(i);
            vehicleType = voi.getVehicleType();
            //remove总量这一条
            if(NangangGlobal.TOTAL_NUM.equals(vehicleType)) vos.remove(i);
            for (int j = 0; j < vos.size(); j++) {
                VehicleTypeAndFlowVo voj = vos.get(j);
                if(null == voj) break;
                 if(vehicleType.equals(voj.getVehicleType())){
                     if(null != voi.getOut() && null == voi.getIn() && null != voj.getIn()){
                         voi.setIn(voj.getIn());
                         vos.remove(j);
                     }else if(null != voi.getIn() && null == voi.getOut() && null != voj.getOut()){
                         voi.setOut(voj.getOut());
                         vos.remove(j);
                     }
                 }
            }
            String in = voi.getIn();
            String out = voi.getOut();
            if(null != in && null != out){
                voi.setExist(String.valueOf(Integer.parseInt(in)-Integer.parseInt(out)));
            }
        }
        //结果集中需要所有车辆类型的数据都各有一条，没查到的放一条指定vehicleType的空数据
        List<VehicleTypeAndFlowVo> results = new ArrayList<>();
        Map<String, VehicleTypeAndFlowVo> resultMap = new HashMap<>();
        for (VehicleTypeAndFlowVo  vehicleTypeAndFlowVo: vos) {
            resultMap.put(vehicleTypeAndFlowVo.getVehicleType(),vehicleTypeAndFlowVo);
        }
        Set<String> vehicleTypeSet = resultMap.keySet();
        for (String vehicleType2 : NangangGlobal.VEHICLE_TYPE_LIST) {
            //如果结果集中没有这个，那我加一个空的
            boolean contains = vehicleTypeSet.contains(vehicleType2);
            if(contains){
                results.add(resultMap.get(vehicleType2));
            }else{
                VehicleTypeAndFlowVo vo = new VehicleTypeAndFlowVo();
                vo.setVehicleType(vehicleType2);
                results.add(vo);
            }
        }
        //组装需要的数据
        for (VehicleTypeAndFlowVo vo : results) {
            yAxisData.add(vo.getVehicleType());
            comeInNum.add(vo.getIn());
            goOutNum.add(vo.getOut());
            totalNumber.add(vo.getExist());
        }
        //把需要的数据封装到指定格式的vo里边 返回
        totalVehicleTypeAndFlowVo.setYAxisData(yAxisData);
        totalVehicleTypeAndFlowVo.setComeInNum(comeInNum);
        totalVehicleTypeAndFlowVo.setGoOutNum(goOutNum);
        totalVehicleTypeAndFlowVo.setTotalNumber(totalNumber);
        return totalVehicleTypeAndFlowVo;
    }

    /**
     * 大屏页面四 不同类型车辆在不同卡口的流量
     * @param entitys
     * @return
     */
    default TotalVehicleTypeAndBayonetFlowVo entityToVehicleTypeAndBayonetFlowVoList(Map<String, PassRecordVehicle> entitys){

        //创建一个总vo，返回就是返回这个
        TotalVehicleTypeAndBayonetFlowVo totalVehicleTypeAndBayonetFlowVo = new TotalVehicleTypeAndBayonetFlowVo();
        //把总vo里的两个集合拿出来
        List<BayonetFlowForTotalVehicleVo> seriesList = new ArrayList<>();
        //List<BayonetFlowForTotalVehicleVo> series = totalVehicleTypeAndBayonetFlowVo.getSeries();
        List<String> xAxis = totalVehicleTypeAndBayonetFlowVo.getXAxis();
        //遍历把总vo里的小series集合所需的元素创建出来,赋值BayonetName，放入series
        for (String bayonetName : NangangGlobal.BAYONET_NAME_LIST) {
            BayonetFlowForTotalVehicleVo bayonetFlowForTotalVehicleVo = new BayonetFlowForTotalVehicleVo();
            ArrayList<String> list = new ArrayList<>();
            bayonetFlowForTotalVehicleVo.setData(list);
            //放上卡口名字
            bayonetFlowForTotalVehicleVo.setBayonetName(bayonetName);
            //再放上对应卡口的数据,比如：红旗路  就放上红旗路不同车辆的数据
            if(NangangGlobal.HONGQI_ROAD.equals(bayonetName)){
                for (String vehicleType : xAxis) {
                    //map get不到返回null
                    PassRecordVehicle passRecordVehicle = entitys.get(vehicleType);
                    if(null == passRecordVehicle){
                        bayonetFlowForTotalVehicleVo.getData().add(null);
                    }else{
                        bayonetFlowForTotalVehicleVo.getData().add(passRecordVehicle.getHongqiRoad());
                    }
                }
            }else if(NangangGlobal.HAIGANG_SOUTH_ROAD.equals(bayonetName)){
                for (String vehicleType : xAxis) {
                    PassRecordVehicle passRecordVehicle = entitys.get(vehicleType);
                    if(null == passRecordVehicle){
                        bayonetFlowForTotalVehicleVo.getData().add(null);
                    }else{
                        bayonetFlowForTotalVehicleVo.getData().add(passRecordVehicle.getHaigangSouthRoad());
                    }
                }
            }else if(NangangGlobal.HAIGANG_NORTH_ROAD.equals(bayonetName)){
                for (String vehicleType : xAxis) {
                    PassRecordVehicle passRecordVehicle = entitys.get(vehicleType);
                    if(null == passRecordVehicle){
                        bayonetFlowForTotalVehicleVo.getData().add(null);
                    }else{
                        bayonetFlowForTotalVehicleVo.getData().add(passRecordVehicle.getHaigangNorthRoad());
                    }
                }
            }else if(NangangGlobal.NANTI_ROAD.equals(bayonetName)){
                for (String vehicleType : xAxis) {
                    PassRecordVehicle passRecordVehicle = entitys.get(vehicleType);
                    if(null == passRecordVehicle){
                        bayonetFlowForTotalVehicleVo.getData().add(null);
                    }else{
                        bayonetFlowForTotalVehicleVo.getData().add(passRecordVehicle.getNantiRoad());
                    }
                }
            }else if(NangangGlobal.HAIFANG_NORTH_ROAD.equals(bayonetName)){
                for (String vehicleType : xAxis) {
                    PassRecordVehicle passRecordVehicle = entitys.get(vehicleType);
                    if(null == passRecordVehicle){
                        bayonetFlowForTotalVehicleVo.getData().add(null);
                    }else{
                        bayonetFlowForTotalVehicleVo.getData().add(passRecordVehicle.getHaifangNorthRoad());
                    }
                }
            }else if(NangangGlobal.HAIFANG_MIDDLE_ROAD.equals(bayonetName)){
                for (String vehicleType : xAxis) {
                    PassRecordVehicle passRecordVehicle = entitys.get(vehicleType);
                    if(null == passRecordVehicle){
                        bayonetFlowForTotalVehicleVo.getData().add(null);
                    }else{
                        bayonetFlowForTotalVehicleVo.getData().add(passRecordVehicle.getHaifangMiddleRoad());
                    }
                }
            }else if(NangangGlobal.HAIFANG_SOUTH_ROAD.equals(bayonetName)){
                for (String vehicleType : xAxis) {
                    PassRecordVehicle passRecordVehicle = entitys.get(vehicleType);
                    if(null == passRecordVehicle){
                        bayonetFlowForTotalVehicleVo.getData().add(null);
                    }else{
                        bayonetFlowForTotalVehicleVo.getData().add(passRecordVehicle.getHaifangSouthRoad());
                    }
                }
            }else if(NangangGlobal.CHUANGYE_ROAD.equals(bayonetName)){
                for (String vehicleType : xAxis) {
                    PassRecordVehicle passRecordVehicle = entitys.get(vehicleType);
                    if(null == passRecordVehicle){
                        bayonetFlowForTotalVehicleVo.getData().add(null);
                    }else{
                        bayonetFlowForTotalVehicleVo.getData().add(passRecordVehicle.getChuangyeRoad());
                    }
                }
            }else if(NangangGlobal.BINHAI_NORTH_ROAD.equals(bayonetName)){
                for (String vehicleType : xAxis) {
                    PassRecordVehicle passRecordVehicle = entitys.get(vehicleType);
                    if(null == passRecordVehicle){
                        bayonetFlowForTotalVehicleVo.getData().add(null);
                    }else{
                        bayonetFlowForTotalVehicleVo.getData().add(passRecordVehicle.getBinhaiNorthRoad());
                    }
                }
            }
            seriesList.add(bayonetFlowForTotalVehicleVo);
        }
        totalVehicleTypeAndBayonetFlowVo.setSeries(seriesList);
        return totalVehicleTypeAndBayonetFlowVo;
    }

    /**
     * 将两个数字字符串做商 输出百分数
     * @param str1
     * @param str2
     * @return
     */
    default String doubleToString(String str1 ,String str2 ){

        double temp = Double.parseDouble(str1)/Double.parseDouble(str2);
        NumberFormat instance = NumberFormat.getPercentInstance();
        instance.setMaximumFractionDigits(2);
        return instance.format(temp).replace("%","");

    }
}
