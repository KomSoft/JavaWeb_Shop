package com.itea.shop.form;

public class MenuForm {
    public static final String LOGOUT_KEY = "logoutKey";
    private static final String MENU = "<center>" +
            "<a href='\\secretpage'>Authorized users only</a> - " +
            "<a href='\\registration'>Registration</a> - " +
            "<a href='\\login'>Login</a>" +
            "%s" +
            "</center><p></p>";

    private static final String MENU_LOGOUT = " - <a href='\\login?" + LOGOUT_KEY + "'>Logout</a>";

    public static String getMenu(boolean withLogout) {
        return String.format(MENU, withLogout ? MENU_LOGOUT : "");
    }

}
