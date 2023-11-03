<%--
  Created by IntelliJ IDEA.
  User: Gandy Théo
  Date: 30/10/2023
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="j2ee_project.model.user.User" %>
<%@ page import="j2ee_project.model.order.Orders" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1, text/html, charset=UTF-8">
    <jsp:include page="include.jsp" />
    <title>Profile</title>
    <style>
        h2{
            text-align: center;
        }
    </style>
</head>
<body>
<%
    User user = (User) request.getAttribute("user");

%>
    <div class="container-fluid" style="width: 100%;max-width: 100%;">
        <div class="d-flex align-items-start">
            <nav>
                <div class="nav nav-tabs flex-column" id="nav-tab" role="tablist">
                    <button class="nav-link active" id="nav-profile_informations-tab" data-bs-toggle="tab" data-bs-target="#nav-profile_informations" type="button" role="tab" aria-controls="nav-profile_informations" aria-selected="true">Profile informations</button>
                    <button class="nav-link" id="nav-loyalty_account-tab" data-bs-toggle="tab" data-bs-target="#nav-loyalty_account" type="button" role="tab" aria-controls="nav-loyalty_account" aria-selected="false">Loyalty account</button>
                    <button formaction="order-history" formmethod="get" class="nav-link" id="nav-order_history-tab" data-bs-toggle="tab" data-bs-target="#nav-order_history" type="button" role="tab" aria-controls="nav-order_history" aria-selected="false">Order history</button>
                    <button class="nav-link" id="nav-preferences-tab" data-bs-toggle="tab" data-bs-target="#nav-preferences" type="button" role="tab" aria-controls="nav-preferences" aria-selected="false">Preferences</button>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-profile_informations" role="tabpanel" aria-labelledby="nav-profile_informations-tab"> <h2>Profile informations</h2>
                    <p></p>
                    <form action="">
                        <div class="form-group">
                            <label for="userName">Name</label>
                            <input type="text" class="form-control" id="userName" aria-describedby="emailHelp" placeholder=<c:out value="${user.getFirstName()}" />>
                        </div>
                        <div class="form-group">
                            <label for="userSurname">Surname</label>
                            <input type="text" class="form-control" id="userSurname" placeholder=<c:out value="${user.getLastName()}" />>
                        </div>
                        <div class="form-group">
                            <label for="userEmail">Email adress</label>
                            <input type="email" class="form-control" id="userEmail" placeholder=<c:out value="${user.getEmail()}" />>
                        </div>
                        <div class="form-group">
                            <label for="userPassword">Password</label>
                            <input type="password" class="form-control" id="userPassword" placeholder=<c:out value="${user.getPassword()}" />>
                        </div>
                        <div class="form-group">
                            <label for="userPhoneNumber">Phone number</label>
                            <input type="text" class="form-control" id="userPhoneNumber" placeholder=<c:out value="${user.getPhoneNumber}" />>
                        </div>
                        <div class="form-group">
                            <label for="userAddress">Address</label>
                            <input type="text" class="form-control" id="userAddress" placeholder="1234 Main St">
                        </div>
                        <div class="form-group">
                            <label for="userPostalCode">Postal code</label>
                            <input type="text" class="form-control" id="userPostalCode">
                        </div>
                        <div class="form-group">
                            <label for="userCity">City</label>
                            <input type="text" class="form-control" id="userCity" placeholder="Cergy">
                        </div>
                        <div class="form-group">
                            <label for="userCountry">Country</label>
                            <input type="text" class="form-control" id="userCountry" placeholder="France">
                        </div>
                        <p></p>
                        <button type="submit" class="btn btn-primary">Update profile</button>
                    </form>
                </div>
                <div class="tab-pane fade" id="nav-loyalty_account" role="tabpanel" aria-labelledby="nav-loyalty_account-tab">Loyalty account</div>
                <div class="tab-pane fade" id="nav-order_history" role="tabpanel" aria-labelledby="nav-order_history-tab"> <h2>Order history</h2>
                    <%
                        List<Orders> orders =(List<Orders>) request.getAttribute("orders");
                    %>
                    <table class="table table-striped table-hover" id="customers-table" style="width: 100%" data-filter-control-visible="false">
                        <thead>
                            <tr>
                                <th>Status</th>
                                <th> </th>
                            </tr>
                        </thead>
                        <c:forEach var = "order" items = "${orders}">
                            <tr>
                                <td><c:out value="${orders.getOrderStatus()}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="tab-pane fade" id="nav-preferences" role="tabpanel" aria-labelledby="nav-preferences-tab">Preferences</div>
            </div>
        </div>
    </div>
</body>
</html>

