package org.java.core.base.file.rename;

import java.io.File;

/**
 * We can use File.renameTo(File dest) method for java rename file and java move file operations.
 * 如果重命名成功，renameTo返回true，else it returns false.
 * 
 */
public class RenameFileJava {
	/**
	 * Rename File or Move File in Java example
	 * @param args
	 */
	public static void main(String[] args) {
		//absolute path rename file
        File file = new File("C:\\Users\\a\\Desktop\\t\\t.txt");
        File newFile = new File("C:\\Users\\a\\Desktop\\t\\t1.txt");
        if(file.renameTo(newFile)){
            System.out.println("File rename success");;
        }else{
            System.out.println("File rename failed");
        }
        
        //relative path rename file
        file = new File("DB.properties");
        newFile = new File("DB_New.properties");
        if(file.renameTo(newFile)){
            System.out.println("File rename success");;
        }else{
            System.out.println("File rename failed");
        }
        
        //java move file from one directory to another
        file = new File("C:\\Users\\a\\Desktop\\t\\t1.txt");
        newFile = new File("DB_Move.properties");
        if(file.renameTo(newFile)){
            System.out.println("File move success");;
        }else{
            System.out.println("File move failed");
        }
        
        //when source file is not present
        file = new File("C:\\Users\\a\\Desktop\\t\\xyz.txt");
        newFile = new File("xyz.properties");
        if(file.renameTo(newFile)){
            System.out.println("File move success");;
        }else{
            System.out.println("File move failed");
        }
        
        // when destination already have a file with same name
        file = new File("C:\\Users\\a\\Desktop\\t\\b.txt");
        newFile = new File("C:\\Users\\a\\Desktop\\t\\c.txt");
        if(file.renameTo(newFile)){
            System.out.println("File move success");;
        }else{
            System.out.println("File move failed");
        }
	}
}
