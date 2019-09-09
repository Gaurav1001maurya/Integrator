package com.healthcare.integrator1.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.healthcare.model.Patient;
import com.healthcare.to.PatientTO;

@Repository
public class PatientDaoImpl implements PatientDao{

	@Autowired
	HibernateTemplate htemp;
	
	
	public Session getSession() {
		Session session=htemp.getSessionFactory().getCurrentSession();
		
		return session;
	}
	
	@Override
	public void addPatient(Patient patient) {
		
		htemp.save(patient);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Patient> getAllPatient(int pageNumber) {
		
		Query query=getSession().createQuery("from Patient");
		query.setFirstResult(pageNumber);
		query.setMaxResults(5);		
		return query.list();
	}
	
	@Override
	public String getPatientName(String patientId) {
		
		Query query=getSession().createQuery("select fullName from Patient where patientId=:patientId");
		query.setParameter("patientId",patientId);
		String fullName = (String) query.uniqueResult();
		return fullName;
		
	}
	
	@Override
	public Long LastpageNumberForAllPatient() {

		Query query = getSession().createQuery("select count(*) from Patient");
		return (Long) query.uniqueResult();
		
	}

	@Override
	public void deletePatientById(String patientId) {
		Patient patient = htemp.get(Patient.class,patientId);
		htemp.delete(patient);
	}
	


	@Override
	public void updatePatientById(PatientTO patientto, String patientId) {
		
		Patient patient = htemp.get(Patient.class, patientId);
		patient.setFullName(patientto.getFullName());
		patient.setEmail(patientto.getEmail());
		patient.setAddress(patientto.getAddress());
		patient.setGender(patientto.getGender());
		patient.setAge(patientto.getAge());
		patient.setCity(patientto.getCity());
		patient.setPhoneNumber(patientto.getPhoneNumber());
		htemp.save(patient);
		
	}	
	
	
	

}