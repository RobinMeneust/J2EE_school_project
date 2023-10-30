<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1, text/html, charset=UTF-8">
    <jsp:include page="include.jsp" />
    <title>Dashboard</title>
    <script type="application/javascript">
        function expandCatalogue(button){
            if (button.getAttribute('id') === 'nav-catalogue-tab'){
                const buttonProducts = document.getElementById("nav-products-tab");
                const buttonCategories = document.getElementById("nav-categories-tab");
                buttonProducts.classList.toggle("hidden");
                buttonCategories.classList.toggle("hidden");
                button.classList.toggle("expand");
            }
        }
    </script>
    <style>
        .hidden{
            display:none;
        }

        #nav-catalogue-tab.expand{
            background-color: green;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="d-flex align-items-start">
            <nav>
                <div class="nav nav-tabs flex-column" id="nav-tab" role="tablist">
                    <button class="nav-link active" id="nav-customers-tab" data-bs-toggle="tab" data-bs-target="#nav-customers" type="button" role="tab" aria-controls="nav-customers" aria-selected="true">Customers</button>
                    <button class="nav-link" id="nav-moderators-tab" data-bs-toggle="tab" data-bs-target="#nav-moderators" type="button" role="tab" aria-controls="nav-moderators" aria-selected="false">Moderators</button>
                    <button onclick="expandCatalogue(this)" class="nav-link" id="nav-catalogue-tab" type="button" aria-controls="nav-catalogue" aria-selected="false">Catalogue</button>
                    <button class="nav-link hidden" id="nav-products-tab" data-bs-toggle="tab" data-bs-target="#nav-products" type="button" role="tab" aria-controls="nav-products" aria-selected="false">Products</button>
                    <button class="nav-link hidden" id="nav-categories-tab" data-bs-toggle="tab" data-bs-target="#nav-categories" type="button" role="tab" aria-controls="nav-categories" aria-selected="false">Categories</button>
                    <button class="nav-link" id="nav-discounts-tab" data-bs-toggle="tab" data-bs-target="#nav-discounts" type="button" role="tab" aria-controls="nav-discounts" aria-selected="false">Discounts</button>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-customers" role="tabpanel" aria-labelledby="nav-customers-tab">Customers</div>
                <div class="tab-pane fade" id="nav-moderators" role="tabpanel" aria-labelledby="nav-moderators-tab">Moderators</div>
                <div class="tab-pane fade" id="nav-products" role="tabpanel" aria-labelledby="nav-products-tab">Products</div>
                <div class="tab-pane fade" id="nav-categories" role="tabpanel" aria-labelledby="nav-discounts-tab">Categories</div>
                <div class="tab-pane fade" id="nav-discounts" role="tabpanel" aria-labelledby="nav-discounts-tab">Discounts</div>
            </div>
        </div>
    </div>
</body>
</html>
