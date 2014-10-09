package edu.unsw.cs9321.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import edu.unsw.cs9321.common.EntityManagerUtil;

public class UserDAO {
	private EntityManager em;
	private static final Long userTypeUserId = Long.parseLong("2"); // Registered user type
	
	public UserDAO() {
		em = EntityManagerUtil.getEntityManager();
	}
	
	public UserDTO setUserFromRequestParams (HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		UserDTO user = new UserDTO();
		user.setUsername(request.getParameter("username"));
		user.setEmail(request.getParameter("email"));
		String password = request.getParameter("password");
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(password.getBytes("UTF-8"));
		user.setPassword(new String(hash, "UTF-8"));
		user.setUserType(getUserTypeById(userTypeUserId));
		return user;
	}
	
	private UserTypeDTO getUserTypeById (Long id) {
		UserTypeDTO userType = em.find(UserTypeDTO.class, id);
		return userType;
	}
	
	public UserDTO saveUser (UserDTO user) {
		System.out.println("Trying to save it");
		try {
			em.getTransaction().begin();
			user = em.merge(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Couldn't save, rolling back. " + e);
			em.getTransaction().rollback();
		}
		return user;
	}
	
	public UserDTO setRegistrationCode (UserDTO user) {
		String uuid = UUID.randomUUID().toString();
		user.setRegistrationCode(uuid);
		return user;
	}
}
