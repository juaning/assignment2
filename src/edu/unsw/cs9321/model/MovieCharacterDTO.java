package edu.unsw.cs9321.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE_CHARACTER")
public class MovieCharacterDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="FIRST_NAME")
	private String firstName;
	@Column(name="LAST_NAME")
	private String lastName;
	
	@ManyToOne
	@JoinColumn(name="MOVIE_CHARACTER_TYPE_ID",referencedColumnName="id")
	private MovieCharacterTypeDTO movieCharacterType;
	
	@ManyToMany(mappedBy="movieCharacters")
//	@OneToMany(cascade=CascadeType.ALL)
//    @JoinTable(name="MOVIE_HAS_MOVIE_CHARACTER",  
//    joinColumns={@JoinColumn(name="MOVIE_CHARACTER_ID", referencedColumnName="id")},  
//    inverseJoinColumns={@JoinColumn(name="MOVIE_ID", referencedColumnName="id")})  
    private Set<MovieDTO> movies;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public MovieCharacterTypeDTO getMovieCharacterType() {
		return movieCharacterType;
	}

	public void setMovieCharacterType(MovieCharacterTypeDTO movieCharacterType) {
		this.movieCharacterType = movieCharacterType;
	}

	public Set<MovieDTO> getMovies() {
		return movies;
	}

	public void setMovies(Set<MovieDTO> movies) {
		this.movies = movies;
	}

}
