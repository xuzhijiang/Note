package com.java.algorithm.uniqueId;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing.
 */
public class HashUtil {

    public static byte[] sha1AsBytes(String input) {
        return sha1AsBytes(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate SHA-1 as bytes.
     *
     * @param input Input as bytes.
     * @return Bytes.
     */
    public static byte[] sha1AsBytes(byte[] input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        return md.digest();
    }

}