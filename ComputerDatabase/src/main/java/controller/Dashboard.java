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

import mapper.exceptions.PageException;
import model.Computer;
import model.pages.Page;
import persistence.exceptions.DaoException;
import service.ComputerServiceImpl;
import validators.ValidationUtils;

@WebServlet
public class Dashboard extends HttpServlet {
    private static final String DASHBOARD_JSP_PATH = "/WEB-INF/pages/dashboard.jsp";

    private static final long serialVersionUID = 1L;

    private static final Long DEFAULT_PAGESIZE = 10L;
    private static final Long DEFAULT_STARTING_PAGE = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);
    private ComputerServiceImpl computerService;

    /**
     * Default constructor.
     */
    public Dashboard() {
        computerService = ComputerServiceImpl.getInstance();
    }

    /**
     * {@inheritDoc}
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     * @param request request
     * @param response response
     * @throws ServletException request
     * @throws IOException exception
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paramMsg = request.getParameter("successDelete");
        if (paramMsg != null && !paramMsg.isEmpty()) {
            RequestUtils.showMsg(request, true, "Company Sucessfully deleted : " + paramMsg);
        }

        loadDashboard(request, response);
    }

    /**
     * @param request request
     * @param response response
     * @throws ServletException could not load
     * @throws IOException could not load
     */
    private void loadDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long pageSize = retrivePageSize(request);
        Long currentPage = ValidationUtils.retrieveLong(request.getParameter("page"), DEFAULT_STARTING_PAGE);

        Page<Computer> page = getPage(currentPage, pageSize);
        List<Computer> computerDtos = null;

        if (page != null) {
            computerDtos = page.getContent();
        }

        request.setAttribute("computers", computerDtos);
        request.setAttribute("page", page);
        request.getRequestDispatcher(DASHBOARD_JSP_PATH).forward(request, response);
    }

    /**
     * @param request request containing params and current session to recover
     *            current pagination parametered
     * @return the page size to use to paginate current-page
     */
    private Long retrivePageSize(HttpServletRequest request) {
        String param = request.getParameter("pagination");

        if (param != null) {
            Long pagination = ValidationUtils.retrieveLong(param, DEFAULT_PAGESIZE);
            request.getSession().setAttribute("pagination", pagination);
            return pagination;
        }

        Long pagination = (Long) request.getSession().getAttribute("pagination");
        if (pagination != null) {
            request.setAttribute("pagination", pagination);
            return pagination;
        }

        request.getSession().setAttribute("pagination", DEFAULT_PAGESIZE);
        return DEFAULT_PAGESIZE;
    }

    /**
     * @param pageNumber page to get
     * @param pageSize page size
     * @return return the page
     */
    private Page<Computer> getPage(Long pageNumber, Long pageSize) {
        try {

            Page<Computer> computers = computerService.getComputerPage(pageNumber, pageSize);
            computers.load();
            return computers;

        } catch (DaoException | PageException e) {

            String msg = String.format("Computer Page (%d) couldn't be loaded, reason \"%s\"", pageNumber,
                    e.getMessage());
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     * @param req request
     * @param resp response
     * @throws ServletException request
     * @throws IOException exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] selection = req.getParameterValues("selection");

        if (selection == null || selection.length <= 0) {
            RequestUtils.showMsg(req, false, "please provide ids for deletion");

        } else {

            List<Long> ids = new ArrayList<Long>();
            Boolean result = ValidationUtils.isLongList(selection, ids);

            if (result) {
                try {
                    computerService.deleteComputers(ids);
                    RequestUtils.showMsg(req, true, "Success, " + ids.size() + " ids deleted");
                } catch (DaoException e) {
                    String msg = "failed to execute deletion, reason :" + e.getMessage();
                    RequestUtils.showMsg(req, false, msg);
                    LOGGER.error(msg);
                }
            } else {
                RequestUtils.showMsg(req, false, "all ids are not valid");
            }
        }

        loadDashboard(req, resp);
    }
}
