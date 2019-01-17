package org.java.core.base.collection.PriorityQueue;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Boolean add（E e） - 此方法将指定的元素插入队列中。
 * 我们已经使用此方法在队列中添加了5个任务。
 * 
 * Comparator comparator（） - 此方法返回用于对此队列中的
 * 元素进行排序的Comparator。 如果未指定比较器，则返回null，
 * 并根据元素的自然顺序对队列进行排序。
 * 
 * boolean contains(Object o) – Returns true if the queue contains the specified element.
 * 
 * boolean offer（E e） - 就像add（）方法一样，此方法也会向队列添加一个元素。
 * 对于容量受限的队列，offer（）和add（）方法实际上有点不同，
 * 但在PriorityQueue的情况下，两者都是相同的。
 * 与add（）不同，即使无法在队列中添加元素，offer（）也不会抛出异常。
 * 
 * E peek（） - 检索此队列的头部，如果此队列为空，则返回null。换句话说，
 * 它返回具有最高优先级的元素,但是它不会删除元素.
 * 
 * E poll（） - 此方法还检索队列的头部（具有最高优先级的元素），
 * 或者如果队列为空则返回null。但与peek（）不同，它也删除了元素。
 * 
 * int size（） - 返回队列中的元素数。
 * boolean remove（Object o） - 从队列中删除指定的元素（如果存在）。如果存在两个相同的元素，则仅删除其中一个元素。
 * Object [] toArray（） - 返回包含队列中所有元素的数组。
 * T [] toArray（T [] a） - 返回包含队列中所有元素的数组，返回数组的类型是指定数组的类型。
 * Iterator iterator（） - 返回队列的迭代器。
 * void clear（） - 从队列中删除所有元素。
 * 除此之外，PriorityQueue还继承了Collection和Object类中的方法。
 * 
 * Java PriorityQueue时间复杂度(Time Complexity)
 * 对于enqueing和dequeing方法，时间复杂度为O（log（n））
 * 对于remove（Object）和contains（Object）方法，时间复杂度是线性的
 * 对于检索方法，它具有恒定的时间复杂度
 * 
 * 优先级队列的这种实现不是线程安全的。 因此，
 * 如果我们需要同步访问，我们需要使用PriorityBlockingQueue。
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
		
		// Let’s create another PriorityQueue which orders 
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
