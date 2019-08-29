package org.java.core.base.concurrent.Timer;

import org.junit.Test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

class TimerTest {

	@Test
	public void startUntouchControllerTimer() {
		Timer timer = new Timer();
		// 取消原来的定时器
		if (timer != null) {
			timer.cancel();
		}
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("...........");
			}
		}, TimeUnit.SECONDS.toMillis(5));
	}

	public static void main(String args[]) {
		// 将相关任务作为守护线程运行.
		Timer timer = new Timer(true);
//		timer.schedule(new CheckServerStatusTimerTask(), 2000, 5000);
		// 第三个参数是时间间隔,2秒,因为一次任务需要10秒,所以就不起作用.也就是一次任务执行完成后,
		// 不会等待2秒,而是直接执行下一个任务.
//		timer.scheduleAtFixedRate(new CheckServerStatusTimerTask(), 0, TimeUnit.SECONDS.toMillis(2L));
		// 第三个参数是时间间隔,12秒,因为一次任务需要10秒,所以能够起作用.
		timer.scheduleAtFixedRate(new CheckServerStatusTimerTask(), 0, TimeUnit.SECONDS.toMillis(12L));
		try {
			Thread.sleep(200000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
		System.out.println("TimerTask cancelled");
	}

	// java.util.TimerTask是一个实现Runnable接口的抽象类
	private static class CheckServerStatusTimerTask extends TimerTask {

		@Override
		public void run() {
			System.out.println("Timer task started at:" + new Date());
			checkServer();
			System.out.println("Timer task end at:" + new Date());
		}

		/**
		 * 注意任务需要花费的时间如果超过了timer调度的时间间隔(period),
		 * 那么时间间隔就不起作用.period小于任务执行的时间间隔才起作用,
		 * 也就是说,period是包含任务执行的时间的.
		 */
		private void checkServer() {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
