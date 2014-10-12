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
		<h1>Welcome to CS Cinema Center</h1>
		<div id="displayAlerts" class="bg-info">
			<c:if test="${!empty msg}">
				${msg}
			</c:if>
		</div>
		<h3>Now showing</h3>
		<div class="row">
			<c:forEach items="${nowShowing}" var="movie" varStatus="loopCC">
				<div class="col-md-4 movie-list">
					<div class="image"><img src="${movie.poster}" /></div>
					<div class="title">${movie.title}</div>
				</div>
				<c:if test="${loopCC.count % 3 == 0 }">
					</div>
					<div class="row">
				</c:if>
			</c:forEach>
		</div>
		<h3>Coming soon</h3>
		<div class="row">
			<c:forEach items="${comingSoon}" var="movie" varStatus="loopCC">
				<div class="col-md-4 movie-list">
					<div class="image"><img src="${movie.poster}" /></div>
					<div class="title">${movie.title}</div>
				</div>
				<c:if test="${loopCC.count % 3 == 0 }">
					</div>
					<div class="row">
				</c:if>
			</c:forEach>
		</div>
		<c:choose>
			<c:when test="${logged}">
				<a href="?action=userBookings">My bookings</a> | 
				<a href="?action=addUser">Edit my data</a> | 
				<a href="?action=logout">Logout</a>
			</c:when>
			<c:otherwise>
				<a href="?action=showLogin">Sign In</a> | 
				<a href="?action=addUser">Sign Up</a>
			</c:otherwise>
		</c:choose>
		<%@include file="javascript.jsp" %>
	</body>
</html>