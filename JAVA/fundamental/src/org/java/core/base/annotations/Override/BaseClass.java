package org.java.core.base.annotations.Override;

public class BaseClass {
	
//	public void doSomething(String str) {
//		System.out.println("Base impl:" + str);
//	}
	
	//Change argument from String to Object
	public void doSomething(Object str) {
		System.out.println("Base impl:" + str);
	}
	//The reason is that BaseClass doSomething(Object str) 
	//method is not anymore overridden by ChildClass. Hence 
	//it¡¯s invoking BaseClass implementation. ChildClass is 
	//overloading the doSomething() method in this case.
}
