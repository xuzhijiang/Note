package org.java.core.base.file;

import java.io.File;
import java.util.Date;

/**
 * Sometimes we need to get the file last modified date in java, 
 * usually for listeners like JBoss config file changes hot deployment.
 *  java.io.File class lastModified() returns last modified date in long, 
 *  we can construct date object in human readable format with this time.
 */
public class FileDate {
	public static void main(String[] args) {
        File file = new File("bin");
        
        long timestamp = file.lastModified();
        //If file doesn¡¯t exists, lastModified() returns 0L
        System.out.println("timestamp: " + timestamp);
        System.out.println("employee.xml last modified date = "+new Date(timestamp));
    }
}
