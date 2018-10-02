package org.java.core.base.file.FileFilter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * From Java 8 onwards, FileNameFilter is a functional 
 * interface since it has a single method.
 * <p><br>
 * We can use FilenameFilter in java to find all the files of specific extension in a directory
 * <p><br>
 * 
 */
public class FileNameFilter {
	public static void main(String[] args){
		String dir = "C:\\Users\\xu\\Desktop\\test";
		String ext = ".txt";
		findFiles(dir, ext);
	}
	
	public static void findFiles(String dir, String ext){
		File file = new File(dir);
		if(!file.exists()){
			System.out.println(dir + " Directory doesn't exist");
		}
//		File[] listFiles = file.listFiles(new MyFileNameFilter(ext));//因为findFiles是静态方法
		//所以必须把内部类也定义为静态类
		File[] listFiles = file.listFiles(new MyFileNameFilter(ext));
		
		//Since FileNameFilter is a functional interface, we can 
		//reduce the above code by using lambda expression. 
		// File[] listFiles = file.listFiles((d, s) -> {
		// return s.toLowerCase().endsWith(ext);
		// });
		
		if(listFiles.length == 0){
			System.out.println(dir + " doesn't have any file with extension " + ext);
		}else{
			for(File f : listFiles){
				System.out.println("File: " + dir + File.separator + f.getName());
			}
		}
	}
	
	// FileNameFilter implementation
	public static class MyFileNameFilter implements FilenameFilter {

		private String ext;
		
		public MyFileNameFilter(String ext){
			this.ext = ext.toLowerCase();
		}
		
		@Override
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith(ext);
		}
		
	}
}
