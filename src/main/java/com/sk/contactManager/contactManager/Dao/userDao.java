package com.sk.contactManager.contactManager.Dao;

import java.util.List;
import java.util.Map;

import com.sk.contactManager.contactManager.Models.User;

public interface userDao {
	void saveUser(Object... userInfo);
	User getUserByUserName(String userName);
	void saveContact(Object... objects);
	Map<Integer, List<Object[]>> getMyAllContact(Object[] objects);
	Integer deleteContact(Object... objects);
	void updateContact(Object... objects);

}
