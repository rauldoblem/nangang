package com.taiji.base.common.enums;

/**
 * 初报状态枚举类
 */
public enum MsgTypeEnum {
    NEWS("0", "消息"),
    UNDO("1", "待办");

    private String code;
    private String desc;

    private MsgTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MsgTypeEnum codeOf(String code) {
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
                return NEWS;
            case 1:
                return UNDO;
            default:
                return null;
        }
    }

    public static MsgTypeEnum descOf(String desc) {
        byte var2 = -1;
        switch(desc.hashCode()) {
            case 893927:
                if (desc.equals("消息")) {
                    var2 = 1;
                }
                break;
            case 779193:
                if (desc.equals("待办")) {
                    var2 = 0;
                }
        }

        switch(var2) {
            case 0:
                return NEWS;
            case 1:
                return UNDO;
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
