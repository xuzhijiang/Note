package org.java.core.base.clone;

/**
 * 要使用java对象克隆方法，我们必须实现标记接口java.lang.Cloneable.他只是一个标记接口，
 * 接口里面没有任何方法要实现，以便它不会在运行时抛出CloneNotSupportedException。
 * 对象克隆也是受保护(protected)的方法，因此我们必须重写它以与其他类一起使用。
 *
 * 使用 clone() 方法来拷贝一个对象即复杂又有风险，它会抛出异常，并且还需要类型转换。
 * Effective Java 书上讲到，最好不要去使用 clone()，可以使用拷贝构造函数或者拷贝工厂来拷贝一个对象。
 */
public class ShallowCopyInJava {
	
	public static void main(String[] args) {
		Student s1 = new Student(001, "jack", new Course("Chemistry", "Physics", "science"));
		Student s2 = null;
		try {
			//Creating a clone of student1 and assigning it to student2
			// 要使用clone方法，我们必须implements Cloneable接口.否则会抛出CloneNotSupportedException
			s2 = (Student) s1.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		System.out.println("s1" + s1);
		System.out.println("s2" + s2);

		//Changing the subject1 of s2
		s2.course.subject1 = "xxxxxxxxx";
		s2.id = 200;
		System.out.println("修改之后");

		//This changeStr will be reflected in original student s1
		System.out.println("s1" + s1);
		System.out.println("s2" + s2);
	}

	private static class Student implements Cloneable
	{
		int id;
		String name;
		Course course;

		public Student(int id, String name, Course course) {
			this.id = id;
			this.name = name;
			this.course = course;
		}

		/**
		 * Default version of clone() method. It creates shallow copy of an object.
		 *
		 * clone() 方法并不是 Cloneable 接口的方法，而是 Object 的一个 protected 方法。
		 * Cloneable 接口只是规定，如果一个类没有实现 Cloneable 接口又调用了 clone() 方法，
		 * 就会抛出 CloneNotSupportedException。
		 */
		protected Object clone() throws CloneNotSupportedException {
			// Java Object类附带了native clone（）方法，该方法返回现有实例的副本
			// java clone对象的默认实现是使用浅拷贝
			return super.clone();
		}

		@Override
		public String toString() {
			return "Student{" +
					"id=" + id +
					", name='" + name + '\'' +
					", course=" + course +
					'}';
		}
	}

	private static class Course {
		String subject1;
		String subject2;
		String subject3;

		public Course(String sub1, String sub2, String sub3) {
			this.subject1 = sub1;
			this.subject2 = sub2;
			this.subject3 = sub3;
		}

		@Override
		public String toString() {
			return "Course{" +
					"subject1='" + subject1 + '\'' +
					", subject2='" + subject2 + '\'' +
					", subject3='" + subject3 + '\'' +
					'}';
		}
	}

}
