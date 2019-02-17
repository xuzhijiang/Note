package org.java.core.advanced.HeapStack;

/**
 * how they are being used to store primitive, Objects and reference variables.
 * 它们(Stack memory(堆栈) and heap(堆) memory)是如何被使用去存储原始值，对象以及引用变量的。
 */
public class Memory {
	
	public static void main(String[] args) {//line 1
		int i = 1;//line 2
		Object obj = new Object();//line 3
		Memory mem = new Memory();//line 4
		mem.foo(obj);//line 5
	}//line 9
	
	private void foo(Object param) {//line 6
		String str = param.toString();//line 7
		System.out.println(str);
	}//line 8
}

//1. 一旦我们运行程序，它就会将所有运行时类加载到Heap Space中。当在第1行找到main(）方法时，
// Java Runtime会创建要由main()方法线程使用的Stack memory。

//2. 我们在第2行创建原始局部变量，因此它被创建并存储在main()方法的stack memory中。

//3. 由于我们在第3行创建了一个Object，它在堆内存(Heap space)中创建，
// 堆栈内存(Stack memory)包含它的引用。 当我们在第4行创建Memory对象时，会发生类似的过程。

//4. 现在，当我们在第5行调用foo(）方法时，会创建堆栈顶部的块以供foo(）方法使用。
// 由于Java是按值传递的，因此在第6行的foo(）堆栈块中创建了对Object的新引用。

//5. 在第7行创建一个字符串，它在堆空间(Heap Space)的String Pool中，
// 并在foo(）堆栈空间中为它创建一个引用。

//6. foo(）方法在第8行终止，此时在堆栈中为foo(）分配的内存块变为空闲。

//7. 在第9行中，main(）方法终止，并且为main(）方法创建的堆栈内存被销毁。 
// 此程序也在此行结束，因此Java Runtime释放所有内存并结束程序的执行。