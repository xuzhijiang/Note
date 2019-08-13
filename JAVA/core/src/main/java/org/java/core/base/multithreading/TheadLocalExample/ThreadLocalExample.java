package org.java.core.base.multithreading.TheadLocalExample;

/**
 * 定义了2个ThreadLocal变量,ThreadLocal<Float>和ThreadLocal<String> ,
 * 每个Thread都有一个ThreadLocalMap,会以ThreadLocal为key,保存在Thread的ThreadLocalMap中.
 *
 * key 	-> 	value
 * ThreadLocal<Float> 	-> 	price_value
 * ThreadLocal<String> 	-> 	name_value
 *
 * 但是要注意，ThreadLocal的initialValue默认是null,如果我们后续还有操作，可能会发生空指针异常，所以推荐创建ThreadLocal对象时，复写initialValue()：
 */
public class ThreadLocalExample {

	private ThreadLocal<Float> price = new ThreadLocal<Float>() {
		@Override
		protected Float initialValue() {
			return 8.8f; // 默认值(初始值)
		}
	};

	private ThreadLocal<String> name; // 默认值为null

	public static void main(String[] args) throws InterruptedException {
		ThreadLocalExample threadLocalExample = new ThreadLocalExample();
		for (int i = 0;i < 10;i++) {
			Thread t = new Thread(new MyThread(threadLocalExample, i));
			t.start();
		}
	}

	private static class MyThread implements Runnable {

		private ThreadLocalExample obj;
		private int offset;

		public MyThread(ThreadLocalExample obj,int offset) {
			this.obj = obj;
			this.offset = offset;
		}

		@Override
		public void run() {
			obj.price.set((float) offset);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " - " + obj.price.get());
		}

	}

}
