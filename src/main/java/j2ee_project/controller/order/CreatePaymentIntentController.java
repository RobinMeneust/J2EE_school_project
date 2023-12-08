package j2ee_project.controller.order;

import com.google.gson.Gson;
import com.stripe.Stripe;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import j2ee_project.dao.order.OrdersDAO;
import j2ee_project.model.order.Orders;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.User;
import j2ee_project.service.AuthService;
import j2ee_project.service.OrdersManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@WebServlet("/create-payment-intent")
public class CreatePaymentIntentController extends HttpServlet {

    private static Gson gson = new Gson();

    static class CreatePaymentResponse {
        private String clientSecret;
        public CreatePaymentResponse(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            // Set your Stripe secret key
            if(Stripe.apiKey == null) {
                String credentialsFilePath = "/credentials.json";
                InputStream inputStream = CreatePaymentIntentController.class.getResourceAsStream(credentialsFilePath);
                if (inputStream == null) {
                    throw new NullPointerException("Cannot find the credentials file");
                }
                JSONTokener tokener = new JSONTokener(inputStream);
                JSONObject credentials = new JSONObject(tokener);

                // Check if the credentials object contains the required fields

                if(!credentials.has("stripe") || !credentials.getJSONObject("stripe").has("secret-key")) {
                    throw new RuntimeException("Invalid credentials");
                }
                Stripe.apiKey = credentials.getJSONObject("stripe").getString("secret-key");
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String orderId = request.getParameter("order-id");
            Orders order = OrdersDAO.getOrder(orderId);

            if(order == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No order is associated to this ID");
                return;
            }

            Object obj = session.getAttribute("user");
            Customer customer = null;
            if(obj instanceof User) {
                customer = AuthService.getCustomer((User) obj);
            }

            String error = OrdersManager.checkOrder(order, customer);

            if(error != null && !error.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, error);
                return;
            }

            PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                    .setAmount(Double.valueOf(Math.floor(order.getTotal()*100)).longValue())
                    .setCurrency("eur")
                    .addPaymentMethodType("card")
                    .build();
            // Create a PaymentIntent with the order amount and currency
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(paymentResponse));
            out.flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
