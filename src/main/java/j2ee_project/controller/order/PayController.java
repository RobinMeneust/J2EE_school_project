package j2ee_project.controller.order;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import j2ee_project.service.MailManager;
import jakarta.mail.PasswordAuthentication;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/pay")
public class PayController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set your Stripe secret key
        if(Stripe.apiKey == null) {
            String credentialsFilePath = "/credentials.json";
            InputStream inputStream = MailManager.class.getResourceAsStream(credentialsFilePath);
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

        // Temporary values:
        float price = 10.0f;
        int orderNb = 0;


        // Get data from the form
        String cardNumber = request.getParameter("card-number");
        String expiryMonth = request.getParameter("expiry-month");
        String expiryYear = request.getParameter("expiry-year");
        String cvv = request.getParameter("cvv");

        // Create Stripe objects
        Map<String, Object> card = new HashMap<>();
        card.put("number", cardNumber);
        card.put("exp_month", expiryMonth);
        card.put("exp_year", expiryYear);
        card.put("cvc", cvv);

        Map<String, Object> paramsPaymentMethod = new HashMap<>();
        paramsPaymentMethod.put("type", "card");
        paramsPaymentMethod.put("card", card);


        try {
            PaymentMethod paymentMethod = PaymentMethod.create(paramsPaymentMethod);

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) price*100)
                .setPaymentMethod(paymentMethod.getId())
                .setCurrency("usd")
                .setDescription("Boarder Games order n°"+orderNb)
                .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            paymentIntent.confirm();

            // Check if the payment was successful
            if ("succeeded".equals(paymentIntent.getStatus())) {
                // TODO: Create order object and send invoice

                // Temporary testing message
                response.getWriter().write("Payment successful!");
            } else {
                // Temporary testing message
                response.getWriter().write("Payment failed. Please try again.");
            }
        } catch (StripeException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Payment refused"+e.getMessage());
        }
    }
}
