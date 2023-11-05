<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" name="viewport" content="width=device-width; initial-scale=1; text/html; charset=UTF-8">
	<jsp:include page="include.jsp" />
	<title>Home</title>
	<style>
		#hide-checkbox {
			opacity: 0;
			height: 0;
			width: 0;
		}

		.toggle {
			position: relative;
			cursor: pointer;
			display: inline-block;
			width: 100px;
			height: 50px;
			background: #211042;
			border-radius: 50px;
			transition: 500ms;
			overflow: hidden;
		}

		.toggle-button {
			position: absolute;
			display: inline-block;
			top: 7px;
			left: 6px;
			width: 35px;
			height: 35px;
			border-radius: 50%;
			background: white;
			overflow: hidden;
			transition: all 500ms ease-out;
		}

		#hide-checkbox:checked + .toggle {
			background: lightgrey;
		}


		#hide-checkbox:checked + .toggle .toggle-button {
			background: black;
			transform: translateX(50px);
		}
	</style>
</head>
<body>
	<jsp:include page="layout/header.jsp" />
	<div class="container">
		<p>Test</p>
		<a href="browse-products">Browse products</a>
		<a href="mailTest.jsp">TEST MAIL</a>
		<p></p>
		<a href="profile-informations">Profile</a>
		<div class="form-check form-switch">
			<input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
			<label class="form-check-label" for="flexSwitchCheckDefault">Default switch checkbox input</label>
		</div>

		<i class="bi bi-moon-fill"></i>
		<div>
			<input type="checkbox" id="hide-checkbox">
			<label for="hide-checkbox" class="toggle">
				<span class="toggle-button bi bi-moon-fill"></span>
			</label>
		</div>

		<div class="jumbotron text-center">
			<h1>My First Bootstrap Page</h1>
			<p>Resize this responsive page to see the effect!</p>
		</div>
		<div class="row">
			<div class="col-sm-4">
				<h3>Column 1</h3>
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
				<p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
			</div>
			<div class="col-sm-4">
				<h3>Column 2</h3>
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
				<p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
			</div>
			<div class="col-sm-4">
				<h3>Column 3</h3>
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
				<p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="d-flex align-items-start">
			<div class="nav flex-column nav-pills me-3" id="v-pills-tab" role="tablist" aria-orientation="vertical">
				<button class="nav-link active" id="v-pills-clients-tab" data-bs-toggle="pill" data-bs-target="#v-pills-home" type="button" role="tab" aria-controls="v-pills-home" aria-selected="true">Clients</button>
				<button class="nav-link" id="v-pills-products-tab" data-bs-toggle="pill" data-bs-target="#v-pills-profile" type="button" role="tab" aria-controls="v-pills-profile" aria-selected="false">Products</button>
				<button class="nav-link" id="v-pills-categories-tab" data-bs-toggle="pill" data-bs-target="#v-pills-messages" type="button" role="tab" aria-controls="v-pills-messages" aria-selected="false">Categories</button>
			</div>

			<div class="tab-content" id="v-pills-tabContent">
				<div class="tab-pane fade show active" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-clients-tab">
					<table id="dtBasicExample" class="table table-striped table-bordered table-sm" style="width:100%">
						<thead>
						<tr>
							<th>Name</th>
							<th>Position</th>
							<th>Office</th>
							<th>Age</th>
							<th>Start date</th>
							<th>Salary</th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td>Tiger Nixon</td>
							<td>System Architect</td>
							<td>Edinburgh</td>
							<td>61</td>
							<td>2011-04-25</td>
							<td>$320,800</td>
						</tr>
						</tbody>
						<tfoot>
						<tr>
							<th>Name</th>
							<th>Position</th>
							<th>Office</th>
							<th>Age</th>
							<th>Start date</th>
							<th>Salary</th>
						</tr>
						</tfoot>
					</table>
					<script>
						new DataTable('#dtBasicExample');
					</script>
				</div>
				<div class="tab-pane fade" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-products-tab">...</div>
				<div class="tab-pane fade" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-categories-tab">...</div>
			</div>
		</div>
	</div>
	<jsp:include page="layout/footer.jsp" />
</body>
</html>