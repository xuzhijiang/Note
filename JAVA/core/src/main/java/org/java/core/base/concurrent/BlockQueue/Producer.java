package org.java.core.base.concurrent.BlockQueue;

import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {

	private BlockingQueue<Message> queue;

	public Producer(BlockingQueue<Message> q) {
		this.queue = q;
	}

	@Override
	public void run() {
		//produce message
		for(int i=0;i<100;i++) {
			Message msg = new Message(""+i);
			try {
				queue.put(msg);
				System.out.println("produce Msg: " + msg.getMsg());
				Thread.sleep(Math.round(Math.random() * 100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//adding exit message
		Message msg = new Message("exit");
		try {
			queue.put(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
