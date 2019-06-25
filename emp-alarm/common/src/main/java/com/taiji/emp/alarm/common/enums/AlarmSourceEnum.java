package com.taiji.emp.alarm.common.enums;

/**
 * 预警来源枚举类
 */
public enum AlarmSourceEnum {

    SYS_ADD("1", "系统录入"),
    WEATHER_ALARM("2", "天气预警"),
    DEVICE_ALARM("3","设备预警");

    private String code;
    private String desc;

    private AlarmSourceEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AlarmSourceEnum codeOf(String code) {
        byte var2 = -1;
        switch(code.hashCode()) {
            case 49:
                if (code.equals("1")) {
                    var2 = 1;
                }
                break;
            case 50:
                if (code.equals("2")) {
                    var2 = 2;
                }
                break;
            case 51:
                if (code.equals("3")) {
                    var2 = 3;
                }
        }

        switch(var2) {
            case 1:
                return SYS_ADD;
            case 2:
                return WEATHER_ALARM;
            case 3:
                return DEVICE_ALARM;
            default:
                return null;
        }
    }

    public static AlarmSourceEnum descOf(String desc) {
        byte var2 = -1;
        switch(desc.hashCode()) {
            case 985152756:
                if (desc.equals("系统录入")) {
                    var2 = 1;
                }
                break;
            case 707814573:
                if (desc.equals("天气预警")) {
                    var2 = 2;
                }
                break;
            case 1088891435:
                if(desc.equals("设备预警")){
                    var2 = 3;
                }
        }

        switch(var2) {
            case 1:
                return SYS_ADD;
            case 2:
                return WEATHER_ALARM;
            case 3:
                return DEVICE_ALARM;
            default:
                return null;
        }
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

}
