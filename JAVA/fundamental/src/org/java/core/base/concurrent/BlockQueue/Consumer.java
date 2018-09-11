package org.java.core.base.concurrent.BlockQueue;

import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {

	private BlockingQueue<Message> queue;

	public Consumer(BlockingQueue<Message> q) {
		this.queue = q;
	}

	@Override
	public void run() {
		// consume
		try {
			Message msg = null;
			while (!(msg = queue.take()).getMsg().equals("exit")) {
				Thread.sleep(30);
				System.out.println("Consume Msg: " + msg.getMsg());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Consume over");
	}
}
