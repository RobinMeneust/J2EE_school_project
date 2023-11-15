package j2ee_project.controller.auth;

import j2ee_project.dao.MailDAO;
import j2ee_project.dao.user.UserDAO;
import j2ee_project.dto.CustomerDTO;
import j2ee_project.model.Mail;
import j2ee_project.model.user.User;
import j2ee_project.service.AuthService;
import j2ee_project.service.MailManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

/**
 * This class is a servlet used register customer. It's a controller in the MVC architecture of this project.
 *
 * @author Lucas VELAY
 */
@WebServlet(name = "RegisterCustomerController", value = "/register-customer-controller")
public class RegisterCustomerController extends HttpServlet {

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
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/register.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Register a customer with the parameters given in the request object. Different errors can be sent to the sender in the request object if a problem occur
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the servlet encountered difficulty at some point
     * @throws IOException If an I/O operation has failed or is interrupted
     */
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
        String errorDestination = "WEB-INF/views/register.jsp";
        String noErrorDestination = "/index.jsp";
        RequestDispatcher dispatcher = null;
        if(inputErrors.isEmpty()){
            if (!UserDAO.emailOrPhoneNumberIsInDb(customer.getEmail(), customer.getPhoneNumber())){
                try {
                    User user = AuthService.registerCustomer(customer);
                    System.out.println(user);

                    sendConfirmationMail(user.getEmail());

                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    System.out.println(session.getAttribute("user"));
                    response.sendRedirect(request.getContextPath() + noErrorDestination);
                } catch(Exception exception){
                    System.out.println(exception.getMessage());
                    request.setAttribute("RegisterProcessError","Error during register process");
                    dispatcher = request.getRequestDispatcher(errorDestination);
                }
            }
            else{
                request.setAttribute("emailOrPhoneNumberInDbError","Email or phone number already used");
                dispatcher = request.getRequestDispatcher(errorDestination);
            }
        }
        else{
            request.setAttribute("InputError", inputErrors);
            dispatcher = request.getRequestDispatcher(errorDestination);
        }

        if (dispatcher != null) dispatcher.forward(request, response);

    }

    private void sendConfirmationMail(String email){
        Mail mail = new Mail();
        MailManager mailManager = MailManager.getInstance();

        try
        {
            mail.setFromAddress("jeewebproject@gmail.com");
            mail.setToAddress(email);
            mail.setSubject("Register confirmation on Boarder Games website");
            mail.setBody("Hello,\nWe confirm your registration on the Boarder Games website.\n");

            // Set the date to current time if it's null
            if(mail.getDate() == null) {
                mail.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
            }

            MailDAO.addMail(mail);
            mailManager.send(mail);
        }
        catch(Exception ignore) {}
    }
}