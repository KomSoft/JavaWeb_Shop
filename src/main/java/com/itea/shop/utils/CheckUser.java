package com.itea.shop.utils;

import com.itea.shop.entity.UserRegisteringData;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class CheckUser {
    private final String OK_MESSAGE = "<li><font color='#00FF00'>&#10004;</font></li>";
    private final String REGEXP_EMAIL = "^\\w+([\\.\\-_]?\\w+)*@(\\w+[\\.\\-_]?)+\\.[\\w+]{2,4}$";
    //      include lower, upper case letters, and minimum one 1 digit
    private final String REGEXP_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*\\d)(?!.*[\\s]).{8,}$";
    //      include lower, upper case letters, one special character #?!@$%^*&- and minimum 2 digits
//    private final String REGEXP_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*\\d[^\\d]?\\d)(?=.*?[#?!@$%^*&\\-])(?!.*[\\s]).{8,}$";
    private String login;
    private String password;
    private String fullName;
    private String region;
    private String gender;
    private String comment;
    private String agreement;
    private static StringBuilder errors;
    private boolean isCorrect;

    public CheckUser() {
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

    public CheckUser(HttpServletRequest request) {
        this.checkUserRegisteringData(request);
    }

    public static List<String> parseErrors() {
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
            if (!login.matches(REGEXP_EMAIL)) {
                isCorrect = false;
                errors.append("<li><font color='#FF0000'>Login has to be a correct e-mail address</font></li>");
            } else {
                errors.append(OK_MESSAGE);
            }
        }
        if (password == null || password.isEmpty()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>Password is empty!</font></li>");
        } else {
            if (!password.matches(REGEXP_PASSWORD)) {
                isCorrect = false;
                errors.append("<li><font color='#FF0000'>Password has to include lower, upper case letters, and minimum one 1 digit</font></li>");
            } else {
                errors.append(OK_MESSAGE);
            }
        }
        if (rePassword == null || rePassword.isEmpty()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>Re-password is empty!</font></li>");
        } else {
            if (!rePassword.matches(REGEXP_PASSWORD)) {
                isCorrect = false;
                errors.append("<li><font color='#FF0000'>Password has to include lower, upper case letters, and minimum one 1 digit</font></li>");
            } else {
                if (!rePassword.equals(password)) {
                    isCorrect = false;
                    errors.append("<li><font color='#FF0000'>Passwords don't match!</font></li>");
                } else {
                    errors.append(OK_MESSAGE);
                }
            }
        }
        if (fullName == null || fullName.isBlank()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>Name is blank!</font></li>");
        } else {
            fullName = fullName.trim();
            errors.append(OK_MESSAGE);
        }
        if (region == null || region.isEmpty()) {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>A region need to be selected!</font></li>");
        } else {
            errors.append(OK_MESSAGE);
        }
        if (gender == null || gender.isEmpty()) {
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
        if ("ON".equalsIgnoreCase(agreement)) {
            errors.append(OK_MESSAGE);
        } else {
            isCorrect = false;
            errors.append("<li><font color='#FF0000'>Please mark 'Glory to Ukraine'</font></li>");
        }
        errors.append("</ul>");
        return getUserRegisteringData();
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
