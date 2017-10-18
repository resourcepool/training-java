package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ComputerServiceImpl;

@WebServlet
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = -8465135918905858327L;

    private ComputerServiceImpl computerService;

    /**
     * ctor.
     */
    public AddComputer() {
        computerService = ComputerServiceImpl.getInstance();
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
        req.getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
    }
}
