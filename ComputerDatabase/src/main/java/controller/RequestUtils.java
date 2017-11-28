package controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.ComputerDto;
import model.pages.Page;
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
    public static void putAttributes(HttpServletRequest req, Long id, String name, LocalDate introduced,
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
     * @param req req to fill
     * @param dto dto to use
     */
    public static void putBackAttributes(HttpServletRequest req, ComputerDto dto) {

        if (dto.getId() != null) {
            req.setAttribute(ValidationUtils.ID, dto.getId());
        }

        req.setAttribute(ValidationUtils.COMPUTER_NAME, dto.getName());
        req.setAttribute(ValidationUtils.INTRODUCED, dto.getIntroduced());
        req.setAttribute(ValidationUtils.DISCONTINUED, dto.getDiscontinued());

        String companyId = dto.getCompanyId();
        req.setAttribute(ValidationUtils.COMPANY_ID, companyId == null || companyId.equals("--") ? null : companyId);
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
     * @param r RedirectAttributes
     * @param success success
     * @param msg msg
     */
    public static void showMsg(RedirectAttributes r, boolean success, String msg) {
        r.addFlashAttribute("msg", msg);
        r.addFlashAttribute("success", success);
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
     * @param req request
     */
    public static void printParameters(HttpServletRequest req) {
        Enumeration<String> params =  req.getParameterNames();
        while (params.hasMoreElements()) {
            System.out.println(params.nextElement());
        }
    }

    /**
     * @param model model to fill
     * @param page page with the request
     */
    public static void buildPageParams(ModelMap model, Page<?> page) {
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
            throw new AssertionError("UTF-8 is unknown");
        }
        model.addAttribute("sortparams", sortParams);
        model.addAttribute("pageparams", pageParam.toString());
    }




}
