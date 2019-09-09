package com.healthcare.integrator1.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.healthcare.command.AppointmentCommand;
import com.healthcare.command.PatientCommand;
import com.healthcare.model.Appointment;
import com.healthcare.model.Patient;
import com.healthcare.model.UserRole;
import com.healthcare.service.ReceptionService;
import com.healthcare.to.AppointmentTO;
import com.healthcare.to.PatientTO;

@Controller
public class ReceptionController {
	

	@Autowired
	ReceptionService receptionService;
	
	
	@RequestMapping("/welcome_patient")
	public String showAddUser() {
	
		return "reception/welcome_patient";
		}
	
	@RequestMapping("/show_appointment")
	public String showAppointment(@RequestParam("employeeRole")String employeeRole,Model model) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date appointmentDate = sdf.parse(sdf.format(new Date()));
		List<Appointment> list1 =  receptionService.getAppointmentsbyDate(appointmentDate);
		model.addAttribute("appointment_details",list1);
		List<UserRole> list2  = receptionService.getAllUsers(employeeRole);
		model.addAttribute("user_role_lists",list2);
		return "reception/appointment";
		}
	
	@RequestMapping(value="/set_appointment",method=RequestMethod.POST)
	public String  addAppointment(@ModelAttribute("appointmentCommand") AppointmentCommand command,Model model) throws ParseException {
		System.out.println(command);
		String date = command.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date appointmentDate = sdf.parse(date);
		AppointmentTO appointmentto = new AppointmentTO(command.getPatientId(),command.getPatientName(),command.getUserId(),command.getDoctorName(),
				command.getTreatmentFor(),appointmentDate,command.getTimeSlot());
		receptionService.addAppointment(appointmentto);
		Date todayDate = sdf.parse(sdf.format(new Date()));
		List<Appointment> list =  receptionService.getAppointmentsbyDate(todayDate);
		model.addAttribute("appointment_details",list);
		return "forward:/show_appointment?employeeRole=ROLE_DOCTOR";
		}
	
	@RequestMapping(value="/get_appointment_by_date",method=RequestMethod.POST)
	public String  getAppointmentBy(@ModelAttribute("appointmentCommand") AppointmentCommand command,Model model) throws ParseException {
		
		String date = command.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date appointmentDate = sdf.parse(date);
		List<Appointment> list =  receptionService.getAppointmentsbyDate(appointmentDate);
		model.addAttribute("appointment_details",list);
		
		return "reception/appointment";
		}
	
	@RequestMapping("/getPatientNameById")
	public @ResponseBody String showAppointment(@RequestParam("patientId") String patientId) {
			String patientName = receptionService.getPatientName(patientId);
			return patientName;
		}
	
	
	@RequestMapping("/add_patient")
	public String showAddUser(Map<String, Object> model) {
		PatientCommand command = new PatientCommand();
		model.put("patient_details", command);
		return "reception/add_patient";
		}	
	
	@RequestMapping("/view_patient")
	public String showViewPatient(Map<String, Object> model,@RequestParam("page") String page) {
		
		int pageNumber = Integer.parseInt(page);
		List<Patient> list = receptionService.getAllPatient(pageNumber);
		model.put("patient_details",list);
		List<Long> pages = receptionService.pagesForAllPatient();
		model.put("pages",pages);
		model.put("currentPage",pageNumber);
		return "reception/view_patient";
	}
	
	@RequestMapping(value = "/add_patient_details", method = RequestMethod.POST)
	public ModelAndView addPatient(@Valid @ModelAttribute("patient_details") PatientCommand patientCommand,
			BindingResult result) throws ParseException, IOException {
		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) {
			mv.setViewName("reception/add_patient");
			return mv;
		}
		else {
		
		long phone = Long.parseLong(patientCommand.getPhoneNumber());
		int age=Integer.parseInt(patientCommand.getAge());
		
		String startDate = patientCommand.getRegistrationDate();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date regDate = sdf1.parse(startDate);
		
		PatientTO patientTO=new PatientTO(patientCommand.getFullName(),patientCommand.getGender(),patientCommand.getBloodGroup(),
				age,phone,regDate,patientCommand.getCity(),patientCommand.getAddress(),patientCommand.getEmail(),patientCommand.getOccupation());
		
		receptionService.addPatient(patientTO);
		
		mv.addObject("patient_name",patientCommand.getFullName());
	 	mv.setViewName("reception/success");
		return mv;
		}

	}
	
	@RequestMapping("/delete_patient_id")
	public String deletePatientById(@RequestParam("patientId")String patientId) {
		receptionService.deletePatientById(patientId);
		return "forward:/view_patient?page=0";
	}
	
	@RequestMapping(value = "/update_patient_id",method=RequestMethod.POST)
	public String UpdateUserById( @ModelAttribute("patient_details") PatientCommand patientCommand,@RequestParam("patientId") String patientId) throws ParseException {
		
		int age = Integer.parseInt(patientCommand.getAge());
		long phone = Long.parseLong(patientCommand.getPhoneNumber());

		
		PatientTO patientto = new PatientTO();
		patientto.setFullName(patientCommand.getFullName());
		patientto.setEmail(patientCommand.getEmail());
		patientto.setAddress(patientCommand.getAddress());
		patientto.setGender(patientCommand.getGender());
		patientto.setAge(age);
		patientto.setCity(patientCommand.getCity());
		patientto.setPhoneNumber(phone);

		receptionService.updatePatientById(patientto, patientId);
		
			
		return "forward:/view_patient?page=0";
		
			
	}


		

}
