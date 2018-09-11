package org.java.core.base.scheduler;

import java.util.Timer;
import java.util.TimerTask;

public class TimerAndTimerTaskInJava{
	
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new MyTask("get the stock price of google"), 2000, 5000);
		timer.schedule(new MyTask("get all player in NBA"), 1000, 3000);
	}
}

class MyTask extends TimerTask
{
	String taskName;
	
	public MyTask(String taskName) {
		this.taskName = taskName;
	}
	
	@Override
	public void run() {
		System.out.println("run the task => " + this.taskName);
	}
}
