package org.java.core.base.concurrent.chapter5.CountdownLatch;

public enum CountryEnum {

    ONE(1, "齐"),
    TWO(2, "楚"),
    THREE(3, "韩"),
    FOUR(4, "魏"),
    FIVE(5, "赵"),
    SIX(6, "燕");

    private Integer code;
    private String msg;

    CountryEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static CountryEnum forEach(Integer code) {
        CountryEnum[] countryEnums = CountryEnum.values();
        for (CountryEnum countryEnum : countryEnums) {
            if (countryEnum.getCode() == code ) {
                return countryEnum;
            }
        }
        return null;
    }
}
