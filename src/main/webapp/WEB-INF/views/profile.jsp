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
    <jsp:include page="../../include.jsp" />
    <title>Profile</title>
    <style>
        h2{
            text-align: center;
        }

        .step-container {
            position: relative;
            text-align: center;
            transform: translateY(-43%);
        }

        .step-circle {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            background-color: #fff;
            border: 2px solid #9c36b5;
            line-height: 30px;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 10px;
            cursor: pointer;
        }

        #multi-step-form{
            overflow-x: hidden;
        }
    </style>
</head>
<body>
<jsp:include page="../../layout/header.jsp" />

<%
    User user = (User) request.getAttribute("user");

%>
    <div class="container-fluid" style="width: 100%;max-width: 100%;">
        <div class="d-flex align-items-start">
            <nav>
                <div class="nav nav-tabs flex-column" id="nav-tab" role="tablist">
                    <form action="profile-informations">
                        <button class="nav-link active" id="nav-profile_informations-tab" data-bs-toggle="tab" data-bs-target="#nav-profile_informations" type="button" role="tab" aria-controls="nav-profile_informations" aria-selected="true">Profile informations</button>
                        <button class="nav-link" id="nav-loyalty_account-tab" data-bs-toggle="tab" data-bs-target="#nav-loyalty_account" type="button" role="tab" aria-controls="nav-loyalty_account" aria-selected="false">Loyalty account</button>
                        <button formaction="order-history" formmethod="get" class="nav-link" id="nav-order_history-tab" data-bs-toggle="tab" data-bs-target="#nav-order_history" type="button" role="tab" aria-controls="nav-order_history" aria-selected="false">Order history</button>
                        <button class="nav-link" id="nav-preferences-tab" data-bs-toggle="tab" data-bs-target="#nav-preferences" type="button" role="tab" aria-controls="nav-preferences" aria-selected="false">Preferences</button>
                    </form>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-profile_informations" role="tabpanel" aria-labelledby="nav-profile_informations-tab"> <h2>Profile informations</h2>
                    <p></p>
                    <form action="profile-informations">
                        <div class="form-group">
                            <label for="userName">Name</label>
                            <input type="text" class="form-control" id="userName" name="userName" placeholder=<c:out value="${user.getFirstName()}" />>
                        </div>
                        <div class="form-group">
                            <label for="userSurname">Surname</label>
                            <input type="text" class="form-control" id="userSurname" name="userSurname" placeholder=<c:out value="${user.getLastName()}" />>
                        </div>
                        <div class="form-group">
                            <label for="userEmail">Email address</label>
                            <input type="email" class="form-control" id="userEmail" name="userEmail" placeholder=<c:out value="${user.getEmail()}" />>
                        </div>
                        <div class="form-group">
                            <label for="userPassword">Password</label>
                            <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder=<c:out value="${user.getPassword()}" />>
                        </div>
                        <div class="form-group">
                            <label for="userPhoneNumber">Phone number</label>
                            <input type="text" class="form-control" id="userPhoneNumber" name="userPhoneNumber" placeholder=<c:out value="${user.getPhoneNumber}" />>
                        </div>
                        <div class="form-group">
                            <label for="userAddress">Address</label>
                            <input type="text" class="form-control" id="userAddress" name="userAddress" placeholder="1234 Main St">
                        </div>
                        <div class="form-group">
                            <label for="userPostalCode">Postal code</label>
                            <input type="text" class="form-control" id="userPostalCode" name="userPostalCode">
                        </div>
                        <div class="form-group">
                            <label for="userCity">City</label>
                            <input type="text" class="form-control" id="userCity" name="userCity" placeholder="Cergy">
                        </div>
                        <div class="form-group">
                            <label for="userCountry">Country</label>
                            <input type="text" class="form-control" id="userCountry" name="userCountry" placeholder="France">
                        </div>
                        <p></p>
                        <button type="submit" class="btn btn-primary">Update profile</button>
                    </form>
                </div>
                <div class="tab-pane fade" id="nav-loyalty_account" role="tabpanel" aria-labelledby="nav-loyalty_account-tab"> <h2>Loyalty account</h2>
                    <div id="container" class="container mt-5">
                        <div class="progress px-1" style="height: 3px;">
                            <div class="progress-bar" role="progressbar" style="width: 0;" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                        </div>
                        <div class="step-container d-flex justify-content-between">
                            <div class="step-circle" onclick="displayStep(1)">1</div>
                            <div class="step-circle" onclick="displayStep(2)">2</div>
                            <div class="step-circle" onclick="displayStep(3)">3</div>
                            <div class="step-circle" onclick="displayStep(4)">4</div>
                            <div class="step-circle" onclick="displayStep(5)">5</div>
                        </div>

                        <form id="multi-step-form">
                            <div class="step step-1">
                                <h3>10% Discount</h3>
                                <div class="mb-3">
                                    <label for="redeemable1" class="form-label">Redeemable : </label>
                                    <input class="form-check-input" type="checkbox" value="" id="redeemable1" name="redeemable1" checked disabled><p></p>
                                    <label for="discount1" class="form-label">Redeem discount : </label>
                                    <input class="form-check-input" type="checkbox" value="" id="discount1" name="Discount1">
                                </div>
                                <button type="button" class="btn btn-primary next-step">Next</button>
                            </div>
                            <div class="step step-2">
                                <h3>15% Discount</h3>
                                <div class="mb-3">
                                    <label for="redeemable2" class="form-label">Redeemable : </label>
                                    <input class="form-check-input" type="checkbox" value="" id="redeemable2" name="redeemable2" disabled><p></p>
                                    <label for="Discount2" class="form-label">Redeem discount : </label>
                                    <input class="form-check-input" type="checkbox" value="" id="Discount2" name="Discount2">
                                </div>
                                <button type="button" class="btn btn-primary prev-step">Previous</button>
                                <button type="button" class="btn btn-primary next-step">Next</button>
                            </div>
                            <div class="step step-3">
                                <h3>20% Discount</h3>
                                <div class="mb-3">
                                    <label for="redeemable3" class="form-label">Redeemable : </label>
                                    <input class="form-check-input" type="checkbox" value="" id="redeemable3" name="redeemable3" disabled><p></p>
                                    <label for="Discount3" class="form-label">Redeem discount : </label>
                                    <input class="form-check-input" type="checkbox" value="" id="Discount3" name="Discount3">
                                </div>
                                <button type="button" class="btn btn-primary prev-step">Previous</button>
                                <button type="button" class="btn btn-primary next-step">Next</button>
                            </div>
                            <div class="step step-4">
                                <h3>30% Discount</h3>
                                <div class="mb-3">
                                    <label for="redeemable4" class="form-label">Redeemable : </label>
                                    <input class="form-check-input" type="checkbox" value="" id="redeemable4" name="redeemable4" disabled><p></p>
                                    <label for="Discount4" class="form-label">Redeem discount : </label>
                                    <input class="form-check-input" type="checkbox" value="" id="Discount4" name="Discount4">
                                </div>
                                <button type="button" class="btn btn-primary prev-step">Previous</button>
                                <button type="button" class="btn btn-primary next-step">Next</button>
                            </div>
                            <div class="step step-5">
                                <h3>50% Discount</h3>
                                <div class="mb-3">
                                    <label for="redeemable5" class="form-label">Redeemable : </label>
                                    <input class="form-check-input" type="checkbox" value="" id="redeemable5" name="redeemable5" disabled><p></p>
                                    <label for="Discount5" class="form-label">Redeem discount : </label>
                                    <input class="form-check-input" type="checkbox" value="" id="Discount5" name="Discount5">
                                </div>
                                <button type="button" class="btn btn-primary prev-step">Previous</button>
                                <button type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </form>
                    </div>

                    <script>
                        var currentStep = 1;
                        var updateProgressBar;

                        function displayStep(stepNumber) {
                            if (stepNumber >= 1 && stepNumber <= 5) {
                                $(".step-" + currentStep).hide();
                                $(".step-" + stepNumber).show();
                                currentStep = stepNumber;
                                updateProgressBar();
                            }
                        }

                        $(document).ready(function() {
                            $('#multi-step-form').find('.step').slice(1).hide();

                            $(".next-step").click(function() {
                                if (currentStep < 5) {
                                    $(".step-" + currentStep);
                                    currentStep++;
                                    setTimeout(function() {
                                        $(".step").hide();
                                        $(".step-" + currentStep).show();
                                        updateProgressBar();
                                    }, 20);
                                }
                            });

                            $(".prev-step").click(function() {
                                if (currentStep > 1) {
                                    $(".step-" + currentStep);
                                    currentStep--;
                                    setTimeout(function() {
                                        $(".step").hide();
                                        $(".step-" + currentStep).show();
                                        updateProgressBar();
                                    }, 20);
                                }
                            });

                            updateProgressBar = function() {
                                var progressPercentage = ((currentStep-1)/4) * 100;
                                $(".progress-bar").css("width", progressPercentage + "%");
                            }
                        });
                    </script>

                </div>
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
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>

