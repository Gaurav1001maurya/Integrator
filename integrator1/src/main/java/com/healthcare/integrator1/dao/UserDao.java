package com.healthcare.integrator1.dao;

import java.util.List;

import com.healthcare.model.User;
import com.healthcare.model.UserRole;
import com.healthcare.to.UserTO;

public interface UserDao {

	public User findByUserName(String username);
	
	public void addUser(UserTO userto) ;

	public List<UserRole> getAllUser(final int pageNumber,String employeeRole);
	
	Long LastpageNumberForAllUser(String employeeRole);

	void deleteUserById(int userRoleId,String userId);
	
	void updateUserById(UserTO userto,String userId);

	List<UserRole> getAllUsers(String employeeRole);

}
