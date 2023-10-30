package j2ee_project.controller.catalog;

import j2ee_project.dao.product.ProductDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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

        request.setAttribute("products", ProductDAO.getProducts());
        System.out.println("SIZE:"+ProductDAO.getProducts().size());

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
