<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
	<jsp:include page="include.jsp" />
	<title>Home</title>
</head>
<body>
	<jsp:include page="layout/header.jsp" />

	<div class="container">
		<p>Test</p>
		<a href="browse-products">Browse products</a>
		<a href="mailTest.jsp">TEST MAIL</a>
		<div class="form-check form-switch">
			<input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
			<label class="form-check-label" for="flexSwitchCheckDefault">Default switch checkbox input</label>
		</div>


		HOME PAGE
	</div>
	<jsp:include page="layout/footer.jsp" />
</body>
</html>