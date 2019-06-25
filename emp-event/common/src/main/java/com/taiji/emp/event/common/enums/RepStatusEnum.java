package com.taiji.emp.event.common.enums;

/**
 * 初报状态枚举类
 */
public enum RepStatusEnum {
    FIRST("0", "初报"),
    NOTFIRST("1", "非初报");

    private String code;
    private String desc;

    private RepStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RepStatusEnum codeOf(String code) {
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
                return FIRST;
            case 1:
                return NOTFIRST;
            default:
                return null;
        }
    }

    public static RepStatusEnum descOf(String desc) {
        byte var2 = -1;
        switch(desc.hashCode()) {
            case 676904:
                if (desc.equals("初报")) {
                    var2 = 0;
                }
                break;
            case 37915654:
                if (desc.equals("非初报")) {
                    var2 = 1;
                }
        }

        switch(var2) {
            case 0:
                return FIRST;
            case 1:
                return NOTFIRST;
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
