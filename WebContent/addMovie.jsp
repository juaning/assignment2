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
		    	<label for="title">Title</label>
		    	<input type="text" class="form-control" name="title" id="title" placeholder="Enter Title">
		  	</div>
		  	<div class="form-group">
		    	<label for="poster">Poster</label>
		    	<input type="text" class="form-control" name="poster" id="poster" placeholder="Enter Poster">
		  	</div>
		  	<div class="form-group">
		    	<label for="actor">Actor</label>
		    	<select multiple class="form-control" name="actor" id="actor">
		    		<c:forEach items="${actors}" var="actor" varStatus="loopCC">
				  		<option value="${actor.id}">${actor.firstName} ${actor.lastName}</option>
				  	</c:forEach>
				</select>
		  	</div>
		  	<div class="form-group">
		    	<label for="actress">Actress</label>
		    	<select multiple class="form-control" name="actress" id="actress">
		    		<c:forEach items="${actresses}" var="actress" varStatus="loopCC">
				  		<option value="${actress.id}">${actress.firstName} ${actress.lastName}</option>
				  	</c:forEach>
				</select>
		  	</div>
		  	<div class="form-group">
		    	<label for="genre">Genre</label>
		    	<select class="form-control" name="genre" id="genre">
		    		<c:forEach items="${genres}" var="genre" varStatus="loopCC">
				  		<option value="${genre.id}">${genre.genre}</option>
				  	</c:forEach>
				</select>
		  	</div>
		  	<div class="form-group">
		    	<label for="director">Director</label>
		    	<select multiple class="form-control" name="director" id="director">
		    		<c:forEach items="${directors}" var="director" varStatus="loopCC">
				  		<option value="${director.id}">${director.firstName} ${director.lastName}</option>
				  	</c:forEach>
				</select>
		  	</div>
		  	<div class="form-group">
		    	<label for="synopsis">Synopsis</label>
		    	<textarea rows="3" class="form-control" name="synopsis" id="synopsis"></textarea>
		  	</div>
		  	<div class="form-group">
		    	<label for="rating">Rating</label>
		    	<select class="form-control" name="rating" id="rating">
		    		<c:forEach items="${ratings}" var="rating" varStatus="loopCC">
				  		<option value="${rating.id}">${rating.rating}</option>
				  	</c:forEach>
				</select>
		  	</div>
		  	<div class="form-group">
		    	<label for="releaseDate">Release Date</label>
		    	<input type="date" class="form-control" name="releaseDate" id="releaseDate" placeholder="Enter Release Date">
		  	</div>
			<input type="hidden" name="action" value="addMovie">
	    	<input type="hidden" name="action-method" value="save">
		  	<button type="submit" class="btn btn-default">Save</button>
		</form>
		<%@include file="javascript.jsp" %>
	</body>
</html>