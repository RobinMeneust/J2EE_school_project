<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="j2ee_project.model.catalog.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: robin
  Date: 30/10/2023
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
    <jsp:include page="../../include.jsp" />
</head>
<body>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if(products == null) {
        products = new ArrayList<>();
    }
%>

<div class="container mt-1 px-4">
    <div class="row g-5">
        <c:forEach var = "product" items = "${products}">
            <div class="col-md-4">
                <a href="get-product-page?id=<c:out value="${product.getId()}"/>" style="text-decoration: none">
                    <div class="card hover-shadow hover-zoom" style="width: 390px; height: 350px;">
                        <img style="width: 390px; height: 250px; object-fit: cover;" alt="product_img" src="<c:out value="${product.getImageUrl()}" />" class="card-img-top">
                        <div class="card-body">
                            <div class="d-flex justify-content-between">
                                <span class="font-weight-bold"><c:out value="${product.getName()}" /></span>
                                <span class="font-weight-bold">$<c:out value="${product.getUnitPrice()}" /></span>
                            </div>
                            <p class="card-text mb-1 mt-1">
                                <c:out value="${product.getDescription()}" />
                            </p>
                        </div>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
