package j2ee_project.controller;

import j2ee_project.dao.OrdersDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/order-history")
public class OrdersHistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCustomer = request.getParameter("id");
        request.setAttribute("orders", OrdersDao.getOrders(Integer.parseInt(idCustomer)));
        System.out.println("Size:"+OrdersDao.getOrders(Integer.parseInt(idCustomer)).size());

        try{
            RequestDispatcher view = request.getRequestDispatcher("profile.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
