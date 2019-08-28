package org.java.core.base.multithreading.TimeTask;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 如果任务已在执行，Timer将等待它完成，一旦完成，它将再次从队列中开始下一个任务。
 */
public class MyTimerTask extends TimerTask {

	@Override
	public void run() {
		System.out.println("Timer task started at:" + new Date());
		completeTask();
		System.out.println("Timer task finished at:" + new Date());
	}

	/**
	 * 在使用Timer调度任务时，您应该确保时间间隔超过正常的线程执行，
	 * 否则任务队列大小将继续增长，最终任务将始终执行。
	 */
	private void completeTask() {
		try {
			// assuming it takes 20 secs to complete the task
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		TimerTask timerTask = new MyTimerTask();
		//可以创建Java Timer对象以将相关任务作为守护程序线程运行。
		// running timer task as daemon thread
		Timer timer = new Timer(true);
//		 Java Timer object is scheduled to run the task every 10 seconds
		timer.scheduleAtFixedRate(timerTask, 0, 1 * 1000);
		System.out.println("TimerTask started");
		// cancel after sometime
		try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
		System.out.println("TimerTask cancelled");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
