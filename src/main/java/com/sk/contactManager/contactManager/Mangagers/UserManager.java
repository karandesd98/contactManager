package com.sk.contactManager.contactManager.Mangagers;

import java.util.List;
import java.util.Map;

import com.sk.contactManager.contactManager.Models.User;

public interface UserManager {

	void saveUser(Object... userInfo);
    public User getUserByUserName(String userName);
	void saveContact(Object... objects);
	Map<Integer, List<Object[]>> getMyAllContact(Object... objects);
	Integer deleteContact(Object... objects);
	void updateContact(Object... objects );
}
