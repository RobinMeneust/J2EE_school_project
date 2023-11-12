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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/checkout.css" />
    <script src="https://js.stripe.com/v3/"></script>
    <script src="${pageContext.request.contextPath}/js/checkout.js" defer></script>
</head>
<body>
<jsp:include page="../../layout/header.jsp" />

<div class="container">
    <form action="pay" method="post">
        <legend>Card information</legend>
        <label for="card-number">Card Number:</label>
        <input type="text" id="card-number" name="card-number" required value="4242424242424242"><br>

        <label>Expiry Date:</label>
        <input type="text" id="expiry-month" name="expiry-month" value="01" required><br>
        <input type="text" id="expiry-year" name="expiry-year" value="30" required><br>

        <label for="cvv">CVV:</label>
        <input type="text" id="cvv" name="cvv" required value="123"><br>

        <input type="submit" value="Confirm">
    </form>

    <form id="payment-form">
        <div id="payment-element">
            <!--Stripe.js injects the Payment Element-->
        </div>
        <button id="submit">
            <div class="spinner hidden" id="spinner"></div>
            <span id="button-text">Pay now</span>
        </button>
        <div id="payment-message" class="hidden"></div>
    </form>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
