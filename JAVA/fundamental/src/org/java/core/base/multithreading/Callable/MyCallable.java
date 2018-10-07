package org.java.core.base.multithreading.Callable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Java Callable和Future在多线程编程中经常使用，我们学到了很多关于java线程的知识，
 * 但有时我们希望线程可以返回一些我们可以使用的值。
 * Java 5在并发包中引入了java.util.concurrent.Callable接口。
 * <p><br>
 * Java Callable接口使用Generic来定义Object的返回类型,
 * Executors类提供了在线程池中执行Java Callable的有用方法,
 * 由于可调用任务并行运行，我们必须等待返回的Object。
 * <p>
 * <strong>Java Future</strong><br><br>
 * Java Callable tasks return java.util.concurrent.Future object. Using Java 
 * Future object, we can find out the status of the Callable task and get the 
 * returned Object. It provides get() method that can wait for the Callable to 
 * finish and then return the result.
 * Java Callable任务返回Future对象，用Java Future对象，我们可以找到Callable任务的 状态，并且
 * 得到返回的对象，它提供了get方法可以等待Callable完成，然后返回结果。
 * <p>
 * Java Future提供cancel（）方法来取消关联的Callable任务。
 * 有一个get（）方法的重载版本，我们可以指定等待结果的时间，
 * 避免当前线程被阻塞更长时间是有用的,有isDone（）和isCancelled（）
 * 方法来查找关联的Callable任务的当前状态。
 * <p>
 */
public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
    	System.out.println("- call - ");
        Thread.sleep(3000);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }
    
    // 我们使用Executor框架并行执行100个任务，并使用Java Future获取提交任务的结果。
    public static void main(String args[]){
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        //create a list to hold the Future object associated with Callable
        List<Future<String>> list = new ArrayList<Future<String>>();
        
        //Create MyCallable instance
        Callable<String> callable = new MyCallable();
        
        for(int i=0; i< 100; i++){
            //submit Callable tasks to be executed by thread pool
            Future<String> future = executor.submit(callable);
            
            //add Future to the list, we can get return value using Future
            list.add(future);
            System.out.println("list add - " + i);
        }
        
        for(Future<String> fut : list){
            try {
            	//如果任务没有执行完成，就阻塞等待，because Future.get() waits 
            	//for task to get completed
            	
            	//print the return value of Future, notice the output delay in console
                System.out.println(new Date()+ "::"+fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();
    }
}


