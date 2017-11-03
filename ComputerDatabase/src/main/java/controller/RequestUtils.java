package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import validators.ValidationUtils;

public class RequestUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(ValidationUtils.DATE_FORMAT);

    /**
     * @param req req
     * @param id id
     * @param name name
     * @param introduced introduced
     * @param discontinued discontinued
     * @param companyId idCompany
     */
    public static void putBackAttributes(HttpServletRequest req, Long id, String name, LocalDate introduced,
            LocalDate discontinued, Long companyId) {
        req.setAttribute("id", id);
        req.setAttribute(ValidationUtils.COMPUTER_NAME, name);
        String introducedStr = getFormattedString(introduced);
        req.setAttribute(ValidationUtils.INTRODUCED, introducedStr);
        String discontinuedStr = getFormattedString(discontinued);
        req.setAttribute(ValidationUtils.DISCONTINUED, discontinuedStr);
        req.setAttribute(ValidationUtils.COMPANY_ID, companyId == null ? null : companyId.toString());
    }

    /**
     * @param req Request to fill for next .jsp form, keeping the values
     * @param name name
     * @param introduced introduced
     * @param discontinued discontinued
     * @param companyId companyId
     */
    public static void putBackAttributes(HttpServletRequest req, String name, String introduced, String discontinued,
            String companyId) {
        req.setAttribute(ValidationUtils.COMPUTER_NAME, name);
        req.setAttribute(ValidationUtils.INTRODUCED, introduced);
        req.setAttribute(ValidationUtils.DISCONTINUED, discontinued);
        req.setAttribute(ValidationUtils.COMPANY_ID, companyId.equals("--") ? null : companyId);
    }

    /**
     * @param req servletRequest
     * @param success success
     * @param msg msg
     */
    public static void showMsg(HttpServletRequest req, boolean success, String msg) {
        req.setAttribute("msg", msg);
        req.setAttribute("success", success);
    }

    /**
     * @param date localDate
     * @return string formatted as ValidationUtils.DATE_FORMAT
     */
    public static String getFormattedString(LocalDate date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DATE_FORMATTER;
        return date.format(formatter);
    }

}
