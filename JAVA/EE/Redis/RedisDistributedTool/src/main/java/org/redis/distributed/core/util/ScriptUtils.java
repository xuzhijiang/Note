package org.redis.distributed.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class ScriptUtils {

    private static Logger logger = LoggerFactory.getLogger(ScriptUtils.class);

    /**
     * read lua script String
     */
    public static String getScript(String path) {
        StringBuilder sb = new StringBuilder();
        // 从类路径下读取的
        InputStream is = ScriptUtils.class.getClassLoader().getResourceAsStream(path);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            logger.error("read lua script exception", e);
        }

        return sb.toString();
    }
}
