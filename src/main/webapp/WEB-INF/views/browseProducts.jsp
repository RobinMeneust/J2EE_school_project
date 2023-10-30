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
</head>
<body>
    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        if(products == null) {
            products = new ArrayList<>();
        }
    %>
    <%= products.size() %>
    <table>
        <c:forEach var = "product" items = "${products}">
            <tr><td>Item <c:out value = "${product.getName()}"/></td></tr>
        </c:forEach>
    </table>
</body>
</html>
