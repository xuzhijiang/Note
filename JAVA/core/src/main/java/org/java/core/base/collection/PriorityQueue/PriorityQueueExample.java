package org.java.core.base.collection.PriorityQueue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

// We will use java random number generation to generate random customer objects. 
// For natural ordering, I will use Integer that is also a java wrapper class.
public class PriorityQueueExample {

	public static void main(String[] args) {
		
		//natural ordering example of priority queue
		Queue<Integer> integerPriorityQueue = new PriorityQueue<>(7);
		Random rand = new Random();
		for(int i=0;i<7;i++){
			integerPriorityQueue.add(new Integer(rand.nextInt(100)));
		}
		// 是按照自然顺序排序的
		for(int i=0;i<7;i++){
			// 轮询
			Integer in = integerPriorityQueue.poll();
			System.out.println("Processing Integer:"+in);
		}
		
		//PriorityQueue example with Comparator
		Queue<Customer> customerPriorityQueue = new PriorityQueue<>(7, idComparator);
		addDataToQueue(customerPriorityQueue);
		
		pollDataFromQueue(customerPriorityQueue);
		
	}
	
	//Comparator anonymous class implementation
	// 请注意，我使用java匿名类来实现Comparator接口并创建基于id的比较器。
	public static Comparator<Customer> idComparator = new Comparator<Customer>(){
		
		@Override
		public int compare(Customer c1, Customer c2) {
            return (int) (c2.getId() - c1.getId());
        }
	};

	//utility method to add random data to Queue
	private static void addDataToQueue(Queue<Customer> customerPriorityQueue) {
		Random rand = new Random();
		for(int i=0; i<7; i++){
			int id = rand.nextInt(100);
			customerPriorityQueue.add(new Customer(id, "xzj "+id));
		}
	}
	
	//utility method to poll data from queue
	private static void pollDataFromQueue(Queue<Customer> customerPriorityQueue) {
		while(true){
			Customer cust = customerPriorityQueue.poll();
			if(cust == null) break;
			System.out.println("Processing Customer with ID="+cust.getId());
		}
	}

}
// 从输出中可以清楚地看出，最小元素处于首位，并首先进行了轮询。 
// 如果我们在创建customerPriorityQueue时不提供比较器，它将在运行时抛出ClassCastException。
