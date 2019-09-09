package com.healthcare.integrator1.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.healthcare.model.Appointment;
import com.healthcare.model.Patient;
import com.healthcare.model.User;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {
	
	@Autowired
	HibernateTemplate htemp;
	
	@Override
	public String  addAppointments(Appointment appointment,String patientId,String userId) {
		String appointmentId = (String) htemp.save(appointment);
		Patient patient = htemp.get(Patient.class,patientId);
		User user = htemp.get(User.class,userId);
		appointment.setPatient(patient);
		appointment.setUser(user);
		htemp.save(appointment);
		return appointmentId;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment>  getAppointmentsbyDate(Date todayDate) {
		Query query = htemp.getSessionFactory().getCurrentSession().createQuery("from Appointment ap where ap.date=:date");
		System.out.println("query executed");
		query.setParameter("date",todayDate);
		List<Appointment> list = query.list();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment>  getAppointmentsbyDateAndUserName(String username,Date todayDate) {
		Query query1 = htemp.getSessionFactory().getCurrentSession().createQuery("from User where username=:username");
		query1.setParameter("username",username);
		User user = (User) query1.uniqueResult();
		
		
		Query query = htemp.getSessionFactory().getCurrentSession().createQuery("from Appointment ap where ap.date=:date and ap.user=:user");
		query.setParameter("date",todayDate);
		query.setParameter("user",user);
		List<Appointment> list = query.list();

		return list;
	}
	

}
