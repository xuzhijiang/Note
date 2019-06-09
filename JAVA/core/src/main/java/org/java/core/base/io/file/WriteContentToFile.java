package org.java.core.base.io.file;

import org.junit.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Java提供了很多种方式往文件里面写内容，可以使用FileWriter,
 * BufferedWriter, Java7的Files以及FileOutputStream
 */
public class WriteContentToFile {

    /**
     *  当处理二进制数据的时候，使用Streams
     *
     *  FileWriter和BufferedWriter用于将文本写入文件，但是当您需要将原始流数据写入文件时，
     *  您应该使用FileOutputStream
     */
    @Test
    public void writeUsingOutputStream() {
        String data = "I will write this String to File in Java";
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(FileUtils.FILE_BASE_PATH + "outputStream.txt"));
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
     * 使用1.7的Files类，内部使用了OutputStream
     */
    @Test
    public void writeUsingFiles() {
        String data = "I will write this String to File in Java";
        try {
            Files.write(Paths.get(FileUtils.FILE_BASE_PATH + "files.txt"), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当写操作的数量比较多的时候使用 BufferedWriter
     * 它使用内部的buffer来减少真正的IO操作，来节省时间
     *
     * BufferedWriter几乎与FileWriter相似，但它使用内部缓冲区将数据写入File。
     * 因此，如果写入操作的数量更多，则实际的IO操作会更少，性能也会更好。
     * 当写操作次数更多时，您应该使用BufferedWriter
     */
    @Test
    public void writeUsingBufferedWriter() {
        String data = "I will write this String to File in Java";
        int noOfLines = 10000;

        File file = new File(FileUtils.FILE_BASE_PATH + "bufferedWriter.txt");
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
     * 当写操作的数量较少的时候使用FileWriter
     */
    @Test
    public void writeUsingFileWriter() {
        String data = "I will write this String to File in Java";
        File file = new File(FileUtils.FILE_BASE_PATH + "FileWriter.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
