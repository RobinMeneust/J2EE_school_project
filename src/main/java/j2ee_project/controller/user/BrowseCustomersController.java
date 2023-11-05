package j2ee_project.controller.user;

import j2ee_project.dao.user.CustomerDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("browse-customers")
public class BrowseCustomersController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            request.setAttribute("customers", CustomerDAO.getCustomers());
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