package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Computer;
import model.pages.Page;
import service.CompanyServiceImpl;
import service.ComputerServiceImpl;
import service.ICompanyService;
import service.IComputerService;
import service.PageBuilder;
import validators.ValidationUtils;

@WebServlet
public class Dashboard extends HttpServlet {
    private static final String DASHBOARD_JSP_PATH = "/WEB-INF/pages/dashboard.jsp";
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);

    private IComputerService computerService;
    private ICompanyService companyService;

    /**
     * Default constructor.
     */
    public Dashboard() {
        computerService = ComputerServiceImpl.getInstance();
        companyService = CompanyServiceImpl.getInstance();
    }

    /**
     * {@inheritDoc}
     *
     * @see HttpServlet#doGet(HttpServletRequest req, HttpServletResponse resp)
     * @param req req
     * @param resp resp
     * @throws ServletException req
     * @throws IOException exception
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        loadDashboard(req, resp);
    }

    /**
     * {@inheritDoc}
     *
     * @see HttpServlet#doPost(HttpServletRequest req, HttpServletResponse resp)
     * @param req req
     * @param resp resp
     * @throws ServletException req
     * @throws IOException exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        handleComputerDeletion(req);
        handleCompanyDeletion(req);
        loadDashboard(req, resp);
    }

    /**
     * @param req req
     */
    private void handleCompanyDeletion(HttpServletRequest req) {
        String id = req.getParameter("company_id_delete");

        if (id == null) {
            return;
        }

        if (!ValidationUtils.isLong(id)) {
            RequestUtils.showMsg(req, false, "please provide a company id");
        } else {

            companyService.delete(Long.parseLong(id));
            String msg = "Sucessfully deleted company : n°" + id;
            RequestUtils.showMsg(req, true, msg);
            LOGGER.info(msg);
        }
    }

    /**
     * @param req req containing parameters of computers to delete ("selection") or
     *            none (ignored then)
     */
    private void handleComputerDeletion(HttpServletRequest req) {
        String[] computerSelection = req.getParameterValues("computer_selection_delete");

        if (computerSelection == null) {
            return;
        }

        List<Long> ids = new ArrayList<Long>();
        Boolean result = ValidationUtils.isLongList(computerSelection, ids);

        if (result) {
            computerService.deleteComputers(ids);
            RequestUtils.showMsg(req, true, "Success, " + ids.size() + " computer ids deleted");
            req.setAttribute("page", 1);
        } else {
            RequestUtils.showMsg(req, false, "all ids are not valid");
        }
    }

    /**
     * @param req req
     * @param resp resp
     * @throws ServletException could not load
     * @throws IOException could not load
     */
    private void loadDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PageBuilder<Computer> builder = new PageBuilder<Computer>();
        Page<Computer> page = computerService.loadPage(builder.with(req));

        if (page != null) {
            buildParams(req, page);
        }

        req.getRequestDispatcher(DASHBOARD_JSP_PATH).forward(req, resp);
    }

    /**
     * @param req req
     * @param page page
     */
    public static void buildParams(HttpServletRequest req, Page<Computer> page) {
        List<Computer> content = page.getContent();
        RequestUtils.buildPageParams(req, page);
        req.setAttribute("computers", content);
        req.setAttribute("page", page);
    }

}
