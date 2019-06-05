package org.java.core.base.file.WriteToFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Java提供了很多种方式往文件里面写内容，我们可以使用FileWriter,
 * BufferedWriter, Java7的Files以及FileOutputStream
 * 
 * 这些是在java中编写文件的标准方法，您应该根据项目要求选择其中任何一个。 
 * 这就是java写入文件示例的全部内容。
 */
public class WriteFile {

	public static final String path = "C:\\Users\\a\\Desktop\\test\\";
    /**
     * This class shows how to write file in java
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) {
        String data = "I will write this String to File in Java";
        int noOfLines = 10000;
        writeUsingFileWriter(data);
        
        writeUsingBufferedWriter(data, noOfLines);
        
        writeUsingFiles(data);
        
        writeUsingOutputStream(data);
        System.out.println("DONE");
    }

    /**
     * Use Streams when you are dealing with raw data
     * @param data
     */
    private static void writeUsingOutputStream(String data) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(path + "os.txt"));
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Use Files class from Java 1.7 to write files, internally uses OutputStream
     * @param data
     */
    private static void writeUsingFiles(String data) {
        try {
            Files.write(Paths.get(path + "files.txt"), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Use BufferedWriter when number of write operations are more
     * It uses internal buffer to reduce real IO operations and saves time
     * @param data
     * @param noOfLines
     */
    private static void writeUsingBufferedWriter(String data, int noOfLines) {
        File file = new File(path + "bufferedWriter.txt");
        FileWriter fr = null;
        BufferedWriter br = null;
        String dataWithNewLine = data + System.getProperty("line.separator");
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for(int i = noOfLines; i>0; i--){
                br.write(dataWithNewLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Use FileWriter when number of write operations are less
     * @param data
     */
    private static void writeUsingFileWriter(String data) {
        File file = new File(path + "new.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
