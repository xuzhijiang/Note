package org.java.core.base.file.DownloadFileFromURL;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * 今天我们将学习如何从java下载URL中的文件。 我们可以使用java.net.URL openStream（）方法
 * 从URL下载文件。 我们可以使用Java NIO Channels
 * 或Java IO InputStream从URL打开流,然后读取数据，然后将其保存到文件中。
 * 
 */
public class JavaDownloadFileFromURL {

    public static void main(String[] args) {
        String url = "https://www.journaldev.com/sitemap.xml";
        
        try {
            downloadUsingNIO(url, "/Users/pankaj/sitemap.xml");
            
            downloadUsingStream(url, "/Users/pankaj/sitemap_stream.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        // 使用URL.openStream方法来创建输入流(InputStream)
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        // 然后我们使用文件输出流(FileOutputStream)从输入流中读取数据并写入文件。
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        // 从URL打开的流数据创建字节通道。 然后使用文件输出流将其写入文件。
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

}
