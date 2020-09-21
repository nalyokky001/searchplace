package com.kabang.searchplace.common;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Component
public class EncryptHelper {

    public static String encrypt(String password, String salt) {
        return encrypt(password, salt.getBytes());
    }

    public static String encrypt(String password, byte[] saltByte) {

        String result = "";
        byte[] passwordByte = password.getBytes();
        byte[] totalByte = new byte[passwordByte.length + saltByte.length];

        System.arraycopy(passwordByte, 0, totalByte, 0, passwordByte.length);
        System.arraycopy(saltByte, 0, totalByte, passwordByte.length, saltByte.length);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(totalByte);

            byte[] byteData = md.digest();

            StringBuilder sb = new StringBuilder();

            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xFF) + 256, 16).substring(1));
            }

            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String generateSalt() {
        Random rand = new Random();

        byte[] saltByte = new byte[8];
        rand.nextBytes(saltByte);

        StringBuilder sb = new StringBuilder();

        for (byte b : saltByte) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
