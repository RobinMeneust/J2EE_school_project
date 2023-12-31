package j2ee_project.controller.catalog.category;

import j2ee_project.dao.catalog.category.CategoryDAO;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import static j2ee_project.dao.user.PermissionDAO.getPermission;

/**
 * This class is a servlet used to delete a category. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/delete-category")
public class DeleteCategoryController extends HttpServlet {
    /**
     * Remove the category whose id is given in the param "id"
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
                String categoryIdStr = request.getParameter("id");
                int categoryId = -1;

                if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                    try {
                        categoryId = Integer.parseInt(categoryIdStr);
                    } catch (Exception ignore) {
                    }
                }

                if (categoryId <= 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category ID must be positive");
                }
                CategoryDAO.deleteCategory(categoryId);
                response.sendRedirect("dashboard?tab=categories");
            } else {
                response.sendRedirect("dashboard");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}