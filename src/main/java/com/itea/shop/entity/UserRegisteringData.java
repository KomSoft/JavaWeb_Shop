package com.itea.shop.entity;

import com.itea.shop.utils.Encoding;

public class UserRegisteringData {
    private String login;
    private String encryptedPassword;
    private String fullName;
    private String region;
    private String gender;
    private String comment;

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
