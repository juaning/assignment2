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
		<h3>Now showing</h3>
		<div class="row">
			<c:forEach items="${nowShowing}" var="cinema" varStatus="loopCC">
				<%-- <c:forEach items="${cinema.movies}" var="movie"> --%>
					<div class="col-md-4 movie-list">
						<div class="image"><img src="${cinema.movie.poster}" /></div>
						<div class="title">${cinema.movie.title}</div>
						<div class>${cinema.cinema.name}</div>
					</div>
					<c:if test="${loopCC.count % 3 == 0 }">
						</div>
						<div class="row">
					</c:if>
				<%-- </c:forEach> --%>
			</c:forEach>
		</div>
		<h3>Coming soon</h3>
		<%@include file="javascript.jsp" %>
	</body>
</html>