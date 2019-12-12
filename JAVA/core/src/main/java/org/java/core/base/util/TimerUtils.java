package org.java.core.base.util;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

class TimerUtils {

	public static void main(String args[]) {
		// 是否将相关任务作为守护线程运行.
		Timer timer = new Timer(true);

		// 第三个参数是时间间隔,2秒,因为一次任务需要10秒,所以就不起作用.也就是一次任务执行完成后,不会等待2秒,而是直接执行下一个任务.
		timer.scheduleAtFixedRate(new CheckServerStatusTimerTask(), 0, TimeUnit.SECONDS.toMillis(2L));

		// 第三个参数是时间间隔,12秒,因为一次任务需要10秒,所以能够起作用.
//		timer.scheduleAtFixedRate(new CheckServerStatusTimerTask(), 0, TimeUnit.SECONDS.toMillis(12L));

		try { Thread.sleep(200 * 1000); } catch (InterruptedException e) { e.printStackTrace(); }
		timer.cancel();
	}

	private static class CheckServerStatusTimerTask extends TimerTask {

		@Override
		public void run() {
			System.out.println();
			System.out.println("Time: " + new Date());
			checkServer();
			System.out.println("Time: " + new Date());
		}

		/**
		 * 注意任务需要花费的时间如果超过了timer调度的时间间隔(period),
		 * 那么时间间隔就不起作用.period小于任务执行的时间间隔才起作用,
		 * 也就是说,period是包含任务执行的时间的.
		 */
		private void checkServer() {
			try { Thread.sleep(10 * 1000); } catch (InterruptedException e) { e.printStackTrace(); }
		}
	}

}
