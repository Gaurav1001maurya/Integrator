package com.healthcare.integrator1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.dao.AppointmentDao;
import com.healthcare.dao.PatientDao;
import com.healthcare.dao.UserDao;
import com.healthcare.model.Appointment;
import com.healthcare.model.Patient;
import com.healthcare.model.UserRole;
import com.healthcare.to.AppointmentTO;
import com.healthcare.to.PatientTO;

@Service
public class ReceptionService {
	@Autowired
	private PatientDao patientDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AppointmentDao appointmentDao;

	@Transactional(readOnly = false)
	public void addPatient(PatientTO patientTO) {
		Patient patient = new Patient(patientTO.getFullName(), patientTO.getGender(), patientTO.getBloodGroup(),
				patientTO.getAge(), patientTO.getPhoneNumber(), patientTO.getRegistrationDate(), patientTO.getCity(),
				patientTO.getAddress(), patientTO.getEmail(), patientTO.getOccupation());

		patientDao.addPatient(patient);
	}

	@Transactional(readOnly = true)
	public List<Patient> getAllPatient(int pageNumber) {
		List<Patient> patient = patientDao.getAllPatient(pageNumber);
		return patient;
	}
	
	@Transactional(readOnly = true)
	public String getPatientName(String patientId) {
		
		return patientDao.getPatientName(patientId);
	}
	

	@Transactional(readOnly = true)
	public List<Long> pagesForAllPatient() {
		Long rowsCount = patientDao.LastpageNumberForAllPatient();
		Long NumberOfPages = (long) Math.ceil(rowsCount / 5.0);
		List<Long> pages = new ArrayList<>();
		for (long i = 0; i < NumberOfPages; i++) {
			pages.add(i);
		}
		return pages;

	}

	@Transactional(readOnly = false)
	public void deletePatientById(String patientId) {
		patientDao.deletePatientById(patientId);
	}

	@Transactional(readOnly = false)
	public void updatePatientById(PatientTO patientto, String patientId) {
		patientDao.updatePatientById(patientto, patientId);
	}

	@Transactional(readOnly = true)
	public List<UserRole> getAllUsers(String employeeRole) {
		List<UserRole> list = userDao.getAllUsers(employeeRole);
		return list;

	}
	
	@Transactional(readOnly = false)
	public String  addAppointment(AppointmentTO appointmentto) {
		
		Appointment appointment = new Appointment(appointmentto.getPatientName(),appointmentto.getDoctorName(),
				appointmentto.getTreatmentFor(),appointmentto.getDate(),appointmentto.getTimeSlot());
				
		String appointmentId =  appointmentDao.addAppointments(appointment,appointmentto.getPatientId(),appointmentto.getUserId());
		return appointmentId;
	}
	
	@Transactional(readOnly = true)
	public List<Appointment>  getAppointmentsbyDate(Date todayDate) {
		
		List<Appointment> list = appointmentDao.getAppointmentsbyDate(todayDate);
		return list;
	}

}