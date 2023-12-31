package j2ee_project.controller.discount;

import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import static j2ee_project.dao.user.PermissionDAO.getPermission;

/**
 * This class is a servlet used to delete a discount. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/delete-discount")
public class DeleteDiscountController extends HttpServlet {
    /**
     * Remove the discount whose id is given in the param "id"
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
                    && moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_DISCOUNT))) {
                String discountIdStr = request.getParameter("id");
                int discountId = -1;

                if (discountIdStr != null && !discountIdStr.trim().isEmpty()) {
                    try {
                        discountId = Integer.parseInt(discountIdStr);
                    } catch (Exception ignore) {
                    }
                }

                if (discountId <= 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Discount ID must be positive");
                }
                DiscountDAO.deleteDiscount(discountId);
                response.sendRedirect("dashboard?tab=discounts");
            } else {
                response.sendRedirect("dashboard");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}