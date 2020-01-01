package com.tuling.seckill.common;

// 注意一定要添加删Getter和Setter,否则会报
// No converter found for return value of type: class com.tuling.seckill.common.ReturnMessage
public class ReturnMessage {

    private String code;
    private String data;
    private String msg;
    private Boolean success;

    public ReturnMessage(String code, String data, String msg, Boolean success) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = success;
    }

    public static ReturnMessage error(String msg) {
        return new ReturnMessage("-1", null, msg, false);
    }

    public static ReturnMessage success() {
        return new ReturnMessage("0", null, null, true);
    }

    @Override
    public String toString() {
        return "ReturnMessage{" +
                "code='" + code + '\'' +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
