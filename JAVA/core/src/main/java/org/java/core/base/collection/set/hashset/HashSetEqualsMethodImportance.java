package org.java.core.base.collection.set.hashset;

import java.util.HashSet;
import java.util.Set;

/**
 * HashSet����HashMap�洢����Ԫ�ء� �����������Ԫ��ʱ��
 * HashSetʹ��equals������hashCode������������ظ�Ԫ�ء� 
 * �����ǿ������Set�д洢�Ķ���û���ṩequals��������ʵ�ֻᷢ��ʲô��
 * 
 * ���Կ����������ܹ���Set�д洢�ظ���Ԫ�ء�ʵ���ϲ�����ˣ���ΪEmp��û�ж�
 * ��equals��������������ʹ����Object��equals��������ʵ�֡� 
 * Object�ඨ����equals��������:
 * public boolean equals(Object obj) { return (this == obj);}
 * ��ˣ��������Ԫ��ʱ�����ڼ��������ö��������ݡ���ˣ������о����ظ����ݵĶ��󣬵������Ǿ��в�ͬ�����á�
 */
public class HashSetEqualsMethodImportance {

	public static void main(String[] args) {

		Set<Emp> emps = new HashSet<>();
		emps.add(new Emp(1,"Pankaj"));
		emps.add(new Emp(2, "David"));
		emps.add(new Emp(1, "Pankaj"));
		
		System.out.println(emps);
	}

}

class Emp {
	private String name;
	private int id;

	public Emp(int i, String n) {
		this.id = i;
		this.name = n;
	}
	
	@Override
	public String toString(){
		return "{"+id+","+name+"}";
	}
}
