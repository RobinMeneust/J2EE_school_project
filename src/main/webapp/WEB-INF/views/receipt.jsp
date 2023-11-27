<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>
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
<div class="container mt-1 px-2 pb-5 mb-5">
    <h1 class="display-1 mb-3">Your Receipt</h1>
    <c:choose>
        <c:when test="${order != null && order.getOrderItems() != null && !order.getOrderItems().isEmpty()}">
            <table>
                <tr>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
                <c:forEach items="${order.getOrderItems()}" var="item">
                    <tr>
                        <td><c:out value="${item.getProduct().getName()}"/></td>
                        <td><c:out value="${item.getQuantity()}"/></td>
                        <td><c:out value="${item.getTotal()}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <div id="error-alert-box" class="my-5 alert alert-warning fade show" role="alert">
                <span>Your order is empty.</span>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
