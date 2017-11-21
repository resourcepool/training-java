package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import model.Computer;
import model.ComputerDto;
import service.ICompanyService;
import service.IComputerService;
import validators.ComputerValidator;
import validators.ValidationUtils;

@WebServlet
public class EditComputer extends HttpServlet {

    private static final String COMPUTER_FORM_JSP = "/WEB-INF/pages/computer_form.jsp";
    private static final String ID_IS_NOT_VALID = "id is not valid";
    private static final long serialVersionUID = -7371267190245615780L;

    private ICompanyService companyService;
    private IComputerService computerService;

    /**
     * @param config ServletConfig
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     * @throws ServletException exception
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.companyService = (ICompanyService) wc.getBean("companyService");
        this.computerService = (IComputerService) wc.getBean("computerService");
    }

    /**
     * @param req current request
     * @param resp response
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!loadComputer(req)) {
            resp.sendRedirect("addComputer");
            return;
        }

        loadPage(req, resp);
    }

    /**
     * @param req current request
     * @param resp response
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ComputerValidator v = new ComputerValidator();
        ComputerDto dto = new ComputerDto(req);
        if (!v.validate(dto)) {

            RequestUtils.showMsg(req, false, "Computer cannot be edited, ");
            req.setAttribute("errors", v.getErrors());

        } else {

            Computer c = new Computer(v.getId(), dto.getName(), v.getIntroduced(), v.getDiscontinued(), v.getCompanyId());
            computerService.update(c);
            RequestUtils.showMsg(req, true, "SUCCESS: Computer \"" + c.getName() + "\" successfully edited (id=" + v.getId() + ")");

        }

        RequestUtils.putBackAttributes(req, dto);
        loadPage(req, resp);
    }

    /**
     * Calls the matching jsp.
     * @param req req
     * @param resp resp
     * @throws ServletException could not be loaded
     * @throws IOException could not be loaded
     */
    private void loadPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        loadCompanies(req);
        req.setAttribute("edit", true);
        req.getRequestDispatcher(COMPUTER_FORM_JSP).forward(req, resp);
    }

    /**
     * @param req servletRequest
     * @return successfully loaded
     */
    private Boolean loadComputer(HttpServletRequest req) {
        String idStr = req.getParameter("id");

        if (!ValidationUtils.isLong(idStr)) {
            RequestUtils.showMsg(req, false, ID_IS_NOT_VALID);
            return false;
        }

        Long id = Long.parseLong(idStr);
        Computer c = computerService.getDetail(id);
        RequestUtils.putAttributes(req, id, c.getName(), c.getIntroduced(), c.getDiscontinued(), c.getCompany().getId());

        return true;
    }

    /**
     * @param req servletRequest
     */
    private void loadCompanies(HttpServletRequest req) {
        req.setAttribute("companies", companyService.getList());
    }

}
