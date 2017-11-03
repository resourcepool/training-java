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

import mapper.ComputerMapping;
import model.Computer;
import model.pages.Order;
import model.pages.Page;
import persistence.exceptions.DaoException;
import service.CompanyServiceImpl;
import service.ComputerServiceImpl;
import service.ICompanyService;
import service.IComputerService;
import validators.ValidationUtils;

@WebServlet
public class Dashboard extends HttpServlet {
    private static final String DASHBOARD_JSP_PATH = "/WEB-INF/pages/dashboard.jsp";

    private static final long serialVersionUID = 1L;

    private static final Long DEFAULT_PAGESIZE = 20L;
    private static final Long DEFAULT_STARTING_PAGE = 1L;
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

            try {

                companyService.delete(Long.parseLong(id));
                String msg = "Sucessfully deleted company : n°" + id;
                RequestUtils.showMsg(req, true, msg);
                LOGGER.info(msg);

            } catch (DaoException e) {

                String msg = "failed to delete : " + e.getMessage();
                RequestUtils.showMsg(req, false, msg);
                LOGGER.error(msg);
            }
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
            try {
                computerService.deleteComputers(ids);
                RequestUtils.showMsg(req, true, "Success, " + ids.size() + " computer ids deleted");
            } catch (DaoException e) {
                String msg = "failed to execute deletion, reason :" + e.getMessage();
                RequestUtils.showMsg(req, false, msg);
                LOGGER.error(msg);
            }
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

        List<Computer> computerDtos = null;
        Page<Computer> page = getPage(req);

        if (page != null) {
            computerDtos = page.getContent();
            String params = DashboardLoader.buildParam(page);
            req.setAttribute("params", params);
        }

        req.setAttribute("computers", computerDtos);
        req.setAttribute("page", page);
        req.getRequestDispatcher(DASHBOARD_JSP_PATH).forward(req, resp);
    }

    /**
     * @param req request
     * @return return the page
     */
    private Page<Computer> getPage(HttpServletRequest req) {
        Long pageSize = ValidationUtils.retrieveLong(req.getParameter("pagination"), DEFAULT_PAGESIZE);
        Long pageNumber = ValidationUtils.retrieveLong(req.getParameter("page"), DEFAULT_STARTING_PAGE);
        String search = req.getParameter("search");
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");

        try {

            Page<Computer> page;
            if (sort != null && order != null) {
                Order o = order.equals(Order.ASC.toString()) ? Order.ASC : Order.DESC;
                ComputerMapping m = ComputerMapping.get(sort);
                page = computerService.getPageWithOrder(pageNumber, pageSize, m, o);
            } else if (search != null) {
                page = computerService.getPageWithSearch(pageNumber, pageSize, search);
            } else {
                page = computerService.getPage(pageNumber, pageSize);
            }

            return page.load();

        } catch (DaoException e) {

            String msg = String.format("Computer Page (%d) couldn't be loaded, reason \"%s\"", pageNumber,
                    e.getMessage());
            RequestUtils.showMsg(req, false, msg);
            LOGGER.error(msg);
            return null;
        }
    }
}
