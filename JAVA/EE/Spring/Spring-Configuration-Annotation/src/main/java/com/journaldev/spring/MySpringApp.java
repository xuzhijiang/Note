package com.journaldev.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MySpringApp {

	public static void main(String[] args) {
		// 请注意，在我们请求bean之前，Spring会将bean加载到它的上下文中。
		// 这是为了确保所有bean都已正确配置，如果出现问题，应用程序将快速失败(fail-fast)。
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		//ctx.register(MyConfiguration.class);
		ctx.register(MyConfiguration3.class);
		// 还必须调用ctx.refresh（），否则当我们尝试从上下文中获
		// 取任何bean时，我们将得到以下错误:
		// Exception in thread "main" java.lang.IllegalStateException:
		// org.springframework.context.annotation.AnnotationConfigApplicationContext@f0f2775
		// has not been refreshed yet
		ctx.refresh();

		// 如果把我获取MyBean实例的语句取消注释，您会注意到它没有调用MyBean的构造函数。 
		// 这是因为spring bean的默认范围是Singleton。 我们可以使用@Scope注释来更改它的scope(范围)
		 MyBean mb1 = ctx.getBean(MyBean.class);
		 System.out.println("mb1: " + mb1.hashCode());
		 MyBean mb2 = ctx.getBean(MyBean.class);
		 System.out.println("mb2: " + mb2.hashCode());

		ctx.close();
	}

}
