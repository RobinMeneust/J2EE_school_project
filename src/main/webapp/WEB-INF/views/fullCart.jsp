<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="../functions.tld"%>
<%--
  Created by IntelliJ IDEA.
  User: robin
  Date: 01/11/2023
  Time: 00:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product</title>
    <jsp:include page="../../include.jsp" />
</head>
<body>
<div class="container mt-1 px-4">
    <c:set var="cart" value="${cf:getCart(sessionCart,null)}"/> <%-- change 'null' to a function to get the authenticated customer --%>

    <ul>
        <c:forEach var="item" items="${cart.getCartItems()}">
            <li><c:out value="${item.getName()}"/></li>
        </c:forEach>
    </ul>
</div>
</body>
</html>
