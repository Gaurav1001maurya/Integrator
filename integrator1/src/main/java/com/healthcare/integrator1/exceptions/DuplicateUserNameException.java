package com.healthcare.integrator1.exceptions;

public class DuplicateUserNameException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	
	public DuplicateUserNameException(String userName) {
		this.userName=userName;
		
	}
	
	public String getMessage() {
		String msg = "Duplicate Username";
		if(userName!=null) {
			msg= "Duplicate Username:" + userName;
		}
		return msg;
	}
	
	public String toString() {
		return getMessage();
	}
	
	

}
