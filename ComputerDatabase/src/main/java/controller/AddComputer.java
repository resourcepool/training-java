package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;
import model.Computer;
import persistence.exceptions.DaoException;
import service.CompanyServiceImpl;
import service.ComputerServiceImpl;
import validators.ComputerValidation;

@WebServlet
public class AddComputer extends HttpServlet {

    private static final long   serialVersionUID = -8465135918905858327L;
    private static final Logger LOGGER           = LoggerFactory.getLogger(Dashboard.class);

    private CompanyServiceImpl  companyService;
    private ComputerServiceImpl computerService;

    /**
     * ctor.
     */
    public AddComputer() {
        computerService = ComputerServiceImpl.getInstance();
        companyService = CompanyServiceImpl.getInstance();
    }

    /**
     * @param req current request
     * @param resp response
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        loadCompanies(req);
        req.getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
    }

    /**
     * @param req request to fill
     */
    private void loadCompanies(HttpServletRequest req) {
        List<Company> companies;

        try {
            companies = companyService.getCompanyList();
        } catch (DaoException e) {
            companies = new ArrayList<Company>();
            LOGGER.error("Companies list couldn't be loaded, reason \"" + e.getMessage() + "\"");
        }

        req.setAttribute("companies", companies);
        req.setAttribute("noCompany", ComputerValidation.NO_COMPANY);
    }

    /**
     * @param req current request
     * @param resp response
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter(ComputerValidation.COMPUTER_NAME);
        String introduced = req.getParameter(ComputerValidation.INTRODUCED);
        String discontinued = req.getParameter(ComputerValidation.DISCONTINUED);
        String companyId = req.getParameter(ComputerValidation.COMPANY_ID);

        ComputerValidation v = new ComputerValidation();
        if (!v.validate(name, introduced, discontinued, companyId)) {

            StringBuilder sb = new StringBuilder();
            Map<String, String> map = v.getErrors();
            for (Entry<String, String> elem : map.entrySet()) {
                String msg = "Computer cannot be created, reason [" + elem.getKey() + "] : \"" + elem.getValue() + "\"";
                sb.append(msg + "<br/>");
            }
            req.setAttribute("msg", sb.toString());
            LOGGER.debug(sb.toString());

        } else {

            Computer c = new Computer(name, v.getIntroduced(), v.getDiscontinued(), v.getCompanyId());
            try {
                Long id = computerService.createComputer(c);
                req.setAttribute("msg", "Computer \"" + name + "\" successfully created (id=" + id + ")");
            } catch (DaoException e) {
                String msg = "Computer cannot be created, reason \"" + e.getMessage() + "\"";
                req.setAttribute("msg", msg);
                LOGGER.error(msg);
            }

        }

        loadCompanies(req);
        req.getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
    }
}
