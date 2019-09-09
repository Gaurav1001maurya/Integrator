package com.healthcare.integrator1.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole{
	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name = "user_role_id", 
		unique = true, nullable = false)
	private Integer userRoleId;
	
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	

	
	@Column(name = "role", nullable = false, length = 45,unique=true)
	private String role;

	public UserRole() {}
	

	public UserRole(String role) {
		this.role = role;
	}

	
	public Integer getUserRoleId() {
		return this.userRoleId;
	}



	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	
	public User getUser() {
		return this.user;
	}
	

	public void setUser(User user) {
		this.user = user;
	}
	
	

	
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	

}
