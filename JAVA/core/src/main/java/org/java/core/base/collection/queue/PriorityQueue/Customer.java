package org.java.core.base.collection.queue.PriorityQueue;

// ���ǵ��Զ�����Customer���ṩ�κ����͵�����
// ��˵����ǳ��Խ�����Priority Queueһ��ʹ��ʱ������Ӧ��Ϊ���ṩһ���Ƚ�������
public class Customer {
	
	private int id;
	private String name;
	
	public Customer(int i, String n){
		this.id=i;
		this.name=n;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
