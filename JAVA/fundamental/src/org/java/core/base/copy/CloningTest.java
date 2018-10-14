package org.java.core.base.copy;

import java.util.HashMap;
import java.util.Map;

public class CloningTest {

	public static void main(String[] args) throws CloneNotSupportedException {

		Employee emp = new Employee();

		emp.setId(1);
		emp.setName("xzj");
		Map<String, String> props = new HashMap<>();
		props.put("salary", "10000");
		props.put("city", "Bangalore");
		emp.setProps(props);

		Employee clonedEmp = (Employee) emp.clone();

		// Check whether the emp and clonedEmp attributes are same or different
		System.out.println("emp and clonedEmp == test: " + (emp == clonedEmp));
		// emp and clonedEmp == test: false
		// 因此emp和clonedEmp是两个不同的对象，而不是指同一个对象。 这符合java克隆对象的要求。
		
		System.out.println("emp and clonedEmp HashMap == test: " + (emp.getProps() == clonedEmp.getProps()));
		// emp and clonedEmp HashMap == test: true
		// 因此emp和clonedEmp对象变量都引用同一个对象。 这带来了克隆的严重问题，接下来我们将看到。
		
		// Lets see the effect of using default cloning
		
		// change emp props
		emp.getProps().put("title", "CEO");
		emp.getProps().put("city", "New York");
		System.out.println("clonedEmp props:" + clonedEmp.getProps());

		// change emp name
		emp.setName("new");
		System.out.println("clonedEmp name:" + clonedEmp.getName());
		// We changed the emp name but clonedEmp name didn’t changed. 
		// It’s because String is immutable. So when we are 
		// setting emp name, a new string is created and emp 
		// name reference is changed in this.name = name;
		
		// Hence clonedEmp name remains unchanged. You will 
		// find similar behaviour for any primitive variable types too. 
		// So we are good with java clone object default method 
		// as long as we have only primitive and immutable variables in the object.
	}

}
