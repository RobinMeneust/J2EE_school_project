package j2ee_project.controller;

import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import static j2ee_project.dao.user.PermissionDAO.getPermission;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute("user");
            if(obj instanceof Moderator moderator) {
                if (moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_CUSTOMER))){
                    RequestDispatcher dispatcherCustomers = getServletContext().getRequestDispatcher("/get-customers");
                    dispatcherCustomers.include(request, response);
                }
                if (moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_MODERATOR))) {
                    RequestDispatcher dispatcherModerators = getServletContext().getRequestDispatcher("/get-moderators");
                    dispatcherModerators.include(request, response);
                }
                if (moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_PRODUCT))) {
                    RequestDispatcher dispatcherProducts = getServletContext().getRequestDispatcher("/get-products");
                    dispatcherProducts.include(request, response);
                }
                if (moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_CATEGORY))) {
                    RequestDispatcher dispatcherCategories = getServletContext().getRequestDispatcher("/get-categories");
                    dispatcherCategories.include(request, response);
                }
                if (moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_DISCOUNT))) {
                    RequestDispatcher dispatcherDiscounts = getServletContext().getRequestDispatcher("/get-discounts");
                    dispatcherDiscounts.include(request, response);
                }
                if (moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_LOYALTY))) {

                }
                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/dashboard.jsp");
                view.forward(request,response);
            } else {
                response.sendRedirect("index.jsp");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}