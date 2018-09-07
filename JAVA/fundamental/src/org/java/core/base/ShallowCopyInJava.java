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
		
	}
}

class Course{
	
}
