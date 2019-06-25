package com.taiji.emp.alarm.common.enums;

public enum AlarmTypeEnum {

    FIRST("1", "Alert"),//1（Alert首次）首次发布的预警；
    UPDATE("2", "Update"),//2(Update更新)更新预警信息（续发属于更新）；
    CANCEL("3","Cancel"),//3(Cancel解除)一次预警事件生命周期结束后，可对其进行解除；
    ACK("8","Ack"),//8（Ack确认）确认收到的预警信息；
    ERROR("9","Error");//9（Error错误）表示拒绝接收到的预警信息。


    private String code;
    private String desc;

    private AlarmTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AlarmTypeEnum codeOf(String code) {
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
                break;
            case 56:
                if (code.equals("8")) {
                    var2 = 8;
                }
                break;
            case 57:
                if (code.equals("9")) {
                    var2 = 9;
                }
        }

        switch(var2) {
            case 1:
                return FIRST;
            case 2:
                return UPDATE;
            case 3:
                return CANCEL;
            case 8:
                return ACK;
            case 9:
                return ERROR;
            default:
                return null;
        }
    }

    public static AlarmTypeEnum descOf(String desc) {
        byte var2 = -1;
        switch(desc.hashCode()) {
            case 63347004:
                if (desc.equals("Alert")) {
                    var2 = 1;
                }
                break;
            case -1754979095:
                if (desc.equals("Update")) {
                    var2 = 2;
                }
                break;
            case 2011110042:
                if(desc.equals("Cancel")){
                    var2 = 3;
                }
                break;
            case 65641:
                if(desc.equals("Ack")){
                    var2 = 3;
                }
                break;
            case 67232232:
                if(desc.equals("Error")){
                    var2 = 3;
                }
        }

        switch(var2) {
            case 1:
                return FIRST;
            case 2:
                return UPDATE;
            case 3:
                return CANCEL;
            case 8:
                return ACK;
            case 9:
                return ERROR;
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
