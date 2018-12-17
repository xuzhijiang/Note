package com.journaldev.java.dependencyinjection.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.journaldev.java.dependencyinjection.consumer.Consumer;
import com.journaldev.java.dependencyinjection.consumer.MyDIApplication;
import com.journaldev.java.dependencyinjection.injector.MessageServiceInjector;
import com.journaldev.java.dependencyinjection.service.MessageService;

// 现在让我们看看如何通过模拟注入器和服务类来轻松测试我们的应用程序类。

// 测试类中使用JUnit 4，所以如果你在测试类之上运行，请确保Junit 4在你的build path中。
public class MyDIApplicationJUnitTest {

	private MessageServiceInjector injector;
	
	// ，我使用匿名类来模拟注入器和服务类，我可以轻松地测试我的应用程序方法。
	@Before
	public void setUp(){
		//mock the injector with anonymous class
		injector = new MessageServiceInjector() {
			
			@Override
			public Consumer getConsumer() {
				//mock the message service
				return new MyDIApplication(new MessageService() {
					
					@Override
					public void sendMessage(String msg, String rec) {
						System.out.println("Msg: " + msg + ", rec: " + rec + ", Mock Message Service implementation");
					}
				});
			}
		};
	}
	
	@Test
	public void test() {
		Consumer consumer = injector.getConsumer();
		consumer.processMessages("Hi xzj", "xzj@abc.com");
	}
	
	@After
	public void tear(){
		injector = null;
	}

}
