<%@page import="com.services.CustomerService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="Views/bootstrap.min.css">
	<link rel="stylesheet" href="Views/styles.css">
	<script src="Components/jquery-3.2.1.min.js"></script>
    <script src="Components/customer.js"></script>
	<title>ElectroGrid</title>
</head>
<body>
	<nav class="navbar navbar-light bg-dark">
	  <h4>Power Grid Management System</h4>
	</nav>
	<div class="customerBody">
		<div class="customerHeading">
			<h5>Customer Management</h5>
		</div>
		<div class="customerContent">
            <form id="formCustomer" name="formCustomer" method="POST" action="client.jsp">
                <div class="left">
                    First Name : <input id="firstName" name="firstName" type="text" class="form-control form-control-sm">
                    <br>
					Last Name : <input id="lastName"name="lastName" type="text" class="form-control form-control-sm">
					<br>
					Email : <input id="email" name="email" type="text" class="form-control form-control-sm">
					<br>
					Mobile : <input id="mobile" name="mobile" type="text" class="form-control form-control-sm">
					<br>
                </div>
                <div class="right">
					Address : <input id="address" name="address" type="text" class="form-control form-control-sm">
					<br>
					Postal Code : <input id="postalCode" name="postalCode" type="text" class="form-control form-control-sm">
					<br>
                </div>
				<div class="bottom">
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
					<br>
					<div class="displayErr">
						<div id="alertSuccess" class="alert alert-success" style="width: 100%;"></div>
						<div id="alertError" class="alert alert-danger" style="width: 100%;"></div>
					</div>
					<input type="hidden" name="hidCustomerIDSave" id="hidCustomerIDSave" value="">
				</div>
            </form>
		</div>
		<div id="divCustomerGrid">
			<%
			CustomerService customerService = new CustomerService(); 
			out.print(customerService.readAllCustomer());
			%>
		</div>
	</div>
</body>
</html>