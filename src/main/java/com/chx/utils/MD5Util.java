package com.chx.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MD5Util {

    private MD5Util() {
    }

    public static String md5(String str) {
        return md5(str, null);
    }

    public static String md5(String str, String salt) {
        try {
            String target = salt == null || salt.isEmpty() ? str : str + salt;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(target.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5 加密失败", e);
        }
    }
}
