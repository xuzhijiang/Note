package org.java.core.base.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * We will use Reflection to parse java annotations from a class. 
 * Please note that Annotation Retention Policy should be RUNTIME
 * otherwise it’s information will not be available at runtime and 
 * we wont be able to fetch any data from it.
 * 我们将使用Reflection来解析类中的java注释。 请注意，注释保留策略应该是RUNTIME，
 * 否则它的信息将无法在运行时获得，我们将无法从中获取任何数据。
 */
public class AnnotationParsing {

	public static void main(String[] args) {
		try {
			for (Method method : AnnotationParsing.class.getClassLoader()
					.loadClass(("org.java.core.base.annotations.AnnotationExample")).getMethods()) {
				// checks if MethodInfo annotation is present for the method
				if (method.isAnnotationPresent(org.java.core.base.annotations.MethodInfo.class)) {
					try {
						// iterates all the annotations available in the method
						for (Annotation anno : method.getDeclaredAnnotations()) {
							System.out.println("Annotation in Method '" + method + "' : " + anno);
						}
						MethodInfo methodAnno = method.getAnnotation(MethodInfo.class);
						if (methodAnno.revision() == 1) {
							System.out.println("Method with revision no 1 = " + method);
						}

					} catch (Throwable ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
