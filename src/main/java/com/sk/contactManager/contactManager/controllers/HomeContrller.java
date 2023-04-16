package com.sk.contactManager.contactManager.controllers;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sk.contactManager.contactManager.Models.User;

@Controller
@RequestMapping("")
public class HomeContrller {
	
	@GetMapping("/signin")
	public String signin()
	{
		return "login";
	}
	
	@GetMapping("/")
	public String home(Model m)
	{
		m.addAttribute("title","home page");
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model m)
	{
		m.addAttribute("title","about page");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model m)
	{
		m.addAttribute("title","signup page");
		m.addAttribute("user",new User());
		return "signup";
	}
	
	

	
}
