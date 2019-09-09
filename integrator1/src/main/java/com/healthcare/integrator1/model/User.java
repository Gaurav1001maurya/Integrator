package com.healthcare.integrator1.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
public class User implements Serializable {
	
	@Id
	@GenericGenerator(name = "user_id", strategy = "com.healthcare.customkey.UserIdGenerator")
	@GeneratedValue(generator = "user_id") 
	private String userId;
	
	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String username;
	
	@Column(name = "password", nullable = false, length = 60)
	private String password;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	private String firstName;
	private String lastName;
	private String gender;
	private int age;
	private String address;
	private String city;
	private String maritialStatus;
	@Temporal(TemporalType.DATE)
	private Date joiningDate;
	private String department;
	private long phone;
	private String email;
	private String photoPath;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserRole> userRole;
	
	public User() {	}

	public User(String username, String password, boolean enabled, String firstName, String lastName, String gender,
			int age, String address, String city, String maritialStatus, Date joiningDate, String department,
			long phone, String email, String photoPath) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.address = address;
		this.city = city;
		this.maritialStatus = maritialStatus;
		this.joiningDate = joiningDate;
		this.department = department;
		this.phone = phone;
		this.email = email;
		this.photoPath = photoPath;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMaritialStatus() {
		return maritialStatus;
	}

	public void setMaritialStatus(String maritialStatus) {
		this.maritialStatus = maritialStatus;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
	
	
	

	

}