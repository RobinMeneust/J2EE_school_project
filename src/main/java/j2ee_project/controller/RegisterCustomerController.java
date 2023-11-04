package j2ee_project.controller;

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
                request.getParameter("phoneNumber")
        );
        Set<ConstraintViolation<UserDTO>> inputErrors = AuthService.userDataValidation(customer);
        if(inputErrors.isEmpty()){
            try {
                User user = AuthService.registerCustomer(customer);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
            }catch (NoSuchAlgorithmException | InvalidKeySpecException exception){

            }
        }


    }
}