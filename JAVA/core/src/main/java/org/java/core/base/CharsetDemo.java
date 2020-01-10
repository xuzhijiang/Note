package org.java.core.base;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharsetDemo {

    public static void main(String[] args) throws IOException {
        System.out.println(StandardCharsets.UTF_8);
        System.out.println(StandardCharsets.UTF_8.name());
        System.out.println(Charset.forName("UTF-8"));
    }

}
