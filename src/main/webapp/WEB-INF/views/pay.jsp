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
    <script src="https://js.stripe.com/v3/"></script>
    <script src="${pageContext.request.contextPath}/js/checkout.js" defer></script>
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
<div class="container">
    <%--For testing purposes use the card number: 4242424242424242--%>
    <p>
        You need to pay <span id="amount-to-be-paid"></span> €
    </p>
    <form id="payment-form">
        <div id="payment-element" class="me-5 my-4 p-4 w-50" style="background-color: lightgray">
            <!--Stripe.js injects the Payment Element-->
        </div>

        <button id="submit" class="pay-btn btn btn-primary" type="button">
            <span class="spinner-pay-btn spinner-border spinner-border-sm visually-hidden" aria-hidden="true"></span>
            <span class="spinner-pay-btn visually-hidden" role="status">Loading...</span>
            <span id="pay-btn-text">Pay now</span>
        </button>

        <div id="payment-message" class="visually-hidden"></div>
    </form>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
