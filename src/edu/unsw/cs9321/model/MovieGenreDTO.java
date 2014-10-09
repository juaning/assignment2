package edu.unsw.cs9321.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "MOVIE_GENRE")
public class MovieGenreDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="GENRE")
	private String genre;
	
	@OneToMany(mappedBy="movieGenre")
	@OrderBy(clause = "title asc")
	private Set<MovieDTO> movies;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Set<MovieDTO> getMovies() {
		return movies;
	}
	public void setMovies(Set<MovieDTO> movies) {
		this.movies = movies;
	}
}
