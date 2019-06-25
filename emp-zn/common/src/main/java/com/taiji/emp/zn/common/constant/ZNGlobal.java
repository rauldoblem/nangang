package com.taiji.emp.zn.common.constant;

/**
 * @author yhcookie
 * @date 2018/12/22 22:24
 */
public class ZNGlobal {
    /**
     * 获取船只列表的外部接口地址
     */
    public static final String GET_SHIPS_URL = "http://extinter-service.edcadp.zhenergy.com.cn/ship/shipList";

    /**
     * 根据mmsi获取一艘船信息的外部接口地址
     */
    public static final String FINDONE_SHIP_URL = "http://extinter-service.edcadp.zhenergy.com.cn/ship/findShipSailing?mmsi=";

    /**
     * 获取天气信息的外部接口地址
     */
    public static final String GET_WEATHER_URL = "http://extinter-service.edcadp.zhenergy.com.cn/weather/findFutureFiveDay?cityName=";

    /**
     * 城市名 杭州
     */
    public static final String HANGZHOU = "杭州";
    /**
     * 获取大坝信息的外部接口
     * code(大坝编码，北海大坝 DB001,华光潭一级坝 DB002,华光潭二级把DB003)
     */
    public static final String GET_DAMINFO = "http://extinter-service.edcadp.zhenergy.com.cn/tsdb/queryByCode?code=";

    /**
     * 获取大坝信息列表外部接口
     * code(大坝编码，北海大坝 DB001,华光潭一级坝 DB002,华光潭二级把DB003)
     */
    public static final String GET_DAMList = "http://extinter-service.edcadp.zhenergy.com.cn/tsdb/queryDblist";

    /**
     * 北海大坝
     */
    public static final String BEIHAIDABA = "北海大坝";

    /**
     * 北海大坝 code
     */
    public static final String BEIHAIDABA_CODE = "DB001";

    /**
     * 华光潭一级坝
     */
    public static final String HUAGUANGTAN_YIJIBA = "华光潭一级坝";

    /**
     * 华光潭一级坝 code
     */
    public static final String HUAGUANGTAN_YIJIBA_CODE = "DB002";

    /**
     * 华光潭二级坝
     */
    public static final String HUAGUANGTAN_ERJIBA = "华光潭二级坝";

    /**
     * 华光潭二级坝 code
     */
    public static final String HUAGUANGTAN_ERJIBA_CODE = "DB003";

    /**
     * 获取台风列表
     */
    public static final String GET_TYPHOON_LIST_URL = "http://extinter-service.edcadp.zhenergy.com.cn/typhoon/findTyphoonList";

    /**
     * 通过id获取台风
     */
    public static final String FINDONE_TYPHOON_BY_ID_URL = "http://extinter-service.edcadp.zhenergy.com.cn/typhoon/findTyphoonById?typhoonid=";

    /**
     * 首页使用的台风信息（y轴）
     */
    public static final String[] LON_LAT_AIRPRESSURE_WINDSPEED = {"经度","纬度","经中心气压度","最大风速"};
}
