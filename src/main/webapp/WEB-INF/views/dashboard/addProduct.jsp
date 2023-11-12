<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="j2ee_project.model.catalog.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1, text/html, charset=UTF-8">
    <jsp:include page="../../../include.jsp" />
    <title>Dashboard</title>
</head>
<body>
<h2>Add Product</h2>
<form action="add-product" method="get">
    <label for="image">Image :</label>
    <input type="file" id="image" name="image" value="">
    <label for="name">Name :</label>
    <input type="text" id="name" name="name" value="">
    <label for="description">Description :</label>
    <input type="text" id="description" name="description" value="">
    <label for="stock-quantity">Stock Quantity :</label>
    <input type="text" id="stock-quantity" name="stock-quantity" value="">
    <label for="unit-price">Unit Price :</label>
    <input type="text" id="unit-price" name="unit-price" value="">
    <label for="weight">Weight :</label>
    <input type="text" id="weight" name="weight" value="">
    <label for="categoryId">Category :</label>
    <select type="" id="categoryId" name="category">
        <option value=""></option>
        <%
            List<Category> categories = (List<Category>) request.getAttribute("categories");
            if(categories == null){
                categories = new ArrayList<>();
            }
        %>
        <c:forEach var="category" items="${categories}">
            <option id="${category.getName()}" name="${category.getName()}" value="${category.getId()}">${category.getName()}</option>
        </c:forEach>
    </select>
    <button type="submit">
        Confirm
    </button>
</form>
</body>
</html>
