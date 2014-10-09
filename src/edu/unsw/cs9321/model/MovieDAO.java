package edu.unsw.cs9321.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import edu.unsw.cs9321.common.EntityManagerUtil;

public class MovieDAO {
	private EntityManager em;
	public static final String nowShowing = "Now Showing";
	public static final String comingSoon = "Coming Soon";
	public static final String director = "Director";
	public static final String actress = "Actress";
	public static final String actor = "Actor";
	
	public MovieDAO() {
		em = EntityManagerUtil.getEntityManager();
	}
	
	public List<MovieCinemaDTO> getMovies(String showTime) {
		try {
			em.getTransaction().begin();
			Query q = null;
			if(showTime.equals(nowShowing)) {
				q = em.createQuery("SELECT m FROM MovieCinemaDTO m WHERE m.releaseDate <= :today");
			} else if (showTime.equals(comingSoon)) {
				q = em.createQuery("SELECT m FROM MovieCinemaDTO m WHERE m.releaseDate > :today");
			}
			q.setParameter("today", new java.util.Date());
			@SuppressWarnings("unchecked")
			List<MovieCinemaDTO> movies = (List<MovieCinemaDTO>) q.getResultList();
	    	em.getTransaction().commit();
	    	return movies;
		} catch (Exception e) {
	    	System.out.println(e);
	    	em.getTransaction().rollback();
	    }
		return null;
	}
	
	public List<MovieCinemaDTO> getNowShowingMovies() {
		return getMovies(nowShowing);
	}
	
	public List<MovieCinemaDTO> getComingSoonMovies() {
		return getMovies(comingSoon);
	}
	
	/**
	 * Get Movies by Title
	 * @param String title
	 * @return List<MovieDTO>
	 */
	public List<MovieDTO> getMoviesByTitle (String title) {
		em.getTransaction().begin();
		Query q = em.createQuery(" FROM MovieDTO WHERE title LIKE :title");
		q.setParameter("title", "%" + title +"%");
		@SuppressWarnings("unchecked")
		List<MovieDTO> movies = (List<MovieDTO>) q.getResultList();
		em.getTransaction().commit();
    	return movies;
	}
	
	/**
	 * Get Movie by Genre Id
	 * @param Long id
	 * @return List<MovieDTO>
	 */
	public List<MovieDTO> getMoviesByGenre (Long id) {
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT m FROM MovieDTO m JOIN m.movieGenre g  WHERE g.id=:id");
		q.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<MovieDTO> movies = (List<MovieDTO>) q.getResultList();
		em.getTransaction().commit();
    	return movies;
	}
	
	/**
	 * Get Movie by Id
	 * @param Long id
	 * @return MovieDTO
	 */
	public MovieDTO getMovieById (Long id) {
		MovieDTO movie = em.find(MovieDTO.class, id);
		return movie;
	}
	
	/**
	 * Get List of All Genres
	 * @return List<MovieGenreDTO>
	 */
	public List<MovieGenreDTO> getMovieGenreList() {
		@SuppressWarnings("unchecked")
		List<MovieGenreDTO> genres = em.createQuery("FROM MovieGenreDTO").getResultList();
		return genres;
	}
	
	/**
	 * Get Movie Rating List
	 * @return List<AgeRatingDTO>
	 */
	public List<AgeRatingDTO> getMovieAgeRatingList() {
		@SuppressWarnings("unchecked")
		List<AgeRatingDTO> rating = em.createQuery("FROM AgeRatingDTO").getResultList();
		return rating;
	}
	
	/**
	 * Get characters by type
	 * @param String type
	 * @return List<MovieCharacterDTO>
	 */
	@SuppressWarnings("unchecked")
	public List<MovieCharacterDTO> getCharacterListByType (String type) {
		List<MovieCharacterDTO> characters = null;
		Query q = em.createQuery("SELECT c FROM MovieCharacterDTO c JOIN c.movieCharacterType t WHERE t.type=:type");
		q.setParameter("type", type);
		characters = q.getResultList();
		return characters;
	}
	
	public List<MovieCharacterDTO> getAllDirectors () {
		return getCharacterListByType(director);
	}
	
	public List<MovieCharacterDTO> getAllActresses () {
		return getCharacterListByType(actress);
	}
	
	public List<MovieCharacterDTO> getAllActors () {
		return getCharacterListByType(actor);
	}
	
	/**
	 * Get Genre by id
	 * @param Long id
	 * @return MovieGenreDTO
	 */
	public MovieGenreDTO getMovieGenreById(Long id) {
		MovieGenreDTO genre = em.find(MovieGenreDTO.class, id);
		return genre;
	}
	
	/**
	 * Get Age Rating by id
	 * @param Long id
	 * @return AgeRatingDTO
	 */
	public AgeRatingDTO getAgeRatingById(Long id) {
		AgeRatingDTO rating = em.find(AgeRatingDTO.class, id);
		return rating;
	}
	
	/**
	 * Transform from Request to Movie object
	 * @param HttpServletRequest request
	 * @return MovieDTO
	 * @throws ParseException
	 */
	public MovieDTO setMovieValuesFromRequest (HttpServletRequest request) throws ParseException {
		MovieDTO movie = new MovieDTO();
		MovieGenreDTO genre = getMovieGenreById(Long.parseLong(request.getParameter("genre")));
		AgeRatingDTO rating = getAgeRatingById(Long.parseLong(request.getParameter("rating")));
		Set<MovieDTO> moviesByGenre = genre.getMovies();
		Set<MovieDTO> moviesByRating = rating.getMovies();
		Set<MovieCharacterDTO> charactersOriginal = setMovieCharactersFromRequest(request);
		Set<MovieCharacterDTO> characters = new HashSet<MovieCharacterDTO> (); 
		movie.setTitle(request.getParameter("title"));
		movie.setPoster(request.getParameter("poster"));
		movie.setSynopsis(request.getParameter("synopsis"));
		Date date = new SimpleDateFormat("dd-MM-yyyy").parse(request.getParameter("releaseDate"));
		movie.setReleaseDate(date);
		movie.setAgeRating(rating);
		movie.setMovieGenre(genre);
		moviesByGenre.add(movie);
		moviesByRating.add(movie);
		genre.setMovies(moviesByGenre);
		rating.setMovies(moviesByRating);
		Iterator<MovieCharacterDTO> iter = charactersOriginal.iterator();
		while (iter.hasNext()) {
			MovieCharacterDTO character = iter.next();
			Set<MovieDTO> movies = character.getMovies();
			movies.add(movie);
			character.setMovies(movies);
			characters.add(character);
		}
		movie.setMovieCharacters(characters);
		return movie;
	}
	
	/**
	 * Set Movie Characters (Director, Actress, Actor) from request
	 * @param HttpServletRequest request
	 * @return Set<MovieCharacterDTO>
	 */
	public Set<MovieCharacterDTO> setMovieCharactersFromRequest (HttpServletRequest request) {
		Set<MovieCharacterDTO> characters = new HashSet<MovieCharacterDTO> ();
		String[] director = request.getParameterValues("director");
		String[] actress = request.getParameterValues("actress");
		String[] actor = request.getParameterValues("actor");
		Set<MovieCharacterDTO> tmpChar = setCharacterFromStringArray(director);
		if (tmpChar != null) {
			characters.addAll(tmpChar);
		}
		tmpChar = setCharacterFromStringArray(actress);
		if (tmpChar != null) {
			characters.addAll(tmpChar);
		}
		tmpChar = setCharacterFromStringArray(actor);
		if (tmpChar != null) {
			characters.addAll(tmpChar);
		}
		return characters;
	}
	
	/**
	 * Set characters list from an array of ids
	 * @param String[] strCharacters
	 * @return Set<MovieCharacterDTO>
	 */
	public Set<MovieCharacterDTO> setCharacterFromStringArray (String[] strCharacters) {
		if (strCharacters.length < 1) {
			return null;
		}
		Set<MovieCharacterDTO> characters = new HashSet<MovieCharacterDTO> ();
		for (int i = 0; i < strCharacters.length; i++) {
			MovieCharacterDTO character = em.find(MovieCharacterDTO.class, Long.parseLong(strCharacters[i]));
			characters.add(character);
		}
		return characters;
	}
	
	/**
	 * Persist Movie and relations on database
	 * @param MovieDTO movie
	 * @return MovieDTO
	 */
	public MovieDTO saveMovie (MovieDTO movie) {
		try {
			em.getTransaction().begin();
			movie = em.merge(movie);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Couldn't save, rolling back. " + e);
			em.getTransaction().rollback();
		}
		return movie;
	}
	
	/**
	 * Get Movie Cinema if exists or create a new one if not
	 * @param MovieDTO movie
	 * @param CinemaDTO cinema
	 * @return MovieCinemaDTO
	 */
	public MovieCinemaDTO getMovieCinemaByMovieAndCinema (MovieDTO movie, CinemaDTO cinema) {
		MovieCinemaDTO movieCinema = null;
		Query q = em.createQuery("SELECT m FROM MovieCinemaDTO m WHERE m.movie=:movie AND m.cinema=:cinema");
		q.setParameter("movie", movie);
		q.setParameter("cinema", cinema);
		@SuppressWarnings("unchecked")
		List<MovieCinemaDTO> listMovieCinema = q.getResultList();
		if (listMovieCinema.size() > 0) {
			movieCinema = listMovieCinema.get(0);
		} else {
			movieCinema = new MovieCinemaDTO();
			movieCinema.setReleaseDate(movie.getReleaseDate());
			movieCinema.setCinema(cinema);
			movieCinema.setMovie(movie);
		}
		return movieCinema;
	}
	
	public MovieCinemaDTO saveMovieCinema (MovieCinemaDTO movieCinema) {
		try {
			em.getTransaction().begin();
			movieCinema = em.merge(movieCinema);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Couldn't save, rolling back. " + e);
			em.getTransaction().rollback();
		}
		return movieCinema;
	}
}
