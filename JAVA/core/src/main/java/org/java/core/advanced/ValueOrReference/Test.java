package org.java.core.advanced.ValueOrReference;

public class Test {
	
	public static void main(String[] args) {

		//let’s assume that “red” is pointing to 50 and “blue” is pointing 
		//to 100 and these are the memory location of both Balloon objects.
		//50和100是2个对象在heap内存的位置
		
		Balloon red = new Balloon("Red"); //memory reference 50
		Balloon blue = new Balloon("Blue"); //memory reference 100
		
		swap(red, blue);
		System.out.println("red color="+red.getColor());
		System.out.println("blue color="+blue.getColor());
		
		foo(blue);
		System.out.println("blue color="+blue.getColor());
		
	}

	private static void foo(Balloon balloon) { //baloon=100
		balloon.setColor("Red"); //baloon=100
		balloon = new Balloon("Green"); //baloon=200
		balloon.setColor("Blue"); //baloon = 200
	}

	//when we are calling swap() method, two new variables o1 and o2 
	//are created pointing to 50 and 100 respectively.
	//当我们执行swap方法的时候，2个新的变量o1和o2被创建，并且分别各自指向50和100
	//the variables are just the reference to the objects,变量只是对象的引用,改变的不是对象本身，
	//而是变量而已.
	// However we are passing a copy of the reference and hence it’s pass by value
	//我们传递给swap的只是引用变量而已，因此是值传递.
	
	//Generic swap method
	public static void swap(Object o1, Object o2){
		Object temp = o1; //temp=50, o1=50, o2=100
		o1=o2; //temp=50, o1=100, o2=100
		o2=temp; //temp=50, o1=100, o2=50
	}//method terminated
}
