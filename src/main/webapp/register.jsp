<%@ page import="j2ee_project.dto.UserDTO" %>
<%@ page import="jakarta.validation.ConstraintViolation" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 05/11/2023
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <jsp:include page="include.jsp"/>
</head>
<body>
<%--    <jsp:include page="../..layout/header.jsp"/>--%>
    <main>
        <form method="post" action="${pageContext.request.contextPath}/RegisterCustomerController">
            <c:if test="${requestScope.emailInDbError != null}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${requestScope.emailInDbError}"/>
                </div>
            </c:if>
            <c:if test="${requestScope.RegisterProcessError != null}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${requestScope.RegisterProcessError}"/>
                </div>
            </c:if>
            <div class="form-group">
                <label for="firstName">First name</label>
                <input type="text" class="form-control ${requestScope.InputError.firstName != null? 'is-invalid' : ''}" id="firstName" name="firstName" aria-describedby="firstNameHelp" placeholder="First name" required>
                <small id="firstNameHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.firstName}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="lastName">Last name</label>
                <input type="text" class="form-control ${requestScope.InputError.lastName != null ? 'is-invalid' : ''}" id="lastName" name="lastName" aria-describedby="lastNameHelp" placeholder="Last name" required>
                <small id="lastNameHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.lastName}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" class="form-control ${requestScope.InputError.email != null ? 'is-invalid' : ''}" id="email" name="email" aria-describedby="emailHelp" placeholder="Email" required>
                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.email}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control ${requestScope.InputError.password != null ? 'is-invalid' : ''}" id="password" name="password" aria-describedby="passwordHelp" placeholder="Password" required>
                <small id="passwordHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.password}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Password confirmation</label>
                <input type="password" class="form-control ${requestScope.InputError.confirmPassword != null ? 'is-invalid' : ''}" id="confirmPassword" name="confirmPassword" aria-describedby="confirmPasswordHelp" placeholder="Password confirmation" required>
                <small id="confirmPasswordHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.confirmPassword}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="phoneNumber">Phone number</label>
                <input type="text" class="form-control ${requestScope.InputError.phoneNumber != null ? 'is-invalid' : ''}" id="phoneNumber" name="phoneNumber" aria-describedby="phoneNumberHelp" placeholder="Phone number" required>
                <small id="phoneNumberHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.phoneNumber}"/>
                </div>
            </div>


            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </main>
<%--    <jsp:include page="../..layout/&footer.jsp"/>--%>
</body>
</html>
