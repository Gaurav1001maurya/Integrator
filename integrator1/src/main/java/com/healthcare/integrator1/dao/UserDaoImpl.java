package com.healthcare.integrator1.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.healthcare.exceptions.DuplicateUserNameException;
import com.healthcare.model.User;
import com.healthcare.model.UserRole;
import com.healthcare.to.UserTO;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	HibernateTemplate htemp;

	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = htemp.getSessionFactory().getCurrentSession().createQuery("from User where username=?").setParameter(0, username)
				.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}
	

	@Override
	public void addUser(UserTO userto) {
		try {
		User  user = new User(userto.getUserName(),userto.getPassword(),true,userto.getFirstName(),userto.getLastName(),
				userto.getGender(),userto.getAge(),userto.getAddress(),userto.getCity(),userto.getMaritialStatus(),userto.getJoiningDate(),
				userto.getDepartment(),userto.getPhone(),userto.getEmail(),userto.getPhotoPath());
				UserRole userRole = new UserRole(userto.getUserRole());
				userRole.setUser(user);
				htemp.save(userRole);
		}catch(Exception ex) {
				throw new DuplicateUserNameException(userto.getUserName());
		}
				
	}

	
	@SuppressWarnings("unchecked")
	@Override
	
	public List<UserRole> getAllUser(int pageNumber, String employeeRole) {

				Query query = htemp.getSessionFactory().getCurrentSession().createQuery("from UserRole u where u.role=:role");
				query.setParameter("role",employeeRole);
				query.setFirstResult(pageNumber);
				query.setMaxResults(5);
				return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRole> getAllUsers(String employeeRole) {

		Query query = htemp.getSessionFactory().getCurrentSession().createQuery("from UserRole u where u.role=:role");
		query.setParameter("role",employeeRole);
		List<UserRole> list = query.list();
		return list;
}

	@Override
	public Long LastpageNumberForAllUser(String employeeRole) {

				Query query = htemp.getSessionFactory().getCurrentSession().createQuery("select count(*) from UserRole u where u.role=:role");
				query.setParameter("role",employeeRole);
				return (Long) query.uniqueResult();
		
	}	
	
	@Override
	public void deleteUserById(int userRoleId,String userId) {

				Session session = htemp.getSessionFactory().getCurrentSession();
				Query query1 = session.createQuery("delete from UserRole  where userRoleId=:userRoleId");
				query1.setParameter("userRoleId",userRoleId);
				query1.executeUpdate();
				
				Query query2 = session.createQuery("delete from User  where userId=:userId");
				query2.setParameter("userId",userId);
				query2.executeUpdate();
				
	}

		
			@Override
			public void updateUserById(UserTO userto,String userId) {
				User user = htemp.get(User.class,userId);
				user.setFirstName(userto.getFirstName());
				user.setLastName(userto.getLastName());
				user.setEmail(userto.getEmail());
				user.setDepartment(userto.getDepartment());
				user.setGender(userto.getGender());
				user.setAddress(userto.getAddress());
				user.setAge(userto.getAge());
				user.setCity(userto.getCity());
				user.setJoiningDate(userto.getJoiningDate());
				user.setMaritialStatus(userto.getMaritialStatus());
				user.setPhone(userto.getPhone());
				htemp.save(user);
			
			}

}
