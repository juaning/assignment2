package edu.unsw.cs9321.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE_BOOKED_PAYMENT")
public class MovieBookedPaymentDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="CREDIT_CARD_NAME")
	private String creditCardName;
	@Column(name="CREDIT_CARD_NUMBER")
	private String creditCardNumber;
	@Column(name="CREDIT_CARD_CSV")
	private int creditCardCsv;
	
	@OneToOne
//	@MapsId
	@JoinColumn(name="MOVIE_BOOKED_ID",referencedColumnName="id")
	private MovieBookedDTO movieBooked;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreditCardName() {
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public int getCreditCardCsv() {
		return creditCardCsv;
	}

	public void setCreditCardCsv(int creditCardCsv) {
		this.creditCardCsv = creditCardCsv;
	}

	public MovieBookedDTO getMovieBooked() {
		return movieBooked;
	}

	public void setMovieBooked(MovieBookedDTO movieBooked) {
		this.movieBooked = movieBooked;
	}
}
