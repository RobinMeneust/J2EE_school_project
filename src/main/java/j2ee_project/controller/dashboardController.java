package j2ee_project.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/dashboard")
public class dashboardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestDispatcher dispatcherCustomers = getServletContext().getRequestDispatcher("/get-customers");
            dispatcherCustomers.include(request, response);

            RequestDispatcher dispatcherModerators = getServletContext().getRequestDispatcher("/get-moderators");
            dispatcherModerators.include(request, response);

            RequestDispatcher dispatcherProducts = getServletContext().getRequestDispatcher("/get-products");
            dispatcherProducts.include(request, response);

            RequestDispatcher dispatcherCategories = getServletContext().getRequestDispatcher("/get-categories");
            dispatcherCategories.include(request, response);

            RequestDispatcher dispatcherDiscounts = getServletContext().getRequestDispatcher("/get-discounts");
            dispatcherDiscounts.include(request, response);

            RequestDispatcher view = request.getRequestDispatcher("dashboard.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}