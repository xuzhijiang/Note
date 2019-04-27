package com.journaldev.spring.model;

// 模型类(Model Class)

// 由于我们尝试使用jackson mapper将我们的Web服务返回的JSON转换为java对象，
// 因此我们必须为此创建模型类。 请注意，此模型类与Web服务中使用的模型类非常相似，
// 只是在这里我们不需要JPA注释(JPA annotations)。
public class Person {

	private Long id;

	private Integer age;

	private String firstName;

	private String lastName;

	public Person() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person{" + "id=" + id + ", age=" + age + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
				+ '\'' + '}';
	}
}