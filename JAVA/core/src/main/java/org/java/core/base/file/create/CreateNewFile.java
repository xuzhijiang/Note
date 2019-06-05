package org.java.core.base.file.create;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateNewFile {
	
	public static void main(String[] args) throws IOException {
		String fileSeparator = System.getProperty("file.separator");
		System.out.println("fileSeparator: " + fileSeparator);
		
		//absolute file name with path
		String absoluteFilePath = "C:" + fileSeparator + "Users" + fileSeparator + "a" + fileSeparator + "Desktop" + fileSeparator + "file.txt";
		File file = new File(absoluteFilePath);
		if(file.createNewFile()) {
			System.out.println(absoluteFilePath + " File.Created");
		}else {
			System.out.println("File " + absoluteFilePath + " already exists");
		}
		
		//file name only
		file = new File("file.txt");
		if(file.createNewFile()){
            System.out.println("file.txt File Created in Project root directory");
        }else 
        	System.out.println("File file.txt already exists in project root directory");
		
		
		try {
			//For relative path, it throws IOException because tmp directory is not present in project root folder.
			//对于相对路径，将会抛出IOException，因为tmp文件夹不存在于项目根目录文件夹中.
			
			//So it’s clear that createNewFile() just tries to create the file and any directory 
			//either absolute or relative should be present already, else it throws IOException.
			//因此很明显，createNewFile()只尝试去创建文件，任何相对目录或者绝对目录都应该已经存在，如果不存在，就会抛异常.
//			So I created tmp directory in project root and executed the program again
			
			//relative path,在IDE中是相对于项目根目录的，在命令行执行class文件是相对于命令行当前目录的
			String relativePath = "tmp" + fileSeparator + "file.txt";
			file = new File(relativePath);
			if(file.createNewFile()){
	            System.out.println(relativePath+" File Created in Project root directory");
	        }else System.out.println("File "+relativePath+" already exists in project root directory");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//如果要创建新文件并同时向其中写入一些数据，可以使用FileOutputStream写入方法。
		String fileData = "Xu Zhijiang";
		FileOutputStream fos = new FileOutputStream("name1.txt");
		fos.write(fileData.getBytes());
		fos.flush();
		fos.close();
		
		//We can use Java NIO Files class to create a new file and write some data into it. 
		//This is a good option because we don’t have to worry about closing IO resources.
		fileData = "Xu Zhijiang";
		Files.write(Paths.get("name2.txt"), fileData.getBytes());
	}
	//first execution from classes output directory	
	//D:\learning\Note\JAVA\fundamental>java org.java.core.base.file.create.CreateNewFile
	//fileSeparator: \
	//File C:\Users\a\Desktop\file.txt already exists
	//file.txt File Created in Project root directory
	//java.io.IOException: 系统找不到指定的路径。
	//        at java.io.WinNTFileSystem.createFileExclusively(Native Method)
	//        at java.io.File.createNewFile(Unknown Source)
	//        at org.java.core.base.file.create.CreateNewFile.main(CreateNewFile.java:41)

	//tmp directory doesn't exist, lets create it
	//D:\learning\Note\JAVA\fundamental> mkdir tmp

	//second time execution
	//fileSeparator: \
	//File C:\Users\a\Desktop\file.txt already exists
	//File file.txt already exists in project root directo
	//tmp\file.txt File Created in Project root directory

	//third and subsequent execution
	//fileSeparator: \
	//File C:\Users\a\Desktop\file.txt already exists
	//File file.txt already exists in project root directo
	//tmp\file.txt File Created in Project root directory
}
