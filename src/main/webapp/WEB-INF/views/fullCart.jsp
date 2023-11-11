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
    <title>Product</title>
    <jsp:include page="../../include.jsp" />
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
    <div class="container mt-1 px-4">
        <c:set var="cart" value="${cf:getCart(sessionCart,null)}"/> <%-- change 'null' to a function to get the authenticated customer --%>
        <c:choose>
            <c:when test="${cart != null && cart.getCartItems() != null}">
                <table class="table table-striped text-center">
                    <tr>
                        <th></th>
                        <th>Name</th>
                        <th>Quantity</th>
                        <th colspan="3">Actions</th>
                    </tr>
                    <c:forEach var="item" items="${cart.getCartItems()}">
                        <tr id="item_${item.getId()}">
                            <td>
                                <a href="get-product-page?id=<c:out value="${item.getProduct().getId()}"/>" style="text-decoration: none">
                                    <img style="width: 100px; height: 100px; object-fit: cover;" alt="product_img" src="<c:out value="${item.getProduct().getImageUrl()}" />">
                                </a>
                            </td>
                            <td class="align-middle"><c:out value="${item.getProduct().getName()}"/></td>
                            <td class="align-middle" id="qty_${item.getId()}"><c:out value="${item.getQuantity()}"/></td>
                            <td class="align-middle" style="width: 50px">
                                <button class="btn rounded" onclick="changeQty('${item.getId()}', 1)"><span class="material-symbols-outlined">add_circle</span></button>
                            </td>
                            <td class="align-middle" style="width: 50px">
                                <button class="btn rounded" onclick="changeQty('${item.getId()}', -1)"><span class="material-symbols-outlined">remove</span></button>
                            </td>
                            <td class="align-middle" style="width: 50px">
                                <button class="btn rounded" onclick="removeCartItem('${item.getId()}')"><span class="material-symbols-outlined text-danger">delete</span></button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <span class="m-5 display-3">Your cart is empty</span>
            </c:otherwise>
        </c:choose>
    </div>
<jsp:include page="../../layout/footer.jsp" />
<script>
    const url = "edit-cart-item-quantity";
    function changeQty(itemId, variation) {
        let quantity = parseInt($("#qty_"+itemId).text()) + parseInt(variation);
        if(quantity<=0) {
            removeCartItem(itemId);
            return;
        }

        fetch(url+"?id="+itemId+"&quantity="+quantity, {
            method: 'GET'
        }).then((response) => {
            if(response.ok) {
                $("#qty_"+itemId).html(quantity);
            }
        });
    }

    function removeCartItem(itemId) {
        fetch(url+"?id="+itemId+"&quantity=0", {
            method: 'GET'
        }).then((response) => {
            if(response.ok) {
                $("#item_"+itemId).remove();
            }
        });
    }

</script>
</body>
</html>
