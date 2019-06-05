package org.java.core.base.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 可以使用Java中的Reader或Stream读取文件。 Reader最适合用于文本数据，
 * 但要使用二进制数据，您应该使用Stream。 FileInputStream用于打开流以从文件中读取数据。
 * 这里我们将把InputStream转换为java中的文件，我们将使用OutputStream来编写新文件。
 */
public class InputStreamToFile {

    public static void main(String[] args) {
        try {
            InputStream is = new FileInputStream("C:\\Users\\a\\Desktop\\test\\source.txt");
            
            OutputStream os = new FileOutputStream("C:\\Users\\a\\Desktop\\test\\new_source.txt");
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            //read from is to buffer
            while((bytesRead = is.read(buffer)) !=-1){
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            //flush OutputStream to write any buffered data to file
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
