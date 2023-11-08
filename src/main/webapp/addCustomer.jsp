<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1, text/html, charset=UTF-8">
    <jsp:include page="include.jsp" />
    <title>Dashboard</title>
</head>
<body>
    <h2>Add Customer</h2>
    <form action="add-customer" method="post">
        <div id="name">
            <label for="last-name">Last Name :</label>
            <input type="text" id="last-name" name="last-name" value="">
            <label for="first-name">First Name :</label>
            <input type="text" id="first-name" name="first-name" value="">
        </div>
        <div>
            <label for="password">Password :</label>
            <input type="text" id="password" name="password" value="">
        </div>
        <div id="address">
            <label for="street">Street</label>
            <input type="text" id="street" name="street" value="">
            <label for="postal-code">Postal Code :</label>
            <input type="text" id="postal-code" name="postal-code" value="">
            <label for="city">City :</label>
            <input type="text" id="city" name="city" value="">
            <label for="country">Country :</label>
            <input type="text" id="country" name="country" value="">
        </div>
        <div id="contact-details">
            <label for="email">Email :</label>
            <input type="text" id="email" name="email" value="">
            <label for="phone-number">Phone Number</label>
            <input type="text" id="phone-number" name="phone-number" value="">
        </div>
        <button type="submit">
            Confirm
        </button>
    </form>
</body>
</html>
