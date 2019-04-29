package org.java.core.base.collection.set.hashset;

import java.util.HashSet;
import java.util.Set;

/**
 * �����ǿ�����������Emp���ж���hashCode������equals��������ʱ�ᷢ��ʲô��
 * 
 * ��ע�⣬�����ǳ������Ԫ��ʱ��HashSet�ܹ�����ظ��
 * �������ǿ���ʹ��setter�������Ķ���ֵ��ʹ���ظ���
 * It worked because there is no operation done on Set. 
 * This is why Immutable objects works better with Set and Map.
 * ����Ч����Ϊ��Set��û������κβ����������Ϊʲô���ɱ������ʹ��Set��Map�Ǹ���.
 */
public class HashSetEqualsMethodImportance2 {

	public static void main(String[] args) {

		Set<Emp2> emps = new HashSet<>();
		emps.add(new Emp2(1,"Pankaj"));
		emps.add(new Emp2(2, "David"));
		emps.add(new Emp2(1, "Pankaj"));
		
		System.out.println(emps);
		
		Emp2 e = new Emp2(3, "Lisa");
		emps.add(e);
		System.out.println(emps);
		
		//set values to make it duplicate
		e.setId(1);
		System.out.println(emps);
		e.setName("Pankaj");
		System.out.println(emps);
	}

}

class Emp2 {
	private String name;
	private int id;

	public Emp2(int i, String n) {
		this.setId(i);
		this.setName(n);
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null || !(obj instanceof Emp2)) return false;
		Emp2 e = (Emp2) obj;
		if(e.getId() == this.getId() && this.getName().equals(e.getName())) return true;
		return false;
	}
	
	@Override
	public int hashCode(){
		return getId();
	}
	
	@Override
	public String toString(){
		return "{"+getId()+","+getName()+"}";
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

}
