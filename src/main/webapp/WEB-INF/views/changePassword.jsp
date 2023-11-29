<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 23/11/2023
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change password</title>
    <jsp:include page="../../include.jsp"/>
    <script src="${pageContext.request.contextPath}/dependencies/jquery/jquery.validate.min.js"></script>
</head>
<body>
    <jsp:include page="../../layout/header.jsp"/>
    <main>
        <h1>Register</h1>
        <form id="registerForm" name="registerForm" method="post" action="${pageContext.request.contextPath}/register-customer-controller">
            <c:if test="${requestScope.emailOrPhoneNumberInDbError != null}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${requestScope.emailOrPhoneNumberInDbError}"/>
                </div>
            </c:if>
            <c:if test="${requestScope.RegisterProcessError != null}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${requestScope.RegisterProcessError}"/>
                </div>
            </c:if>
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
            <button id="submitButton" type="submit" class="btn btn-primary">Submit</button>
        </form>
    </main>
    <jsp:include page="../../layout/footer.jsp"/>
    <script>
        $(function()
        {
            $.validator.addMethod("patternPassword", function (value){
                return value.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,24}$/) != null;
            }, "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24.")

            $("form[name='registerForm']").validate({
                rules: {
                    email: {
                        required: true,
                        email: true
                    },
                    password: {
                        required: true,
                        patternPassword: true
                    },
                    confirmPassword: {
                        required: true,
                        equalTo: "#password"
                    },
                },
                messages: {
                    email: {
                        required: "Please enter an email address",
                        email: "Email is not valid."
                    },
                    password: {
                        required: "Please provide a password",
                    },
                    confirmPassword: {
                        required: "Please enter a confirmation password",
                        equalTo: "Password and Confirm Password must match."
                    },
                },
                submitHandler: function(form) {
                    form.submit();
                }
            })
        })
    </script>
</body>
</html>
