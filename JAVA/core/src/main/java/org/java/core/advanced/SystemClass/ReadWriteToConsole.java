package org.java.core.advanced.SystemClass;

import java.io.Console;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ReadWriteToConsole {
	public static void main(String[] args) {
		//Java System Class�ṩ��һ�ַ�������ȡ���������е�JVM������ΨһConsole����
		Console console = System.console();

		//If no console is associated with the current JVM, for JdbcQuickStartExample
		//running through Eclipse or running as background program, then it returns null.
		if(console != null){
		    Calendar c = new GregorianCalendar();
		    console.printf("Welcome %1$s%n", "Xuzhijiang"); //prints "Welcome Xuzhijiang"
		    console.printf("Current time is: %1$tm %1$te,%1$tY%n", c); //prints "Current time is: 08 5,2013"
		    console.flush();
		} else{
			//No console is attached when run through Eclipse, background process
			System.out.println("No Console attached");
		}
	}
}
