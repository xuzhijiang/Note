package org.java.core.base.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerExecutorTest {
	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new ExecutorTask("Running"), 1, 3, TimeUnit.SECONDS);
		executor.scheduleAtFixedRate(new ExecutorTask("Playing"), 1, 5, TimeUnit.SECONDS);
	}
}

class ExecutorTask implements Runnable
{
	String name;
	
	public ExecutorTask(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		System.out.println("run task => " + this.name);
	}
	
}