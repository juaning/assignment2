package edu.unsw.cs9321.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import edu.unsw.cs9321.common.EntityManagerUtil;

public class CinemaDAO {
	private EntityManager em;
	
	public CinemaDAO () {
		em = EntityManagerUtil.getEntityManager();
	}
	
	public CinemaDTO setCinemaValuesFromRequest (HttpServletRequest request) {
		CinemaDTO cinema = new CinemaDTO();
		AddressDTO address = setAddressFromRequest(request);
		Set<CinemaAmenityDTO> cinemaAmenitiesOriginal = setCinemaAmenitiesFromRequest(request);
		Set<CinemaAmenityDTO> cinemaAmenities = new HashSet<CinemaAmenityDTO>();
		address.setCinema(cinema);
		cinema.setCapacity(Long.parseLong(request.getParameter("capacity")));
		cinema.setName(request.getParameter("name"));
		cinema.setAddress(address);
		Iterator<CinemaAmenityDTO> iter = cinemaAmenitiesOriginal.iterator();
		while (iter.hasNext()) {
			CinemaAmenityDTO amenity = iter.next();
			Set<CinemaDTO> cinemas = amenity.getCinemas();
			cinemas.add(cinema);
			amenity.setCinemas(cinemas);
			cinemaAmenities.add(amenity);
		}
		cinema.setCinemaAmenities(cinemaAmenities);
		return cinema;
	}
	
	public AddressDTO setAddressFromRequest(HttpServletRequest request) {
		AddressDTO address = new AddressDTO();
		address.setCity(request.getParameter("city"));
		address.setPostcode(Long.parseLong(request.getParameter("postcode")));
		address.setState(request.getParameter("state"));
		address.setStreet(request.getParameter("street"));
		return address;
	}
	
	public Set<CinemaAmenityDTO> setCinemaAmenitiesFromRequest (HttpServletRequest request) {
		String[] amenities = request.getParameterValues("amenities");
		
		Set<CinemaAmenityDTO> amenitiesSet = new HashSet<CinemaAmenityDTO>();
		for (int i = 0; i < amenities.length; i++) {
			CinemaAmenityDTO amenity = em.find(CinemaAmenityDTO.class, Long.parseLong(amenities[i]));
			amenitiesSet.add(amenity);
		}
		return amenitiesSet;
	}
	
	@SuppressWarnings("unchecked")
	public List<CinemaAmenityDTO> getAllAmenities () {
		List<CinemaAmenityDTO> amenities = em.createQuery("FROM CinemaAmenityDTO").getResultList();
		return amenities;
	}
	
	public CinemaDTO saveCinema(CinemaDTO cinema) {
		try {
			em.getTransaction().begin();
			cinema = em.merge(cinema);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Couldn't save, rolling back. " + e);
			em.getTransaction().rollback();
		}
		return cinema;
	}
	
	public List<CinemaDTO> getAllCinemas() {
		@SuppressWarnings("unchecked")
		List<CinemaDTO> cinemas = em.createQuery("FROM CinemaDTO").getResultList();
		return cinemas;
	}
	
	public CinemaDTO getCinemaById (Long id) {
		CinemaDTO cinema = em.find(CinemaDTO.class, id);
		return cinema;
	}
}
