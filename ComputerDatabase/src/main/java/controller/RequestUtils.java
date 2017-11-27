package controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.pages.Page;
import validators.ValidationUtils;

public class RequestUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(ValidationUtils.DATE_FORMAT);
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtils.class);

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

    /**
     * @param req req to fill
     * @param page page with the request
     */
    public static void buildPageParams(HttpServletRequest req, Page<?> page) {
        StringBuilder pageParam = new StringBuilder();
        String sortParams = null;
        try {
            if (page.getSearch() != null) {
                sortParams = "&search=" + URLEncoder.encode(page.getSearch(), "UTF-8");
                pageParam.append(sortParams);
            }

            if (page.getFormSort() != null) {
                String sort = "sort=" + URLEncoder.encode(page.getFormSort(), "UTF-8");
                pageParam.append('&' + sort);
            }

            if (page.getOrder() != null) {
                String order = "&order=" + page.getOrder().toString();
                pageParam.append(order);
            }

        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Encoding url failed { }", e);
        }
        req.setAttribute("sortparams", sortParams);
        req.setAttribute("pageparams", pageParam.toString());
    }
}
