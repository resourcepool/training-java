package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dtos.ComputerDto;
import mapper.ComputerMapper;
import mapper.exceptions.PageException;
import mapper.pages.Page;
import model.Computer;
import persistence.exceptions.DaoException;
import service.ComputerServiceImpl;

/**
 * Servlet implementation class Dashboard.
 */
@WebServlet
public class Dashboard extends HttpServlet {
    private static final long          serialVersionUID = 1L;
    private static final Logger        LOGGER           = LoggerFactory.getLogger(Dashboard.class);
    private static ComputerServiceImpl computerService;

    /**
     * Default constructor.
     */
    public Dashboard() {
        computerService = ComputerServiceImpl.getInstance();
    }

    /**
     * {@inheritDoc}
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * @param request request
     * @param response response
     * @throws ServletException request
     * @throws IOException exception
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long pageNumber = retriveLongParameter(request, "page", 1L);
        Long pageSize = retriveLongParameter(request, "pagination", 10L);

        Page<Computer> page = getPage(pageNumber, pageSize);
        List<ComputerDto> computerDtos = new ComputerMapper().createDtos(page.getContent());

        request.setAttribute("computers", computerDtos);
        request.setAttribute("page", page);
        request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
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
            LOGGER.error("Computer Page (X/X) couldn't be loaded, reason \"" + e.getMessage() + "\"");
            return null;
        }
    }

    /**
     * @param request currently executed page request
     * @param paramName name to load/retrieve from request parameters
     * @param defaultValue value to return if @paramName doesn't exists
     * @return the selected parameter from the request params
     */
    private Long retriveLongParameter(HttpServletRequest request, String paramName, Long defaultValue) {
        String param = request.getParameter(paramName);
        if (!isLong(param)) {
            request.setAttribute(paramName, defaultValue);
            return defaultValue;
        }
        return Long.parseLong(param);
    }

    /**
     * @param s String to check
     * @return true is containing only digit and could call Long.parseLong without throw
     */
    private boolean isLong(String s) {
        if (s == null) {
            return false;
        }

        int size = s.length();
        for (int i = 0; i < size; i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * @param request request
     * @param response response
     * @throws ServletException request
     * @throws IOException exception
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
