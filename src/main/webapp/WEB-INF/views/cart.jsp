<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Cart</title>
    <jsp:include page="../../include.jsp" />
    <script src="${pageContext.request.contextPath}/js/cart.js"></script>
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
    <div class="container mt-1 px-4">
        <c:set var="cart" value="${cf:getCart(sessionCart,null)}"/> <%-- change 'null' to a function to get the authenticated customer --%>
        <c:choose>
            <c:when test="${cart != null && cart.getCartItems() != null}">
                <div class="row">
                    <table class="col table text-center">
                        <tr>
                            <th class="col"></th>
                            <th class="col">Name</th>
                            <th class="col">Quantity</th>
                            <th class="col" colspan="3">Actions</th>
                        </tr>
                        <c:forEach var="item" items="${cart.getCartItems()}">
                            <tr id="item_${item.getId()}">
                                <td class="col">
                                    <a href="get-product-page?id=<c:out value="${item.getProduct().getId()}"/>" style="text-decoration: none">
                                        <img style="width: 100px; height: 100px; object-fit: cover;" alt="product_img" src="<c:out value="${item.getProduct().getImageUrl()}" />">
                                    </a>
                                </td>
                                <td class="align-middle col"><c:out value="${item.getProduct().getName()}"/></td>
                                <td class="align-middle col" id="qty_${item.getId()}"><c:out value="${item.getQuantity()}"/></td>
                                <td class="align-middle col">
                                    <button class="btn rounded" onclick="changeQty('${item.getId()}', 1)"><span class="material-symbols-outlined">add_circle</span></button>
                                </td>
                                <td class="align-middle col">
                                    <button class="btn rounded" onclick="changeQty('${item.getId()}', -1)"><span class="material-symbols-outlined">remove</span></button>
                                </td>
                                <td class="align-middle col">
                                    <button class="btn rounded" onclick="removeCartItem('${item.getId()}')"><span class="material-symbols-outlined text-danger">delete</span></button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="col-5">
                        <table class="table table-striped">
                            <tr>
                                <th class="col">Name</th>
                                <th class="col">Unit Price</th>
                                <th class="col">Quantity</th>
                                <th class="col">Total</th>
                            </tr>
                            <c:set var="total" value="${0}"/>
                            <c:forEach var="item" items="${cart.getCartItems()}">
                                <tr>
                                    <td class="align-middle col"><c:out value="${item.getProduct().getName()}"/></td>
                                    <c:choose>
                                        <c:when test="${not empty item.getProduct().getCategory().getDiscount() && item.getProduct().getCategory().getDiscount().getDiscountPercentage() > 0}">
                                            <c:set var="price" value="${item.getProduct().getUnitPrice()*(1 - item.getProduct().getCategory().getDiscount().getDiscountPercentage()/100)}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="price" value="${item.getProduct().getUnitPrice()}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="align-middle col"><c:out value="${price}"/></td>
                                    <td class="align-middle col"><c:out value="${item.getQuantity()}"/></td>
                                    <td class="align-middle col"><c:out value="${price * item.getQuantity()}"/></td>
                                    <c:set var="total" value="${total + price * item.getQuantity()}"/>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="4"></td>
                            </tr>
                            <tr>
                                <th class="col">Total</th>
                                <th class="col"></th>
                                <th class="col"></th>
                                <th class="col"><c:out value="${total}"/></th>
                            </tr>
                            <tr>
                                <td colspan="4"><a href="confirm-cart"><button class="btn btn-primary">Confirm</button></a></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <span class="m-5 display-3">Your cart is empty</span>
            </c:otherwise>
        </c:choose>
    </div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
