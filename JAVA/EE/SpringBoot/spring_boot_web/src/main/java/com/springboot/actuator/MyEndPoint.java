package com.springboot.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义自己的端点
 *
 * 上面介绍的 info、health 都是spring-boot-actuator内置的端点，
 * 真正要实现自己的端点还得通过@Endpoint、 @ReadOperation、@WriteOperation、@DeleteOperation。
 *
 * 不同请求的操作，调用时缺少必需参数，或者使用无法转换为所需类型的参数，则不会调用操作方法，响应状态将为400（错误请求）
 * <P>@ReadOperation = GET 响应状态为 200 如果没有返回值响应 404（资源未找到） </P>
 * <P>@WriteOperation = POST 响应状态为 200 如果没有返回值响应 204（无响应内容） </P>
 * <P>@DeleteOperation = DELETE 响应状态为 200 如果没有返回值响应 204（无响应内容） </P>
 */
@Endpoint(id = "xzj")// 通过http://localhost:8080/actuator/xzj访问
public class MyEndPoint {

    @ReadOperation
    public Map<String, String> hello() {
        Map<String, String> result = new HashMap<>();
        result.put("author", "xzj");
        result.put("age", "26");
        result.put("email", "xxxxxxxxxx@qq.com");
        return result;
    }

}
