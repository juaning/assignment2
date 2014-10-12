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
				<div class="genre"><strong>Genre:</strong> ${movie.movieGenre.genre}</div>
				<div class="synopsis"><strong>Synopsis:</strong>${movie.synopsis}</div>
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
				<c:if test="${not empty movie.movieCinemas }">
					<div class="times">
						<strong>Times:</strong>
						<table class="table table-hover">
							<tr>
								<th>Cinema</th>
								<th>Day</th>
								<th>Time</th>
								<c:if test="${logged}">
									<th>Qty</th>
									<th>Book</th>
								</c:if>
							</tr>
							<c:forEach items="${movie.movieCinemas}" var="movieCinema" varStatus="cc">
								<c:forEach items="${movieCinema.times}" var="movieTime" varStatus="cc2">
									<tr>
										<td>${movieCinema.cinema.name}</td>
										<td>${movieTime.day}</td>
										<td>${movieTime.time}</td>
										<c:if test="${logged}">
											
												<td>
													<form role="form" method="POST" action="movies">
														<select class="form-control" name="qty" id="qty">
												    		<option value="0">Tickets</option>
													  		<c:forEach begin="1" end="10" var="i">
												    			<option value="${i}">${i}</option>
												    		</c:forEach>
														</select>
														<input type="hidden" name="action" value="addBook" />
														<input type="hidden" name="timeId" value="${movieTime.id}" />
														<input type="hidden" name="movieCinemaId" value="${movieCinema.id}" />
														<button type="submit" class="btn btn-primary real-submit" style="display:none;">Book</button>
													</form>
												</td>
												<td><button type="button" class="btn btn-primary fake-submit">Book</button></td>
											
										</c:if>
									</tr>
								</c:forEach>
							</c:forEach>
						</table>
					</div>
				</c:if>
				<!-- Only released movies can have show times and comments -->
				<jsp:useBean id="now" class="java.util.Date"/>
				<c:if test="${movie.releaseDate lt now}">
					<!-- Admin options -->
					<c:if test="${admin}">
						<strong>Add Times:</strong>
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
					<c:if test="${logged && !admin}">
						<!-- Comments list -->
						<strong>Comments:</strong>
						<c:if test="${not empty movie.movieComments }">
							<c:forEach items="${movie.movieComments}" var="comment" varStatus="commentsCC">
								<blockquote>
								  	<p>${comment.review}</p>
								  	<footer>Rated ${comment.rating} by <cite title="${comment.user.nickname }">${comment.user.nickname }</cite></footer>
								</blockquote>
							</c:forEach>
						</c:if>
						<!-- Form to add comments -->
						<strong>Add Comment:</strong>
						<form class="form-horizontal" role="form" method="POST" action="movies">
							<div class="form-group">
						    	<label for="rating" class="col-sm-2 control-label">Rating</label>
						    	<div class="col-sm-10">
							    	<select class="form-control" name="rating" id="rating">
							    		<option value="">Rating</option>
								  		<option value="1">1</option>
								  		<option value="2">2</option>
								  		<option value="3">3</option>
								  		<option value="4">4</option>
								  		<option value="5">5</option>
									</select>
								</div>
						  	</div>
							<div class="form-group">
						    	<label for="review" class="col-sm-2 control-label">Review</label>
						    	<div class="col-sm-10">
							    	<textarea name="review" id="review" class="form-control" rows="3"></textarea>
								</div>
						  	</div>
						  	<input type="hidden" name="movie" value="${movie.id}">
							<input type="hidden" name="action" value="addComment">
							<button type="submit" class="btn btn-default">Comment</button>
						</form>
					</c:if>
				</c:if>
			</div>
		</c:if>
		<c:if test="${empty movie }">
			<h1>Movie</h1>
			<div id="displayAlerts" class="bg-info">We can't find a movie to show you. Try again with another one please.</div>
		</c:if>
		<%@include file="javascript.jsp" %>
		<script>
			$(document).ready(function() {
				$('.fake-submit').on('click', function(e) {
					e.preventDefault();
					var real = $(this).parent().prev().find('.real-submit');
					var qty = $(this).parent().prev().find('#qty').val();
					if (qty > 0)
						$(real).trigger('click');
				})
			});
		</script>
	</body>
</html>