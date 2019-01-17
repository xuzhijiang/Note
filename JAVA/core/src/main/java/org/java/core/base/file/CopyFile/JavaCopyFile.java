package org.java.core.base.file.CopyFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

/**
 * Java拷贝文件是一种非常常见的操作。 但是java.io.File类没有任何快捷方法
 * 可以将文件从源复制到目标。 在这里，我们将了解可以在java中复制文件的四种不同方法。
 * 
 * 现在要找出哪个是最快的方法，我编写了一个测试类，并逐个执行上面的方法，
 * 用于1 GB的复制文件。在每次调用中，我使用不同的文件来避免因为缓存而对以后的方法带来任何好处。
 * 
 * 请注意我在上面的代码中注释，以确保每次只有一个方法用于java文件复制操作。
 * 
 * 从输出中可以清楚地看出，Stream Copy是在Java中复制File的最佳方式。
 *  但这是一个非常基本的测试，如果你正在开展一个性能密集型项目，
 *  那么你应该尝试不同的java拷贝文件方法，并记下时间，找出你项目的最佳方法。
 *  
 * 您还应该根据文件的平均大小来比对不同的的java复制文件方式。
 * 
 * Time taken by Stream  =     6401036996 nanoTime
 * Time taken by Channel  =     553044816 nanoTime
 * Time taken by Java7 Files  = 603724328 nanoTime
 */
public class JavaCopyFile {

	static int UNIT_CONVERT = 1000 * 1000;
	
    public static void main(String[] args) throws InterruptedException, IOException {
        File source = new File("C:\\Users\\a\\Desktop\\test\\source.zip");
        File dest = new File("C:\\Users\\a\\Desktop\\test\\dest.zip");
        long start = 0L;

        //copy file conventional way using Stream
        start = System.nanoTime();
        copyFileUsingStream(source, dest);
        System.out.println("Time taken by Stream  = "+(System.nanoTime()-start)/UNIT_CONVERT + " millionSeconds");
        
          //copy files using java.nio FileChannel
//        source = new File("C:\\Users\\a\\Desktop\\test\\channelSource.zip");
//        dest = new File("C:\\Users\\a\\Desktop\\test\\channelDest.zip");
//        start = System.nanoTime();
//        copyFileUsingChannel(source, dest);
//        System.out.println("Time taken by Channel  = "+(System.nanoTime()-start) + " nanoTime");
        
          // using Java 7 Files class
//        source = new File("C:\\Users\\a\\Desktop\\test\\FilesSource.zip");
//        dest = new File("C:\\Users\\a\\Desktop\\test\\aa.zip");
//        start = System.nanoTime();
//        copyFileUsingJava7Files(source, dest);
//        System.out.println("Time taken by Java7 Files  = "+(System.nanoTime()-start) + " nanoTime");        
    }
    
    /**
     * 这是java中常规的文件复制方式，这里我们创建两个文件，源和目标。
     * 然后我们从源创建InputStream并使用OutputStream将其写入
     * 目标文件以进行java复制文件操作。
     * @param source
     * @param dest
     * @throws IOException
     */
    private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
    
    /**
     * Java Copy File – java.nio.channels.FileChannel
     * 
     * Java NIO类在Java 1.4中引入，FileChannel可用于在java中复制文件。 
     * 根据transferFrom（）方法javadoc，这种复制文件的方式应该比使用Streams
     *  for java copy文件更快。
     * @param source
     * @param dest
     * @throws IOException
     */
	private static void copyFileUsingChannel(File source, File dest) throws IOException {
	    FileChannel sourceChannel = null;
	    FileChannel destChannel = null;
	    try {
	        sourceChannel = new FileInputStream(source).getChannel();
	        destChannel = new FileOutputStream(dest).getChannel();
	        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally{
           sourceChannel.close();
           destChannel.close();
	    }
	}
	

	/**
	 * 如果您正在使用Java 7或更高版本，则可以使用Files类copy（）
	 * 方法在java中复制文件。它使用文件系统提供程序来复制文件。
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
	    Files.copy(source.toPath(), dest.toPath());
	}


}
