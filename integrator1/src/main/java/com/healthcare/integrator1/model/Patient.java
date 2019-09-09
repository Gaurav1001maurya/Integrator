package com.healthcare.integrator1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="patient")
public class Patient {
	
	@Id
	@GenericGenerator(name = "patientId", strategy = "com.healthcare.customkey.PatientIdGenerator")
	@GeneratedValue(generator = "patientId") 
	private String patientId;
	private String fullName;
	private String gender;
	private String bloodGroup;
	private int age;
	private long phoneNumber;
	@Temporal(TemporalType.DATE)
	private Date registrationDate;
	private String city;
	private String address;
	private String email; 	
	private String occupation;
	
	
	
	public Patient() {}

	public Patient(String fullName, String gender, String bloodGroup, int age, long phoneNumber,
			Date registrationDate, String city, String address, String email, String occupation) {
		this.fullName = fullName;
		this.gender = gender;
		this.bloodGroup = bloodGroup;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.registrationDate = registrationDate;
		this.city = city;
		this.address = address;
		this.email = email;
		this.occupation = occupation;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	
	
}