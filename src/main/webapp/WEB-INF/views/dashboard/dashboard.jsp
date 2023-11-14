<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../include.jsp"/>
    <script type="application/javascript">
        function expandCatalogue(){
            const buttonProducts = document.getElementById("nav-products-tab");
            const buttonCategories = document.getElementById("nav-categories-tab");
            const spanChevron = document.getElementById("span-chevron");
            buttonProducts.classList.toggle("hidden");
            buttonCategories.classList.toggle("hidden");
            if (buttonProducts.classList.contains("hidden")){
                spanChevron.textContent = "chevron_right";
            } else {
                spanChevron.textContent = "expand_more";
            }
        }

        function changeURLParameter(tab){
            let url = new URL(window.location.href);
            url.searchParams.set("tab",tab);
            window.history.replaceState(null, null, url.href);
        }
    </script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard/dashboard.css">
</head>
<body>
    <jsp:include page="../../../layout/header.jsp" />
    <c:set var="tab" value="${param.tab}"/>
    <div class="container-fluid">
        <div class="d-flex">
            <nav class="nav-div-tabs mt-4">
                <div class="nav nav-tabs flex-column" id="nav-tab" role="tablist">
                    <button onclick="changeURLParameter('customers')"
                            class="nav-link
                                <c:if test="${tab=='customers'}">
                                    <c:out value="active"/>
                                </c:if>"
                            id="nav-customers-tab"
                            data-bs-toggle="tab"
                            data-bs-target="#nav-customers"
                            type="button"
                            role="tab"
                            aria-controls="nav-customers"
                            aria-selected="true">
                        <span class="d-flex justify-content-between flex-row">
                            Customers
                        </span>
                    </button>
                    <button onclick="changeURLParameter('moderators')"
                            class="nav-link
                                <c:if test="${tab=='moderators'}">
                                    <c:out value="active"/>
                                </c:if>"
                            id="nav-moderators-tab"
                            data-bs-toggle="tab"
                            data-bs-target="#nav-moderators"
                            type="button"
                            role="tab"
                            aria-controls="nav-moderators"
                            aria-selected="false">
                        <span class="d-flex justify-content-between flex-row">
                            Moderators
                        </span>
                    </button>
                    <button onclick="expandCatalogue()"
                            class="nav-link"
                            id="nav-catalogue-tab"
                            type="button"
                            aria-controls="nav-catalogue"
                            aria-selected="false">
                        <span class="d-flex justify-content-between flex-row">
                            <span>Catalogue</span>
                            <span class="material-symbols-outlined" id="span-chevron">chevron_right</span>
                        </span>
                    </button>
                    <button onclick="changeURLParameter('products')"
                            class="nav-link
                                <c:choose>
                                    <c:when test="${tab=='products'}">
                                        <c:out value="active"/>
                                    </c:when>
                                    <c:when test="${tab ne 'categories'}">
                                        <c:out value="hidden"/>
                                    </c:when>
                                </c:choose>"
                            id="nav-products-tab"
                            data-bs-toggle="tab"
                            data-bs-target="#nav-products"
                            type="button"
                            role="tab"
                            aria-controls="nav-products"
                            aria-selected="false">
                        <span class="d-flex justify-content-between flex-row">
                            Products
                        </span>
                    </button>
                    <button onclick="changeURLParameter('categories')"
                            class="nav-link
                                <c:choose>
                                    <c:when test="${tab=='categories'}">
                                        <c:out value="active"/>
                                    </c:when>
                                    <c:when test="${tab ne 'products'}">
                                        <c:out value="hidden"/>
                                    </c:when>
                                </c:choose>"
                            id="nav-categories-tab"
                            data-bs-toggle="tab"
                            data-bs-target="#nav-categories"
                            type="button"
                            role="tab"
                            aria-controls="nav-categories"
                            aria-selected="false">
                        <span class="d-flex justify-content-between flex-row">
                            Categories
                        </span>
                    </button>
                    <button onclick="changeURLParameter('discounts')"
                            class="nav-link
                                <c:if test="${tab=='discounts'}">
                                    <c:out value="active"/>
                                </c:if>"
                            id="nav-discounts-tab"
                            data-bs-toggle="tab"
                            data-bs-target="#nav-discounts"
                            type="button"
                            role="tab"
                            aria-controls="nav-discounts"
                            aria-selected="false">
                        <span class="d-flex justify-content-between flex-row">
                            Discounts
                        </span>
                    </button>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent">
                <div class="table-responsive tab-pane fade
                                <c:if test="${tab=='customers'}">
                                    <c:out value="show"/>
                                    <c:out value="active"/>
                                </c:if>"
                     id="nav-customers"
                     role="tabpanel"
                     aria-labelledby="nav-customers-tab">
                    <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                        <a href="add-customer" class="add-data btn btn-primary" id="add-customer">Add Customer</a>
                    </div>
                    <c:set var="customers" value="${requestScope.customers}"/>
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
                                        <a href="edit-customer?id=${customer.id}">
                                            <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="delete-customer?id=${customer.id}">
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
                <div class="tab-pane fade
                                <c:if test="${tab=='moderators'}">
                                    <c:out value="show"/>
                                    <c:out value="active"/>
                                </c:if>"
                     id="nav-moderators"
                     role="tabpanel"
                     aria-labelledby="nav-moderators-tab">
                    <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                        <a href="add-moderator" class="add-data btn btn-primary" id="add-moderator">Add Moderator</a>
                    </div>
                    <c:set var="moderators" value="${requestScope.moderators}"/>
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
                                <td><c:out value = "${moderator.lastName}"/></td>
                                <td><c:out value = "${moderator.firstName}"/></td>
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
                                <td><c:out value = "${moderator.email}"/></td>
                                <td><c:out value = "${moderator.phoneNumber}"/></td>
                                <td>
                                    <a href="">
                                        <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                    </a>
                                </td>
                                <td>
                                    <a href="delete-moderator?id=${moderator.id}">
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
                <div class="tab-pane fade
                                <c:if test="${tab=='products'}">
                                    <c:out value="show"/>
                                    <c:out value="active"/>
                                </c:if>"
                     id="nav-products"
                     role="tabpanel"
                     aria-labelledby="nav-products-tab">
                    <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                        <a href="add-product" class="add-data btn btn-primary" id="add-product">Add Product</a>
                    </div>
                    <c:set var="products" value="${requestScope.products}"/>
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
                                         src="<c:out value="${product.imageUrl}" />"
                                         class="card-img-top">
                                </td>
                                <td><c:out value="${product.name}"/></td>
                                <td><c:out value="${product.description}"/></td>
                                <td><c:out value="${product.stockQuantity}"/></td>
                                <td><c:out value="${product.unitPrice}"/></td>
                                <td><c:out value="${product.weight}"/></td>
                                <td><c:out value="${product.category.name}"/></td>
                                <td>
                                    <a href="">
                                        <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                    </a>
                                </td>
                                <td>
                                    <a href="delete-product?id=${product.id}">
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
                <div class="tab-pane fade
                                <c:if test="${tab=='categories'}">
                                    <c:out value="show"/>
                                    <c:out value="active"/>
                                </c:if>"
                     id="nav-categories"
                     role="tabpanel"
                     aria-labelledby="nav-discounts-tab">
                    <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                        <a href="add-category" class="add-data btn btn-primary" id="add-category">Add Category</a>
                    </div>
                    <c:set var="categories" value="${requestScope.categories}"/>
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
                                <td><c:out value = "${category.name}"/></td>
                                <td><c:out value = "${category.description}"/></td>

                                <td>
                                    <c:if test="${category.discount!=null}">
                                        <c:out value = "${category.discount.name}"/>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="">
                                        <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                    </a>
                                </td>
                                <td>
                                    <a href="delete-category?id=${category.id}">
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
                <div class="tab-pane fade
                                <c:if test="${tab=='discounts'}">
                                    <c:out value="show"/>
                                    <c:out value="active"/>
                                </c:if>"
                     id="nav-discounts"
                     role="tabpanel"
                     aria-labelledby="nav-discounts-tab">
                    <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                        <a href="add-discount" class="add-data btn btn-primary" id="add-discount">Add Discount</a>
                    </div>
                    <c:set var="discounts" value="${requestScope.discounts}"/>
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
                                <td><c:out value = "${discount.name}"/></td>
                                <td><c:out value = "${discount.startDate}"/></td>
                                <td><c:out value = "${discount.endDate}"/></td>
                                <td><c:out value = "${discount.discountPercentage}"/></td>
                                <td>
                                    <a href="">
                                        <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                    </a>
                                </td>
                                <td>
                                    <a href="delete-discount?id=${discount.id}">
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
