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
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE_CINEMA")
public class MovieCinemaDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="RELEASE_DATE")
	private Date releaseDate;
	
	@ManyToOne
	@JoinColumn(name="CINEMA_ID",referencedColumnName="id")
	private CinemaDTO cinema;
	
	@ManyToOne
	@JoinColumn(name="MOVIE_ID",referencedColumnName="id")
	private MovieDTO movie;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="MOVIE_CINEMA_HAS_TIMES",  
    joinColumns={@JoinColumn(name="MOVIE_CINEMA_ID", referencedColumnName="id")},  
    inverseJoinColumns={@JoinColumn(name="TIMES_ID", referencedColumnName="id")})  
    private Set<TimeDTO> times;
	
	@OneToMany(mappedBy="movieCinema", fetch=FetchType.LAZY)
	private Set<MovieBookedDTO> movieBookeds;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public CinemaDTO getCinema() {
		return cinema;
	}

	public void setCinema(CinemaDTO cinema) {
		this.cinema = cinema;
	}

	public MovieDTO getMovie() {
		return movie;
	}

	public void setMovie(MovieDTO movie) {
		this.movie = movie;
	}

	public Set<TimeDTO> getTimes() {
		return times;
	}

	public void setTimes(Set<TimeDTO> times) {
		this.times = times;
	}

	public Set<MovieBookedDTO> getMovieBookeds() {
		return movieBookeds;
	}

	public void setMovieBookeds(Set<MovieBookedDTO> movieBookeds) {
		this.movieBookeds = movieBookeds;
	}
}
