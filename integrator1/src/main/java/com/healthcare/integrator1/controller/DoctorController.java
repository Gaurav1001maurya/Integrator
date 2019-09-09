package com.healthcare.integrator1.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.healthcare.model.Appointment;
import com.healthcare.service.DoctorService;

@Controller
public class DoctorController {
	
	@Autowired
	DoctorService doctorService;
	
	@RequestMapping("/show_2dayappointment")
	public String showTodayAppointment(Authentication authencation,Model model) throws ParseException {
		
		String username = authencation.getName();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date todayDate = sdf.parse(sdf.format(new Date()));
		List<Appointment> list = doctorService.getAppointmentsbyDateAndUserName(username,todayDate);
		model.addAttribute("appointment_details",list);
		for(Appointment x :list) {
			System.out.println(x.getPatientName());
		}
		
		return "doctor/appointment2day";
		}
	
	
		

}
