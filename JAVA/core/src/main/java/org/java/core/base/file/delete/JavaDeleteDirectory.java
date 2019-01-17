package org.java.core.base.file.delete;

import java.io.File;

/**
 * Below is a simple program showing how to delete a non empty 
 * directory. This will work if your directory contains files only.
 * 如果文件夹里面只包含了文件
 */
public class JavaDeleteDirectory {
	
	public static void main(String[] args) {
		File dir = new File("C:\\Users\\a\\Desktop\\test");
		
		if(!dir.isDirectory()) {
			System.out.println("Not a directory. Do nothing");
			return;
		}
		File[] listFiles = dir.listFiles();
		for(File file : listFiles){
			System.out.println("Deleting "+file.getName());
			file.delete();
		}
		//now directory is empty, so we can delete it
		System.out.println("Deleting Directory. Success = "+dir.delete());
	}
}
