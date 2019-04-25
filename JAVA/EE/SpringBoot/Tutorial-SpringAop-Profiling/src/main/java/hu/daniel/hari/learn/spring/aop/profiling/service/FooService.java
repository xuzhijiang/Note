package hu.daniel.hari.learn.spring.aop.profiling.service;

import org.springframework.stereotype.Component;

// Spring Service类(待分析to be profiled）

// 我们首先编写我们的服务类，它具有模拟短进程和长进程的方法，还有一个抛出异常的方法。

// 请注意，我们没有在服务类中放置任何分析代码(profiling code)。

/**
 * Example service class that we want to profile through AOP.
 * (Notice that no need to insert any line of profiling code!)
 * 
 * @author Daniel Hari
 */
@Component
public class FooService {

	public void doShortJob() {
		sleep(10l);
	}

	public void doLongJob() {
		sleep(300l);
	}

	public void doSomethingThatThrowsException() {
		throw new RuntimeException("Simulated Exception.");
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
