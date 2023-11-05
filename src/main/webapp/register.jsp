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
        <form>
            <div class="form-group">
                <label for="firstName">First name</label>
                <input type="text" class="form-control" id="firstName" aria-describedby="firstNameHelp" placeholder="First name">
                <small id="firstNameHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">

                </div>
            </div>
            <div class="form-group">
                <label for="lastName">Email address</label>
                <input type="text" class="form-control" id="lastName" aria-describedby="lastNameHelp" placeholder="Last name">
                <small id="lastNameHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">

                </div>
            </div>
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Email">
                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">

                </div>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" aria-describedby="passwordHelp" placeholder="Password">
                <small id="passwordHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">

                </div>
            </div>
            <div class="form-group">
                <label for="phoneNumber">Password</label>
                <input type="password" class="form-control" id="phoneNumber" aria-describedby="phoneNumberHelp" placeholder="Phone number">
                <small id="phoneNumberHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                <div class="invalid-feedback">

                </div>
            </div>


            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </main>
<%--    <jsp:include page="../..layout/&footer.jsp"/>--%>
</body>
</html>
