package j2ee_project.controller;

import j2ee_project.dao.user.UserDAO;
import j2ee_project.dto.CustomerDTO;
import j2ee_project.dto.UserDTO;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.User;
import j2ee_project.service.AuthService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.validation.ConstraintViolation;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "RegisterCustomerController", value = "/RegisterCustomerController")
public class RegisterCustomerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDTO customer = new CustomerDTO(
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("confirmPassword"),
                request.getParameter("phoneNumber")
        );
        Map<String, String> inputErrors = AuthService.userDataValidation(customer);
        String destination;
        String errorDestination = "register.jsp";
        String noErrorDestination = "index.jsp";
        RequestDispatcher dispatcher = null;
        if(inputErrors.isEmpty()){
            if (!UserDAO.emailIsInDb(customer.getEmail())){
                try {
                    User user = AuthService.registerCustomer(customer);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    System.out.println(noErrorDestination);
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } catch(Exception exception){
                    request.setAttribute("RegisterProcessError","Error during register process");
                    System.out.println(errorDestination + " : register process error");
                    dispatcher = request.getRequestDispatcher(errorDestination);
                }
            }
            else{
                request.setAttribute("emailInDbError","Email already used");
                System.out.println(errorDestination + " : email in db error");
                dispatcher = request.getRequestDispatcher(errorDestination);
            }
        }
        else{
            request.setAttribute("InputError", inputErrors);
            System.out.println(errorDestination + " : input error : " + inputErrors);
            dispatcher = request.getRequestDispatcher(errorDestination);
        }

        if (dispatcher != null) dispatcher.forward(request, response);

    }
}