package j2ee_project.controller.order;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

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

            if(!credentials.has("stripe") || !credentials.getJSONObject("stripe").has("secret_key")) {
                throw new RuntimeException("Invalid credentials");
            }
            Stripe.apiKey = credentials.getJSONObject("stripe").getString("secret_key");
        }

        // Get data from the form
        String cardNumber = request.getParameter("card-number");
        String expiryDate = request.getParameter("expiry-date");
        String cvv = request.getParameter("cvv");

        // Temporary values:
        float price = 10.0f;
        int orderNb = 0;

        try {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", (int) (price * 100)); // amount in cents
            chargeParams.put("currency", "usd");
            chargeParams.put("source", cardNumber);
            chargeParams.put("description", "Boarder Games order n°"+orderNb);

            Charge charge = Charge.create(chargeParams);

            // Check if the payment was successful
            if ("succeeded".equals(charge.getStatus())) {
                // Create order object and send invoice

                // Temporary testing message
                response.getWriter().write("Payment successful!");
            } else {
                // Temporary testing message
                response.getWriter().write("Payment failed. Please try again.");
            }
        } catch (StripeException e) {
            throw new ServletException("Error processing payment", e);
        }
    }
}
