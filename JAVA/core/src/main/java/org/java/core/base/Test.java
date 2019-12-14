package org.java.core.base;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws IOException {
        System.out.println(StandardCharsets.UTF_8);
        System.out.println(StandardCharsets.UTF_8.name());
        System.out.println(Charset.forName("UTF-8"));
    }

    private static void nba() throws IOException {
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
