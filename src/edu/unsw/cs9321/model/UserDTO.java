package edu.unsw.cs9321.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "USER")
public class UserDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="USERNAME")
	private String username;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="EMAIL")
	private String email;
	@Column(name="NICKNAME")
	private String nickname;
	@Column(name="FIRST_NAME")
	private String firstName;
	@Column(name="LAST_NAME")
	private String lastName;
	@Column(name="REGISTRATION_CODE")
	private String registrationCode;
	@Column(name="CREATE_TIME")
	private Timestamp createTime;
	
	@ManyToOne
	@JoinColumn(name="USER_TYPE_ID",referencedColumnName="id")
	private UserTypeDTO userType;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	@OrderBy(clause = "createdTime asc")
	private Set<MovieCommentDTO> movieComments;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<MovieBookedDTO> movieBookeds;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public UserTypeDTO getUserType() {
		return userType;
	}

	public void setUserType(UserTypeDTO userType) {
		this.userType = userType;
	}

	public Set<MovieCommentDTO> getMovieComments() {
		return movieComments;
	}

	public void setMovieComments(Set<MovieCommentDTO> movieComments) {
		this.movieComments = movieComments;
	}

	public Set<MovieBookedDTO> getMovieBookeds() {
		return movieBookeds;
	}

	public void setMovieBookeds(Set<MovieBookedDTO> movieBookeds) {
		this.movieBookeds = movieBookeds;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
