package org.java.core.base.copy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * If our Employee class won’t implement Cloneable interface, below program will
 * throw java.lang.CloneNotSupportedException runtime exception.
 */
public class Employee implements Cloneable {

	private int id;

	private String name;

	private Map<String, String> props;
	
	public Employee(){
		
	}
	
	// 我们还可以定义复制构造函数并获取对象的副本，而不依赖于克隆。
	// Whenever we need a copy of employee object, we can get 
	// it using Employee clonedEmp = new Employee(emp);
	
	// 但是，如果您的类有很多变量，那么编写复制构造函数可能是一项繁琐的工作，
	// 大多数是原始的和不可变的。
	
	public Employee(Employee emp) {
		
		this.setId(emp.getId());
		this.setName(emp.getName());
		
		Map<String, String> hm = new HashMap<>();
		String key;
		Iterator<String> it = emp.getProps().keySet().iterator();
		// Deep Copy of field by field
		while (it.hasNext()) {
			key = it.next();
			hm.put(key, emp.getProps().get(key));
		}
		this.setProps(hm);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getProps() {
		return props;
	}

	public void setProps(Map<String, String> p) {
		this.props = p;
	}

	// Shallow Copy:
	/*@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}*/

	// Deep Copy:
	// 这就是我们想要的，克隆应该返回一个完全脱离原始对象的新对象。
	@Override
	public Object clone() throws CloneNotSupportedException {

		Object obj = super.clone(); // utilize clone Object method

		Employee emp = (Employee) obj;

		// deep cloning for immutable fields
		emp.setProps(null);
		Map<String, String> hm = new HashMap<>();
		String key = null;
		Iterator<String> it = this.props.keySet().iterator();
		// Deep Copy of field by field
		while (it.hasNext()) {
			key = it.next();
			hm.put(key, this.props.get(key));
		}
		emp.setProps(hm);

		return emp;
	}
}
