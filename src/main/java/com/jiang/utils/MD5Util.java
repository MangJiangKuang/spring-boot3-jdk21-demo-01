package com.jiang.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String encrypt(String plainText) {
        if (plainText == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(plainText.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 加密失败", e);
        }
    }

    public static boolean verify(String plainText, String md5Hash) {
        if (plainText == null || md5Hash == null) {
            return false;
        }
        return md5Hash.equals(encrypt(plainText));
    }
    //生成salt值
    public static String generateSalt() {
        // 这里可以使用更复杂的逻辑来生成盐值
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
    public static void main(String[] args) {
        String s = generateSalt();
        System.out.println("salt: " + s);
        String password ="123456"+s;
        String md5Hash = encrypt(password);
        System.out.println("MD5 Hash: " + md5Hash);
        System.out.println("Verification: " + verify(password, md5Hash));
        System.out.println("Verification with wrong password: " + verify("wrongPassword", md5Hash));
    }
}
