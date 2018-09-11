package org.java.core.base.copy;

/**
 * A class implements the {@link java.lang.Cloneable} interface to
 * indicate to the {@link java.lang.Object#clone()} method that it
 * is legal for that method to make a
 * field-for-field copy of instances of that class.
 * <p>
 * Invoking Object's clone method on an instance that does not implement the
 * {@link java.lang.Cloneable} interface results in the exception
 * <code>CloneNotSupportedException</code> being thrown.
 * <p>
 * The default version of clone() method creates the shallow copy of an object. 
 * To create the deep copy of an object, you have to override the clone() method.
 * <p>
 * The shallow copy of an object will have exact copy of all the fields of 
 * original object. If original object has any references to other objects as 
 * fields, then only references of those objects are copied into clone object, 
 * copy of those objects are not created. That means any changes made to those 
 * objects through clone object will be reflected in original object or vice-versa. 
 * Shallow copy is not 100% disjoint from original object. Shallow copy is not 100% 
 * independent of original object.
 */
public class ShallowCopyInJava {
	
	public static void main(String[] args) {
		Course course = new Course("Chemistry", "Physics", "science");
		Student s1 = new Student(001, "jack", course);
		Student s2 = null;
		try {
			//Creating a clone of student1 and assigning it to student2
			s2 = (Student) s1.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		System.out.println("s1 id: " + s1.id);
		System.out.println("s1 name: " + s1.name);
		
		//Printing the subject1 of s1
		System.out.println("s1 course: " + s1.course.subject1);
		
		System.out.println("s2 id: " + s1.id);
		System.out.println("s2 name: " + s1.name);
		System.out.println("s2 course: " + s1.course.subject1);
		
		//Changing the subject1 of s2
		s2.course.subject1 = "Biology";
		
		//This change will be reflected in original student s1
		System.out.println("s1 course: " + s1.course.subject1);
		
		System.out.println("s2 course: " + s1.course.subject1);
	}
}

class Student implements Cloneable
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
	 */
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

class Course
{
	String subject1;
	String subject2;
	String subject3;
	
	public Course(String sub1, String sub2, String sub3) {
		this.subject1 = sub1;
		this.subject2 = sub2;
		this.subject3 = sub3;
	}
}
