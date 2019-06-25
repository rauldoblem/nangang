package com.taiji.emp.nangang.common.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * nangang微服务全局常量类
 */
public class NangangGlobal {

    public static final Integer ZERO = 0;
    /**
     * 交接班标志位
     */
    public static final String IS_SHIFT_NO = "0";
    public static final String IS_SHIFT_YES = "1";

    /**
     * 卡口名称
     * for PassRecordVehicleMapper
     * 大屏车辆信息使用
     */
    public static final String HONGQI_ROAD = "红旗路";
    public static final String HAIGANG_SOUTH_ROAD = "海港南路";
    public static final String HAIGANG_NORTH_ROAD = "海港北路";
    public static final String NANTI_ROAD= "南堤路";
    public static final String HAIFANG_NORTH_ROAD = "海防北路";
    public static final String HAIFANG_MIDDLE_ROAD = "海防中路";
    public static final String HAIFANG_SOUTH_ROAD = "海防南路";
    public static final String CHUANGYE_ROAD= "创业路";
    public static final String BINHAI_NORTH_ROAD = "港北路西";

    /**
     * BAYONET_NAME 卡口名称List
     * for PassRecordVehicleMapper
     * 大屏车辆信息使用
     */
    public static final List<String> BAYONET_NAME_LIST = new ArrayList();
    static{
        BAYONET_NAME_LIST.add(HONGQI_ROAD);
        BAYONET_NAME_LIST.add(HAIGANG_SOUTH_ROAD);
        BAYONET_NAME_LIST.add(HAIGANG_NORTH_ROAD);
        BAYONET_NAME_LIST.add(NANTI_ROAD);
        BAYONET_NAME_LIST.add(HAIFANG_NORTH_ROAD);
        BAYONET_NAME_LIST.add(HAIFANG_MIDDLE_ROAD);
        BAYONET_NAME_LIST.add(HAIFANG_SOUTH_ROAD);
        BAYONET_NAME_LIST.add(CHUANGYE_ROAD);
        BAYONET_NAME_LIST.add(BINHAI_NORTH_ROAD);
    }

    /**
     * VEHICLE_TYPE 车辆类型数组
     * for PassRecordVehicleMapper
     * 大屏车辆信息使用
     */
    public static final String SHIGONG_VIHICLE = "施工车辆";
    public static final String OTHER_VIHICLE = "未知车辆";
    public static final String[] VEHICLE_TYPE =
            {"普通货车","普通客车","危化品车辆","施工车辆","抢险救援特种车辆"};

    /**
     * VEHICLE_TYPE 车辆类型数组
     * for PassRecordVehicleMapper
     * 大屏车辆信息使用
     */
    public static final List<String> VEHICLE_TYPE_LIST = new ArrayList<>();
    static{
        VEHICLE_TYPE_LIST.addAll(Arrays.asList(VEHICLE_TYPE));
    }
    /**
     * IN_OUT_FLAG 车辆进出标志
     * for PassRecordVehicleMapper
     * 大屏车辆信息使用
     */
    public static final String IN_OUT_FLAG_IN = "进";
    public static final String IN_OUT_FLAG_OUT = "出";
    /**
     * TOTAL_NUM 总量
     * for PassRecordVehicleMapper
     * 大屏车辆信息使用
     */
    public static final String TOTAL_NUM = "总量";

    public static final String DEAL_ORG_ID = "1";
    public static final String DEAL_ORG_NAME = "应急指挥中心";

    /**
     * 首页工作检查单，固定班次id,班次名字
     */
    public static final String SHIFT_PATTERN_ID = "000001";
    public static final String SHIFT_PATTERN_NAME = "白班";
    /**
     * 要过滤的用户名
     */
    public static final String INFO_USER_NAME = "yjzhzxdp";
    /**
     *环境数据取的nodeID
     */
    public static final String IF_METEOROLOGICAL_NODEID = "4028e4774cbfefd0014d46d2b8af0004";
    /**
     * 废水取的nodeID
     */
    public static final String IF_WASTEWATER_NODEID = "10114";

    /**
     * 首页签到时间范围可修改
     */
    public static final String SIGN_TIME = "45";
}
