package edu.unsw.cs9321.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CINEMA_AMENITIES")
public class CinemaAmenityDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="AMENITY")
	private String amenity;
	
	//(cascade=CascadeType.NONE)
	@ManyToMany(mappedBy="cinemaAmenities")
//    @JoinTable(name="CINEMA_HAS_CINEMA_AMENITIES",  
//    joinColumns={@JoinColumn(name="CINEMA_AMENITIES_ID", referencedColumnName="id")},  
//    inverseJoinColumns={@JoinColumn(name="CINEMA_ID", referencedColumnName="id")})  
    private Set<CinemaDTO> cinemas;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAmenity() {
		return amenity;
	}
	public void setAmenity(String amenity) {
		this.amenity = amenity;
	}
	public Set<CinemaDTO> getCinemas() {
		return cinemas;
	}
	public void setCinemas(Set<CinemaDTO> cinemas) {
		this.cinemas = cinemas;
	}
}
