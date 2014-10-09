package edu.unsw.cs9321.model;

import java.sql.Time;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TIMES")
public class TimeDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="DAY")
	private String day;
	@Column(name="TIME")
	private Time time;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="MOVIE_CINEMA_HAS_TIMES",  
    joinColumns={@JoinColumn(name="TIMES_ID", referencedColumnName="id")},  
    inverseJoinColumns={@JoinColumn(name="MOVIE_CINEMA_ID", referencedColumnName="id")})  
    private Set<MovieCinemaDTO> movieCinemas;
	
	@OneToMany(mappedBy="time", fetch=FetchType.LAZY)
	private Set<MovieBookedDTO> movieBookeds;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public Set<MovieCinemaDTO> getMovieCinemas() {
		return movieCinemas;
	}
	public void setMovieCinemas(Set<MovieCinemaDTO> movieCinemas) {
		this.movieCinemas = movieCinemas;
	}
	public Set<MovieBookedDTO> getMovieBookeds() {
		return movieBookeds;
	}
	public void setMovieBookeds(Set<MovieBookedDTO> movieBookeds) {
		this.movieBookeds = movieBookeds;
	}
}
