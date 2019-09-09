package com.healthcare.integrator1.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.dao.UserDao;
import com.healthcare.model.UserRole;
import com.healthcare.to.UserTO;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	@Transactional(readOnly = false)
	public void addUser(UserTO user) throws IOException, ParseException {
			userDao.addUser(user);
	}
	
		
		
	
	@Transactional(readOnly =false)
	public List<UserRole> getAllUser(int pageNumber,String employeeRole){
		return userDao.getAllUser(pageNumber, employeeRole);
		
	}
	
	@Transactional(readOnly =false)
	public List<Long> pagesForAllEmployee(String employeeRole) {
				Long rowsCount = userDao.LastpageNumberForAllUser(employeeRole);
				Long NumberOfPages =  (long) Math.ceil(rowsCount/2.0);
				List<Long> pages = new ArrayList<>();
				for(long i =0;i<NumberOfPages;i++) {
					pages.add(i);
				}
				return pages;
				
	}

	@Transactional(readOnly =false)
	public void deleteUserById(int userRoleId,String userId) {
			userDao.deleteUserById(userRoleId,userId);
			
	}
	
	@Transactional(readOnly =false)
	public void updateUserById(UserTO userto,String userId) {
		userDao.updateUserById(userto, userId);
	
	}

	
	
}
