<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="j2ee_project.model.catalog.Product" %>
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
<%
    Product product = (Product) request.getAttribute("product");
%>

<c:set var="product" value="<%=product%>"/>

<div class="container mt-1 px-4">
    <img style="width: 390px; height: 250px; object-fit: cover;" alt="product_img" src="<c:out value="${product.getImageUrl()}" />">
    <div>
        <span class="font-weight-bold"><c:out value="${product.getName()}" /></span>
        <span class="font-weight-bold">$<c:out value="${product.getUnitPrice()}" /></span>
    </div>
    <p>
        <c:out value="${product.getDescription()}" />
    </p>
    <a class="btn btn-primary" href="add-to-cart?id=${product.getId()}" role="button">Add to cart</a>

    <c:if test="${isAlreadyInCart}">
        <p>
            This product is in your cart
        </p>
    </c:if>
</div>
</body>
</html>
