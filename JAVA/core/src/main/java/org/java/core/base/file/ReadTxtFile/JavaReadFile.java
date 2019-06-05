package org.java.core.base.file.ReadTxtFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * 选择使用Scanner或BufferedReader或Files来读取文件取决于您的项目要求。 
 * 例如， if you are just logging the file，则可以使用Files和BufferedReader。
 *  如果要查找基于分隔符的文件，则应使用Scanner类。
 * @author a
 *
 */
public class JavaReadFile {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";

    public static void main(String[] args) throws IOException {
        //using Java 7 Files class to process small files, get complete file data
        readUsingFiles(fileName);
        
        //using Scanner class for large files, to read line by line
        readUsingScanner(fileName);
        
        //read using BufferedReader, to read line by line
        readUsingBufferedReader(fileName);
        readUsingBufferedReaderJava7(fileName, StandardCharsets.UTF_8);
        readUsingBufferedReader(fileName, StandardCharsets.UTF_8);
        
        //read using FileReader, no encoding support, not efficient
        readUsingFileReader(fileName);
    }

    private static void readUsingBufferedReader(String fileName, Charset cs) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        System.out.println("Read text file using InputStreamReader");
        while((line = br.readLine()) != null){
            //process the line
            System.out.println(line);
        }
        br.close();
        
    }

    private static void readUsingBufferedReaderJava7(String fileName, Charset cs) throws IOException {
        Path path = Paths.get(fileName);
        BufferedReader br = Files.newBufferedReader(path, cs);
        String line;
        System.out.println("Read text file using BufferedReader Java 7 improvement");
        while((line = br.readLine()) != null){
            //process the line
            System.out.println(line);
        }
        br.close();
    }

    private static void readUsingBufferedReader(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        System.out.println("Read text file using BufferedReader");
        while((line = br.readLine()) != null){
            //process the line
            System.out.println(line);
        }
        //close resources
        br.close();
        fr.close();
    }
    
    private static void readUsingFileReader(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        System.out.println("Reading text file using FileReader");
        while((line = br.readLine()) != null){
            //process the line
            System.out.println(line);
        }
        br.close();
        fr.close();
    }

    private static void readUsingScanner(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path, StandardCharsets.UTF_8.toString());
        System.out.println("Read text file using Scanner");
        //read line by line
        while(scanner.hasNextLine()){
            //process each line
            String line = scanner.nextLine();
            System.out.println(line);
        }
        scanner.close();
    }

    private static void readUsingFiles(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        //read file to byte array
        byte[] bytes = Files.readAllBytes(path);
        System.out.println("Read text file using Files class");
        //read file to String list
        @SuppressWarnings("unused")
		List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

}
