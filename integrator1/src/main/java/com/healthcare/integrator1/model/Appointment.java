
package com.healthcare.integrator1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Appointment {
	
	@Id
	@GenericGenerator(name = "appointmentId", strategy = "com.healthcare.customkey.AppointmentIdGenerator")
	@GeneratedValue(generator = "appointmentId")
	private String appointmentId;
	private String patientName;
	private String doctorName;
	private String treatmentFor;
	@Temporal(TemporalType.DATE)
	private Date    date;
	private String timeSlot;
	
	@ManyToOne
	@JoinColumn(name="patientId")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name="username",referencedColumnName = "username")
	private User user;
	
	public Appointment() {}

	public Appointment(String patientName, String doctorName, String treatmentFor, Date date,
			String timeSlot) {
		this.patientName = patientName;
		this.doctorName = doctorName;
		this.treatmentFor = treatmentFor;
		this.date = date;
		this.timeSlot = timeSlot;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getTreatmentFor() {
		return treatmentFor;
	}

	public void setTreatmentFor(String treatmentFor) {
		this.treatmentFor = treatmentFor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
