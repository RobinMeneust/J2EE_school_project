<%@ page import="j2ee_project.model.user.User" %>
<%@ page import="j2ee_project.model.user.Customer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>
<%--
  Created by IntelliJ IDEA.
  User: robin
  Date: 03/11/2023
  Time: 01:16
  To change this template use File | Settings | File Templates.
--%>

<header style="background-color: #9C36B5;" class="py-1 px-5 mb-3 border-bottom">
    <nav class="navbar navbar-light navbar-expand-lg">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>


        <a href="${pageContext.request.contextPath}/" class="navbar-brand" title="Home">
            <img class="d-inline-block align-top" style="height:80px; width:80px;" src="${pageContext.request.contextPath}/img/logo_boarder_games.png" alt="logo_boarder_games">
        </a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <form class="form-inline my-2 my-lg-0 mx-5 w-50" action="browse-products" method="get">
                <input id="name" name="name" class="form-control me-sm-2" type="search" placeholder="Search" aria-label="Search">
            </form>
            <ul class="navbar-nav ms-auto">
                <li class="nav-item mx-2" title="Cart">
                    <a href="cart" class="nav-link">
                        <span style="font-size:60px" class="material-symbols-outlined text-white">shopping_cart</span>
                    </a>
                </li>
                <li class="nav-item mx-2" title="Account">
                    <c:set var="customer" value="${cf:getCustomer(user)}"/>
                    <c:choose>
                        <c:when test="${not empty customer}">
                            <c:set var="profileIconLink" value="profile-informations"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="profileIconLink" value="login"/>
                        </c:otherwise>
                    </c:choose>
                    <a href="${profileIconLink}" class="nav-link">
                        <span style="font-size:60px" class="material-symbols-outlined text-white">account_circle</span>
                    </a>
                </li>
                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item mx-2" title="Logout">
                        <a href="LogOutController" class="nav-link">
                            <span style="font-size:60px" class="material-symbols-outlined text-white">logout</span>
                        </a>
                    </li>
                </c:if>--%>
                <li class="dropdown nav-item">
                    <button class="btn dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <span style="font-size:60px" class="material-symbols-outlined text-white">account_circle</span>
                    </button>
                    <c:if test="${sessionScope.user == null}">
                        <ul class="dropdown-menu">
                            <li class="nav-item mx-2" title="Logout">
                                <a href="login" class="nav-link">
                                    <span class="material-symbols-outlined">login</span>
                                    Log in
                                </a>
                            </li>
                            <li class="nav-item mx-2" title="Logout">
                                <a href="register" class="nav-link">
                                    <span class="material-symbols-outlined">how_to_reg</span>
                                    Register
                                </a>
                            </li>
                        </ul>
                    </c:if>
                    <c:if test="${sessionScope.user != null}">
                        <%
                            String customerId = null;
                            String loyaltyAccountId = null;
                            User user = (User) session.getAttribute("user");
                            if(user instanceof Customer){
                                customerId = user.getId() + "";
                                if(((Customer) user).getLoyaltyAccount() != null){
                                    loyaltyAccountId = ((Customer) user).getLoyaltyAccount().getId() + "";
                                }
                            }
                        %>
                        <c:set var="customerId" value="<%=customerId%>"/>
                        <c:set var="loyaltyAccountId" value="<%=loyaltyAccountId%>"/>
                        <ul class="dropdown-menu">
                            <li class="nav-item mx-2" title="ProfileInformation">
                                <a href="profile-informations" class="nav-link">
                                    <span class="material-symbols-outlined">account_circle</span>
                                    Profile information
                                </a>
                            </li>
                            <c:if test="${customerId && loyaltyAccountId}">
                                <li class="nav-item mx-2" title="LoyaltyRedeem">
                                    <a href="loyalty-redeem?customerId=${customerId}&loyaltyAccountId=${loyaltyAccountId}" class="nav-link">
                                        <span class="material-symbols-outlined">redeem</span>
                                        Loyalty redeem
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${customerId}">
                                <li class="nav-item mx-2" title="OrderHistory">
                                    <a href="order-history?id=${customerId}" class="nav-link">
                                        <span class="material-symbols-outlined">history</span>
                                        Order history
                                    </a>
                                </li>
                            </c:if>
                            <li class="nav-item mx-2" title="Logout">
                                <a href="logout-controller" class="nav-link">
                                    <span class="material-symbols-outlined">logout</span>
                                    Log out
                                </a>
                            </li>
                        </ul>
                    </c:if>
                </li>
                <li class="nav-item mx-2" title="HomeDark / Light mode">
                    <input type="checkbox" id="dark-mode-button" onclick="switchTheme();">
                    <label for="dark-mode-button" class="toggle-dark-mode mt-3">
                        <span class="text-center toggle-dark-mode-button material-symbols-outlined">dark_mode</span>
                    </label>
                </li>
            </ul>
        </div>
    </nav>
</header>