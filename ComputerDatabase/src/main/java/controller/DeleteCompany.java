package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistence.exceptions.DaoException;
import service.CompanyServiceImpl;
import validators.ValidationUtils;

@WebServlet
public class DeleteCompany extends HttpServlet {

    private static final long serialVersionUID = -437544682524232070L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCompany.class);
    private CompanyServiceImpl companyService;

    /**
     * Default constructor.
     */
    public DeleteCompany() {
        companyService = CompanyServiceImpl.getInstance();
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String selection = req.getParameter("id");
        if (selection != null && ValidationUtils.isLong(selection)) {
            try {

                companyService.deleteCompany(Long.parseLong(selection));
                String msg = "Sucessfully deleted : " + selection;
                RequestUtils.showMsg(req, true, msg);
                LOGGER.info(msg);

            } catch (DaoException e) {


                String msg = "failed to delete : " + e.getMessage();
                RequestUtils.showMsg(req, false, msg);
                LOGGER.error(msg);
            }
        }

        getServletContext().getRequestDispatcher("/dashboard").forward(req, resp);
        //
    }
}
