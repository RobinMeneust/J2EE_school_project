package j2ee_project.controller.user.customer;

import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import static j2ee_project.dao.user.PermissionDAO.getPermission;

/**
 * This class is a servlet used to delete a customer. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/delete-customer")
public class DeleteCustomerController extends HttpServlet {
    /**
     * Remove the customer whose id is given in the param "id"
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute("user");
            if (obj instanceof Moderator moderator
                    && moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_CUSTOMER))) {
                String customerIdStr = request.getParameter("id");
                int customerId = -1;

                if (customerIdStr != null && !customerIdStr.trim().isEmpty()) {
                    try {
                        customerId = Integer.parseInt(customerIdStr);
                    } catch (Exception ignore) {
                    }
                }

                if (customerId <= 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID must be positive");
                }
                CustomerDAO.deleteCustomer(customerId);
                response.sendRedirect("dashboard?tab=customers");
            } else {
                response.sendRedirect("dashboard");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}