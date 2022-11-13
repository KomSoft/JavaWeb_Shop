package com.itea.shop;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainRunner {

    public static void main(String[] args) {
/*
        PostgreSQLJDBC testDB = new PostgreSQLJDBC();
        System.out.println("Establishing connection to DB");
        testDB.establishConnection();
        System.out.println("Error: " + testDB.getError());
        String fullName = testDB.getFullNameByLoginAndPassword("admin@com.com", "admin");
        System.out.println( "fullName = " + fullName);
*/

/*
        System.out.println("Get by login Error: " + testDB.getError());
        testDB.getFullNameByLoginAndPassword("administrator");
        System.out.println("Get by login Error: " + testDB.getError());
        testDB.getFullNameByLoginAndPassword("admin");
        System.out.println("Get by login Error: " + testDB.getError());
*/
//        testDB.closeConnection();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(StandardCharsets.UTF_8.encode("admin"));
            System.out.println(String.format("%032x", new BigInteger(messageDigest.digest())));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(StandardCharsets.UTF_8.encode("admin"));
            System.out.println(String.format("%064x", new BigInteger(1, messageDigest.digest())));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
