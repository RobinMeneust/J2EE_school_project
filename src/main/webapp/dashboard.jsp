<%@ page import="java.util.List" %>
<%@ page import="j2ee_project.dao.user.CustomerDAO" %>
<%@ page import="j2ee_project.model.user.Customer" %>
<%@ page import="java.util.ArrayList" %>
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
            }
        }
    </script>
    <style>
        .hidden{
            display:none;
        }
    </style>
</head>
<body>
    <div class="container-fluid" style="width: 100%;max-width: 100%;">
        <div class="d-flex ">
            <nav style="width: 20%;" class="col-lg-2">
                <div class="nav nav-tabs flex-column" id="nav-tab" role="tablist">
                    <button class="nav-link active" id="nav-customers-tab" data-bs-toggle="tab" data-bs-target="#nav-customers" type="button" role="tab" aria-controls="nav-customers" aria-selected="true">Customers</button>
                    <button class="nav-link" id="nav-moderators-tab" data-bs-toggle="tab" data-bs-target="#nav-moderators" type="button" role="tab" aria-controls="nav-moderators" aria-selected="false">Moderators</button>
                    <button onclick="expandCatalogue(this)" class="nav-link" id="nav-catalogue-tab" type="button" aria-controls="nav-catalogue" aria-selected="false">Catalogue</button>
                    <button class="nav-link hidden" id="nav-products-tab" data-bs-toggle="tab" data-bs-target="#nav-products" type="button" role="tab" aria-controls="nav-products" aria-selected="false">Products</button>
                    <button class="nav-link hidden" id="nav-categories-tab" data-bs-toggle="tab" data-bs-target="#nav-categories" type="button" role="tab" aria-controls="nav-categories" aria-selected="false">Categories</button>
                    <button class="nav-link" id="nav-discounts-tab" data-bs-toggle="tab" data-bs-target="#nav-discounts" type="button" role="tab" aria-controls="nav-discounts" aria-selected="false">Discounts</button>
                </div>
            </nav>
            <div class="tab-content col-lg-10" id="nav-tabContent" style="width: 80%">
                <div class="tab-pane fade show active" id="nav-customers" role="tabpanel" aria-labelledby="nav-customers-tab">
                    <%
                        List<Customer> customers = (List<Customer>) request.getAttribute("customers");
                        if(customers == null){
                            customers = new ArrayList<>();
                        }
                    %>
                    <table class="table table-striped table-hover" id="customers-table" style="width: 100%" data-filter-control-visible="false">
                        <thead>
                            <tr>
                                <th>Last Name</th>
                                <th>First Name</th>
                                <th>Street</th>
                                <th>Postal Code</th>
                                <th>City</th>
                                <th>Country</th>
                                <th>Email</th>
                                <th>Phone Number</th>
                                <th data-sortable="false"></th>
                                <th data-sortable="false"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>SAELEN</td>
                                <td>Jérémy</td>
                                <td>15 Boulevard du Port</td>
                                <td>95000</td>
                                <td>Cergy</td>
                                <td>France</td>
                                <td>saelenjere@cy-tech.fr</td>
                                <td>07 83 02 28 22</td>
                                <td>
                                    <form action="editCustomerDashboard.jsp" method="POST">
                                        <input type="hidden" name="last-name" value="SAELEN">
                                        <input type="hidden" name="first-name" value="Jérémy">
                                        <input type="hidden" name="street" value="15 Boulevard du Port">
                                        <input type="hidden" name="postal-code" value="95000">
                                        <input type="hidden" name="city" value="Cergy">
                                        <input type="hidden" name="country" value="France">
                                        <input type="hidden" name="email" value="saelenjere@cy-tech.fr">
                                        <input type="hidden" name="phone-number" value="07 83 02 28 22">
                                        <button type="submit" style="border: none; background-color: transparent">
                                            <img src="img/pencil.svg" alt="Pencil">
                                        </button>
                                    </form>
                                </td>
                                <td>
                                    <form action="" method="POST">
                                        <input type="hidden" name="last-name" value="SAELEN">
                                        <input type="hidden" name="first-name" value="Jérémy">
                                        <input type="hidden" name="street" value="15 Boulevard du Port">
                                        <input type="hidden" name="postal-code" value="95000">
                                        <input type="hidden" name="city" value="Cergy">
                                        <input type="hidden" name="country" value="France">
                                        <input type="hidden" name="email" value="saelenjere@cy-tech.fr">
                                        <input type="hidden" name="phone-number" value="07 83 02 28 22">
                                        <button type="submit" style="border: none; background-color: transparent">
                                            <img src="img/trash.svg" alt="Trash can">
                                        </button>
                                    </form>
                                </td>
                            </tr>
                            <c:forEach var = "customer" items = "${customers}">
                                <tr>
                                    <td><c:out value = "${customer.getLastName()}"/></td>
                                    <td><c:out value = "${customer.getFirstName()}"/></td>
                                    <td><c:out value = "${customer.getAddress().getStreetAddress()}"/></td>
                                    <td><c:out value = "${customer.getAddress().getPostalCode()}"/></td>
                                    <td><c:out value = "${customer.getAddress().getCity()}"/></td>
                                    <td><c:out value = "${customer.getAddress().getCountry()}"/></td>
                                    <td><c:out value = "${customer.getEmail()}"/></td>
                                    <td><c:out value = "${customer.getPhoneNumber()}"/></td>
                                    <td class="border-bottom-0">
                                        <button style="border: none; background-color: transparent">
                                            <img src="img/pencil.svg" alt="Pencil">
                                        </button>
                                    </td>
                                    <td class="border-bottom-0">
                                        <button style="border: none; background-color: transparent">
                                            <img src="img/trash.svg" alt="Trash can">
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <script>
                        new DataTable('#customers-table');
                    </script>
                </div>
                <div class="tab-pane fade" id="nav-moderators" role="tabpanel" aria-labelledby="nav-moderators-tab">Moderators</div>
                <div class="tab-pane fade" id="nav-products" role="tabpanel" aria-labelledby="nav-products-tab">Products</div>
                <div class="tab-pane fade" id="nav-categories" role="tabpanel" aria-labelledby="nav-discounts-tab">Categories</div>
                <div class="tab-pane fade" id="nav-discounts" role="tabpanel" aria-labelledby="nav-discounts-tab">Discounts</div>
            </div>
        </div>
    </div>
</body>
</html>
