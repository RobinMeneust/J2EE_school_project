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
    <h1 class="display-1 mb-3">Your Cart</h1>
    <c:set var="cart" value="${cf:getCart(sessionCart,null)}"/> <%-- change 'null' to a function to get the authenticated customer --%>
    <c:set var="total" value="${0}"/>
    <c:set var="customer" value="${cf:getCustomer(user)}"/>

    <c:choose>
        <c:when test="${cart != null && cart.getCartItems() != null && cart.getCartItems().size() > 0}">
            <form action="confirm-cart" method="post">
                <c:if test="${not empty customer}">
                    <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="pills-home-tab" data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home" aria-selected="true">Cart</button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="pills-profile-tab" data-bs-toggle="pill" data-bs-target="#pills-profile" type="button" role="tab" aria-controls="pills-profile" aria-selected="false">Delivery & confirmation</button>
                        </li>
                    </ul>
                </c:if>
                <div class="tab-content" id="pills-tabContent">
                    <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">
                        <table class="col table text-center">
                            <tr>
                                <th class="col"></th>
                                <th class="col">Name</th>
                                <th class="col">Quantity</th>
                                <th class="col">Price</th>
                                <th class="col" colspan="3">Actions</th>
                            </tr>
                            <c:forEach var="item" items="${cart.getCartItems()}">
                                <tr>
                                    <td class="align-middle col">
                                        <a href="get-product-page?id=<c:out value="${item.getProduct().getId()}"/>" style="text-decoration: none">
                                            <img style="width: 100px; height: 100px; object-fit: contain;" alt="product_img" src="<c:out value="product/image?id=${product.getId()}" />">
                                        </a>
                                    </td>
                                    <td class="align-middle col"><c:out value="${item.getProduct().getName()}"/></td>
                                    <td class="align-middle col"><c:out value="${item.getQuantity()}"/></td>
                                    <c:choose>
                                        <c:when test="${not empty item.getProduct().getCategory().getDiscount() && item.getProduct().getCategory().getDiscount().getDiscountPercentage() > 0}">
                                            <c:set var="price" value="${item.getProduct().getUnitPrice()*(1 - item.getProduct().getCategory().getDiscount().getDiscountPercentage()/100) * item.getQuantity()}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="price" value="${item.getProduct().getUnitPrice() * item.getQuantity()}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:set var="total" value="${total + price}"/>
                                    <td class="align-middle col"><c:out value="${price}€"/></td>

                                        <%--Actions--%>
                                    <td class="align-middle p-0">
                                        <a href="edit-cart-item-quantity?id=<c:out value="${item.getId()}"/>&quantity=<c:out value="${item.getQuantity() + 1}"/>">
                                            <button type="button" class="btn rounded text-success p-0"><span class="material-symbols-outlined">add_circle</span></button>
                                        </a>
                                    </td>
                                    <td class="align-middle p-0">
                                        <a href="edit-cart-item-quantity?id=<c:out value="${item.getId()}"/>&quantity=<c:out value="${item.getQuantity() - 1}"/>">
                                            <button type="button" class="btn rounded p-0"><span class="material-symbols-outlined">remove</span></button>
                                        </a>
                                    </td>
                                    <td class="align-middle p-0">
                                        <a href="edit-cart-item-quantity?id=<c:out value="${item.getId()}"/>&quantity=0">
                                            <button type="button" class="btn rounded text-danger p-0"><span class="material-symbols-outlined">delete</span></button>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <c:if test="${empty customer}">
                            <div class="row py-2 w-100">
                                <input class="col-1 btn btn-primary" type="submit" value="Confirm">
                            </div>
                        </c:if>
                    </div>
                    <c:if test="${not empty customer}">
                        <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">
                            <div class="row g-4">
                                    <%--TODO: Check the form content--%>
                                <div class="col-5">
                                    <h6 class="display-6">Delivery Address</h6>
                                    <div class="bg-secondary-subtle shadow p-3 mb-4 rounded d-flex align-items-start flex-column" style="min-width:250px; max-width:450px">
                                        <c:set var="addressObject" value="${customer.getAddress()}"/>
                                        <div class="form-group">
                                            <label for="street-address">Address</label>
                                            <input type="text" class="form-control" id="street-address" name="street-address" required value="<c:out value="${not empty addressObject && not empty addressObject.getStreetAddress() ? addressObject.getStreetAddress() : ''}"/>">
                                        </div>
                                        <div class="form-group">
                                            <label for="postal-code">Postal code</label>
                                            <input type="text" class="form-control" id="postal-code" name="postal-code" required value="<c:out value="${addressObject.getPostalCode()}"/>">
                                        </div>
                                        <div class="form-group">
                                            <label for="city">City</label>
                                            <input type="text" class="form-control" id="city" name="city" required value="<c:out value="${addressObject.getCity()}"/>">
                                        </div>
                                        <div class="form-group">
                                            <label for="country">Country</label>
                                            <input type="text" class="form-control" id="country" name="country" required value="<c:out value="${addressObject.getCountry()}"/>">
                                        </div>
                                    </div>
                                </div>

                                <div class="col-5">
                                    <c:if test="${false}"> <%--TODO: Test if the customer is connected and if he is subscribed to a loyalty program and get his claimed loyalty rewards--%>
                                        <div class="bg-secondary-subtle shadow p-3 mb-4 rounded d-flex align-items-start flex-column" style="min-width:250px; max-width:450px">
                                            <label class="mb-1" for="discount-id">Select a discount</label>
                                            <select class="form-select" id="discount-id" name="discount-id">
                                                <option selected value="">No Discount</option>
                                                    <%--TODO: Add discounts--%>
                                            </select>
                                        </div>
                                    </c:if>
                                    <div class="bg-secondary-subtle shadow p-3 rounded d-flex align-items-start flex-column" style="min-width:250px; max-width:450px">
                                        <div class="row py-2 w-100">
                                            <div class="col text-start"><b>Cart</b></div>
                                            <div class="col text-end"><c:out value="${total}"/> €</div>
                                        </div>
                                        <hr class="w-100"/>
                                        <c:set var="totalWithLoyaltyDiscount" value="${total}"/>
                                        <c:if test="${false}"> <%--TODO: Get the active discount--%>
                                            <div class="row py-2 w-100">
                                                <c:set var="totalWithLoyaltyDiscount" value="${total - (total*(discount/100))}"/>
                                                <div class="col text-start"><span>Discount</span></div>
                                                <div class="col text-end"><c:out value="- ${total*(discount/100)}"/> €</div>
                                            </div>
                                            <hr class="w-100"/>
                                        </c:if>
                                        <div class="row py-2 w-100">
                                            <div class="col text-start"><span class="material-symbols-outlined">local_shipping</span> <span>Shipping fees</span></div>
                                            <div class="col text-end">+ 5.00 €</div>
                                        </div>
                                        <hr class="w-100"/>
                                        <div class="row py-2 w-100">
                                            <div class="col text-start"><b>TOTAL</b></div>
                                            <div class="col text-end"><c:out value="${totalWithLoyaltyDiscount + 5} €"/></div>
                                        </div>
                                        <div class="row py-2 w-100">
                                            <input class="col btn btn-primary" type="submit" value="Confirm">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </form>
        </c:when>
        <c:otherwise>
            <div id="error-alert-box" class="my-5 alert alert-warning fade show" role="alert">
                <span>Your cart is empty.</span>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
