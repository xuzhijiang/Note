package org.java.core.base.nested;

import java.util.Arrays;
import org.java.core.base.nested.OuterClass.StaticNestedClass;

public class InnerClassTest {
	
	public static void main(String[] args) {
		OuterClass outer = new OuterClass(1, 2, 3, 4);
		
		//static nested classes JdbcQuickStartExample
		StaticNestedClass staticNestedClass = new StaticNestedClass();
		StaticNestedClass staticNestedClass1 = new StaticNestedClass();
		OuterClass.StaticNestedClass staticNestedClass2 = new OuterClass.StaticNestedClass();//另一种创建方式
		
		System.out.println(staticNestedClass.getName());
		staticNestedClass.d = 10;
		staticNestedClass.c = 20;
		staticNestedClass.b = 30;
		//staticNestedClass.a = 40;
		
		System.out.println(staticNestedClass.d);
		System.out.println(staticNestedClass1.d);
		
		//inner class JdbcQuickStartExample
		
		//wrong demo of creating instance of InnerClass
		//InnerClass innerClass = new InnerClass();
		
		//Enclosing instance of type OuterClass is not accessible.(类型OuterClass的封闭实例是不可以访问的)
		//Must qualify the allocation with an enclosing instance (必须使用OuterClass类型的封闭实例限定分配)
		//of type OuterClass (e.g. x.new A() where x is an instance of OuterClass).
		//也就是说x.new A(),创建出来的这个new A()只属于x实例
		
		OuterClass.InnerClass innerClass = outer.new InnerClass();
		System.out.println(innerClass.getName());
        System.out.println(innerClass);
        innerClass.setValues();
        System.out.println(innerClass);
		
        //calling method using local inner class
        outer.print("Outer");
        
        //calling method using anonymous inner class
        System.out.println(Arrays.toString(outer.getFilesInDir("src/org/java/core/base/nested", ".java")));
        
        System.out.println(Arrays.toString(outer.getFilesInDir("bin/org/java/core/base/nested", ".class")));
	}
}
