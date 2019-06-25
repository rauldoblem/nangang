package com.taiji.emp.event.common.enums;

/**
 * 初报状态枚举类
 */
public enum CmdSubTypeEnum {
    PERSONAL("0", "个人"),
    ORG("1", "单位");

    private String code;
    private String desc;

    private CmdSubTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CmdSubTypeEnum codeOf(String code) {
        byte var2 = -1;
        switch(code.hashCode()) {
            case 48:
                if (code.equals("0")) {
                    var2 = 0;
                }
                break;
            case 49:
                if (code.equals("1")) {
                    var2 = 1;
                }
        }

        switch(var2) {
            case 0:
                return PERSONAL;
            case 1:
                return ORG;
            default:
                return null;
        }
    }

    public static CmdSubTypeEnum descOf(String desc) {
        byte var2 = -1;
        switch(desc.hashCode()) {
            case 640464:
                if (desc.equals("个人")) {
                    var2 = 1;
                }
                break;
            case 681624:
                if (desc.equals("单位")) {
                    var2 = 0;
                }
        }

        switch(var2) {
            case 0:
                return PERSONAL;
            case 1:
                return ORG;
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
