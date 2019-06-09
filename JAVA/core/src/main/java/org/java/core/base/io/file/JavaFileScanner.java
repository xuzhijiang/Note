package org.java.core.base.io.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Java Scanner类可用于使用任何正则表达式分隔符将输入分解，并且它也适用于解析文件。
 * 
 * Scanner扩展了String的split()的功能，可以将分解的块转成int，float，String，
 * long和其他包装类
 *
 * 我使用Scanner逐行读取文件，解析CSV文件
 * 
 * 需要将输入解析为特定数据类型标记时，请使用java Scanner类。
 * Use java Scanner classes when you need to parse the input into specific datatype tokens.
 */
public class JavaFileScanner {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";
	public static String csvFile = "C:\\Users\\a\\Desktop\\test\\data.csv";

    public static void main(String[] args) throws IOException {
        /**
         *  My Name is Pankaj
         *  My website is journaldev.com
         *  Phone : 1234567890
         */
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path, StandardCharsets.UTF_8.toString());
        
        //read file line by line,用行分隔符作为分割符
        scanner.useDelimiter(System.getProperty("line.separator"));
        System.out.print("System line separator: " + System.getProperty("line.separator"));
        while(scanner.hasNext()){
            System.out.println("Lines: "+scanner.next());
        }
        scanner.close();
        
        //read CSV Files and parse it to object array
        /**
         * Pankaj,28,Male
         * Lisa,30,Female
         * Mike,25,Male
         */
        scanner = new Scanner(Paths.get(csvFile));
        // 行分隔符
        scanner.useDelimiter(System.getProperty("line.separator"));
        while(scanner.hasNext()){
            //parse line to get Emp Object
            Employee emp = parseCSVLine(scanner.next());
            System.out.println(emp.toString());
        }
        scanner.close();
        
        //read from system input
        System.out.println("Read from system input:");
        scanner = new Scanner(System.in);
        System.out.println("Input first word: "+scanner.next());
    }
    
    private static Employee parseCSVLine(String line) {
         Scanner scanner = new Scanner(line);
         // 用正则表达式创建分隔符
         scanner.useDelimiter("\\s*,\\s*");
         String name = scanner.next();
         int age = scanner.nextInt();
         String gender = scanner.next();
         JavaFileScanner jfs = new JavaFileScanner();
         scanner.close();
         return jfs.new Employee(name, age, gender);
    }

    public class Employee{
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
            return "Name="+this.name+"::Age="+this.age+"::Gender="+this.gender;
        }
    }

}
