package org.java.core.advanced.SystemClass;

public class SystemSecurityManager {
	
	//SecurityManager class is used to implement security policy for 
	//applications, System class provide useful methods to get SecurityManager 
	//for the currently running JVM and to set the SecurityManager for the application.
	
	//SecurityManager类用于实现应用程序的安全策略，System类提供有用的方法来获取当
	//前运行的JVM的SecurityManager并为应用程序设置SecurityManager。
	
	public static void main(String[] args) {
		SecurityManager secManager = System.getSecurityManager();
		if(secManager == null){
			System.out.println("SecurityManager is not configured");
		}
		SecurityManager mySecManager = new SecurityManager();
		System.setSecurityManager(mySecManager);
		secManager = System.getSecurityManager();
		if(secManager != null){
			System.out.println("SecurityManager is configured");
		}
	}
}
