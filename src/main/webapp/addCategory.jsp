<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="j2ee_project.model.Discount" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1, text/html, charset=UTF-8">
    <jsp:include page="include.jsp" />
    <title>Dashboard</title>
</head>
<body>
<h2>Add Category</h2>
<form action="add-category" method="post">

    <label for="name">Name :</label>
    <input type="text" id="name" name="name" value="">
    <label for="description">Description :</label>
    <input type="text" id="description" name="description" value="">
    <label for="discountId">Discount :</label>
    <select type="" id="discountId" name="discount">
        <option value=""></option>
        <%
            List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
            if(discounts == null){
                discounts = new ArrayList<>();
            }
        %>
        <c:forEach var="discount" items="${discounts}">
            <option id="${discount.getName()}" name="${discount.getName()}" value="${discount.getId()}">${discount.getName()}</option>
        </c:forEach>
    </select>
    <button type="submit">
        Confirm
    </button>
</form>
</body>
</html>
