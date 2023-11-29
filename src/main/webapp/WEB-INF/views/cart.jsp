<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <c:set var="customer" value="${cf:getCustomer(user)}"/>
    <c:set var="cart" value="${cf:getCart(sessionCart,customer)}"/>
    <c:set var="total" value="${0}"/>

    <c:choose>
        <c:when test="${cart != null && cart.getCartItems() != null && !cart.getCartItems().isEmpty()}">
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
                                            <img style="width: 100px; height: 100px; object-fit: contain;" alt="product_img" src="<c:out value="product/image?id=${item.getProduct().getId()}" />">
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
                                    <td class="align-middle col"><fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${price}"/> €</td>

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
                            <div class="row g-3">
                                    <%--TODO: Check the form content--%>
                                <div class="col-xl-6 col-md-6 col-xs-6">
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

                                <c:set var="loyaltyAccount" value="${customer.getLoyaltyAccount()}"/>
                                <c:set var="loyaltyProgram" value="${null}"/>
                                <c:if test="${not empty loyaltyAccount}">
                                    <c:set var="loyaltyProgram" value="${loyaltyAccount.getLoyaltyProgram()}"/>
                                </c:if>

                                <div class="col-xl-6 col-md-6 col-xs-6">
                                    <c:if test="${not empty loyaltyProgram}">
                                        <div class="bg-secondary-subtle shadow p-3 mb-4 rounded d-flex align-items-start flex-column" style="min-width:250px; max-width:450px">
                                            <label class="mb-1" for="loyalty-level-id">Select a discount</label>
                                            <div class="row g-2 w-100">
                                                <div class="col-10">
                                                    <select class="form-select" id="loyalty-level-id" name="loyalty-level-id">
                                                        <option selected value="">No Discount</option>
                                                        <c:forEach var="loyaltyLevel" items="${loyaltyProgram.getLoyaltyLevels()}">
                                                            <c:if test="${!loyaltyAccount.isLevelUsed(loyaltyLevel)}">
                                                                <c:set var="loyaltyDiscount" value="${loyaltyLevel.getDiscount()}"/>
                                                                <option value="<c:out value="${loyaltyLevel.getId()}"/>">
                                                                    <c:out value="${loyaltyDiscount.getName()} (- ${loyaltyDiscount.getDiscountPercentage()} %)"/>
                                                                </option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-2">
                                                    <button type="button" onclick="selectLoyaltyLevel()" class="btn" title="confirm"><span class="material-symbols-outlined">done</span></button>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <div class="bg-secondary-subtle shadow p-3 rounded d-flex align-items-start flex-column" style="min-width:250px; max-width:450px">
                                        <div class="row py-2 w-100">
                                            <div class="col text-start"><b>Cart</b></div>
                                            <div class="col text-end"><fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${total}"/> €</div>
                                        </div>
                                        <hr class="w-100"/>
                                        <c:set var="totalWithLoyaltyDiscount" value="${total}"/>
                                        <c:if test="${not empty cart.getDiscount()}">
                                            <div class="row py-2 w-100">
                                                <c:set var="totalWithLoyaltyDiscount" value="${total - (total*(cart.getDiscount().getDiscountPercentage()/100))}"/>
                                                <div class="col text-start"><span>Discount</span></div>
                                                <div class="col text-end text-success">- <c:out value="${cart.getDiscount().getDiscountPercentage()}"/> %</div>
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
                                            <div class="col text-end"><fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${totalWithLoyaltyDiscount + 5}"/> €</div>
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

<script>
    async function selectLoyaltyLevel() {
        let selectedLoyaltyLevelId = $("#loyalty-level-id").find(":selected").val();
        if(selectedLoyaltyLevelId != null) {
            await fetch("cart/loyalty-level-discount", {
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({"id":parseInt(selectedLoyaltyLevelId)}),
                method: "POST",
            }).catch((e) => {
               console.error(e);
            });
            location.reload();
        }
    }
</script>


<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
