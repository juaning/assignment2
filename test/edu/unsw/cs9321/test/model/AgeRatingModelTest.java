package edu.unsw.cs9321.test.model;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import edu.unsw.cs9321.common.EntityManagerUtil;
import edu.unsw.cs9321.model.AgeRatingDTO;
import edu.unsw.cs9321.model.MovieCharacterDTO;
import edu.unsw.cs9321.model.MovieDTO;
import edu.unsw.cs9321.model.MovieGenreDTO;

public class AgeRatingModelTest {
	private EntityManager entityManager = EntityManagerUtil.getEntityManager();
	
	public static void main(String[] args) {
		AgeRatingModelTest test = new AgeRatingModelTest();
//		System.out.println("After successfully insertion.");
//		AgeRatingModel ageRating1 = test.saveAgeRating("G");
//		AgeRatingModel ageRating2 = test.saveAgeRating("PG");
		System.out.println("Listing");
		test.printCharactersByMovieTitle("In Bloom");
//		test.updateAgeRating(ageRating1.getId(), "G1");
//		test.updateAgeRating(ageRating2.getId(), "PG1");
//		
//		test.listAgeRating();
//		
//		System.out.println("After Successfully deletion");
//		test.deleteAgeRating(ageRating2.getId());
//		
//		test.listAgeRating();
	}
	
	public void printCharactersByMovieTitle(String movie) {
		try {
			entityManager.getTransaction().begin();
			Query q = entityManager.createQuery("FROM MovieDTO m WHERE title = :title");
			q.setParameter("title", movie);
			MovieDTO m = (MovieDTO) q.getSingleResult();
			System.out.println(movie);
			for (Iterator<MovieCharacterDTO> iterator = m.getMovieCharacters().iterator(); iterator.hasNext();) {
				MovieCharacterDTO character = (MovieCharacterDTO) iterator.next();
				System.out.println(character.getMovieCharacterType().getType());
				System.out.println(character.getFirstName() + " " + character.getLastName());
			}
		} catch (Exception e) {
	    	System.out.println("Try again");
	    	System.out.println(e);
	    	entityManager.getTransaction().rollback();
	    }
	}
	
	public void printMoviesUnderGender(String g) {
		try {
			entityManager.getTransaction().begin();
	    	Query q = entityManager.createQuery("FROM MovieGenreDTO m WHERE genre = :genre");
	    	q.setParameter("genre", g);
	    	MovieGenreDTO mg = (MovieGenreDTO) q.getSingleResult();
	    	System.out.println(g);
	    	for (Iterator<MovieDTO> iterator = mg.getMovies().iterator(); iterator.hasNext();) {
		        MovieDTO m = (MovieDTO) iterator.next();
		        System.out.println(m.getTitle());
	    	}
	    	entityManager.getTransaction().commit();
		} catch (Exception e) {
	    	System.out.println("Try again");
	    	System.out.println(e);
	    	entityManager.getTransaction().rollback();
	    }
	}
	
	public AgeRatingDTO saveAgeRating(String rating) {
		AgeRatingDTO ar = new AgeRatingDTO();
		try {
			entityManager.getTransaction().begin();
			ar.setRating(rating);
			ar = entityManager.merge(ar);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
		return ar;
	}
	
	public void listAgeRating() {
		EntityTransaction transaction = entityManager.getTransaction();
	    try {
//	    	entityManager.getTransaction().begin();
	    	transaction.begin();
	    	@SuppressWarnings("unchecked")
	    	List<AgeRatingDTO> ars = entityManager.createQuery("SELECT a FROM AgeRatingDTO a").getResultList();
	    	entityManager.flush();
	    	for (Iterator<AgeRatingDTO> iterator = ars.iterator(); iterator.hasNext();) {
		        AgeRatingDTO ar = (AgeRatingDTO) iterator.next();
		        System.out.println(ar.getRating());
	    	}
//	    	entityManager.getTransaction().commit();
	    	transaction.commit();
	    } catch (Exception e) {
	    	System.out.println("Error");
	    	System.out.println(e);
//	    	entityManager.getTransaction().rollback();
	    	transaction.rollback();
	    }
	}
	
	public void listMovieGenre () {
		try {
			entityManager.getTransaction().begin();
	    	@SuppressWarnings("unchecked")
	    	List<MovieGenreDTO> mg = entityManager.createQuery("SELECT m FROM MovieGenreDTO m").getResultList();
	    	for (Iterator<MovieGenreDTO> iterator = mg.iterator(); iterator.hasNext();) {
		        MovieGenreDTO m = (MovieGenreDTO) iterator.next();
		        System.out.println(m.getGenre());
	    	}
	    	entityManager.getTransaction().commit();
		} catch (Exception e) {
	    	System.out.println("Try again");
	    	System.out.println(e);
	    	
	    	entityManager.getTransaction().rollback();
	    }
	}
	
	public void updateAgeRating(Long id, String rating) {
	    try {
	    	entityManager.getTransaction().begin();
	    	AgeRatingDTO ar = (AgeRatingDTO) entityManager.find(AgeRatingDTO.class, id);
	    	ar.setRating(rating);
	    	entityManager.getTransaction().commit();
	    } catch (Exception e) {
	    	entityManager.getTransaction().rollback();
	    }
	}
	
	public void deleteAgeRating(Long id) {
	    try {
	    	entityManager.getTransaction().begin();
	      	AgeRatingDTO ar = (AgeRatingDTO) entityManager.find(AgeRatingDTO.class, id);
	      	entityManager.remove(ar);
	      	entityManager.getTransaction().commit();
	    } catch (Exception e) {
	    	entityManager.getTransaction().rollback();
	    }
	}
}
