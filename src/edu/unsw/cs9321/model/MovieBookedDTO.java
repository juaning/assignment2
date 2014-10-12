package edu.unsw.cs9321.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE_BOOKED")
public class MovieBookedDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="SEATS")
	private int seats;
	
	@OneToOne (mappedBy="movieBooked", cascade=CascadeType.ALL)
	private MovieBookedPaymentDTO movieBookedPayment;
	
	@ManyToOne
	@JoinColumn(name="CINEMA_USER_ID",referencedColumnName="id")
	private UserDTO user;
	
	@ManyToOne
	@JoinColumn(name="MOVIE_CINEMA_ID",referencedColumnName="id")
	private MovieCinemaDTO movieCinema;
	
	@ManyToOne
	@JoinColumn(name="TIMES_ID",referencedColumnName="id")
	private TimeDTO time;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public MovieBookedPaymentDTO getMovieBookedPayment() {
		return movieBookedPayment;
	}

	public void setMovieBookedPayment(MovieBookedPaymentDTO movieBookedPayment) {
		this.movieBookedPayment = movieBookedPayment;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public MovieCinemaDTO getMovieCinema() {
		return movieCinema;
	}

	public void setMovieCinema(MovieCinemaDTO movieCinema) {
		this.movieCinema = movieCinema;
	}

	public TimeDTO getTime() {
		return time;
	}

	public void setTime(TimeDTO time) {
		this.time = time;
	}
}
