<%@ page import="java.util.List" %>
<%@ page import="j2ee_project.model.user.Customer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="j2ee_project.model.catalog.Product" %>
<%@ page import="j2ee_project.model.user.Moderator" %>
<%@ page import="j2ee_project.model.catalog.Category" %>
<%@ page import="j2ee_project.model.Discount" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../include.jsp"/>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
    <jsp:include page="../../../layout/header.jsp" />
    <div class="container-fluid">
        <div class="d-flex ">
            <nav class="col-lg-2">
                <div class="nav nav-tabs flex-column" id="nav-tab" role="tablist">
                    <button class="nav-link active" id="nav-customers-tab" data-bs-toggle="tab" data-bs-target="#nav-customers" type="button" role="tab" aria-controls="nav-customers" aria-selected="true">Customers</button>
                    <button class="nav-link" id="nav-moderators-tab" data-bs-toggle="tab" data-bs-target="#nav-moderators" type="button" role="tab" aria-controls="nav-moderators" aria-selected="false">Moderators</button>
                    <button onclick="expandCatalogue(this)" class="nav-link" id="nav-catalogue-tab" type="button" aria-controls="nav-catalogue" aria-selected="false">Catalogue</button>
                    <button class="nav-link hidden" id="nav-products-tab" data-bs-toggle="tab" data-bs-target="#nav-products" type="button" role="tab" aria-controls="nav-products" aria-selected="false">Products</button>
                    <button class="nav-link hidden" id="nav-categories-tab" data-bs-toggle="tab" data-bs-target="#nav-categories" type="button" role="tab" aria-controls="nav-categories" aria-selected="false">Categories</button>
                    <button class="nav-link" id="nav-discounts-tab" data-bs-toggle="tab" data-bs-target="#nav-discounts" type="button" role="tab" aria-controls="nav-discounts" aria-selected="false">Discounts</button>
                </div>
            </nav>
            <div class="tab-content col-lg-10" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-customers" role="tabpanel" aria-labelledby="nav-customers-tab">
                    <a href="add-customer" class="add-data" id="add-customer">Add Customer</a>
                    <%
                        List<Customer> customers = (List<Customer>) request.getAttribute("customers");
                        if(customers == null){
                            customers = new ArrayList<>();
                        }
                    %>
                    <table class="table table-striped table-hover" id="customers-table" data-filter-control-visible="false">
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
                                        <a href="" class="pencil">
                                            <img src="${pageContext.request.contextPath}/img/pencil.svg" alt="Pencil">
                                        </a>
                                    </td>
                                    <td class="border-bottom-0">
                                        <a href="delete-customer?id=${customer.getId()}" class="trash-can">
                                            <img src="${pageContext.request.contextPath}/img/trash.svg" alt="Trash can">
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <script>
                        new DataTable('#customers-table');
                    </script>
                </div>
                <div class="tab-pane fade" id="nav-moderators" role="tabpanel" aria-labelledby="nav-moderators-tab">
                    <a href="add-moderator" class="add-data" id="add-moderator">Add Moderator</a>
                    <%
                        List<Moderator> moderators = (List<Moderator>) request.getAttribute("moderators");
                        if(moderators == null) {
                            moderators = new ArrayList<>();
                        }
                    %>
                    <c:set var="moderators" value="<%=moderators%>"/>
                    <table class="table table-striped table-hover" id="moderators-table" data-filter-control-visible="false">
                        <thead>
                        <tr>
                            <th>Last Name</th>
                            <th>First Name</th>
                            <%--<th>Permissions</th>--%>
                            <th>Email</th>
                            <th>Phone Number</th>
                            <th data-sortable="false"></th>
                            <th data-sortable="false"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var = "moderator" items = "${moderators}">
                            <tr>
                                <td><c:out value = "${moderator.getLastName()}"/></td>
                                <td><c:out value = "${moderator.getFirstName()}"/></td>
                                <%--<td>
                                    <ul>
                                        <c:if test="${not empty moderator.permissions}">
                                            <c:forEach var="permission" items="${moderator.permissions}">
                                                <li>
                                                    <c:out value="${permission.getPermission()}"/>
                                                </li>
                                            </c:forEach>
                                        </c:if>
                                    </ul>
                                </td>--%>
                                <td><c:out value = "${moderator.getEmail()}"/></td>
                                <td><c:out value = "${moderator.getPhoneNumber()}"/></td>
                                <td class="border-bottom-0">
                                    <a href="" class="pencil">
                                        <img src="${pageContext.request.contextPath}/img/pencil.svg" alt="Pencil">
                                    </a>
                                </td>
                                <td class="border-bottom-0">
                                    <a href="delete-moderator?id=${moderator.getId()}" class="trash-can">
                                        <img src="${pageContext.request.contextPath}/img/trash.svg" alt="Trash can">
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <script>
                        new DataTable('#moderators-table');
                    </script>
                </div>
                <div class="tab-pane fade" id="nav-products" role="tabpanel" aria-labelledby="nav-products-tab">
                    <a href="add-product" class="add-data" id="add-product">Add Product</a>
                    <%
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        if(products == null){
                            products = new ArrayList<>();
                        }
                    %>
                    <table class="table table-striped table-hover" id="products-table" data-filter-control-visible="false">
                        <thead>
                        <tr>
                            <th>Image</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Stock Quantity</th>
                            <th>Price</th>
                            <th>Weight</th>
                            <th>Category</th>
                            <th data-sortable="false"></th>
                            <th data-sortable="false"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var = "product" items = "${products}">
                            <tr>
                                <td>
                                    <img style="width: 78px; height: 50px; object-fit: cover;"
                                         alt="product_img"
                                         src="<c:out value="${product.getImageUrl()}" />"
                                         class="card-img-top">
                                </td>
                                <td><c:out value="${product.getName()}"/></td>
                                <td><c:out value="${product.getDescription()}"/></td>
                                <td><c:out value="${product.getStockQuantity()}"/></td>
                                <td><c:out value="${product.getUnitPrice()}"/></td>
                                <td><c:out value="${product.getWeight()}"/></td>
                                <td><c:out value="${product.getCategory().getName()}"/></td>
                                <td class="border-bottom-0">
                                    <a href="" class="pencil">
                                        <img src="${pageContext.request.contextPath}/img/pencil.svg" alt="Pencil">
                                    </a>
                                </td>
                                <td class="border-bottom-0">
                                    <a href="delete-product?id=${product.getId()}" class="trash-can">
                                        <img src="${pageContext.request.contextPath}/img/trash.svg" alt="Trash can">
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <script>
                        new DataTable('#products-table');
                    </script>
                </div>
                <div class="tab-pane fade" id="nav-categories" role="tabpanel" aria-labelledby="nav-discounts-tab">
                    <a href="add-category" class="add-data" id="add-category">Add Category</a>
                    <%
                        List<Category> categories = (List<Category>) request.getAttribute("categories");
                        if(categories == null){
                            categories = new ArrayList<>();
                        }
                    %>
                    <table class="table table-striped table-hover" id="categories-table" data-filter-control-visible="false">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Discount</th>
                            <th data-sortable="false"></th>
                            <th data-sortable="false"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var = "category" items = "${categories}">
                            <tr>
                                <td><c:out value = "${category.getName()}"/></td>
                                <td><c:out value = "${category.getDescription()}"/></td>

                                <td>
                                    <c:if test="${category.getDiscount()!=null}">
                                        <c:out value = "${category.getDiscount().getName()}"/>
                                    </c:if>
                                </td>
                                <td class="border-bottom-0">
                                    <a href="" class="pencil">
                                        <img src="${pageContext.request.contextPath}/img/pencil.svg" alt="Pencil">
                                    </a>
                                </td>
                                <td class="border-bottom-0">
                                    <a href="delete-category?id=${category.getId()}" class="trash-can">
                                        <img src="${pageContext.request.contextPath}/img/trash.svg" alt="Trash can">
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <script>
                        new DataTable('#categories-table');
                    </script>
                </div>
                <div class="tab-pane fade" id="nav-discounts" role="tabpanel" aria-labelledby="nav-discounts-tab">
                    <a href="add-discount" class="add-data" id="add-discount">Add Discount</a>
                    <%
                        List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
                        if(discounts == null){
                            discounts = new ArrayList<>();
                        }
                    %>
                    <table class="table table-striped table-hover" id="categories-table" data-filter-control-visible="false">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Discount Percentage</th>
                            <th data-sortable="false"></th>
                            <th data-sortable="false"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var = "discount" items = "${discounts}">
                            <tr>
                                <td><c:out value = "${discount.getName()}"/></td>
                                <td><c:out value = "${discount.getStartDate()}"/></td>
                                <td><c:out value = "${discount.getEndDate()}"/></td>
                                <td><c:out value = "${discount.getDiscountPercentage()}"/></td>
                                <td class="border-bottom-0">
                                    <a href="" class="pencil">
                                        <img src="${pageContext.request.contextPath}/img/pencil.svg" alt="Pencil">
                                    </a>
                                </td>
                                <td class="border-bottom-0">
                                    <a href="delete-discount?id=${discount.getId()}" class="trash-can">
                                        <img src="${pageContext.request.contextPath}/img/trash.svg" alt="Trash can">
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <script>
                        new DataTable('#discount-table');
                    </script>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../../../layout/footer.jsp" />
</body>
</html>
