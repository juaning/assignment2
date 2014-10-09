package edu.unsw.cs9321.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.cs9321.model.CinemaDAO;
import edu.unsw.cs9321.model.CinemaDTO;
import edu.unsw.cs9321.model.MovieCinemaDTO;
import edu.unsw.cs9321.model.MovieDAO;
import edu.unsw.cs9321.model.MovieDTO;
import edu.unsw.cs9321.model.TimeDTO;

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
	private static final String saveMethod = "save";
	private static final String searchMovie = "searchMovie";
	private static final String searchForm = "searchForm";
       
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
	private void processRequests(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = "";
		String forwardPage = "";
		String actionMethod = request.getParameter("action-method");
		// TODO: get if admin
		boolean admin = true;
		if (request.getParameter("action") == null) {
			// Show movie list
			MovieDAO movies = new MovieDAO();
			List<MovieCinemaDTO> nowShowingMovies = movies.getNowShowingMovies();
			request.setAttribute("nowShowing", nowShowingMovies);
			forwardPage = "welcome.jsp";
		} else {
			action = request.getParameter("action");
			// Cinema owner actions
			// TODO: Add check if admin user logged
			if (action.equals(addCinema)) {
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
			} else if (action.equals(addMovie)) {
				MovieDAO movieController = new MovieDAO();
				if(actionMethod != null) {
					if (actionMethod.equals(saveMethod)) {
						// Save movie and show message
						String msg = "The movie has been saved.";
						MovieDTO movie;
						try {
							movie = movieController.setMovieValuesFromRequest(request);
							movieController.saveMovie(movie);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
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
			} else if (action.equals(searchForm)) {
				MovieDAO movieController = new MovieDAO();
				request.setAttribute("genres", movieController.getMovieGenreList());
				forwardPage = "searchForm.jsp";
			}else if (action.equals(searchMovie)) {
				MovieDAO movieController = new MovieDAO();
				List<MovieDTO> movies = null;
				String msg = "There was an error with your search. Please try again";
				if (request.getParameter("q") != null) {
					movies = movieController.getMoviesByTitle(request.getParameter("q"));
				} else if (request.getParameter("genre") != null || request.getParameter("genre") != "0") {
					System.out.println("I've shouldn't be coming here");
					movies = movieController.getMoviesByGenre(Long.parseLong(request.getParameter("genre")));
				}
				if (movies != null) {
					forwardPage = "searchResult.jsp";
					request.setAttribute("movies", movies);
				} else {
					forwardPage = "searchForm.jsp";
					request.setAttribute("msg", msg);
					request.setAttribute("genres", movieController.getMovieGenreList());
				}
				request.setAttribute("admin", admin);
			} else if (action.equals(addMovieTime)) {
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
							
							movieController.saveMovie(movie);
							cinemaController.saveCinema(cinema);
						}	
					}
					forwardPage = "moviePage.jsp";
					request.setAttribute("movie", movie);
					request.setAttribute("admin", admin);
					request.setAttribute("cinemas", cinemas);
				} else {
					forwardPage = "searchResult.jsp";
					String msg = "You need to select a movie.";
					request.setAttribute("msg", msg);
				}
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+forwardPage);
		dispatcher.forward(request, response);
	}

}