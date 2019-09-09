package com.healthcare.integrator1.dao;

import java.util.Date;
import java.util.List;

import com.healthcare.model.Appointment;

public interface AppointmentDao {

	String addAppointments(Appointment appointment, String patientId, String userId);

	List<Appointment> getAppointmentsbyDate(Date todayDate);

	List<Appointment> getAppointmentsbyDateAndUserName(String username, Date todayDate);

}
