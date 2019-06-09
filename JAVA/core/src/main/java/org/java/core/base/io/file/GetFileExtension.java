package org.java.core.base.io.file;

import java.io.File;

public class GetFileExtension {
	
	static final String FILE_NAME = "C:\\Users\\a\\Desktop\\test\\a.txt";
	static final String FILE_DIR = "C:\\Users\\a\\Desktop\\test\\";
	
    /**
     * Get File extension in java
     * @param args
     */
    public static void main(String[] args) {
        File file = new File(FILE_NAME);
        System.out.println("File extension is: "+getFileExtension(file));
        //file name without extension
        file = new File(FILE_DIR);
        System.out.println("File extension is: "+getFileExtension(file));
        //file name with dot
        file = new File("/Users/xzj/java.util.txt");
        System.out.println("File extension is: "+getFileExtension(file));
        //hidden files without extension
        file = new File("/Users/xzj/.htaccess");
        System.out.println("File extension is: "+getFileExtension(file));
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    
}
