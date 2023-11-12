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
        function expandCatalogue(){
            const buttonProducts = document.getElementById("nav-products-tab");
            const buttonCategories = document.getElementById("nav-categories-tab");
            buttonProducts.classList.toggle("hidden");
            buttonCategories.classList.toggle("hidden");
        }

        function toggleClass(classes, htmlElementsList, activeHtmlElement){
            for (let htmlElement of htmlElementsList){
                if (htmlElement === activeHtmlElement){
                    for (const classe of classes){
                        htmlElement.classList.add(classe);
                    }
                } else {
                    for (const classe of classes){
                        htmlElement.classList.remove(classe);
                    }
                }
            }
        }

        function toggleAriaSelected(dashboardNavs, currentNav){
            for(const navLink of dashboardNavs){
                if (navLink === currentNav){
                    navLink.setAttribute("aria-selected","true");
                } else {
                    navLink.setAttribute("aria-selected","false");
                }
            }
        }

        function loadCurrentTab(url){
            let activeTab = url.searchParams.get("tab");
            let dashboardNavs = document.getElementsByClassName("dashboard-nav");
            let tabPanes = document.getElementsByClassName("tab-pane");

            switch (activeTab){
                case "customers":
                    toggleClass(["active"],dashboardNavs,dashboardNavs[0]);
                    toggleClass(["show","active"], tabPanes, tabPanes[0]);
                    toggleAriaSelected(dashboardNavs);
                    break;
                case "moderators":
                    toggleClass(["active"],dashboardNavs,dashboardNavs[1]);
                    toggleClass(["show","active"], tabPanes, tabPanes[1]);
                    toggleAriaSelected(dashboardNavs);
                    break;
                case "products":
                    toggleClass(["active"],dashboardNavs,dashboardNavs[2]);
                    toggleClass(["show","active"], tabPanes, tabPanes[2]);
                    expandCatalogue();
                    toggleAriaSelected(dashboardNavs);
                    break;
                case "categories":
                    toggleClass(["active"],dashboardNavs,dashboardNavs[3]);
                    toggleClass(["show","active"], tabPanes, tabPanes[3]);
                    expandCatalogue();
                    toggleAriaSelected(dashboardNavs);
                    break;
                case "discounts":
                    toggleClass(["active"],dashboardNavs,dashboardNavs[4]);
                    toggleClass(["show","active"], tabPanes, tabPanes[4]);
                    toggleAriaSelected(dashboardNavs);
                    break;
                default:
                    break;
            }
        }

        function changeURLParameter(tab){
            let url = new URL(window.location.href);
            url.searchParams.set("tab",tab);
            window.history.replaceState(null, null, url.href);
            //loadCurrentTab(url);
        }

        document.addEventListener("DOMContentLoaded", function() {
            let url = new URL(window.location.href);
            console.log(window.location.href);
            console.log(url);
            loadCurrentTab(url);
        });
    </script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard/dashboard.css">
</head>
<body>
    <jsp:include page="../../../layout/header.jsp" />
    <div class="container-fluid">
        <div class="d-flex">
            <nav class="col-lg-2">
                <div class="nav nav-tabs flex-column" id="nav-tab" role="tablist">
                    <button onclick="changeURLParameter('customers')" class="dashboard-nav nav-link active" id="nav-customers-tab" data-bs-toggle="tab" data-bs-target="#nav-customers" type="button" role="tab" aria-controls="nav-customers" aria-selected="true">Customers</button>
                    <button onclick="changeURLParameter('moderators')" class="dashboard-nav nav-link" id="nav-moderators-tab" data-bs-toggle="tab" data-bs-target="#nav-moderators" type="button" role="tab" aria-controls="nav-moderators" aria-selected="false">Moderators</button>
                    <button onclick="expandCatalogue()" class="nav-link" id="nav-catalogue-tab" type="button" aria-controls="nav-catalogue" aria-selected="false">Catalogue</button>
                    <button onclick="changeURLParameter('products')" class="dashboard-nav nav-link hidden" id="nav-products-tab" data-bs-toggle="tab" data-bs-target="#nav-products" type="button" role="tab" aria-controls="nav-products" aria-selected="false">Products</button>
                    <button onclick="changeURLParameter('categories')" class="dashboard-nav nav-link hidden" id="nav-categories-tab" data-bs-toggle="tab" data-bs-target="#nav-categories" type="button" role="tab" aria-controls="nav-categories" aria-selected="false">Categories</button>
                    <button onclick="changeURLParameter('discounts')" class="dashboard-nav nav-link" id="nav-discounts-tab" data-bs-toggle="tab" data-bs-target="#nav-discounts" type="button" role="tab" aria-controls="nav-discounts" aria-selected="false">Discounts</button>
                </div>
            </nav>
            <div class="tab-content col-lg-10" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-customers" role="tabpanel" aria-labelledby="nav-customers-tab">
                    <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                        <a href="add-customer" class="add-data btn btn-primary" id="add-customer">Add Customer</a>
                    </div>
                    <%
                        List<Customer> customers = (List<Customer>) request.getAttribute("customers");
                        if(customers == null){
                            customers = new ArrayList<>();
                        }
                    %>
                    <c:set var="customers" value="<%=customers%>"/>
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
                                    <td><c:out value = "${customer.lastName}"/></td>
                                    <td><c:out value = "${customer.firstName}"/></td>
                                    <td><c:out value = "${customer.address.streetAddress}"/></td>
                                    <td><c:out value = "${customer.address.postalCode}"/></td>
                                    <td><c:out value = "${customer.address.city}"/></td>
                                    <td><c:out value = "${customer.address.country}"/></td>
                                    <td><c:out value = "${customer.email}"/></td>
                                    <td><c:out value = "${customer.phoneNumber}"/></td>
                                    <td>
                                        <a href="">
                                            <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="delete-customer?id=${customer.getId()}">
                                            <button class="btn rounded"><span class="material-symbols-outlined">delete</span></button>
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
                    <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                        <a href="add-moderator" class="add-data btn btn-primary" id="add-moderator">Add Moderator</a>
                    </div>
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
                                <td>
                                    <a href="">
                                        <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                    </a>
                                </td>
                                <td>
                                    <a href="delete-moderator?id=${moderator.getId()}">
                                        <button class="btn rounded"><span class="material-symbols-outlined">delete</span></button>
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
                    <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                        <a href="add-product" class="add-data btn btn-primary" id="add-product">Add Product</a>
                    </div>
                    <%
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        if(products == null){
                            products = new ArrayList<>();
                        }
                    %>
                    <c:set var="products" value="<%=products%>"/>
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
                                <td>
                                    <a href="">
                                        <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                    </a>
                                </td>
                                <td>
                                    <a href="delete-product?id=${product.getId()}">
                                        <button class="btn rounded"><span class="material-symbols-outlined">delete</span></button>
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
                    <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                        <a href="add-category" class="add-data btn btn-primary" id="add-category">Add Category</a>
                    </div>
                    <%
                        List<Category> categories = (List<Category>) request.getAttribute("categories");
                        if(categories == null){
                            categories = new ArrayList<>();
                        }
                    %>
                    <c:set var="categories" value="<%=categories%>"/>
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
                                <td>
                                    <a href="">
                                        <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                    </a>
                                </td>
                                <td>
                                    <a href="delete-category?id=${category.getId()}">
                                        <button class="btn rounded"><span class="material-symbols-outlined">delete</span></button>
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
                    <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                        <a href="add-discount" class="add-data btn btn-primary" id="add-discount">Add Discount</a>
                    </div>
                    <%
                        List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
                        if(discounts == null){
                            discounts = new ArrayList<>();
                        }
                    %>
                    <c:set var="discounts" value="<%=discounts%>"/>
                    <table class="table table-striped table-hover" id="discounts-table" data-filter-control-visible="false">
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
                                <td>
                                    <a href="">
                                        <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                    </a>
                                </td>
                                <td>
                                    <a href="delete-discount?id=${discount.getId()}">
                                        <button class="btn rounded"><span class="material-symbols-outlined">delete</span></button>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <script>
                        new DataTable('#discounts-table');
                    </script>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../../../layout/footer.jsp" />
</body>
</html>
