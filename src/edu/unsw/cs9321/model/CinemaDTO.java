package edu.unsw.cs9321.model;

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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "CINEMA")
public class CinemaDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="NAME")
	private String name;
	@Column(name="CAPACITY")
	private long capacity;

	@OneToOne ( cascade = {CascadeType.ALL})
	@JoinColumn(name="ADDRESS_ID",referencedColumnName="id")
	private AddressDTO address;
	
	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="CINEMA_HAS_CINEMA_AMENITIES",  
    joinColumns={@JoinColumn(name="CINEMA_ID", referencedColumnName="id")},  
    inverseJoinColumns={@JoinColumn(name="CINEMA_AMENITIES_ID", referencedColumnName="id")})  
    private Set<CinemaAmenityDTO> cinemaAmenities;
	
	@OneToMany(mappedBy="cinema", fetch=FetchType.LAZY)
	@OrderBy(clause = "releaseDate asc")
	private Set<MovieCinemaDTO> movieCinemas;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public Set<CinemaAmenityDTO> getCinemaAmenities() {
		return cinemaAmenities;
	}

	public void setCinemaAmenities(Set<CinemaAmenityDTO> cinemaAmenities) {
		this.cinemaAmenities = cinemaAmenities;
	}

	public Set<MovieCinemaDTO> getMovieCinemas() {
		return movieCinemas;
	}

	public void setMovieCinemas(Set<MovieCinemaDTO> movieCinemas) {
		this.movieCinemas = movieCinemas;
	}
}
