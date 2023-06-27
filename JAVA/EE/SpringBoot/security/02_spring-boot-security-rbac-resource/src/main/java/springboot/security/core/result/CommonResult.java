package springboot.security.core.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用Restful返回工具
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    public int code; // 返回状态码200成功

    private String msg; // 返回描述信息

    private T data; // 返回内容体
    
    private final static String SUCCESS = "success";

    private final static String FAIL = "fail";

    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(HttpCodeEnum.SUCCESS.code, SUCCESS, null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(HttpCodeEnum.SUCCESS.code, SUCCESS, data);
    }

    public static <T> CommonResult<T> success(String msg) {
        return new CommonResult<T>(HttpCodeEnum.SUCCESS.code, msg, null);
    }

    public static <T> CommonResult<T> success(HttpCodeEnum codeEnum, T data) {
        return new CommonResult<T>(codeEnum.code, SUCCESS, data);
    }

    public static <T> CommonResult<T> fail() {
        return new CommonResult<T>(HttpCodeEnum.FAIL.code, FAIL, null);
    }

    public static <T> CommonResult<T> fail(HttpCodeEnum codeEnum) {
        return new CommonResult<T>(codeEnum.code, codeEnum.msg, null);
    }
}
