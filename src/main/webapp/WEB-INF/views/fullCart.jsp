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
    <title>Product</title>
    <jsp:include page="../../include.jsp" />
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
    <div class="container mt-1 px-4">
        <c:set var="cart" value="${cf:getCart(sessionCart,null)}"/> <%-- change 'null' to a function to get the authenticated customer --%>
        <ul>
            <c:forEach var="item" items="${cart.getCartItems()}">
                <li><c:out value="${item.getProduct().getName()} Qty: ${item.getQuantity()}"/></li>
            </c:forEach>
        </ul>
    </div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
