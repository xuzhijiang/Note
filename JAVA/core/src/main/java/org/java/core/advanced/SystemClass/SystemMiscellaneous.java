package org.java.core.advanced.SystemClass;

public class SystemMiscellaneous {
	
//	System class provides some other methods for miscellaneous tasks. 
	//For example, to run Garbage Collector, load external libraries, map the 
	//library name to OS specific String, run the finalize method for any
	//object waiting for finalization and to terminate the JVM.
	
//	System类为其他任务提供了一些其他方法。 例如，要运行垃圾收集器，加载外部库，
//	将库名称映射到特定于操作系统的字符串，对等待完成的任何对象运行finalize方法并终止JVM。
	public static void main(String[] args) {
		//run the garbage collector
		System.gc();
		System.out.println("Garbage collector executed.");

		//map library name
		String libName = System.mapLibraryName("os.name");
		System.out.println("os.name library="+libName);

		//load external libraries
		System.load("lixXYZ.so");
		System.loadLibrary("libos.name.dylib");

		//run finalization
		System.runFinalization();

		//terminates the currently running JVM
		System.exit(1);
		// this line will never print because JVM is terminated
		System.out.println("JVM is terminated"); 
	}
}
