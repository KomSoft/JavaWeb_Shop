package com.itea.shop.entity;

import com.itea.shop.utils.Encoding;

public class UserRegisteringData {
    private final String login;
    private final String encryptedPassword;
    private final String fullName;
    private final String region;
    private final String gender;
    private final String comment;

    public UserRegisteringData(String login, String password, String fullName, String region, String gender, String comment) {
        this.login = login;
        this.encryptedPassword = this.encryptPassword(password);
        this.fullName = fullName;
        this.region = region;
        this.gender = gender;
        this.comment = comment;
    }

    public String getLogin() {
        return login;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRegion() {
        return region;
    }

    public String getGender() {
        return gender;
    }

    public String getComment() {
        return comment;
    }

    public static String encryptPassword(String password) {
        return Encoding.md5EncryptionWithSalt(password);
    }

}
