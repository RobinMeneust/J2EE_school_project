<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>
<%--
  Created by IntelliJ IDEA.
  User: robin
  Date: 01/11/2023
  Time: 00:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pay</title>
    <jsp:include page="../../include.jsp" />
</head>
<body>
<jsp:include page="../../layout/header.jsp" />

<script>
    // A reference to Stripe.js initialized with your real test publishable API key.
    var stripe = Stripe(stripePublicKey);

    // The items the customer wants to buy
    var purchase = {
        email:email
        amount: amount,
        featureRequest: featureRequest
    };

    // Disable the button until we have Stripe set up on the page
    document.querySelector("button").disabled = true;
    fetch("/create-payment-intent", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(purchase)
    })
        .then(function(result) {
            return result.json();
        })
        .then(function(data) {
            var elements = stripe.elements();

            var style = {
                base: {
                    color: "#32325d",
                    fontFamily: 'Arial, sans-serif',
                    fontSmoothing: "antialiased",
                    fontSize: "16px",
                    "::placeholder": {
                        color: "#32325d"
                    }
                },
                invalid: {
                    fontFamily: 'Arial, sans-serif',
                    color: "#fa755a",
                    iconColor: "#fa755a"
                }
            };

            var card = elements.create("card", { style: style });
            // Stripe injects an iframe into the DOM
            card.mount("#card-element");

            card.on("change", function (event) {
                // Disable the Pay button if there are no card details in the Element
                document.querySelector("button").disabled = event.empty;
                document.querySelector("#card-error").textContent = event.error ? event.error.message : "";
            });

            var form = document.getElementById("payment-form");
            form.addEventListener("submit", function(event) {
                event.preventDefault();
                // Complete payment when the submit button is clicked
                payWithCard(stripe, card, data.clientSecret);
            });
        });

    // Calls stripe.confirmCardPayment
    // If the card requires authentication Stripe shows a pop-up modal to
    // prompt the user to enter authentication details without leaving your page.
    var payWithCard = function(stripe, card, clientSecret) {
        loading(true);
        stripe
            .confirmCardPayment(clientSecret, {
                receipt_email: email,
                payment_method: {
                    card: card,
                    billing_details: {
                        email: email
                    }
                }
            })
            .then(function(result) {
                if (result.error) {
                    // Show error to your customer
                    showError(result.error.message);
                } else {
                    // The payment succeeded!
                    orderComplete(result.paymentIntent.id);
                }
            });
    };

    /* ------- UI helpers ------- */

    // Shows a success message when the payment is complete
    var orderComplete = function(paymentIntentId) {
        loading(false);
        document
            .querySelector(".result-message a")
            .setAttribute(
                "href",
                "https://dashboard.stripe.com/test/payments/" + paymentIntentId
            );
        document.querySelector(".result-message").classList.remove("hidden");
        document.querySelector("button").disabled = true;
    };

    // Show the customer the error from Stripe if their card fails to charge
    var showError = function(errorMsgText) {
        loading(false);
        var errorMsg = document.querySelector("#card-error");
        errorMsg.textContent = errorMsgText;
        setTimeout(function() {
            errorMsg.textContent = "";
        }, 4000);
    };

    // Show a spinner on payment submission
    var loading = function(isLoading) {
        if (isLoading) {
            // Disable the button and show a spinner
            document.querySelector("button").disabled = true;
            document.querySelector("#spinner").classList.remove("hidden");
            document.querySelector("#button-text").classList.add("hidden");
        } else {
            document.querySelector("button").disabled = false;
            document.querySelector("#spinner").classList.add("hidden");
            document.querySelector("#button-text").classList.remove("hidden");
        }
    };
</script>

<div class="container">
    <div class="form-card">
        <form id="payment-form" th:action="@{/}" th:object="${checkoutForm}" method="post">
            <p><b>Payment details for a product</b></p>
            <div class="form-control">
                <input type="text" th:field="*{email}" placeholder="Email address" required />
                <p th:if="${#fields.hasErrors('email')}" th:errors="*{email}" style="color: red;"></p>
            </div>
            <div class="form-control">
                <input type="text" th:field="*{amount}" placeholder="Amount" required />
                <p th:if="${#fields.hasErrors('amount')}" th:errors="*{amount}" style="color: red;"></p>
            </div>
            <div class="form-control">
                <input type="text" th:field="*{featureRequest}" placeholder="Feature Request" required />
                <p th:if="${#fields.hasErrors('featureRequest')}" th:errors="*{featureRequest}" style="color: red;"></p>
            </div>
            <p><input type="submit" class="btn btn-primary"></p>
        </form>

    </div>

<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
