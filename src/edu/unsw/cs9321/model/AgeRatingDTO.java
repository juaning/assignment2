package edu.unsw.cs9321.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "AGE_RATING")
public class AgeRatingDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="RATING")
	private String rating;
	
	@OneToMany(mappedBy="ageRating", fetch=FetchType.LAZY)
	@OrderBy(clause = "title asc")
	private Set<MovieDTO> movies;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public Set<MovieDTO> getMovies() {
		return movies;
	}
	public void setMovies(Set<MovieDTO> movies) {
		this.movies = movies;
	}
	
}