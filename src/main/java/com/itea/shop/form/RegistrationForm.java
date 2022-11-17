package com.itea.shop.form;

import com.itea.shop.entity.AuthorizedUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Formatter;
import java.util.List;

public class RegistrationForm {
    private static final int FIELD_COUNT = 8;
    private static final String[] errors = new String[FIELD_COUNT];

    private static void getErrors(String errorString) {
        List<String> errorsList = AuthorizedUser.parseErrors(errorString);
        for (int i = 0; i < FIELD_COUNT; i++) {
            errors[i] = "";
        }
        int size = errorsList == null ? 0 : Math.min(errorsList.size(), FIELD_COUNT);
//        int size = errorsList == null ? 0 : (errorsList.size() < FIELD_COUNT ? errorsList.size() : FIELD_COUNT);
        for (int i = 0; i < size; i++) {
            errors[i] = errorsList.get(i);
        }
    }

    public static final String REGISTRATION_FORM = "<center><table border='0'><form action='' method='POST'>" +
            "<tr>" +
            "   <td width='150'>Login</td>" +
            "   <td width='200'><input type='email' name='login' value='%9$s' /></td>" +
            "   <td width='250'>%1$s</td>" +
            "</tr>" +
            "<tr>" +
            "   <td>Password</td>" +
            "   <td><input type='password' name='password' /></td>" +
            "   <td>%2$s</td>" +
            "</tr>" +
            "<tr>" +
            "   <td>Re-Password</td>" +
            "   <td><input type='password' name='rePassword' /></td>" +
            "   <td>%3$s</td>" +
            "</tr>" +
            "<tr>" +
            "   <td>Name</td>" +
            "   <td><input type='text' name='fullName' value='%10$s' /></td>" +
            "   <td>%4$s</td>" +
            "</tr>" +
            "<tr>" +
            "   <td>Region</td>" +
            "   <td><select name='region'>" +
            "      <option value=''>Select your region</option>" +
            "      <option value='Kyiv' %15$s >Kyiv region</option>" +
            "      <option value='Lviv' %16$s >Lviv region</option>" +
            "      <option value='Odesa' %17$s >Odesa region</option>" +
            "      </select></td>" +
            "   <td>%5$s</td>" +
            "</tr>" +
            "<tr>" +
            "   <td>Gender</td>" +
            "   <td>F<input type='radio' name='gender' value='F' %11$s />" +
            "       M<input type='radio' name='gender' value='M' %12$s />" +
            "   </td>" +
            "   <td>%6$s</td>" +
            "</tr>" +
            "<tr>" +
            "   <td>Comment</td>" +
            "   <td><textarea name='comment' cols='21' rows='5'>%13$s</textarea></td>" +
            "   <td>%7$s</td>" +
            "</tr>" +
            "<tr>" +
            "   <td>Glory to Ukraine</td>" +
            "   <td><input type='checkbox' name='agreement' %14$s /></td>" +
            "   <td>%8$s</td>" +
            "</tr>" +
            "<tr>" +
            "   <td>&nbsp;</td>" +
            "   <td align='center'><input type='submit' value='Send' /></td>" +
            "   <td>&nbsp;</td>" +
            "</tr>" +
            "</table></form></center>";

    public static String fill(HttpServletRequest request) {
        final String CHECKED = "checked";
        final String SELECTED = "selected";
        String[] regions = new String[3];
        getErrors((String) request.getAttribute("errors"));
        Formatter formatter = new Formatter();
        String login = request.getParameter("login") != null ? request.getParameter("login") : "";
        String fullName = request.getParameter("fullName") != null ? request.getParameter("fullName") : "";
        String genderF = "F".equalsIgnoreCase(request.getParameter("gender")) ? CHECKED : "";
        String genderM = "M".equalsIgnoreCase(request.getParameter("gender")) ? CHECKED : "";
        String comment = request.getParameter("comment") != null ? request.getParameter("comment") : "";
        String agreement = "on".equalsIgnoreCase(request.getParameter("agreement")) ? CHECKED : "";
        regions[0] = "Kyiv".equals(request.getParameter("region")) ? SELECTED : "";
        regions[1] = "Lviv".equals(request.getParameter("region")) ? SELECTED : "";
        regions[2] = "Odesa".equals(request.getParameter("region")) ? SELECTED : "";
        formatter.format(REGISTRATION_FORM, errors[0], errors[1], errors[2], errors[3], errors[4], errors[5], errors[6], errors[7],
                login, fullName, genderF, genderM, comment, agreement, regions[0], regions[1], regions[2]);
        return formatter.toString();
    }
}
