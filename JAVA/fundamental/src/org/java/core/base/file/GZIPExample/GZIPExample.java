package org.java.core.base.file.GZIPExample;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP是在Unix系统中压缩文件的最受欢迎的工具之一。 
 * 我们可以压缩GZIP格式的单个文件，但是我们不能使用
 * GZIP来压缩和存档目录像ZIP文件这样。
 * 
 * 这是一个简单的java GZIP示例程序，显示我们如何将文件压缩为GZIP格式，
 * 然后解压缩GZIP文件以创建新文件。
 * 
 * 在解压缩GZIP文件时，如果它不是GZIP格式，则会抛出以下异常。
 */
public class GZIPExample {

    public static void main(String[] args) {
        String file = "/Users/pankaj/sitemap.xml";
        String gzipFile = "/Users/pankaj/sitemap.xml.gz";
        String newFile = "/Users/pankaj/new_sitemap.xml";
        
        compressGzipFile(file, gzipFile);
        
        decompressGzipFile(gzipFile, newFile);
               
    }

    private static void decompressGzipFile(String gzipFile, String newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private static void compressGzipFile(String file, String gzipFile) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
