package org.java.core.base.io.file;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Scanner类可使用正则表达式将输入分解，也适用于解析文件
 */
public class ScannerUtils {
	
    /**
     * 解析CSV格式的文件
     */
    @Test
    public void parseCSVLine() {
        List<Employee> list = new ArrayList<Employee>();
        String lineSeparator = System.getProperty("line.separator");
        String csvString = "aa,28,Male" + lineSeparator + "bb,30,Female" + lineSeparator + "cc,25,Male";

        Scanner scanner = new Scanner(csvString);
        scanner.useDelimiter(lineSeparator);
        while(scanner.hasNext()){
            String line = scanner.next();
            Scanner empScanner = new Scanner(line);
            // 用正则表达式创建分隔符
            empScanner.useDelimiter("\\s*,\\s*");
            String name = empScanner.next();
            int age = empScanner.nextInt();
            String gender = empScanner.next();
            list.add(new Employee(name, age, gender));
            empScanner.close();
        }
        scanner.close();
        System.out.println(list);
    }

    private class Employee{

        private String name;
        private int age;
        private String gender;
        
        public Employee(String n, int a, String gen){
            this.name = n;
            this.age = a;
            this.gender = gen;
        }
        
        @Override
        public String toString(){
            return "Name="+this.name+", Age="+this.age+", Gender="+this.gender;
        }
    }

}
