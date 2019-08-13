package org.java.core.base;

public class InnerClassTest {

	public static void main(String[] args) {
		OuterClass.InnerClass innerClass = new OuterClass().new InnerClass();
		OuterClass.StaticInnerClass staticInnerClass = new OuterClass.StaticInnerClass();
	}
}

class OuterClass {
	
	static class StaticInnerClass{}
	
	class InnerClass{}

}
