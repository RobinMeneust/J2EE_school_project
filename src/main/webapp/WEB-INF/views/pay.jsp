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

<div class="container">
    <form action="pay" method="post">
        <label for="card-number">Card Number:</label>
        <input type="text" id="card-number" name="card-number" required value="4242424242424242"><br>

        <label for="expiry-date">Expiry Date:</label>
        <input type="text" id="expiry-date" name="expiry-date" value="01/30" required><br>

        <label for="cvv">CVV:</label>
        <input type="text" id="cvv" name="cvv" required value="123"><br>

        <input type="submit" value="Confirm">
    </form>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
