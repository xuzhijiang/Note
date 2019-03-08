package org.java.core.base.concurrent.chapter7;

import java.util.concurrent.*;

public class SmartFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException{

        SmartThreadExecutorPool smartThreadExecutorPool =
                new SmartThreadExecutorPool(5,10,
                        10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        //提交一个任务
        SmartFuture<String> smartFuture = smartThreadExecutorPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "当前时间：" + System.currentTimeMillis();
            }
        });

        smartFuture.addListener(new SmartFutureListener<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("异步回调成功,"+result);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("异步回调失败,"+throwable);
            }
        });


        String syncResult = null;
        try {
            syncResult = smartFuture.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("同步回调成功："+syncResult);
    }

}
