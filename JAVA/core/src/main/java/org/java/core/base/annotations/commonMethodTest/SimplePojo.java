package org.java.core.base.annotations.commonMethodTest;

import java.lang.annotation.Documented;

public class SimplePojo {

	@Deprecated
	private String name;

	@Deprecated
	public int age;

	public boolean gender;

	@Override
	@MethodInfoAnnotation(author = "xzj", comments = "StaticProxyTest method", date = "Nov 17 2012", revision = 1)
	public String toString() {
		return "Overriden toString method";
	}

	@MethodInfoAnnotation(author = "xzj", comments = "StaticProxyTest method", date = "Nov 17 2012", revision = 10)
	public void setAge(int age) {
		this.age = age;
	}

	@Deprecated
	@MethodInfoAnnotation(comments = "deprecated method", date = "Nov 17 2012")
	public static void oldMethod() {
		System.out.println("old method, don't use it.");
	}

	public void setName(String name) {
		this.name = name;
	}

	private void privateMethod() {}

	void friendlyMethod() {}

	protected void protectedMethod() {}
}