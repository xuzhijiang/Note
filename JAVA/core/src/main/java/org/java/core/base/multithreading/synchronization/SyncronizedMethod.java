package org.java.core.base.multithreading.synchronization;

import java.util.Arrays;

/**
 * δ�������,�뿴102.Thread Safety in Java �C Synchronization
 * <strong>Java synchronized</strong>
 * <p><br>
 * Synchronization is the tool using which we can achieve 
 * thread safety, JVM guarantees that synchronized code will 
 * be executed by only one thread at a time. java keyword 
 * synchronized is used to create synchronized code and 
 * internally it uses locks on Object or Class to make sure
 * only one thread is executing the synchronized code.
 * <p>
 * ͬ��������ʵ���̰߳�ȫ�Ĺ���,���ڲ���ʹ��Object��Class������ȷ��ֻ��һ���߳�����ִ��ͬ�����롣<br><p>
 * Java synchronization works on locking and unlocking of resource(Java��ͬ�������������ͽ�����Դ), 
 * before any thread enters into synchronized code, it has to acquire
 * lock on the Object and when code execution ends, it unlocks the resource
 * that can be locked by other threads. In the mean time other threads are 
 * in wait state to lock the synchronized resource.(ͬʱ�������̴߳��ڵȴ�״̬������ͬ����Դ)
 * <p><br>
 * We can use synchronized keyword in two ways, one is to make a complete 
 * method synchronized and other way is to create synchronized block.
 * <p><br>
 * When a method is synchronized, it locks the Object, 
 * if method is static it locks the Class,
 * so it��s always best practice to use synchronized block (ʹ��ͬ�������������ʵ��)to 
 * lock the only sections of method that needs synchronization.(������Ҫͬ��������Ψһ����)
 * <p><br>
 * While creating synchronized block, we need to provide the resource on which lock will be acquired,
 * it can be XYZ.class or any Object field of the class.
 * <p><br>
 * synchronized(this) will lock the Object before entering into the synchronized block.
 * <p>
 * You should use the lowest level of locking, for JdbcQuickStartExample
 * if there are multiple synchronized block in a class and one 
 * of them is locking the Object, then other synchronized blocks
 * will also be not available for execution by other threads. 
 * When we lock an Object, it acquires lock on all the fields of the Object.(����������һ��Objectʱ�������ȡObject�������ֶε�����)
 * <p>
 * Java Synchronization provides data integrity on the cost of performance,(Javaͬ���ṩ�����ܳɱ����������������)
 * so it should be used only when it��s absolutely necessary.
 * <p>
 * Java Synchronization works only in the same JVM, so if you need 
 * to lock some resource in multiple JVM environment, it will not work 
 * and you might have to look after some global locking mechanism(�������Ҫ�չ�һЩȫ����������).
 * Java Synchronization could result in deadlocks, check this post about deadlock in java and how to avoid them.
 * <p>
 * Java synchronized keyword cannot be used for constructors and variables.
 * <p>
 * It is preferable to create a dummy private Object to use for synchronized 
 * block(��������˽�ж�������ͬ��������ѡ��), so that it��s reference can��t be changed by any other code.
 * For JdbcQuickStartExample if you have a setter method for Object on which you
 * are synchronizing, it��s reference can be changed by some other 
 * code leads to parallel execution of the synchronized block.
 * <p>
 * We should not use any object that is maintained in a constant pool,(���ǲ�Ӧʹ���ڳ�������ά�����κζ���)
 * for JdbcQuickStartExample String should not be used for synchronization because
 * if any other code is also locking on same String(��Ϊ����������κδ���Ҳ��������ͬ���ַ�����), it will try 
 * to acquire lock on the same reference object from String pool (���������ַ����л����ͬ���ö������)
 * and even though both the codes are unrelated, they will lock each other.
 */
public class SyncronizedMethod {
	public static void main(String[] args) throws InterruptedException {
        String[] arr = {"1","2","3","4","5","6"};
        HashMapProcessor hmp = new HashMapProcessor(arr);
        Thread t1=new Thread(hmp, "t1");
        Thread t2=new Thread(hmp, "t2");
        Thread t3=new Thread(hmp, "t3");
        long start = System.currentTimeMillis();
        //start all the threads
        t1.start();t2.start();t3.start();
        //wait for threads to finish
        t1.join();t2.join();t3.join();
        System.out.println("Time taken= "+(System.currentTimeMillis()-start));
        //check the shared variable value now
        System.out.println(Arrays.asList(hmp.getMap()));
    }
}

class HashMapProcessor implements Runnable{
    
    private String[] strArr = null;
    
    public HashMapProcessor(String[] m){
        this.strArr=m;
    }
    
    public String[] getMap() {
        return strArr;
    }

    @Override
    public void run() {
        processArr(Thread.currentThread().getName());
    }

    private void processArr(String name) {
        for(int i=0; i < strArr.length; i++){
            //process data and append thread name
            processSomething(i);
            addThreadName(i, name);
        }
    }
    
    private void addThreadName(int i, String name) {
        strArr[i] = strArr[i] +":"+name;
    }

//    Here is how we can change addThreadName() method to make our program thread safe.

//    private Object lock = new Object();
//    private void addThreadName(int i, String name) {
//        synchronized(lock){
//        strArr[i] = strArr[i] +":"+name;
//        }
//    }

    private void processSomething(int index) {
        // processing some job
        try {
            Thread.sleep(index*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}