package com.itea.shop;

import com.itea.shop.utils.Encoding;

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

/*
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(StandardCharsets.UTF_8.encode("admin@com.com"));
            System.out.println(String.format("%032x", new BigInteger(messageDigest.digest())));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
*/
/*
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(StandardCharsets.UTF_8.encode("admin"));
            System.out.println(String.format("%064x", new BigInteger(1, messageDigest.digest())));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
*/
        System.out.println(Encoding.md5Encryption("admin@com.com"));
//        Md5(admin@com.com) = 32bf81e28acfd72a67e3447a17a2fc4c
        System.out.println(Encoding.md5Encryption("admin@com.com" + "com.itea"));
//        Md5(admin@com.comcom.itea) = c49021b227ed59045d443972fa6b1886
        System.out.println(Encoding.md5EncryptionWithSalt("admin@com.com"));

    }
}
