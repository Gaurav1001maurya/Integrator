package com.healthcare.integrator1.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.healthcare.command.UserCommand;
import com.healthcare.exceptions.DuplicateUserNameException;

@ControllerAdvice
public class UserExceptionHandler {
	
	@ExceptionHandler(DuplicateUserNameException.class)
	public ModelAndView handleDuplicateUserNameException(DuplicateUserNameException exception) {
		UserCommand command = new UserCommand();
		ModelAndView mv = new ModelAndView();
			mv.addObject("user_details", command);
			mv.addObject("duplicateUsername",exception.getMessage());
			mv.setViewName("admin/add_user");
			return mv;
	}

}
