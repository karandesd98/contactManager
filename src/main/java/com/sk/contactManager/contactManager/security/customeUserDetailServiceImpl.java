package com.sk.contactManager.contactManager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sk.contactManager.contactManager.Mangagers.UserManager;
import com.sk.contactManager.contactManager.Models.User;

public class customeUserDetailServiceImpl implements UserDetailsService {

	
	@Autowired 
	public UserManager userManager;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userManager.getUserByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("could not found usre");
		}

		CustomUserDetail customUserDetail = new CustomUserDetail(user);
		return customUserDetail;
	}

}
