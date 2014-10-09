package edu.unsw.cs9321.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "USER_TYPE")
public class UserTypeDTO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="USER_TYPE")
	private String type;
	
	@OneToMany(mappedBy="userType")
	private Set<UserDTO> users;
	
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
	public Set<UserDTO> getUsers() {
		return users;
	}
	public void setUsers(Set<UserDTO> users) {
		this.users = users;
	}
}
