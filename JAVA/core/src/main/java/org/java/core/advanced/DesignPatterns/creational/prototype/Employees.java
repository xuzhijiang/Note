package org.java.core.advanced.DesignPatterns.creational.prototype;

import java.util.ArrayList;
import java.util.List;

public class Employees implements Cloneable {

	private List<String> empList;

	public Employees() {
		empList = new ArrayList<String>();
	}

	public Employees(List<String> list) {
		this.empList = list;
	}

	// 我们有一个从数据库加载数据的Object。 现在我们需要在程序中多次修改这些数据，
	// 因此使用new关键字创建Object并从数据库再次加载所有数据不是一个好主意。

	// 更好的方法是将现有对象克隆到新对象中，然后进行数据操作。
	public void loadData() {
		// read all employees from database and put into the list
		empList.add("xzj");
		empList.add("xiaoming");
		empList.add("liyan");
		empList.add("kingLBJ");
	}

	public List<String> getEmpList() {
		return this.empList;
	}

	/**
	 * 原型设计模式要求您复制的对象应提供复制功能。 它不应该由任何其他类完成。 
	 * 但是，是否使用Object属性的浅或深副本取决于要求及其设计决策。
	 * 
	 * 以下使用了deep copy
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		List<String> temp = new ArrayList<String>();
		for (String s : getEmpList()) {
			temp.add(s);
		}
		return new Employees(temp);
	}
	
	// 如果未提供对象克隆，我们将不得不进行数据库调用以每次获取员工列表。 
	// 然后进行本来耗费resource和time的操作。
}
