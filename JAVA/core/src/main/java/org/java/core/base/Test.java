package org.java.core.base;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\projects\\CommonRepo\\english-repo\\NBA\\A-F.md");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        Map<String, String> map = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            if (line.length() > 0) {
                System.out.println(line);
                System.out.println();
            }
        }
        reader.close();
    }

}
