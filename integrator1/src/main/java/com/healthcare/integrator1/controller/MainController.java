package com.healthcare.integrator1.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This is default page!");
		model.setViewName("home");
		return model;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin/admin");

		return model;

	}
	
	@RequestMapping(value = "/nurse**", method = RequestMethod.GET)
	public ModelAndView nursePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("nurse/nurse");

		return model;

	}
	
	@RequestMapping(value = "/doctor**", method = RequestMethod.GET)
	public ModelAndView doctorPage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("doctor/doctor");

		return model;

	}
	
	@RequestMapping(value = "/reception**", method = RequestMethod.GET)
	public ModelAndView receptionPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("message", "This page is for ROLE_ADMIN and ROLE_RECEPTION");
		model.setViewName("reception/reception");

		return model;

	}
	
	@RequestMapping(value = "/pharmacy**", method = RequestMethod.GET)
	public ModelAndView pharmacyPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("message", "This page is for ROLE_ADMIN and ROLE_PHARMACY");
		model.setViewName("pharmacy/pharmacy");

		return model;

	}
	
	@RequestMapping(value = "/lab**", method = RequestMethod.GET)
	public ModelAndView labPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("message", "This page is for ROLE_ADMIN and ROLE_LAB");
		model.setViewName("lab/lab");

		return model;

	}
	
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);


		}

		model.setViewName("403");
		return model;

	}

}