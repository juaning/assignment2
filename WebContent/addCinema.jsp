<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@include file="css.jsp" %>
		<title>Movie List</title>
	</head>
	<body>
		<h1>Add Cinema</h1>
		<div id="displayAlerts" class="bg-info">
			<c:if test="${!empty msg}">
				${msg}
			</c:if>
		</div>
		<form role="form" method="POST" action="movies">
		  	<div class="form-group">
		    	<label for="name">Name</label>
		    	<input type="text" class="form-control" name="name" id="name" placeholder="Enter name">
		  	</div>
		  	<div class="form-group">
		    	<label for="capacity">Capacity</label>
		    	<input type="text" class="form-control" name="capacity" id="capacity" placeholder="Capacity">
		  	</div>
		  	<!-- <div class="form-group container">
		  		<h4>Address</h4> -->
	  		<div class="form-group">
		    	<label for="street">Street</label>
		    	<input type="text" class="form-control" name="street" id="street" placeholder="Street">
	    	</div>
	    	<div class="form-group">
		    	<label for="city">City</label>
		    	<input type="text" class="form-control" name="city" id="city" placeholder="City">
	    	</div>
	    	<div class="form-group">
		    	<label for="state">State</label>
		    	<input type="text" class="form-control" name="state" id="state" placeholder="State">
	    	</div>
	    	<div class="form-group">
		    	<label for="postcode">Postcode</label>
		    	<input type="text" class="form-control" name="postcode" id="postcode" placeholder="Postcode">
	    	</div>
		  	<!-- </div> -->
		  	<div class="form-group">
		    	<label for="amenities">Amenities</label>
		    	<c:forEach items="${amenities}" var="amenity" varStatus="loopCC">
			    	<div class="checkbox">
				    	<label>
				      		<input type="checkbox" name="amenities" value="${amenity.id}" > ${amenity.amenity}
				    	</label>
				  	</div>
				</c:forEach>
	    	</div>
	    	<input type="hidden" name="action" value="addCinema">
	    	<input type="hidden" name="action-method" value="save">
		  	<button type="submit" class="btn btn-default">Save</button>
		</form>
		<%@include file="javascript.jsp" %>
	</body>
</html>