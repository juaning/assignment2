package edu.unsw.cs9321.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.unsw.comp9321.mail.exceptions.MailSenderException;
import edu.unsw.comp9321.mail.exceptions.ServiceLocatorException;
import edu.unsw.cs9321.model.CinemaDAO;
import edu.unsw.cs9321.model.CinemaDTO;
import edu.unsw.cs9321.model.MovieBookedDTO;
import edu.unsw.cs9321.model.MovieBookedPaymentDTO;
import edu.unsw.cs9321.model.MovieCinemaDTO;
import edu.unsw.cs9321.model.MovieCommentDTO;
import edu.unsw.cs9321.model.MovieDAO;
import edu.unsw.cs9321.model.MovieDTO;
import edu.unsw.cs9321.model.TimeDTO;
import edu.unsw.cs9321.model.UserDAO;
import edu.unsw.cs9321.model.UserDTO;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet(name="MainServlet", urlPatterns ="/movies/*")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MainServlet.class.getName());
	private static final String addCinema = "addCinema";
	private static final String addMovie = "addMovie";
	private static final String addMovieTime = "addMovieTime";
	private static final String addUser = "addUser";
	private static final String saveMethod = "save";
	private static final String updateMethod = "update";
	private static final String searchMovie = "searchMovie";
	private static final String searchForm = "searchForm";
	private static final String typeAdmin = "Admin";
	private static final String movieDetails = "movieDetails";
	private static final String addComment = "addComment";
	private static final String addBook = "addBook";
	private static final String addPayment = "addPayment";
	private static final String login = "login";
	private static final String showLogin = "showLogin";
	private static final String userBookings = "userBookings";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequests(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequests(request,response);
	}
	
	/**
	 * Processes get and post request
	 * @param request object
	 * @param response object
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void processRequests(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = "";
		String forwardPage = "";
		String actionMethod = request.getParameter("action-method");
		HttpSession session = request.getSession();
		UserDTO loggedUser = null;
		boolean isAdmin = false;
		boolean isLogged = false;
		if(session.getAttribute("user") != null) {
			loggedUser = (UserDTO) session.getAttribute("user");
			isLogged = true;
			if (loggedUser.getUserType().getType().equals(typeAdmin)) {
				isAdmin = true;
			}
		}
		if (request.getParameter("action") == null) {
			// Show movie list
			MovieDAO movieController = new MovieDAO();
			List<MovieDTO> nowShowing = movieController.getNowShowingMoviesForLanding();
			List<MovieDTO> comingSoon = movieController.getComingSoonMoviesForLanding();
			request.setAttribute("nowShowing", nowShowing);
			request.setAttribute("comingSoon", comingSoon);
			forwardPage = "welcome.jsp";
		} else {
			action = request.getParameter("action");
			// Cinema owner actions
			if (action.equals(addCinema) && isAdmin) {
				CinemaDAO cinemaController = new CinemaDAO();
				if(actionMethod != null) {
					if (actionMethod.equals(saveMethod)) {
						// Save cinema and show message
						String msg = "The cinema has been saved.";
						CinemaDTO cinema = cinemaController.setCinemaValuesFromRequest(request);
						cinemaController.saveCinema(cinema);
						request.setAttribute("msg", msg);
					}
				}
				request.setAttribute("amenities", cinemaController.getAllAmenities());
				forwardPage = "addCinema.jsp";
			} else if (action.equals(addMovie) && isAdmin) {
				MovieDAO movieController = new MovieDAO();
				if(actionMethod != null) {
					if (actionMethod.equals(saveMethod)) {
						// Save movie and show message
						String msg = "The movie has been saved.";
						MovieDTO movie;
						try {
//							System.out.println("Release Date: " + request.getParameter("releaseDate"));
							movie = movieController.setMovieValuesFromRequest(request);
							movieController.saveMovie(movie);
						} catch (ParseException e) {
							msg = "The movie could not be saved. Please try again.";
							e.printStackTrace();
						}
						request.setAttribute("msg", msg);
					}
				}
				request.setAttribute("genres", movieController.getMovieGenreList());
				request.setAttribute("ratings", movieController.getMovieAgeRatingList());
				request.setAttribute("directors", movieController.getAllDirectors());
				request.setAttribute("actresses", movieController.getAllActresses());
				request.setAttribute("actors", movieController.getAllActors());
				forwardPage = "addMovie.jsp";
			} else if (action.equals(searchForm) && isLogged) {
				MovieDAO movieController = new MovieDAO();
				request.setAttribute("genres", movieController.getMovieGenreList());
				forwardPage = "searchForm.jsp";
			}else if (action.equals(searchMovie) && isLogged) {
				MovieDAO movieController = new MovieDAO();
				List<MovieDTO> movies = null;
				String msg = "There was an error with your search. Please try again";
				String q = request.getParameter("q");
				String strGenre = request.getParameter("genre");
				if (q != null && q.length() > 0) {
					movies = movieController.getMoviesByTitle(request.getParameter("q"));
				} else if (strGenre != null && strGenre.length() > 0 && !strGenre.equals("0")) {
					movies = movieController.getMoviesByGenre(Long.parseLong(request.getParameter("genre")));
				}
				if (movies != null && movies.size() > 0) {
					forwardPage = "searchResult.jsp";
					List<MovieDTO> moviesWithRating = new ArrayList<MovieDTO>();
					Iterator<MovieDTO> iter = movies.iterator();
					while (iter.hasNext()) {
						MovieDTO m = iter.next();
						m.setMovieRating(movieController.getMovieRating(m));
						moviesWithRating.add(m);
					}
					request.setAttribute("movies", moviesWithRating);
				} else {
					forwardPage = "searchForm.jsp";
					request.setAttribute("msg", msg);
					request.setAttribute("genres", movieController.getMovieGenreList());
				}
			} else if ((action.equals(addMovieTime) && isAdmin) || (action.equals(movieDetails) && isLogged)) {
				MovieDAO movieController = new MovieDAO();
				CinemaDAO cinemaController = new CinemaDAO();
				MovieDTO movie = null;
				if (request.getParameter("movie") != null) {
					String movieId = request.getParameter("movie");
					List<CinemaDTO> cinemas = cinemaController.getAllCinemas();
					movie = movieController.getMovieById(Long.parseLong(movieId));
					if(actionMethod != null) {
						if (actionMethod.equals(saveMethod)) {
							Long cinemaId = Long.parseLong(request.getParameter("cinema"));
							String day = request.getParameter("day");
							String hour = request.getParameter("hour");
							String minute = request.getParameter("minute");
							DateFormat formatter = new SimpleDateFormat ("HH:mm");
							CinemaDTO cinema = cinemaController.getCinemaById(cinemaId);
							MovieCinemaDTO movieCinema = movieController.getMovieCinemaByMovieAndCinema(movie, cinema);
							TimeDTO time = new TimeDTO();
							time.setDay(day);
							if (minute.length() < 2) {
								minute = "0" + minute;
							}
							try {
								java.sql.Time objTime = new java.sql.Time(formatter.parse(hour + ":" + minute).getTime());
								time.setTime(objTime);
							} catch (ParseException e) {
								System.out.println(e);
							}
							Set<TimeDTO> listTimes = movieCinema.getTimes();
							if (listTimes == null) {
								listTimes = new HashSet<TimeDTO> ();
							}
							listTimes.add(time);
							movieCinema.setTimes(listTimes);
							movieController.saveMovieCinema(movieCinema);
							
							Set<MovieCinemaDTO> mcMovie = movie.getMovieCinemas();
							if (mcMovie == null) {
								mcMovie = new HashSet<MovieCinemaDTO> ();
							}
							mcMovie.add(movieCinema);
							movie.setMovieCinemas(mcMovie);
							
							Set<MovieCinemaDTO> mcCinema = cinema.getMovieCinemas();
							if (mcCinema == null) {
								mcCinema = new HashSet<MovieCinemaDTO> ();
							}
							mcCinema.add(movieCinema);
							cinema.setMovieCinemas(mcCinema);
							
							movie = movieController.saveMovie(movie);
							cinema = cinemaController.saveCinema(cinema);
						}	
					}
					movie.setMovieRating(movieController.getMovieRating(movie));
					forwardPage = "moviePage.jsp";
					request.setAttribute("movie", movie);
					request.setAttribute("cinemas", cinemas);
				} else {
					forwardPage = "searchResult.jsp";
					String msg = "You need to select a movie.";
					request.setAttribute("msg", msg);
				}
			} else if (action.equals(addUser)) {
				forwardPage = "userData.jsp";
				UserDAO userController = new UserDAO();
				String msg = "";
				String title = "Add User";
				// Save
				if (actionMethod != null) {
					if (actionMethod.equals(saveMethod)) {
						try {
							UserDTO user = userController.setUserFromRequestParams(request);
							user = userController.setRegistrationCode(user);
							user = userController.setCreatedTime(user);
							user = userController.saveUser(user);
							userController.sendEmail(user);
							session.setAttribute("user", user);
							forwardPage = "userTransition.jsp";
							msg = "You should receive an email with a link to activate your account shortly.";
						} catch (NoSuchAlgorithmException e) {
							msg = "There was an error. User could not be created.";
							request.setAttribute("msg", msg);
						} catch (AddressException e) {
							msg = "The email Address is not right.";
						} catch (ServiceLocatorException e) {
							msg = "Email service provider does not exists.";
						} catch (MailSenderException e) {
							msg = "There was an error with your email activation.";
						} catch (MessagingException e) {
							msg = "There was an error with your email activation.";
						}
					} else if (actionMethod.equals(updateMethod)) { // Update
						try {
							if (loggedUser != null) {
								loggedUser = userController.setUpdateUserFromRequest(loggedUser, request);
							} else {
								System.out.println("Logged user's null");
							}
							loggedUser = userController.saveUser(loggedUser);
							session.setAttribute("user", loggedUser);
							msg = "Data updated successfuly";
						} catch (NoSuchAlgorithmException e) {
							msg = "There was an error while updating your data";
						}
					}
				}
				// Validate
				if (request.getParameter("v") != null) {
					String code = request.getParameter("v");
					UserDTO user = userController.getUserByRegistrationCode(code);
					if (user != null) {
						// Delete registration code
						user.setRegistrationCode(null);
						user = userController.saveUser(user);
						title = "Welcome " + user.getUsername();
						request.setAttribute("logged", true);
						request.setAttribute("user", user);
						session.setAttribute("user", user);
					} else {
						forwardPage = "userTransition.jsp";
						msg = "We coudn't find that activation code in our registry.";
						request.setAttribute("msg", msg);
					}
				}
				request.setAttribute("title", title);
			} else if (action.equals(addComment) && isLogged) {
				MovieDAO movieController = new MovieDAO();
				CinemaDAO cinemaController = new CinemaDAO();
				MovieDTO movie = null;
				MovieCommentDTO comment = null;
				List<CinemaDTO> cinemas = cinemaController.getAllCinemas();
				
				String msg = "Your comment could not be saved. Please try again.";
				if (request.getParameter("movie") != null && request.getParameter("movie").length() > 0) {
					String movieId = request.getParameter("movie");
					movie = movieController.getMovieById(Long.parseLong(movieId));
					comment = movieController.setMovieCommentFromRequest(request);
					System.out.println("Title: " + movie.getTitle());
					System.out.println("Characters: " + movie.getMovieCharacters().size());
					if (comment != null) {
						comment.setUser(loggedUser);
						comment.setMovie(movie);
						comment = movieController.saveMovieComment(comment);
						Set<MovieCommentDTO> comments =  movie.getMovieComments();
						comments.add(comment);
						movie.setMovieComments(comments);
						movie.setMovieRating(movieController.getMovieRating(movie));
						msg = "Your comment was saved. Thanks for sharing.";
					}
				}
				forwardPage = "moviePage.jsp";
				request.setAttribute("movie", movie);
				request.setAttribute("cinemas", cinemas);
			} else if (action.equals(addBook) && isLogged) {
				MovieDAO movieController = new MovieDAO();
				Long timeId = Long.parseLong(request.getParameter("timeId"));
				Long movieCinemaId = Long.parseLong(request.getParameter("movieCinemaId"));
				int bookQty = Integer.parseInt(request.getParameter("qty"));
				String msg = "Booked confirmed";
				MovieCinemaDTO mCinema = movieController.getMovieCinemaById(movieCinemaId);
				TimeDTO objTime = movieController.getTimeById(timeId);
				MovieBookedDTO mBook = new MovieBookedDTO();
				int occupedSeats = movieController.getBookedSeatsPerSession(mCinema, objTime);
				if ((occupedSeats + bookQty) > mCinema.getCinema().getCapacity()) {
					msg = "No seats available for this session";
					request.setAttribute("msg", msg);
					forwardPage = "moviePage.jsp";
					request.setAttribute("movie", mCinema.getMovie());
					request.setAttribute("cinemas", mCinema.getCinema());
				} else {
					mBook.setMovieCinema(mCinema);
					mBook.setTime(objTime);
					mBook.setUser(loggedUser);
					mBook.setSeats(bookQty);
					mBook = movieController.saveMovieBooked(mBook);
					forwardPage = "bookCheckout.jsp";
					request.setAttribute("movieBooked", mBook);
				}
			} else if (action.equals(addPayment) && isLogged) {
				MovieDAO movieController = new MovieDAO ();
				Long bookId = Long.parseLong(request.getParameter("movieBookedId"));
				MovieBookedDTO booked = movieController.getMovieBookedById(bookId);
				MovieBookedPaymentDTO payment = movieController.setMovieBookedPaymentFromRequest(request);
				payment.setMovieBooked(booked);
				payment = movieController.saveMovieBookedPayment(payment);
				forwardPage = "searchForm.jsp";
				request.setAttribute("msg", "Your payment was accepted. Your booking is complete. You can access your booking details in your account page.");
			} else if (action.equals(showLogin)) {
				forwardPage = "login.jsp";
			} else if (action.equals(login)) {
				UserDAO userController = new UserDAO();
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				UserDTO user;
				String msg = "";
				try {
					user = userController.loginUser(username, password);
					if (user != null) {
						session.setAttribute("user", user);
						if (user.getUserType().getType().equals(typeAdmin)) {
							forwardPage = "addCinema.jsp";
							CinemaDAO cinemaController = new CinemaDAO();
							request.setAttribute("amenities", cinemaController.getAllAmenities());
							isAdmin = true;
						} else {
							forwardPage = "searchForm.jsp";
							MovieDAO movieController = new MovieDAO();
							request.setAttribute("genres", movieController.getMovieGenreList());
							isLogged = true;
						}
					}
				} catch (NoSuchAlgorithmException e) {
					forwardPage = "login.jsp";
					msg = "Please check your username/password and try again.";
				}
			} else if (action.equals(userBookings) && isLogged) {
				forwardPage = "userBookings.jsp";
				request.setAttribute("user", loggedUser);
			} else if (action.equals(userBookings) && isLogged) {
				MovieDAO movieController = new MovieDAO();
				List<MovieDTO> nowShowing = movieController.getNowShowingMoviesForLanding();
				List<MovieDTO> comingSoon = movieController.getComingSoonMoviesForLanding();
				request.setAttribute("nowShowing", nowShowing);
				request.setAttribute("comingSoon", comingSoon);
				forwardPage = "welcome.jsp";
				String msg = "You logged out successfuly.";
				session.setAttribute("user", null);
				session.invalidate();
				isLogged = false;
				isAdmin = false;
				request.setAttribute("msg", msg);
			} else {
				MovieDAO movieController = new MovieDAO();
				List<MovieDTO> nowShowing = movieController.getNowShowingMoviesForLanding();
				List<MovieDTO> comingSoon = movieController.getComingSoonMoviesForLanding();
				request.setAttribute("nowShowing", nowShowing);
				request.setAttribute("comingSoon", comingSoon);
				forwardPage = "welcome.jsp";
				String msg = "You need to be logged to access this page.";
				request.setAttribute("msg", msg);
			}
		}
		request.setAttribute("admin", isAdmin);
		request.setAttribute("logged", isLogged);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+forwardPage);
		dispatcher.forward(request, response);
	}

}
