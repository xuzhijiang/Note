package org.java.core.advanced.jvm.classloader;

// 类加载器相当于是快递员
public class ClassLoaderInfo {
	
	public static void main(String[] args) {

		Object object = new Object();
		// java.lang.Object是通过"启动类加载器（Bootstrap）C++"加载的,因为是使用C++语言编写的,所以打印出null(无法打印出java的东西,所以就打印出null)
		// "启动类加载器（Bootstrap）C++"用于加载$JAVA_HOME/jre/lib/rt.jar (runtime的jar)
		// java.lang.Object就是存放在$JAVA_HOME/jre/lib/rt.jar中
		// "启动类加载器(Bootstrap)"一起动的就会帮我们自动加载JAVA_HOME/jre/lib/rt.jar中的类,所以我们可以直接使用
		System.out.println(object.getClass().getClassLoader()); // 打印 null
		// 不要再抛人家祖坟了,启动类加载器（Bootstrap）就是最顶级了,所以下面2行会抛异常
		// System.out.println(object.getClass().getClassLoader().getParent());
		// System.out.println(object.getClass().getClassLoader().getParent().getParent());

		System.out.println("-----------------");

		ClassLoaderInfo classLoaderInfo = new ClassLoaderInfo();
		System.out.println(classLoaderInfo.getClass().getClassLoader().getParent().getParent());
		System.out.println(classLoaderInfo.getClass().getClassLoader().getParent());
		System.out.println(classLoaderInfo.getClass().getClassLoader());

		System.out.println("ClassLoaderInfo的ClassLoader: " + ClassLoaderInfo.class.getClassLoader());
	}
	
}
