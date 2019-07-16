package com.android.core.util;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 3DES加密
 */
public class ThreeDESUtils {

    /**
     * 定义 加密算法,可用 DES,DESede,Blowfish
     */
    private static final String ALGORITHM = "DESede";
    /**
     * 密钥
     */
    private static final byte[] KEY = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x70, 0x52, (byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x31, 0x42, 0x36, (byte) 0xE2 };

    /**
     * 加密
     *
     * @param key
     *            密钥字符串
     * @param src
     *            需要加密的字节数组
     * @return
     */
    public static byte[] encryptMode(String key, byte[] src) {
        key = filledKey(key);
        return encryptMode(key.getBytes(), src);
    }

    /**
     * 解密
     *
     * @param key
     *            密钥 字符串
     * @param src
     *            加密后的字节数组
     * @return
     */
    public static byte[] decryptMode(String key, byte[] src) {
        key = filledKey(key);
        return decryptMode(key.getBytes(), src);
    }

    /**
     * 当密钥串不足24字节的后面用字符0补齐
     *
     * @param key
     *            原始密码串
     * @return
     */
    private static String filledKey(String key) {
        if (key != null) {
            if (key.length() < 24) {
                StringBuffer keyStringBuffer = new StringBuffer();
                keyStringBuffer.append(key);
                for (int i = key.length(); i < 24; i++) {
                    keyStringBuffer.append(new String(new byte[] { 0x00 }));
                }
                key = keyStringBuffer.toString();
                return key;
            } else if (key.length() == 24) {
                return key;
            } else {
                StringBuffer msgStringBuffer = new StringBuffer();
                msgStringBuffer.append("the key ");
                msgStringBuffer.append(key);
                msgStringBuffer.append(" is invalid!");
                msgStringBuffer.append("the key length must be 24 bytes.");
                throw new RuntimeException(msgStringBuffer.toString());
            }
        } else {
            throw new RuntimeException("the key is null");
        }
    }

    /**
     * 使用默认密钥加密
     *
     * @param src
     *            需要加密的字节数组
     * @return
     */
    public static byte[] encryptMode(byte[] src) {
        return encryptMode(KEY, src);
    }

    /**
     * 使用默认密钥解密
     *
     * @param src
     *            加密后的字节数组
     * @return
     */
    public static byte[] decryptMode(byte[] src) {
        return decryptMode(KEY, src);
    }

    /**
     * 加密
     *
     * @param keybyte
     *            密钥
     * @param src
     *            需要加密的字节数组
     * @return
     */
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            if (isValidKey(keybyte)) {
                SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
                Cipher c1 = Cipher.getInstance(ALGORITHM);
                c1.init(Cipher.ENCRYPT_MODE, deskey);
                return c1.doFinal(src);
            }
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param keybyte
     *            加密密钥 长
     * @param src
     *            加密后的字节数组
     * @return
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            if (isValidKey(keybyte)) {
                SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
                Cipher c1 = Cipher.getInstance(ALGORITHM);
                c1.init(Cipher.DECRYPT_MODE, deskey);
                return c1.doFinal(src);
            }
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    private static boolean isValidKey(byte[] keybyte) {
        if (keybyte.length == 24) {
            return true;
        } else {
            StringBuffer msg = new StringBuffer();
            msg.append("Invalid key length: ");
            msg.append(keybyte.length);
            msg.append(" bytes;the key length must be 24 bytes.");
            throw new RuntimeException(msg.toString());
        }
    }

    /**
     * 转换为十六进制串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    public static void main(String[] args) {
        // String szSrc = "hahahaha";
        // System.out.println("加密前的字符串:" + szSrc);
        // byte[] encoded = encryptMode(szSrc.getBytes());
        // System.out.println("加密后的字符串:" + new String(encoded));
        // System.out.println(byte2hex(szSrc.getBytes()));
        // byte[] srcBytes = decryptMode(encoded);
        // System.out.println("解密后的字符串:" + (new String(srcBytes)));
        // String key = "123456789012345678901234";
        // key = filledKey(key);
        // byte[] encode = encryptMode(key.getBytes(), szSrc.getBytes());
        // System.out.println(new String(decryptMode(key.getBytes(), encode)));
        String str = "93242250$D6D0B9FAB5E7D0C5C9CFBAA3D4BAD6C6$hwceshi05$004101FF0001101001E2000763E5F464$172.16.6.185$00:07:63:DD:B3:5B$$CTC";
        String key = "123456780000000000000000";
        byte[] encode = encryptMode(key, str.getBytes());
        System.out.println(new String(decryptMode(key, encode)));
    }

}
