package org.java.core.base.annotations;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class AnnotationExample {

	@Test
	public void printClassMethodInfo() throws ClassNotFoundException {
		Class clzStr = Class.forName("java.lang.String");
		// 返回对象方法数组
		Method[] methodArray = clzStr.getDeclaredMethods();
		for (Method m : methodArray) {
			// 获取方法的 "修饰符" 字符串
			String mod = Modifier.toString(m.getModifiers());
			System.out.print(mod + " " + m.getName() + "(");
			Class<?>[] parameterTypesArray = m.getParameterTypes();
			if (parameterTypesArray.length == 0) {
				System.out.print(')');
				System.out.println();
				continue;
			}
			for (int i = 0; i < parameterTypesArray.length;i++) {
				char end = ',';
				if (i == (parameterTypesArray.length -1)) {
					end = ')';
				}
				System.out.print(parameterTypesArray[i].getSimpleName() + end);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {

	}

	@Override
	@MethodInfo(author = "Pankaj", comments = "Main method", date = "Nov 17 2012", revision = 1)
	public String toString() {
		return "Overriden toString method";
	}

	@Deprecated
	@MethodInfo(comments = "deprecated method", date = "Nov 17 2012")
	public static void oldMethod() {
		System.out.println("old method, don't use it.");
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@MethodInfo(author = "Pankaj", comments = "Main method", date = "Nov 17 2012", revision = 10)
	public static void genericsTest() throws FileNotFoundException {
		List l = new ArrayList();
		l.add("abc");
		oldMethod();
	}

}