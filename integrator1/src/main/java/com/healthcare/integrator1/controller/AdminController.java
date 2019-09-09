package com.healthcare.integrator1.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.healthcare.integrator1.model.UserRole;
import com.healthcare.integrator1.service.ReceptionService;
import com.healthcare.integrator1.service.UserService;

@Controller
public class AdminController {

	@Autowired
	UserService userService;
	
	@Autowired
	ReceptionService receptionService;
	
	private String role;
	
	private static final String UPLOAD_DIRECTORY = "C:/images";
	
	@RequestMapping("/welcome_user")
	public String showWelcomeUser() {
		return "admin/welcome_user";
		}	

	@RequestMapping("/add_user")
	public String showAddUser(Map<String, Object> model) {
//		UserCommand command = new UserCommand();
//		model.put("user_details", command);
		return "admin/add_user";
		}	
	
	@RequestMapping("/view")
	public String showViewUser(Map<String, Object> model) {
		switch (role) {
		case "ROLE_DOCTOR":
			return "forward:/view_doctor?page=0&role=ROLE_DOCTOR";

		case "ROLE_NURSE":
			return "forward:/view_nurse?page=0&role=ROLE_NURSE";

		case "ROLE_RECEPTION":
		
			return "forward:/view_receptionist?page=0&role=ROLE_RECEPTION";

		case "ROLE_PHARMACY":
			
			return "forward:/view_pharmacist?page=0&role=ROLE_PHARMACY";

		case "ROLE_LAB":
		
			return "forward:/view_laboratorist?page=0&role=ROLE_LAB";
			
		default:
			return "";

		}
	}
	

	@RequestMapping("/view_doctor")
	public String showViewDoctor(Map<String, Object> model,@RequestParam("page") String page,@RequestParam("role") String employeeRole) {
		role="ROLE_DOCTOR";	
		int pageNumber = Integer.parseInt(page);
//		List<UserRole> list = userService.getAllUser(pageNumber, employeeRole);
		model.put("doctor_details",list);
		List<Long> pages = userService.pagesForAllEmployee(employeeRole);
		model.put("pages",pages);
		model.put("currentPage",pageNumber);
		return "admin/view_doctor";
	}

	@RequestMapping("/view_nurse")
	public String showViewNurse(Map<String, Object> model,@RequestParam("page") String page,@RequestParam("role") String employeeRole) {
		role="ROLE_NURSE";
		int pageNumber = Integer.parseInt(page);
		List<UserRole> list = userService.getAllUser(pageNumber, employeeRole);
		model.put("nurse_details",list);
		List<Long> pages = userService.pagesForAllEmployee(employeeRole);
		model.put("pages",pages);
		model.put("currentPage",pageNumber);
		return "admin/view_nurse";
	}

	@RequestMapping("/view_receptionist")
	public String showViewReceptionist(Map<String, Object> model,@RequestParam("page") String page,@RequestParam("role") String employeeRole) {
		role="ROLE_RECEPTION";
		int pageNumber = Integer.parseInt(page);
		List<UserRole> list = userService.getAllUser(pageNumber, employeeRole);
		model.put("receptionist_details",list);
		List<Long> pages = userService.pagesForAllEmployee(employeeRole);
		model.put("pages",pages);
		model.put("currentPage",pageNumber);
		return "admin/view_receptionist";
	}

	@RequestMapping("/view_pharmacist")
	public String showViewPharmacist(Map<String, Object> model,@RequestParam("page") String page,@RequestParam("role") String employeeRole) {
		role="ROLE_PHARMACY";
		int pageNumber = Integer.parseInt(page);
		List<UserRole> list = userService.getAllUser(pageNumber, employeeRole);
		model.put("pharmacist_details",list);
		List<Long> pages = userService.pagesForAllEmployee(employeeRole);
		model.put("pages",pages);
		model.put("currentPage",pageNumber);
		return "admin/view_pharmacist";
	}

	@RequestMapping("/view_laboratorist")
	public String showViewLaboratorist(Map<String, Object> model,@RequestParam("page") String page,@RequestParam("role") String employeeRole) {
		role="ROLE_LAB";
		int pageNumber = Integer.parseInt(page);
		List<UserRole> list = userService.getAllUser(pageNumber, employeeRole);
		model.put("laboratorist_details",list);
		List<Long> pages = userService.pagesForAllEmployee(employeeRole);
		model.put("pages",pages);
		model.put("currentPage",pageNumber);
		return "admin/view_laboratorist";
	}
	

	@RequestMapping(value = "/add_user_details", method = RequestMethod.POST)
	public ModelAndView addUser(@Valid @ModelAttribute("user_details") UserCommand userCommand,
			BindingResult result) throws ParseException, IOException {
		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) {
			mv.setViewName("admin/add_user");
			return mv;
		} else {
			MultipartFile file = userCommand.getFile();

			String fileName = file.getOriginalFilename();
			String photoPath = UPLOAD_DIRECTORY + "/" + fileName;

			File dir = new File(UPLOAD_DIRECTORY);
			if (!dir.exists())
				dir.mkdirs();
			File f = new File(dir, fileName);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f));
			byte data[] = file.getBytes();
			stream.write(data);
			stream.close();

			int age = Integer.parseInt(userCommand.getAge());
			long phone = Long.parseLong(userCommand.getPhone());
			String startDate = userCommand.getJoiningDate();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			Date joiningDate = sdf1.parse(startDate);

			UserTO user = new UserTO(userCommand.getFirstName(), userCommand.getLastName(),
					userCommand.getUserName(), userCommand.getPassword(),userCommand.getGender(), age, userCommand.getAddress(),
					userCommand.getMaritialStatus(),joiningDate, userCommand.getDepartment(), phone, userCommand.getCity(),
					userCommand.getEmail(),photoPath,userCommand.getUserRole());

			
			userService.addUser(user);
			mv.addObject("user_name", userCommand.getFirstName());
			role=userCommand.getUserRole();
			mv.setViewName("admin/success");
			return mv;
		}
	}
		@RequestMapping("/delete_user")
		public String deleteUserById(@RequestParam("userRoleId")String userRoleId, @RequestParam("userId")String userId) {
				int roleId = Integer.parseInt(userRoleId);
				userService.deleteUserById(roleId,userId);
				return "forward:/view";
		}	
		
		@RequestMapping("/update_user")
		public String UpdateUserById( @ModelAttribute("user_details") UserCommand userCommand,@RequestParam("userId") String userId) throws ParseException {
			
			int age = Integer.parseInt(userCommand.getAge());
			long phone = Long.parseLong(userCommand.getPhone());
			String startDate = userCommand.getJoiningDate();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = sdf1.parse(startDate);
			java.sql.Date sqlStartDate = new java.sql.Date(date1.getTime());
			Date joiningDate = sqlStartDate;
			
			UserTO userto = new UserTO();
			userto.setFirstName(userCommand.getFirstName());
			userto.setLastName(userCommand.getLastName());
			userto.setEmail(userCommand.getEmail());
			userto.setAddress(userCommand.getAddress());
			userto.setDepartment(userCommand.getDepartment());
			userto.setGender(userCommand.getGender());
			userto.setAge(age);
			userto.setCity(userCommand.getCity());
			userto.setJoiningDate(joiningDate);
			userto.setMaritialStatus(userCommand.getMaritialStatus());
			userto.setPhone(phone);

			userService.updateUserById(userto, userId);
			
				
			return "forward:/view";
			
				
		}
}
