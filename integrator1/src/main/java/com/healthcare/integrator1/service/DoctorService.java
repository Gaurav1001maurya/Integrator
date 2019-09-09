package com.healthcare.integrator1.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.dao.AppointmentDao;
import com.healthcare.model.Appointment;

@Service
public class DoctorService {
	@Autowired
	AppointmentDao appointmentDao;
	
	@Transactional(readOnly = true)
	public List<Appointment>  getAppointmentsbyDateAndUserName(String username,Date todayDate) {
		
		List<Appointment> list = appointmentDao.getAppointmentsbyDateAndUserName(username,todayDate);
		return list;
	}
}
