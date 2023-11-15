package j2ee_project.controller.auth;

import j2ee_project.model.user.User;
import j2ee_project.service.AuthService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * This class is a servlet used to log in a user. It's a controller in the MVC architecture of this project.
 *
 * @author Lucas VELAY
 */
@WebServlet(name = "LogInController", value = "/login-controller")
public class LogInController extends HttpServlet {

    /**
     * Redirect to the sender of this request and set an error message since GET queries aren't accepted by this servlet
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the servlet encountered difficulty at some point
     * @throws IOException If an I/O operation has failed or is interrupted
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/login.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Log in a user with the parameters given in the request object. Different errors can be sent to the sender in the request object if a problem occur
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the servlet encountered difficulty at some point
     * @throws IOException If an I/O operation has failed or is interrupted
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String errorDestination = "WEB-INF/views/login.jsp";
        String noErrorDestination = "/index.jsp";
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
                System.out.println(user);
                response.sendRedirect(request.getContextPath() + noErrorDestination);
            }
        }catch (Exception e) {
            request.setAttribute("LoggingProcessError","Error during logging, check your email and your password");
            dispatcher = request.getRequestDispatcher(errorDestination);
        }

        if (dispatcher != null) dispatcher.forward(request, response);
    }
}