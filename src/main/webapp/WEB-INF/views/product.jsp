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
<!DOCTYPE html>
<html>
<head>
    <title>Product</title>
    <jsp:include page="../../include.jsp" />
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
<%
    Product product = (Product) request.getAttribute("product");
%>

<c:set var="product" value="<%=product%>"/>

<script>
    function addToCart(productId) {
        if(productId != null && !isNaN(productId) && productId>0) {
            const url = "add-to-cart?id="+productId;

            fetch(url, {
                method: 'GET'
            }).then((response) => {
                if(response.ok) {
                    response.json().then((data) => {
                        if (data != null && "isAlreadyInCart" in data && data["isAlreadyInCart"]) {
                            $("#success-alert-box").text("Already in your cart");
                        } else {
                            $("#success-alert-box").text("Added");
                        }
                    });

                    $("#success-alert-box").removeClass("invisible").addClass("visible");
                    $("#failure-alert-box").removeClass("visible").addClass("invisible");
                } else {
                    $("#failure-alert-box").removeClass("invisible").addClass("visible");
                    $("#success-alert-box").removeClass("visible").addClass("invisible");
                }
            });
        }
    }
</script>

<div class="container mt-1 px-4">
    <img style="width: 390px; height: 250px; object-fit: cover;" alt="product_img" src="<c:out value="${product.getImageUrl()}" />">
    <div>
        <span class="font-weight-bold"><c:out value="${product.getName()}" /></span>
        <span class="font-weight-bold">$<c:out value="${product.getUnitPrice()}" /></span>
    </div>
    <p>
        <c:out value="${product.getDescription()}" />
    </p>
    <button onclick="addToCart(${product.getId()})" class="btn btn-primary">Add to cart</button>
    <div id="success-alert-box" class="alert alert-success invisible" role="alert">Added</div>
    <div id="failure-alert-box" class="alert alert-failure invisible" role="alert">Failure</div>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
