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
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/toolcool-range-slider.min.js"></script>
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/tcrs-generated-labels.min.js"></script>
</head>
<body>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if(products == null) {
        products = new ArrayList<>();
    }

    Integer pageIndex = null;
    Object pageIndexObj = request.getAttribute("page");
    if(pageIndexObj instanceof String) {
        pageIndex = Integer.parseInt((String) pageIndexObj);
    } else if(pageIndexObj instanceof Integer) {
        pageIndex = (Integer) pageIndexObj;
    }

    if(pageIndex == null || pageIndex < 0) { // by default the current page is the first one
        pageIndex = 1;
    }

    Long totalPages = (Long) request.getAttribute("totalPages");
    if(totalPages == null) {
        totalPages = 0L;
    }

    String name = request.getParameter("name");
    String category = request.getParameter("category");
    String minPrice = request.getParameter("min-price");
    String maxPrice = request.getParameter("min-price");

    if(minPrice != null && minPrice.trim().isEmpty()) {
        minPrice = null;
    }
    if(maxPrice != null && maxPrice.trim().isEmpty()) {
        maxPrice = null;
    }
%>

<c:set var="products" value="<%=products%>" />
<c:set var="pageIndex" value="<%=pageIndex%>"/>
<c:set var="totalPages" value="<%=totalPages%>"/>
<c:set var="name" value="<%=name%>"/>
<c:set var="category" value="<%=category%>"/>
<c:set var="minPrice" value="<%=minPrice%>"/>
<c:set var="maxPrice" value="<%=maxPrice%>"/>

<div class="container mt-1 px-4">
    <button class="btn btn-outline-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling">
        <span class="material-symbols-outlined">filter_alt</span>
    </button>
    <div class="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="offcanvasScrollingLabel">Search Filters</h5>
            <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <form action="browse-products" method="get">
                <label class="form-label" for="name">Name: </label> <input value="${name}" type="text" id="name" name="name"><br>
                <label class="form-label" for="category">Category: </label> <input value="${category}" type="text" id="category" name="category"><br><br>
                <label class="form-label">Price: </label>
                <input type="hidden" id="min-price" name="min-price"/>
                <input type="hidden" id="max-price" name="max-price"/>

                <tc-range-slider
                        id="priceSlider"
                        min="-100"
                        max="100"
                        step="5"
                        value1="0"
                        value2="50"
                        generate-labels="true"
                        round="0"
                        <c:if test="${minPrice != null && maxPrice != null}">
                            set="[${minPrice},${maxPrice}]"
                        </c:if>
                ></tc-range-slider>
                <input type="submit" value="Submit">
            </form>
            <script>
                const priceSlider = document.getElementById('priceSlider');
                const minPriceInput = document.getElementById('min-price');
                const maxPriceInput = document.getElementById('max-price');
                priceSlider.addEventListener('change', (evt) => {
                    console.log(evt.detail.value1, evt.detail.value2);
                    minPriceInput.setAttribute("value", evt.detail.value1);
                    maxPriceInput.setAttribute("value", evt.detail.value2);
                });
            </script>
        </div>
    </div>

    <div class="row my-4">
        <c:forEach var = "product" items="${products}">
            <div class="col-4 my-2">
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

    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <c:if test="${pageIndex>1}">
                <li class="page-item"><a class="page-link" href="browse-products?page=1">First</a></li>
                <li class="page-item">
                    <a class="page-link" href="browse-products?page=${pageIndex-1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>

            <c:forEach var="i" begin="${Math.max(1,pageIndex-3)}" end="${Math.min(pageIndex+3 + Math.abs(pageIndex-3 - 1), totalPages)}" step="1">
                <c:choose>
                    <c:when test="${i == pageIndex}">
                        <li class="page-item active"><a class="page-link" href="browse-products?page=<c:out value="${i}"/>"><c:out value="${i}"/></a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="browse-products?page=<c:out value="${i}"/>"><c:out value="${i}"/></a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${pageIndex<totalPages}">
                <li class="page-item">
                    <a class="page-link" href="browse-products?page=${pageIndex+1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <li class="page-item"><a class="page-link" href="browse-products?page=<c:out value="${totalPages}"/>">Last</a></li>
            </c:if>
        </ul>
    </nav>
</div>
</body>
</html>
