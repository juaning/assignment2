package edu.unsw.cs9321.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "MOVIE")
public class MovieDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="TITLE")
	private String title;
	@Column(name="POSTER")
	private String poster;
	@Column(name="SYNOPSIS")
	private String synopsis;
	@Column(name="RELEASE_DATE")
	private Date releaseDate;
	
	@ManyToOne
	@JoinColumn(name="AGE_RATING_ID",referencedColumnName="id")
	private AgeRatingDTO ageRating;
	
	@ManyToOne
	@JoinColumn(name="MOVIE_GENRE_ID",referencedColumnName="id")
	private MovieGenreDTO movieGenre;
	
	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="MOVIE_HAS_MOVIE_CHARACTER",  
    joinColumns={@JoinColumn(name="MOVIE_ID", referencedColumnName="id")},  
    inverseJoinColumns={@JoinColumn(name="MOVIE_CHARACTER_ID", referencedColumnName="id")})  
    private Set<MovieCharacterDTO> movieCharacters;
	
	@OneToMany(mappedBy="movie", fetch=FetchType.LAZY)
	private Set<MovieCinemaDTO> movieCinemas;
	
	@OneToMany(mappedBy="movie", fetch=FetchType.LAZY)
	@OrderBy(clause = "createdTime asc")
	private Set<MovieCommentDTO> movieComments;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public AgeRatingDTO getAgeRating() {
		return ageRating;
	}
	public void setAgeRating(AgeRatingDTO ageRating) {
		this.ageRating = ageRating;
	}
	public MovieGenreDTO getMovieGenre() {
		return movieGenre;
	}
	public void setMovieGenre(MovieGenreDTO movieGenre) {
		this.movieGenre = movieGenre;
	}
	public Set<MovieCharacterDTO> getMovieCharacters() {
		return movieCharacters;
	}
	public void setMovieCharacters(Set<MovieCharacterDTO> movieCharacters) {
		this.movieCharacters = movieCharacters;
	}
	public Set<MovieCinemaDTO> getMovieCinemas() {
		return movieCinemas;
	}
	public void setMovieCinemas(Set<MovieCinemaDTO> movieCinemas) {
		this.movieCinemas = movieCinemas;
	}
	public Set<MovieCommentDTO> getMovieComments() {
		return movieComments;
	}
	public void setMovieComments(Set<MovieCommentDTO> movieComments) {
		this.movieComments = movieComments;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
}
