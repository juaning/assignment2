<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@include file="css.jsp" %>
		<title>Movie Results</title>
	</head>
	<body>
		<h1>Result</h1>
		<div id="displayAlerts" class="bg-info">
			<c:if test="${!empty msg}">
				${msg}
			</c:if>
		</div>
		<div class="row">
			<c:forEach items="${movies}" var="movie" varStatus="loopCC">
				<div class="col-md-4 movie-list">
					<div class="image"><img src="${movie.poster}" /></div>
					<div class="title"><strong>Title:</strong> ${movie.title}</div>
					<div class="genre"><strong>Genre:</strong> ${movie.movieGenre.genre}</div>
					<c:if test="${not empty movie.movieCharacters }">
						<div class="actors">
							<strong>Actors:</strong>
							<ul>
								<c:forEach items="${movie.movieCharacters}" var="character" varStatus="ccChar">
									<c:if test="${character.movieCharacterType.type ne 'Director' }">
										<li>${character.firstName} ${character.lastName}</li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					<c:if test="${movie.movieRating ne '0' }">
						<div class="rating"><strong>Rating:</strong> ${movie.movieRating}</div>
					</c:if>
					<c:if test="${admin}">
						<div class="action"><a href="movies?action=addMovieTime&movie=${movie.id }">Add Session</a></div>
					</c:if>
					<c:if test="${logged}">
						<div class="action"><a href="movies?action=movieDetails&movie=${movie.id }">See Details</a></div>
					</c:if>
				</div>
				<c:if test="${loopCC.count % 3 == 0 }">
					</div>
					<div class="row">
				</c:if>
			</c:forEach>
		</div>
		<c:if test="${logged}">
			<a href="?action=userBookings">My bookings</a> | 
			<a href="?action=addUser">Edit my data</a> | 
			<a href="?action=logout">Logout</a>
		</c:if>
		<%@include file="javascript.jsp" %>
	</body>
</html>