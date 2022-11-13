package com.itea.shop.form;

public class Messages {
    public static final String ACCESS_GRANTED = "<h2 align='center'><font color='#0000FF'>Access granted. Hello, %s!</font></h2>";
    public static final String ACCESS_DENIED = "<center><h2><font color='#0000FF'>Access denied</font></h2><br>You have %d attempt(s)<center>";
    public static final String BLOCKED_FOR_TIMEOUT = "<center>You are blocked for %d seconds!</center>";
    public static final String WAIT_FOR_TIMEOUT = "<center>Wait %d second(s) to try again</center>";
    public static final String CANT_CONNECT_DB = "Can't connect to DataBase (%s). Try later.";

/*
    public static String getAccessGrantedForName(String name) {
        return String.format(ACCESS_GRANTED, name);
    }

    public static String getAccessDenied(int tries) {
        return String.format(ACCESS_DENIED, tries);
    }

    public static String getAccessDenied(int tries) {
        return String.format(ACCESS_DENIED, tries);
    }
*/

}
