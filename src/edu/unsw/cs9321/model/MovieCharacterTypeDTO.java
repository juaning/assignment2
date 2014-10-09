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
@Table(name = "MOVIE_CHARACTER_TYPE")
public class MovieCharacterTypeDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="CHARACTER_TYPE")
	private String type;
	
	@OneToMany(mappedBy="movieCharacterType", fetch=FetchType.LAZY)
	@OrderBy(clause = "firstName asc")
	private Set<MovieCharacterDTO> movieCharacters;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<MovieCharacterDTO> getMovieCharacters() {
		return movieCharacters;
	}
	public void setMovieCharacters(Set<MovieCharacterDTO> movieCharacters) {
		this.movieCharacters = movieCharacters;
	}
}
