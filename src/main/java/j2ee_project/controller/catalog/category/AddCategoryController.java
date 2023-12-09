package j2ee_project.controller.catalog.category;

import j2ee_project.dao.catalog.category.CategoryDAO;
import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.dao.user.UserDAO;
import j2ee_project.dto.catalog.CategoryDTO;
import j2ee_project.model.Discount;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.AuthService;
import j2ee_project.service.DTOService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Map;

import static j2ee_project.dao.user.PermissionDAO.getPermission;

/**
 * This class is a servlet used to add a category. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/add-category")
public class AddCategoryController extends HttpServlet {
    /**
     * Get the page to add a category
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
                    && moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_CATEGORY))) {
                request.setAttribute("discounts", DiscountDAO.getDiscounts());
                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addCategory.jsp");
                view.forward(request, response);
            } else {
                response.sendRedirect("dashboard");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Add a category to the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String discountStr = (request.getParameter("discount").isEmpty()) ? null : request.getParameter(("discount"));
        Discount discount = null;
        if (discountStr != null) {
            int discountId = Integer.parseInt(discountStr);
            discount = DiscountDAO.getDiscount(discountId);
        }

        CategoryDTO categoryDTO = new CategoryDTO(
                request.getParameter("name"),
                request.getParameter("description"),
                discount
        );

        Map<String, String> inputErrors = DTOService.categoryDataValidation(categoryDTO);

        String errorDestination = "WEB-INF/views/dashboard/add/addCategory.jsp";
        RequestDispatcher dispatcher = null;

        if(inputErrors.isEmpty()){
            try {
                CategoryDAO.addCategory(new Category(categoryDTO));
                response.sendRedirect("dashboard?tab=categories");
            } catch(Exception exception){
                System.err.println(exception.getMessage());
                request.setAttribute("RegisterProcessError","Error during register process");
                dispatcher = request.getRequestDispatcher(errorDestination);
                dispatcher.include(request, response);
            }
        } else {
            request.setAttribute("InputError", inputErrors);
            dispatcher = request.getRequestDispatcher(errorDestination);
            dispatcher.include(request, response);
        }

        if (dispatcher != null) doGet(request, response);
    }
}