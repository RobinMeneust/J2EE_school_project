<%--
  Created by IntelliJ IDEA.
  User: Gandy Théo
  Date: 30/10/2023
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1, text/html, charset=UTF-8">
    <jsp:include page="include.jsp" />
    <title>Profile</title>
</head>
<body>
<div class="container">
    <div class="d-flex align-items-start">
        <nav>
            <div class="nav nav-tabs flex-column" id="nav-tab" role="tablist">
                <button class="nav-link active" id="nav-profile_informations-tab" data-bs-toggle="tab" data-bs-target="#nav-profile_informations" type="button" role="tab" aria-controls="nav-profile_informations" aria-selected="true">Profile informations</button>
                <button class="nav-link" id="nav-loyalty_account-tab" data-bs-toggle="tab" data-bs-target="#nav-loyalty_account" type="button" role="tab" aria-controls="nav-loyalty_account" aria-selected="false">Loyalty account</button>
                <button class="nav-link" id="nav-order_history-tab" data-bs-toggle="tab" data-bs-target="#nav-order_history" type="button" role="tab" aria-controls="nav-order_history" aria-selected="false">Order history</button>
                <button class="nav-link" id="nav-preferences-tab" data-bs-toggle="tab" data-bs-target="#nav-preferences" type="button" role="tab" aria-controls="nav-preferences" aria-selected="false">Preferences</button>

            </div>
        </nav>
        <div class="tab-content" id="nav-tabContent">
            <div class="tab-pane fade show active" id="nav-profile_informations" role="tabpanel" aria-labelledby="nav-profile_informations-tab">Profile informations</div>
            <div class="tab-pane fade" id="nav-loyalty_account" role="tabpanel" aria-labelledby="nav-loyalty_account-tab">Loyalty account</div>
            <div class="tab-pane fade" id="nav-order_history" role="tabpanel" aria-labelledby="nav-order_history-tab">Order history</div>
            <div class="tab-pane fade" id="nav-preferences" role="tabpanel" aria-labelledby="nav-preferences-tab">Preferences</div>
        </div>
    </div>
</div>
</body>
</html>

