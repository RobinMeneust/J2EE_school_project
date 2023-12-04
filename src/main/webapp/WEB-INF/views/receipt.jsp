<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Receipt</title>
    <jsp:include page="../../include.jsp" />
    <script src="${pageContext.request.contextPath}/js/print_element.js"></script>
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
<div class="container mt-1 px-2 pb-5 mb-5">
    <c:choose>
        <c:when test="${order != null && order.getOrderItems() != null && !order.getOrderItems().isEmpty()}">
            <div class="card">
                <div class="card-body mx-3">
                    <div class="container">
                        <h3 class="mx-3 my-4 display-3">Order n°<c:out value="${order.getId()}"/></h3>
                        <p class="my-2">Date: <c:out value="${order.getDate()}"/></p>
                        <table class="table mt-5">
                            <tr>
                                <th class="align-middle col-8">Item</th>
                                <th class="text-center align-middle col-2">Quantity</th>
                                <th class="text-center align-middle col-2">Amount paid</th>
                            </tr>
                            <c:forEach items="${order.getOrderItems()}" var="item">
                                <tr>
                                    <td class="align-middle col-8"><c:out value="${item.getProduct().getName()}"/></td>
                                    <td class="text-center align-middle col-2"><c:out value="${item.getQuantity()}"/></td>
                                    <td class="text-center align-middle col-2"><fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${item.getTotal()}"/> €</td>
                                </tr>
                            </c:forEach>
                        </table>
                        <div class="row">
                            <div class="col-12">
                                <p class="float-end fw-bold">Total: <fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${order.getTotal()}"/> €</p>
                            </div>
                            <hr>
                        </div>
                    </div>
                </div>
            </div>
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
