package j2ee_project.controller.user;

import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.user.Customer;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CustomersController", value = "/CustomersController")
public class CustomerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("customers", CustomerDAO.getCustomer());
        System.out.println("SIZE:"+CustomerDAO.getCustomer().size());

        try {
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