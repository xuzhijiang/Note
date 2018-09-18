package org.java.core.base.nested;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Java inner class is defined inside the body of another class.
 * Java inner class can be declared private, public, protected, 
 * or with default access whereas an outer class can have only 
 * public or default access.
 * <p><br>
 * Java Nested classes are divided into two types:(Java嵌套类被分为2种类型)
 * <p><br>
 * 1. static nested class(静态内嵌类)<p><br>
 * If the nested class is static, then it’s called static nested class(如果内嵌的类是静态的，则他被叫做静态内嵌类)
 * Static nested classes can access only static members of the outer class.
 * Static nested class is same as any other top-level class and 
 * is nested for only packaging convenience.(静态嵌套类只能访问外部类的静态成员。 静态嵌套类与任何其他顶级类相同，
 *  并且仅为了包装方便而嵌套。)
 *  <p><br>
 * 2. java inner class<p>
 * Any non-static nested class is known as inner class in java. (非静态内嵌类在java中成为内部类)
 * Java inner class is associated with the object of the class 
 * and they can access all the variables and methods of the outer class.
 * Since inner classes are associated with instance, (因为内部类和外部类的实例关联，所以我们不能在其中包含任何静态变量)
 * we can’t have any static variables in them.
 * <p>
 * Object of java inner class are part of the outer class object 
 * and to create an instance of inner class, we first need to create instance of outer class.
 * <p><br>
 * There are two special kinds of java inner classes.(这里有2中特殊的Java内部类)<p><br>
 * a. Local inner class
 * <p>
 * If a class is defined in a method body, it’s known as local inner class.
 * <p>
 * Since local inner class is not associated with Object, we can’t use private, 
 * public or protected access modifiers with it. The only allowed modifiers are abstract or final.
 * <p>
 * A local inner class can access all the members of the enclosing
 *  class and local final variables in the scope it’s defined.(本地内部类可以访问封闭类的所有成员以及它定义的作用域中的本地final变量。)
 * <p>
 * b. anonymous inner class
 * A local inner class without name is known as 
 * anonymous inner class(没有名字的inner class就是匿名). An anonymous class is defined 
 * and instantiated in a single statement.
 * Anonymous inner class always extend a class or implement 
 * an interface. Since an anonymous class has no name, it is 
 * not possible to define a constructor for an anonymous class.
 * (匿名内部类没有名字，所以不可以为它定义构造函数)
 * <p>
 * Notice that when OuterClass is compiled, separate class files are
 *  created for inner class, local inner class and static nested class.
 *  <p>
 * Benefits of Java Inner Class:<p>
 * 1. If a class is useful to only one class, 
 * it makes sense to keep it nested and together. It helps in packaging of the classes.
 * <br>2. Java inner classes implements encapsulation. 
 * Note that inner classes can access outer class private members 
 * and at the same time we can hide inner class from outer world.
 * <br>3. Keeping the small class within top-level classes (将小类放到顶级类中)
 * places the code closer to where it is used (把代码放到更接近于他被使用的地方)
 * and makes code more readable and maintainable.
 */
public class OuterClass {
	
	private static String name = "OuterClass";
	private int i;
	protected int j;
	int k;
	public int l;
	
	//OuterClass constructor
	public OuterClass(int i, int j, int k, int l) {
		this.i = i;
		this.j = j;
		this.k = k;
		this.l = l;
	}
	
	public int getI() {
		return this.i;
	}
	
	//static nested class, can access OuterClass static variables/methods
	static class StaticNestedClass{
		private int a;
		protected int b;
		int c;
		public int d;
		
		public int getA() {
			return this.a;
		}
		
		public String getName() {
			return name;
		}
	}
	
	//inner class, non static and can access all the variables/methods of outer class.
	class InnerClass{
		private int w;
        protected int x;
        int y;
        public int z;

        public int getW() {
            return this.w;
        }

        public void setValues() {
            this.w = i;
            this.x = j;
            this.y = k;
            this.z = l;
        }
        
        @Override
        public String toString() {
            return "w=" + w + ":x=" + x + ":y=" + y + ":z=" + z;
        }

        public String getName() {
            return name;
        }
	}
	
	//local inner class
	public void print(String initial) {
		//local inner class inside the method
		class Logger {
			String name;
			
			public Logger(String name) {
				this.name = name;
			}
			
			public void log(String str) {
				System.out.println(this.name + ": " + str);
			}
		}
		 //instantiate local inner class in the method to use
		Logger logger = new Logger(initial);
		logger.log(name);
		logger.log("" + this.i);
		logger.log("" + this.j);
        logger.log("" + this.k);
        logger.log("" + this.l);
	}
	
	//anonymous inner class
	public String[] getFilesInDir(String dir, final String ext) {
		File file = new File(dir);
		
		//anonymous inner class implementing FilenameFilter interface
		String[] filesList = file.list(new FilenameFilter() {
			//Anonymous inner classes are accessible only at the point where it is defined.
			//It’s a bit hard to define how to create anonymous inner class, 
			//we will see it’s real time usage in test program below.
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(ext);
			}
			
		});
		
		return filesList;
	}
}
