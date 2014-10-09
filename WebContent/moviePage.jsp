<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@include file="css.jsp" %>
		<title>Movie</title>
	</head>
	<body>
		<c:if test="${not empty movie }">
			<h1>Movie ${movie.title}</h1>
			<div id="displayAlerts" class="bg-info">
				<c:if test="${!empty msg}">
					${msg}
				</c:if>
			</div>
			<div class="movieItem">
				<div class="poster"><img src="${movie.poster }" /></div>
				<div class="title"><h3>${movie.title}</h3></div>
				<div class="synopsis">${movie.synopsis}</div>
				<c:if test="${not empty movie.movieCinemas }">
					<div class="times">
						<table class="table table-hover">
							<tr>
								<th>Cinema</th>
								<th>Day</th>
								<th>Time</th>
								<c:if test="${logged}">
									<th>Book</th>
								</c:if>
							</tr>
							<c:forEach items="${movie.movieCinemas}" var="movieCinema" varStatus="cc">
								<c:forEach items="${movieCinema.times}" var="movieTime" varStatus="cc2">
									<tr>
										<td>${movieCinema.cinema.name}</td>
										<td>${movieTime.day}</td>
										<td>${movieTime.time}</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</table>
					</div>
				</c:if>
				<!-- Admin options -->
				<c:if test="${admin}">
				<form class="form-horizontal" role="form" method="POST" action="movies">
					<div class="form-group">
				    	<label for="cinema" class="col-sm-2 control-label">Cinema</label>
				    	<div class="col-sm-10">
					    	<select class="form-control" name="cinema" id="cinema">
					    		<option value="">Select Cinema</option>
					    		<c:forEach items="${cinemas}" var="cinema" varStatus="loopCC">
							  		<option value="${cinema.id}">${cinema.name}</option>
							  	</c:forEach>
							</select>
						</div>
				  	</div>
				  	<div class="form-group">
				    	<label for="day" class="col-sm-2 control-label">Day</label>
				    	<div class="col-sm-10">
					    	<select class="form-control" name="day" id="day">
					    		<option value="">Select Day</option>
						  		<option value="Sunday">Sunday</option>
						  		<option value="Monday">Monday</option>
						  		<option value="Tuesday">Tuesday</option>
						  		<option value="Wednesday">Wednesday</option>
						  		<option value="Thursday">Thursday</option>
						  		<option value="Friday">Friday</option>
						  		<option value="Saturday">Saturday</option>
							</select>
						</div>
				  	</div>
				  	<div class="form-group">
				    	<label for="time" class="col-sm-2 control-label">Time</label>
				    	<div class="col-sm-5">
					    	<select class="form-control" name="hour" id="hour">
					    		<option value="">Hours</option>
					    		<c:forEach begin="0" end="23" var="i">
					    			<option value="${i}">${i}</option>
					    		</c:forEach>
							</select>
						</div>
						<div class="col-sm-5">
					    	<select class="form-control" name="minute" id="minute">
					    		<option value="">Minutes</option>
					    		<c:forEach begin="0" end="55" var="i" step="5">
					    			<option value="${i}">${i}</option>
					    		</c:forEach>
							</select>
						</div>
				  	</div>
				  	<input type="hidden" name="movie" value="${movie.id}">
					<input type="hidden" name="action" value="addMovieTime">
					<input type="hidden" name="action-method" value="save">
					<button type="submit" class="btn btn-default">Add</button>
				</form>
				</c:if>
				<!-- User options -->
				<c:if test="${logged}">
				<!-- Form to add comments -->
				</c:if>
			</div>
		</c:if>
		<c:if test="${empty movie }">
			<h1>Movie</h1>
			<div id="displayAlerts" class="bg-info">We can't find a movie to show you. Try again with another one please.</div>
		</c:if>
	</body>
</html>