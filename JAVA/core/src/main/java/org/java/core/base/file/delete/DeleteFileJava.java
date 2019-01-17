package org.java.core.base.file.delete;

import java.io.File;

public class DeleteFileJava {
	
	public static void main(String[] args) {
		String fileSeparator = System.getProperty("file.separator");
		System.out.println("fileSeparator: " + fileSeparator);
		
		//absolute file name with path
		String absoluteFilePath = "C:" + fileSeparator + "Users" + fileSeparator + "a" + fileSeparator + "Desktop" + fileSeparator + "file.txt";
        File file = new File(absoluteFilePath);
        if(file.delete()){
            System.out.println("absoluteFilePath has been deleted");
        }else System.out.println("absoluteFilePath doesn't exists");
        
        //file name only
        file = new File("file.txt");
        if(file.delete()){
            System.out.println("file.txt File deleted from Project root directory");
        }else System.out.println("File file.txt doesn't exists in project root directory");
        
        //relative path
        file = new File("tmp/file.txt");
        if(file.delete()){
            System.out.println("tmp/file.txt File deleted from Project root directory");
        }else System.out.println("File tmp/file.txt doesn't exists in project root directory");
        
        //delete empty directory
        file = new File("tmp");
        if(file.delete()){
            System.out.println("tmp directory deleted from Project root directory");
        }else System.out.println("tmp directory doesn't exists or not empty in project root directory");
        
        //try to delete directory with files
        file = new File("bin");
        if(file.delete()){
            System.out.println("tmp directory deleted from Project root directory");
        }else System.out.println("tmp directory doesn't exists or not empty");
        
        //Note that tmp directory had file.txt and it got deleted first and then directory 
        //was empty and got deleted successfully, bin was not empty and hence not deleted.
	}
}
