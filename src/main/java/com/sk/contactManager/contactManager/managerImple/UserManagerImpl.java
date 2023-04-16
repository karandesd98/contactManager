package com.sk.contactManager.contactManager.managerImple;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.contactManager.contactManager.Dao.userDao;
import com.sk.contactManager.contactManager.Mangagers.UserManager;
import com.sk.contactManager.contactManager.Models.User;

@Service
public class UserManagerImpl implements UserManager {

	@Autowired
	public userDao userDaoObj;
	
	@Override
	public void saveUser(Object... userInfo) {
		userDaoObj.saveUser(userInfo);
		
	}

	@Override
	public User getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDaoObj.getUserByUserName(userName);
	}

	@Override
	public void saveContact(Object... objects) {
		 userDaoObj.saveContact(objects);	
		
	}

	@Override
	public Map<Integer, List<Object[]>> getMyAllContact(Object... objects) {
		// TODO Auto-generated method stub
	return	 userDaoObj.getMyAllContact(objects);	
	}

	@Override
	public Integer deleteContact(Object... objects) {
		// TODO Auto-generated method stub
		return	 userDaoObj.deleteContact(objects);	
	}

	@Override
	public void updateContact(Object... objects) {
		 userDaoObj.updateContact(objects);			
	}

}
