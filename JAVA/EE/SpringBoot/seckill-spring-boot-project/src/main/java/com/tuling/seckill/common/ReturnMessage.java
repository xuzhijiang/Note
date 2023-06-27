package com.tuling.seckill.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnMessage {

    private String code;
    private String data;
    private String msg;
    private Boolean success;

    public static ReturnMessage error(String msg) {
        return new ReturnMessage("-1", null, msg, false);
    }

    public static ReturnMessage success() {
        return new ReturnMessage("0", null, null, true);
    }
}
