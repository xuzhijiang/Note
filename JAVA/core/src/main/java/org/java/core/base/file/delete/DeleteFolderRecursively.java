package org.java.core.base.file.delete;

import java.io.File;

/**
 * This utility class can be used to delete 
 * folders recursively in java
 * <p><br>
 * 在这里，我们将学习如何以递归方式删除java中的非空目录/文件夹。 java.io.File delete（）
 * 方法删除了一个文件或一个空目录，但如果目录不为空，则不删除它并返回false。
 * 我们将递归地使用delete（）函数来删除java程序中的目录/文件夹。
 */
public class DeleteFolderRecursively {
    public static void main(String[] args) {
        String folder = "C:\\Users\\a\\Desktop\\t";
        //delete folder recursively
        recursiveDelete(new File(folder));
    }
    
    public static void recursiveDelete(File file) {
        //to end the recursive loop
        if (!file.exists())
            return;
        
        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                recursiveDelete(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
        System.out.println("Deleted file/folder: "+file.getAbsolutePath());
    }
}
