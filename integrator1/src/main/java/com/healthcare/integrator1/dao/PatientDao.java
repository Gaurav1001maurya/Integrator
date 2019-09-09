package com.healthcare.integrator1.dao;

import java.util.List;

import com.healthcare.model.Patient;
import com.healthcare.to.PatientTO;

public interface PatientDao {

		public void addPatient(Patient patient);
		public void deletePatientById(String patientId);
		public List<Patient> getAllPatient(int pageNumber);
		Long LastpageNumberForAllPatient();
		public void updatePatientById(PatientTO patientto, String patientId);
		String getPatientName(String patientId);
		
}