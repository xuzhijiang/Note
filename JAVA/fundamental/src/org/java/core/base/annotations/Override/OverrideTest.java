package org.java.core.base.annotations.Override;

/**
 * Java注解在1.5被引入，Java@Override注解是Java默认注解之一，
 * 当我们为一个方法使用这个注解的时候，他告诉编译器我们在尝试覆盖父类的方法.
 * Java @Override annotation benefits
 * It’s clear that using java @Override annotation will make
 * sure any superclass changes in method signature will result in
 * a warning and you will have to do necessary changes to make sure 
 * the classes work as expected.父类上方法的 改变可能会引起子类的警告.
 * <p><br>
 * It’s better to resolve potential issues at compile time than 
 * runtime. So always use java @Override annotation whenever you 
 * are trying to override a superclass 
 * method.(在编译时解析潜在的问题比运行时更好，因此在重写父类方法的时候应该使用@Override)
 */
public class OverrideTest {
	public static void main(String[] args) {
		//Here bc is of type BaseClass,但是在运行时他是
		//ChildClass的对象，因此当我们调用doSomething(String str)方法
		//的时候，它寻找在ChildClass中的方法
		BaseClass bc = new ChildClass();
		bc.doSomething("override");
	}
}
