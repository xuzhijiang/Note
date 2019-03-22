package org.java.core.base.multithreading.lock;

/**
 * ���һ���߳̽���foo���������ͻ�����EqualsOperatorTest����(it has the lock on EqualsOperatorTest object),
 * ���Ե�������ִ��bar��������ʱ�����߳��Ǳ������ȥִ��bar����������
 * ��Ϊ��������EqualsOperatorTest�����ϵ���,������ͬsynchronized(this).
 * @author a
 *
 */
public class Test {
	
	public synchronized void foo(){
	    //do something
		bar();
	}

	public synchronized void bar(){
		//do some more
	}
}
