package org.java.core.base.concurrent.chapter7;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 需要说明的是Callable<T>和Future<T>都有一个泛型参数，
 * 这指的是线程运行返回值结果类型。调用future.get()方法，会进行阻塞，直到线程运行完并返回结果。
 *
 *
 */
public class CallableAndFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("当前时间: " + new Date());
        ExecutorService service = Executors.newSingleThreadExecutor();


        //Future与Callable中的泛型，就是返回值的类型
        // Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。
        // 必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果。
        Future<String> future = service.submit(new Callable<String>(){

            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "hello";
            }
        });

        String result = future.get();//该方法会阻塞运行，等待执行完成
        System.out.println("result:  " + result);

        System.out.println("结束时间: " + new Date());
        service.shutdown();

    }
}
