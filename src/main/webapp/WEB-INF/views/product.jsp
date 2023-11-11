<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="j2ee_project.model.catalog.Product" %>
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
    <script src="${pageContext.request.contextPath}/js/cart.js"></script>
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
<%
    Product product = (Product) request.getAttribute("product");
%>

<c:set var="product" value="<%=product%>"/>
<c:set var="cart" value="${cf:getCart(sessionCart,null)}"/> <%-- change 'null' to a function to get the authenticated customer --%>


<div class="container mt-1 px-2">
    <div id="error-alert-box" class="alert invisible alert-warning alert-dismissible fade" role="alert">
        <strong>Failure</strong> The product could not be added to the cart.
        <button type="button" class="close btn" data-bs-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <h3 class="display-3 mb-3"><c:out value="${product.getName()}" /></h3>
    <div class="row">
        <div class="col-4">
            <img class="rounded" style="width: 420px; height: 300px; object-fit: cover;" alt="product_img" src="<c:out value="${product.getImageUrl()}" />">
        </div>
        <div class="col-4">
            <p>
                <c:out value="${product.getDescription()}" />
            </p>
        </div>
        <div class="col-2">
            <span class="font-weight-bold">$<c:out value="${product.getUnitPrice()}" /></span>

            <c:choose>
                <c:when test="${cart != null && cart.getCartItems() != null && cart.containsProduct(product.getId())}">
                    <button class="btn btn-success" disabled>Already in cart</button>
                </c:when>
                <c:otherwise>
                    <button onclick="addToCart(this, ${product.getId()})" class="btn btn-primary">Add to cart</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
