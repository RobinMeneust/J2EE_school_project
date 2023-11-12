package j2ee_project.controller.auth;

import j2ee_project.dao.user.UserDAO;
import j2ee_project.dto.CustomerDTO;
import j2ee_project.model.user.User;
import j2ee_project.service.AuthService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "LogInController", value = "/LogInController")
public class LogInController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String errorDestination = "WEB-INF/views/login.jsp";
        String noErrorDestination = "index.jsp";
        RequestDispatcher dispatcher = null;

        try {
            User user = AuthService.logIn(email, password);
            if(user == null) {
                request.setAttribute("LoggingProcessError","Error during logging, check your email and your password");
                dispatcher = request.getRequestDispatcher(errorDestination);
            }
            else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + noErrorDestination);
            }
        }catch (Exception e) {
            request.setAttribute("LoggingProcessError","Error during logging, check your email and your password");
            dispatcher = request.getRequestDispatcher(errorDestination);
        }

        if (dispatcher != null) dispatcher.forward(request, response);
    }
}