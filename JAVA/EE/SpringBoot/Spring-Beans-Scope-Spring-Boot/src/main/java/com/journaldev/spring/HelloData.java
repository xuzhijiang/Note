package com.journaldev.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 最后，让我们创建一个RestController类,并为我们的测试目的配置一些API端点。
@RestController
public class HelloData {

	@Autowired
	private Customer customer;

	// access: http://localhost:8080/nameRS并检查控制台输出。
	// 您应该看到DataRequestScope Constructor Called在每个请求上打印。
	@RequestMapping("/nameRS")
	public String helloRS() {
		return customer.getDataRequestScope().getName();
	}

	// 现在转到http://localhost：8080/nameSSUpdated，
	// 以便将DataSessionScope的name更新为Session Scope Updated:

	// 再次转到http://localhost:8080/nameSS，您应该看到更新的值：
	// `Session Scope Updated`

	// 到这时，您应该在控制台输出中仅看到一次: DataSessionScope Constructor Called at XXX
	@RequestMapping("/nameSSUpdated")
	public String helloSSUpdated() {
		customer.getDataSessionScope().setName("Session Scope Updated");
		return customer.getDataSessionScope().getName();
	}

	// access: http://localhost:8080/nameSS and you would get following output:
	// `Session Scope`
	@RequestMapping("/nameSS")
	public String helloSS() {
		return customer.getDataSessionScope().getName();
	}
}

// 现在等待1分钟，以便我们的session scoped bean(会话范围的bean)失效了。
// 然后再次转到http://localhost:8080/nameSS，您应该看到原始输出
// (DataSessionScope Constructor Called at XXX）。此外，您应该检查控制台消息，
// 以便容器再次创建DataSessionScope。