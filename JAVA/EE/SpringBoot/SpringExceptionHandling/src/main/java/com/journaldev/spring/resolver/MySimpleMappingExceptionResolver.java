package com.journaldev.spring.resolver;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

//HandlerExceptionResolver


// 我们只是扩展SimpleMappingExceptionResolver并覆盖其中一个方法，
// 但我们可以覆盖它最重要的方法resolveException来用于记录logging和发送不同类型的视图页面。
// 但这与使用ControllerAdvice实现相同，所以我要离开它。

// 我们将使用它来 “为所有其他的异常” 配置 “视图页面”，
// 这些“所有其他的异常”是指 我们未通过“响应通用错误页面”处理的页面
public class MySimpleMappingExceptionResolver extends
		SimpleMappingExceptionResolver {

	@Override
	public String buildLogMessage(Exception ex, HttpServletRequest request) {
        return "Spring MVC exception: " + ex.getLocalizedMessage();
    }
}
