package org.java.core.base.annotations.commonMethodTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.tree.AnnotationNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import org.junit.Test;

public class AnnotationTest {

	@Test
	public void testGetMethod() {
		ClassLoader classLoader = AnnotationTest.class.getClassLoader();
		try {
			Class<?> clazz = classLoader.loadClass("org.java.core.base.annotations.commonMethodTest.SimplePojo");
			// 获取自己以及父类的所有的public的方法,不包括friendly,protected,private的方法
			Method[] methods = clazz.getMethods();
			for (Method m : methods) {
				System.out.println("Method name: " + m.getName());
			}

			// 获取自己声明的所有方法,包括public,private,protected,friendly的方法,不包括父类的任何方法.(不包括继承的)
			Method[] declaredMethods = clazz.getDeclaredMethods();
			for (Method m : declaredMethods) {
				System.out.println("declaredMethods name: " + m.getName());
			}

			// 通过clazz.getMethod获取的只能是自己以及父类的所有的public的方法,不包括friendly,protected,private的方法
			Method publicMethod = clazz.getMethod("setName", String.class);
			System.out.println("------publicMethod method : " + publicMethod.getName());
			publicMethod = clazz.getMethod("setAge", int.class);//注意参数不能写成Integer.class,会提示找不到方法.
			System.out.println("------publicMethod method : " + publicMethod.getName());

			// 通过getDeclaredMethod获取类自己声明的任何方法
			Method friendlyMethod = clazz.getDeclaredMethod("friendlyMethod", null);
			System.out.println("-firendly method : " + friendlyMethod.getName());

			friendlyMethod = clazz.getMethod("friendlyMethod", null);
			System.out.println("-firendly method : " + friendlyMethod.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetField() {
		Class<SimplePojo> clazz = SimplePojo.class;

		//  获取public成员变量,也可以获取父类的public的field
		try {
			Field[] fields = clazz.getFields();
			System.out.println("all public field: " + Arrays.toString(fields));

			Field field = clazz.getField("age");
			System.out.println("public age field: " + field.getModifiers() + " " + field.getName());

			field = clazz.getField("name");// 抛异常
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		// 获取自己定义的,任何访问修饰符的成员变量.无法获取父类的成员变量.
		try {
			Field[] declaredFields = clazz.getDeclaredFields();
			System.out.println("declaredFields: " + Arrays.toString(declaredFields));

			Field field = clazz.getDeclaredField("name");
			System.out.println("private name field: " + field.getName());
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAnnotation() {
		Class<SimplePojo> clazz = SimplePojo.class;

		// 获取类上的注解
		Annotation[] annotationOnClass = clazz.getAnnotations();
		System.out.println("类上的所有注解: " + Arrays.toString(annotationOnClass));

		// 获取field上的注解
		try {
			Field field = clazz.getField("age");
			Annotation[] annotations = field.getAnnotations();
			System.out.println("public age field 上的注解: " + Arrays.toString(annotations));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		// 获取public方法上的注解
		try {
			Method method = clazz.getMethod("setAge", int.class);
			Annotation[] annotationsOnMethod = method.getAnnotations();
			System.out.println("setAge上的所有注解: " + Arrays.toString(annotationsOnMethod));

			if (method.isAnnotationPresent(MethodInfoAnnotation.class)) {
				MethodInfoAnnotation methodInfoAnnotation = method.getAnnotation(MethodInfoAnnotation.class);
				System.out.println("methodInfoAnnotation: " + methodInfoAnnotation.author() + ", " + methodInfoAnnotation.comments());
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void printStringClassMethodInfo() throws ClassNotFoundException {
		Class<?> clazz = Class.forName("java.lang.String");
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method m : declaredMethods) {
			String mod = Modifier.toString(m.getModifiers());
			System.out.print(mod + " " + m.getReturnType() + " " + m.getName() + "(");
			Class<?>[] parameterTypesArray = m.getParameterTypes();
			if (parameterTypesArray.length == 0) {
				System.out.println("){}");
				continue;
			}
			for (int i = 0; i < parameterTypesArray.length;i++) {
				String end = ",";
				if (i == (parameterTypesArray.length -1)) {
					end = "){}";
				}
				System.out.print(parameterTypesArray[i].getSimpleName() + end);
			}
			System.out.println();
		}
	}

	@Test
	public void testASM() throws IOException {
		ClassNode classNode = new ClassNode();

		ClassReader cr = new ClassReader(new FileInputStream("classes\\org\\java\\core\\base\\annotations\\AnnotationObject.class"));

		cr.accept(classNode, 0);

		System.out.println("Class Name: " + classNode.name);
		System.out.println("Source File: " + classNode.sourceFile);

		System.out.println("invisible: ");
		AnnotationNode anNode = null;
		for (Object annotation : classNode.invisibleAnnotations) {
			anNode = (AnnotationNode) annotation;
			System.out.println("Annotation Descriptor : " + anNode.desc);
			System.out.println("Annotation attribute pairs : " + anNode.values);
		}

		System.out.println("visible: ");
		for (Object annotation : classNode.visibleAnnotations) {
			anNode = (AnnotationNode) annotation;
			System.out.println("Annotation Descriptor : " + anNode.desc);
			System.out.println("Annotation attribute pairs : " + anNode.values);
		}
	}

}
