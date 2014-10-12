<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@include file="css.jsp" %>
		<title>Movie Checkout</title>
	</head>
	<body>
		<h1>Movie ${movie.title}</h1>
		<div id="displayAlerts" class="bg-info">
			<c:if test="${!empty msg}">
				${msg}
			</c:if>
		</div>
		<form class="form-horizontal" role="form" method="post" action="movies">
		  	<div class="form-group">
		    	<label for="ccname" class="col-sm-2 control-label">Name as in Credit Card</label>
		    	<div class="col-sm-10">
		      		<input type="text" class="form-control" name="ccname" id="ccname" placeholder="Name">
		    	</div>
		  	</div>
		  	<div class="form-group">
		    	<label for="ccnumber" class="col-sm-2 control-label">Credit Card Number</label>
		    	<div class="col-sm-10">
		      		<input type="text" class="form-control" name="ccnumber" id="ccnumber" placeholder="Credit Card Number">
		    	</div>
		  	</div>
		  	<div class="form-group">
		    	<label for="cccsv" class="col-sm-2 control-label">Credit Card CVV</label>
		    	<div class="col-sm-10">
		      		<input type="text" class="form-control" name="cccsv" id="cccsv" placeholder="Credit Card CVV">
		    	</div>
		  	</div>
		  	<input type="hidden" name="movieBookedId" value="${movieBooked.id}">
		  	<input type="hidden" name="movieBookedSeats" value="${movieBooked.seats}">
			<input type="hidden" name="action" value="addPayment">
			<button type="submit" class="btn btn-default">Checkout</button>
		</form>
		<c:if test="${logged}">
			<a href="?action=userBookings">My bookings</a> | 
			<a href="?action=addUser">Edit my data</a> | 
			<a href="?action=logout">Logout</a>
		</c:if>
		<%@include file="javascript.jsp" %>
	</body>
</html>