package j2ee_project.controller.discount;

import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.dto.discount.DiscountDTO;
import j2ee_project.model.Discount;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.DTOService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

import static j2ee_project.dao.user.PermissionDAO.getPermission;

/**
 * This class is a servlet used to edit a discount. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/edit-discount")
public class EditDiscountController extends HttpServlet {
    /**
     * Get the page used to edit the discount whose id is given in the param "id"
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
            if (obj instanceof Moderator user
                    && user.isAllowed(getPermission(TypePermission.CAN_MANAGE_DISCOUNT))) {
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

                Discount discount = DiscountDAO.getDiscount(discountId);
                request.setAttribute("discount", discount);
                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/edit/editDiscount.jsp");
                view.forward(request, response);
            } else {
                response.sendRedirect("dashboard");
            }
        } catch(Exception err) {
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Edit the discount whose id is given in the param "id" with the provided data
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        Discount discount = DiscountDAO.getDiscount(discountId);

        try {

            DiscountDTO discountDTO = new DiscountDTO(
                    request.getParameter("name"),
                    Date.valueOf(request.getParameter("startDate")),
                    Date.valueOf(request.getParameter("endDate")),
                    Integer.parseInt(request.getParameter("discountPercentage"))
            );

            Map<String, String> inputErrors = DTOService.discountDataValidation(discountDTO);

            String errorDestination = "WEB-INF/views/dashboard/edit/editDiscount.jsp";
            RequestDispatcher dispatcher = null;

            if (inputErrors.isEmpty()) {
                try {

                    if (discountDTO.getName() != null && !discountDTO.getName().isEmpty()) {
                        discount.setName(discountDTO.getName());
                    }
                    if (discountDTO.getStartDate() != null) {
                        discount.setStartDate(discountDTO.getStartDate());
                    }
                    if (discountDTO.getEndDate() != null) {
                        discount.setEndDate(discountDTO.getEndDate());
                    }
                    discount.setDiscountPercentage(discountDTO.getDiscountPercentage());


                    DiscountDAO.updateDiscount(discount);
                    response.sendRedirect("dashboard?tab=discounts");
                } catch (Exception exception) {
                    System.err.println(exception.getMessage());
                    request.setAttribute("ModificationProcessError", "Error during Modification process");
                    dispatcher = request.getRequestDispatcher(errorDestination);
                    dispatcher.include(request, response);
                }
            } else {
                request.setAttribute("InputError", inputErrors);
                dispatcher = request.getRequestDispatcher(errorDestination);
                dispatcher.include(request, response);
            }

            if (dispatcher != null) doGet(request, response);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}