package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Dashboard.
 */
@WebServlet
public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public Dashboard() {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CharSequence files = getFiles();
        request.setAttribute("real_source", files);
        request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);

    }

    /**
     * @return iterate at "." to list all files
     */
    private CharSequence getFiles() {
        Collection<File> all = new ArrayList<File>();
        StringBuilder sb = new StringBuilder();

        File currentfile = new File(".");
        addTree(currentfile, all);

        sb.append(currentfile.getAbsolutePath() + System.lineSeparator());
        sb.append("<br/>");
        for (File file : all) {
            sb.append(file.getPath());
            sb.append("<br/>");
        }

        return sb.toString();
    }

    /**
     * @param file directory
     * @param all colection of path found
     * @throws IOException fail to read directory
     */
    private static void addTree(File file, Collection<File> all) {
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {

                String path = child.getPath();

                if (!path.startsWith("./.")) {
                    all.add(child);
                    addTree(child, all);
                }
            }
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
