package com.itea.shop.entity;

import com.itea.shop.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AuthorizedUser {
    private final String OK_MESSAGE = "<li><font color='#00FF00'>&#10004;</font></li>";
    private static final String REGEXP_EMAIL = "^\\w+([\\.\\-_]?\\w+)*@(\\w+[\\.\\-_]?)+\\.[\\w+]{2,4}$";
    //      include lower, upper case letters, and minimum one 1 digit
    private static final String REGEXP_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*\\d)(?!.*[\\s]).{8,}$";
    //      include lower, upper case letters, one special character #?!@$%^*&- and minimum 2 digits
    //    private static final String REGEXP_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*\\d[^\\d]?\\d)(?=.*?[#?!@$%^*&\\-])(?!.*[\\s]).{8,}$";
    private String login;
    private String password;
    private String fullName;
    private String region;
    private String gender;
    private String comment;
    private String agreement;
    private static StringBuilder errors;
    private boolean isCorrect;

    public AuthorizedUser() {
        login = null;
        password = null;
        fullName = null;
        region = null;
        gender = null;
        comment = null;
        agreement = null;
        isCorrect = false;
        errors = null;
    }

    public AuthorizedUser(HttpServletRequest request) {
        this.checkUserRegisteringData(request);
    }

    public static List<String> parseErrors(String errors) {
        if (errors == null || errors.isEmpty()) {
            return null;
        } else {
            List<String> resultList = new ArrayList<>();
            int iBegin, iEnd;
            iBegin = errors.indexOf("<li>");
            iEnd = errors.indexOf("</li>", iBegin);
            while (iEnd > 0) {
                resultList.add(errors.substring(iBegin + 4, iEnd));
                iBegin = iEnd + 5;
                iEnd = errors.indexOf("</li>", iBegin);
            }
            return resultList;
        }
    }

    private static boolean isLoginCorrect(String email) {
        return email.matches(REGEXP_EMAIL);
    }

    public UserRegisteringData checkUserRegisteringData(HttpServletRequest request) {
        login = request.getParameter("login");
        password = request.getParameter("password");
        String rePassword = request.getParameter("rePassword");
        fullName = request.getParameter("fullName");
        region = request.getParameter("region");
        gender = request.getParameter("gender");
        comment = request.getParameter("comment");
        agreement = request.getParameter("agreement");
        isCorrect = true;
        errors = new StringBuilder("<ul>");
        if (login == null || login.isEmpty()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>Login is empty!</font></li>");
        } else {
            if (isCorrect = login.matches(REGEXP_EMAIL)) {
                errors.append(OK_MESSAGE);
            } else {
                errors.append("<li><font color='#FF0000'>Login has to be a correct e-mail address</font></li>");
            }
        }
        if (password == null || password.isEmpty()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>Password is empty!</font></li>");
        } else {
            if (isCorrect = password.matches(REGEXP_PASSWORD)) {
                errors.append(OK_MESSAGE);
            } else {
                errors.append("<li><font color='#FF0000'>Password has to include lower, upper case letters, and minimum one 1 digit</font></li>");
            }
        }
        if (rePassword == null || rePassword.isEmpty()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>Re-password is empty!</font></li>");
        } else {
            if (isCorrect = rePassword.matches(REGEXP_PASSWORD)) {
                if (isCorrect = rePassword.equals(password)) {
                    errors.append(OK_MESSAGE);
                } else {
                    errors.append("<li><font color='#FF0000'>Passwords don't match!</font></li>");
                }
            } else {
                errors.append("<li><font color='#FF0000'>Password has to include lower, upper case letters, and minimum one 1 digit</font></li>");
            }
        }
        if (fullName == null || fullName.isBlank()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>Name is blank!</font></li>");
        } else {
            fullName = fullName.trim();
            errors.append(OK_MESSAGE);
        }
        if (region == null || region.isBlank()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>A region need to be selected!</font></li>");
        } else {
            errors.append(OK_MESSAGE);
        }
        if (gender == null || gender.isBlank()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>Gender isn't selected!</font></li>");
        } else {
            errors.append(OK_MESSAGE);
        }
        if (comment == null || comment.isBlank()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>Please write a comment</font></li>");
        } else {
            errors.append(OK_MESSAGE);
        }
        if (isCorrect = "ON".equalsIgnoreCase(agreement)) {
            errors.append(OK_MESSAGE);
        } else {
            errors.append("<li><font color='#FF0000'>Please mark 'Glory to Ukraine'</font></li>");
        }
        errors.append("</ul>");
        return getUserRegisteringData();
    }

    public static boolean isUserRegisteringDataCorrect(UserRegisteringData user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("[CheckUser] Object User is null");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || !isLoginCorrect(user.getLogin())) {
            throw new ValidationException("[CheckUser] User login is null, empty or isn't valid e-mail");
        }
        if (user.getEncryptedPassword() == null || user.getEncryptedPassword().isEmpty()) {
            throw new ValidationException("[CheckUser] User password is null or empty");
        }
        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new ValidationException("[CheckUser] User full name is null or empty");
        }
        if (user.getRegion() == null || user.getRegion().isBlank()) {
            throw new ValidationException("[CheckUser] User region is null or empty");
        }
        if (user.getGender() == null || user.getGender().isBlank()) {
            throw new ValidationException("[CheckUser] User gender is null or empty");
        }
        if (user.getComment() == null || user.getComment().isBlank()) {
            throw new ValidationException("[CheckUser] User comment is null or empty");
        }
        return true;
    }

    public UserRegisteringData getUserRegisteringData() {
        if (isCorrect) {
            return new UserRegisteringData(login, password, fullName, region, gender, comment);
        } else {
            return null;
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
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

    public String getAgreement() {
        return agreement;
    }

    public String getErrors() {
        return errors.toString();
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}