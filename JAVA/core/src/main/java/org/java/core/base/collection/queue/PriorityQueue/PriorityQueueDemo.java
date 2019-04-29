package org.java.core.base.collection.queue.PriorityQueue;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Boolean add��E e�� - �˷�����ָ����Ԫ�ز�������С�
 * �����Ѿ�ʹ�ô˷����ڶ����������5������
 * 
 * Comparator comparator���� - �˷����������ڶԴ˶����е�
 * Ԫ�ؽ��������Comparator�� ���δָ���Ƚ������򷵻�null��
 * ������Ԫ�ص���Ȼ˳��Զ��н�������
 * 
 * boolean contains(Object o) �C Returns true if the queue contains the specified element.
 * 
 * boolean offer��E e�� - ����add��������һ�����˷���Ҳ����������һ��Ԫ�ء�
 * �����������޵Ķ��У�offer������add��������ʵ�����е㲻ͬ��
 * ����PriorityQueue������£����߶�����ͬ�ġ�
 * ��add������ͬ����ʹ�޷��ڶ��������Ԫ�أ�offer����Ҳ�����׳��쳣��
 * 
 * E peek���� - �����˶��е�ͷ��������˶���Ϊ�գ��򷵻�null�����仰˵��
 * �����ؾ���������ȼ���Ԫ��,����������ɾ��Ԫ��.
 * 
 * E poll���� - �˷������������е�ͷ��������������ȼ���Ԫ�أ���
 * �����������Ϊ���򷵻�null������peek������ͬ����Ҳɾ����Ԫ�ء�
 * 
 * int size���� - ���ض����е�Ԫ������
 * boolean remove��Object o�� - �Ӷ�����ɾ��ָ����Ԫ�أ�������ڣ����������������ͬ��Ԫ�أ����ɾ������һ��Ԫ�ء�
 * Object [] toArray���� - ���ذ�������������Ԫ�ص����顣
 * T [] toArray��T [] a�� - ���ذ�������������Ԫ�ص����飬���������������ָ����������͡�
 * Iterator iterator���� - ���ض��еĵ�������
 * void clear���� - �Ӷ�����ɾ������Ԫ�ء�
 * ����֮�⣬PriorityQueue���̳���Collection��Object���еķ�����
 * 
 * Java PriorityQueueʱ�临�Ӷ�(Time Complexity)
 * ����enqueing��dequeing������ʱ�临�Ӷ�ΪO��log��n����
 * ����remove��Object����contains��Object��������ʱ�临�Ӷ������Ե�
 * ���ڼ��������������к㶨��ʱ�临�Ӷ�
 * 
 * ���ȼ����е�����ʵ�ֲ����̰߳�ȫ�ġ� ��ˣ�
 * ���������Ҫͬ�����ʣ�������Ҫʹ��PriorityBlockingQueue��
 */
public class PriorityQueueDemo {

	public static void main(String[] args){

		//This creates a PriorityQueue of tasks, which 
		//will be ordered by the natural ordering of String.
		PriorityQueue<String> tasks=new PriorityQueue<>();
		tasks.add("task1");
		tasks.add("task4");
		tasks.add("task3");
		tasks.add("task2");
		tasks.add("task5");
		System.out.println(tasks.contains("task3"));
		System.out.println("Peek on tasks:" + tasks.peek());
		System.out.println("Poll on tasks:"+tasks.poll());
		System.out.println("Peek on tasks: "+tasks.peek());
		
		while(true){
			String str = tasks.poll();
			if(str == null) break;
			System.out.println("--------- " + str);
		}
		
		// Let��s create another PriorityQueue which orders 
		//the tasks in reverse order of natural ordering.
		//So we need to pass a Comparator:
		PriorityQueue<String> reverseTasks=new PriorityQueue<String>(Comparator.reverseOrder());
		reverseTasks.add("task1");
		reverseTasks.add("task4");
		reverseTasks.add("task3");
		reverseTasks.add("task2");
		reverseTasks.add("task5");
		
		System.out.println("Peek on reverseTasks:" + reverseTasks.peek());
		System.out.println("Poll on reverseTasks: " + reverseTasks.poll());
		System.out.println("Peek on reverseTasks:" +reverseTasks.peek());

		while(true){
			String str = reverseTasks.poll();
			if(str == null) break;
			System.out.println("--------- " + str);
		}
		

		System.out.println(tasks.comparator());
		System.out.println(reverseTasks.comparator());

		System.out.println(tasks.contains("task3"));
	}
}
