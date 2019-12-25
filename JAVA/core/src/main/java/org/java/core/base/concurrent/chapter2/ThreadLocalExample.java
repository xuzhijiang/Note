package org.java.core.base.concurrent.chapter2;

/**
 * 定义了2个ThreadLocal变量,ThreadLocal<Float>和ThreadLocal<String> ,
 * 每个Thread都有一个ThreadLocal.ThreadLocalMap,会以ThreadLocal对象为key
 *
 * key 	-> 	value
 * ThreadLocal<Float> 	-> 	price_value
 * ThreadLocal<String> 	-> 	name_value
 *
 * 但是要注意，ThreadLocal的initialValue默认是null,如果我们后续还有操作，可能会发生空指针异常，
 * 所以推荐创建ThreadLocal对象时，重写initialValue()
 *
 * 在一些场景 (尤其是使用线程池) 下，应该尽可能在每次使用 ThreadLocal 后手动调用 remove()，
 * 以避免出现 ThreadLocal 造成自身业务混乱
 */
public class ThreadLocalExample {

	public static void main(String[] args) throws InterruptedException {
		ShareData shareData = new ShareData();

		for (int i = 0;i < 10;i++) {
			final int temp = i;
			new Thread(() -> {
				shareData.changValue(temp);
				try { Thread.sleep(2000L); } catch (InterruptedException e) { e.printStackTrace();}
				System.out.println(Thread.currentThread().getName() + " - " + shareData.getValue());
				shareData.remove();
			}, String.valueOf(i)).start();
		}
	}

	private static class ShareData {
		//private ThreadLocal<String> name; // 默认值为null

		ThreadLocal<Float> price = ThreadLocal.withInitial(() -> {
			return 8.8f; // 默认值(初始值)
		});

		public void changValue(int value) {
			price.set((float) value);
		}

		public Float getValue() {
			return price.get();
		}

		public void remove() {
			price.remove();
		}
	}

}
