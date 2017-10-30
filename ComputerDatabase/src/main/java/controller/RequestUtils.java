package controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import mapper.DateToStringFormatted;
import validators.ComputerValidator;

public class RequestUtils {

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
        req.setAttribute(ComputerValidator.COMPUTER_NAME, name);
        String introducedStr = DateToStringFormatted.getFormattedString(introduced);
        req.setAttribute(ComputerValidator.INTRODUCED, introducedStr);
        String discontinuedStr = DateToStringFormatted.getFormattedString(discontinued);
        req.setAttribute(ComputerValidator.DISCONTINUED, discontinuedStr);
        req.setAttribute(ComputerValidator.COMPANY_ID, companyId.toString());
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
        req.setAttribute(ComputerValidator.COMPUTER_NAME, name);
        req.setAttribute(ComputerValidator.INTRODUCED, introduced);
        req.setAttribute(ComputerValidator.DISCONTINUED, discontinued);
        req.setAttribute(ComputerValidator.COMPANY_ID, companyId);
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

}
