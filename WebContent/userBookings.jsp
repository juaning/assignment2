<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@include file="css.jsp" %>
		<title>User Bookings</title>
	</head>
	<body>
		<h1>Your bookings</h1>
		<div id="displayAlerts" class="bg-info">
			<c:if test="${!empty msg}">
				${msg}
			</c:if>
		</div>
		<c:if test="${not empty user.movieBookeds}">
			<table class="table table-hover">
				<tr>
					<th>Book Id</th>
					<th>Cinema</th>
					<th>Movie</th>
					<th>Time</th>
					<th>Seats</th>
				</tr>
				<c:forEach items="${user.movieBookeds}" var="movie" varStatus="loopCC">
					<tr>
						<td>${movie.id}</td>
						<td>${movie.movieCinema.cinema.name}</td>
						<td>${movie.movieCinema.movie.title}</td>
						<td>${movie.time.day} at ${movie.time.time}</td>
						<td>${movie.seats}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${logged}">
			<a href="?action=userBookings">My bookings</a> | 
			<a href="?action=addUser">Edit my data</a> | 
			<a href="?action=logout">Logout</a>
		</c:if>
	</body>
</html>