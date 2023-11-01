package j2ee_project.controller.catalog;

import j2ee_project.dao.product.ProductDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class is a servlet used to browse products. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/browse-products")
public class BrowseProductsController extends HttpServlet
{
    /**
     * Send a mail with the parameters given in the request object. An error is sent to the sender in the request object if the mail could not be sent
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // TODO : add search filters in the request params

        String pageStr = request.getParameter("page");
        int page = 1;

        if(pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
            } catch(Exception ignore) {}
        }

        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String minPrice = request.getParameter("min-price");
        String maxPrice = request.getParameter("max-price");

        HashMap<String,String> textMatchFilters = new HashMap<>(2);
        HashMap<String,String[]> rangeFilters = new HashMap<>(2);

        textMatchFilters.put("name", name);
        textMatchFilters.put("category", category);
        String[] range = {minPrice, maxPrice};
        rangeFilters.put("price", range);

        textMatchFilters.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue().trim().isEmpty());
        rangeFilters.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue().length != 2 || entry.getValue()[0] == null || entry.getValue()[1] == null|| entry.getValue()[0].trim().isEmpty() || entry.getValue()[1].trim().isEmpty());

        request.setAttribute("page", page);
        request.setAttribute("products", ProductDAO.getProducts(15*(page-1),15, ));
        long totalPages = ((ProductDAO.getSize()-1) / 15) + 1;
        request.setAttribute("totalPages", totalPages);


        if(page <= 1 && page > totalPages) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/browseProducts.jsp");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
