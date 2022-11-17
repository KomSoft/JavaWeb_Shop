package com.itea.shop.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Encoding {
    private static final String SALT = "com.itea";
    static Logger logger = Logger.getLogger(Encoding.class.getName());
    public static String md5Encryption(String inputData) {
        String result = null;
        try {
            System.out.println("Input data: " + inputData);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(StandardCharsets.UTF_8.encode(inputData));
            result = String.format("%032x", new BigInteger(messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, String.format("[Encoding] %s", e.getMessage()));
        }
        return result;
    }

    public static String sha512Encryption(String inputData) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(StandardCharsets.UTF_8.encode(inputData));
            result = String.format("%064x", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, String.format("[Encoding] %s", e.getMessage()));
        }
        return result;
    }

    public static String md5EncryptionWithSalt(String inputData) {
        return md5Encryption(inputData + SALT);
    }

    public static String sha512EncryptionWithSalt(String inputData) {
        return sha512Encryption(inputData + SALT);
    }
}
