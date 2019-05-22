package org.java.core.advanced.SystemClass;

public class SystemMiscellaneous {
	
//	System class provides some other methods for miscellaneous tasks. 
	//For JdbcQuickStartExample, to run Garbage Collector, load external libraries, map the
	//library name to OS specific String, run the finalize method for any
	//object waiting for finalization and to terminate the JVM.
	
//	System��Ϊ���������ṩ��һЩ���������� ���磬Ҫ���������ռ����������ⲿ�⣬
//	��������ӳ�䵽�ض��ڲ���ϵͳ���ַ������Եȴ���ɵ��κζ�������finalize��������ֹJVM��
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
