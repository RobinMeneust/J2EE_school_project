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
    <script src="dependencies/jquery/jquery.validate.min.js"></script>
</head>
<body>
<%--    <jsp:include page="../..layout/header.jsp"/>--%>
    <main>
        <form id="registerForm" method="post" action="${pageContext.request.contextPath}/RegisterCustomerController">
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
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.firstName}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="lastName">Last name</label>
                <input type="text" class="form-control ${requestScope.InputError.lastName != null ? 'is-invalid' : ''}" id="lastName" name="lastName" aria-describedby="lastNameHelp" placeholder="Last name" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.lastName}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" class="form-control ${requestScope.InputError.email != null ? 'is-invalid' : ''}" id="email" name="email" aria-describedby="emailHelp" placeholder="Email" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.email}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control ${requestScope.InputError.password != null ? 'is-invalid' : ''}" id="password" name="password" aria-describedby="passwordHelp" placeholder="Password" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.password}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Password confirmation</label>
                <input type="password" class="form-control ${requestScope.InputError.confirmPassword != null ? 'is-invalid' : ''}" id="confirmPassword" name="confirmPassword" aria-describedby="confirmPasswordHelp" placeholder="Password confirmation" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.confirmPassword}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="phoneNumber">Phone number</label>
                <input type="text" class="form-control ${requestScope.InputError.phoneNumber != null ? 'is-invalid' : ''}" id="phoneNumber" name="phoneNumber" aria-describedby="phoneNumberHelp" placeholder="Phone number" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.phoneNumber}"/>
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </main>
<%--    <jsp:include page="../..layout/&footer.jsp"/>--%>
    <script>
        $.validator.addMethod("pattern", function (value, element, param){
            return $.validator.optional(element) || value.match(param) != null;
        }, "Name not valid")
        /*$.validator.addMethod("patternName", function (value){
            return value.match(/^[a-zA-ZÀ-ÖØ-öø-ÿ\-']*$/) != null;
        }, "Name not valid")
        $.validator.addMethod("patternPassword", function (value){
            return value.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,24}$/) != null;
        }, "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24.")
        $.validator.addMethod("patternPhoneNumber", function (value){
            return value.match(/^[0-9]{10}$/) != null;
        }, "Phone number must be composed by 10 numbers with this format : 0000000000")*/
        $(document).ready(function(){
            $("#registerForm").validate({
                rules: {
                    firstName: {
                        required: true,
                        max: 30,
                        pattern: /^[a-zA-ZÀ-ÖØ-öø-ÿ\-']*$/,
                    },
                    lastName: {
                        required: true,
                        max: 30,
                        pattern: /^[a-zA-ZÀ-ÖØ-öø-ÿ\-']*$/,
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    password: {
                        pattern: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,24}$/
                    },
                    confirmPassword: {
                        equalTo: "#password"
                    },
                    phoneNumber: {
                        pattern: /^[0-9]{10}$/
                    }
                },
                messages: {
                    firstName: {
                        required: "First name can not be blank.",
                        max: "First name can not exceed 30 characters.",
                        pattern: "First name is not valid : only letters and -' are authorized.",
                    },
                    lastName: {
                        required: "Last name can not be blank.",
                        max: "Last name can not exceed 30 characters.",
                        pattern: "Last name is not valid : only letters and -' are authorized.",
                    },
                    email: {
                        required: "Last name can not be blank.",
                        email: "Email is not valid."
                    },
                    password: {
                        pattern: "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24."
                    },
                    confirmPassword: {
                        equalTo: "Password and Confirm Password must match."
                    },
                    phoneNumber: {
                        pattern: "Phone number must be composed by 10 numbers with this format : 0000000000"
                    }
                },
                submitHandler: function(form) {
                    form.submit();
                }
            })
        })
    </script>
</body>
</html>
