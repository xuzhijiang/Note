package org.java.core.base.file;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class JavaOpenFile {
	
	public static String fileName = "C:\\Users\\a\\Desktop\\test\\progress.txt";
	public static String pdfFile = "C:\\Users\\a\\Desktop\\test\\a.pdf";

    public static void main(String[] args) throws IOException {
        //text file, should be opening in default text editor
        File file = new File(fileName);
        
        //first check if Desktop is supported by Platform or not
        // Desktop implementation is platform dependent
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }
        
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) {
        	desktop.open(file);
        }
        
        //let's try to open PDF file
        file = new File(pdfFile);
        if(file.exists()) {
        	desktop.open(file);
        }
    }

}
