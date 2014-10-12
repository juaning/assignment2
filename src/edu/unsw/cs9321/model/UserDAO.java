package edu.unsw.cs9321.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import edu.unsw.comp9321.mail.MailSender;
import edu.unsw.comp9321.mail.exceptions.MailSenderException;
import edu.unsw.comp9321.mail.exceptions.ServiceLocatorException;
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
		user.setPassword(setUserPassword(password));
//		user.setPassword(request.getParameter("password"));
		user.setUserType(getUserTypeById(userTypeUserId));
		return user;
	}
	
	private String setUserPassword (String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(pass.getBytes("UTF-8"));
//		user.setPassword(new String(hash, "UTF-8"));
		return new String(hash, "UTF-8");
	}
	
	private UserTypeDTO getUserTypeById (Long id) {
		UserTypeDTO userType = em.find(UserTypeDTO.class, id);
		return userType;
	}
	
	/**
	 * Get User by Id
	 * @param Long id
	 * @return UserDTO
	 */
	public UserDTO getUserById (Long id) {
		UserDTO user = em.find(UserDTO.class, id);
		return user;
	}
	
	public UserDTO saveUser (UserDTO user) {
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
	
	public UserDTO setCreatedTime (UserDTO user) {
		java.util.Date date = new java.util.Date();
		user.setCreateTime(new Timestamp(date.getTime()));
		return user;
	}
	
	public void sendEmail (UserDTO user) throws ServiceLocatorException, MailSenderException, AddressException, MessagingException {
		MailSender sender = MailSender.getMailSender();
		System.out.println("Sendint email");
		String fromAddress = "z3431406@zmail.unsw.edu.au";
		String subject = "User Created";
		StringBuffer mailBody = new StringBuffer();
		mailBody.append("To activate your account please go click to the following link: http://localhost:8080/assignment2/movies?action=addUser&v=" + user.getRegistrationCode());
		sender.sendMessage(fromAddress, user.getEmail(), subject, mailBody);
	}
	
	public UserDTO getUserByRegistrationCode (String code) {
		UserDTO user = null;
		em.getTransaction().begin();
		Query q = em.createQuery(" FROM UserDTO WHERE registrationCode = :code");
		q.setParameter("code", code);
		@SuppressWarnings("unchecked")
		List<UserDTO> lstUser = (List<UserDTO>) q.getResultList();
		em.getTransaction().commit();
		if (lstUser.size() > 0) {
			user = lstUser.get(0);
		}
    	return user;
	}
	
	public UserDTO setUpdateUserFromRequest (UserDTO user, HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		if (password != null && password.length() > 0 && !user.getPassword().equals(setUserPassword(password))) {
			user.setPassword(setUserPassword(password));
		}
		if (email != null && email.length() > 0 && !user.getEmail().equals(email)) {
			System.out.println("Email: " + email);
			user.setEmail(email);
		}
		if (nickname != null && nickname.length() > 0 && (user.getNickname() == null || !user.getNickname().equals(nickname))) {
			System.out.println("nickname: " + nickname);
			user.setNickname(nickname);
		}
		if (firstname != null && firstname.length() > 0 && (user.getFirstName() == null || !user.getFirstName().equals(firstname))) {
			System.out.println("firstname: " + firstname);
			user.setFirstName(firstname);
		}
		if (lastname != null && lastname.length() > 0 && (user.getLastName() == null && !user.getLastName().equals(lastname))) {
			System.out.println("lastname: " + lastname);
			user.setLastName(lastname);
		}
		return user;
	}
	
	/**
	 * Login User into the system
	 * @param String username
	 * @param String password
	 * @return UserDTO
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public UserDTO loginUser(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		UserDTO user = null;
		password = setUserPassword(password);
		em.getTransaction().begin();
		Query q = em.createQuery(" FROM UserDTO WHERE username = :username AND password=:password AND registrationCode IS NULL");
		q.setParameter("username", username);
		q.setParameter("password", password);
		@SuppressWarnings("unchecked")
		List<UserDTO> lstUser = (List<UserDTO>) q.getResultList();
		em.getTransaction().commit();
		if (lstUser.size() > 0) {
			user = lstUser.get(0);
		}
		return user;
	}
}
