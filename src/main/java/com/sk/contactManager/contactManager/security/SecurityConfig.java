package com.sk.contactManager.contactManager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	
/*	@Override  // for authantication
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		.antMatchers("/public/**").permitAll()
		.antMatchers("/user/**").authenticated()
		.and().formLogin()
		.loginPage("/public/signin")
		.loginProcessingUrl("/public/doLogin")
		.defaultSuccessUrl("/public/");
		// .and().httpBasic();
	}

	@Override  // for autherization
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println(this.passwordEncode().encode("sachin"));
		auth.inMemoryAuthentication()
		.withUser("sachin").password(this.passwordEncode().encode("sachin")).roles("ADMIN").and()
		.withUser("nilesh").password(this.passwordEncode().encode("nilesh")).roles("ADMIN").and()
		.withUser("pratik").password(this.passwordEncode().encode("pratik")).roles("NORMAL");
	}
	
	*/
	
	@Bean
	public UserDetailsService getUserDetailService()
	{
		return new customeUserDetailServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	
	@Override  // for autherization
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		
	}
	
	@Override  // for authantication
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user/**").authenticated()  // .hasRole("USER")
        .antMatchers("/**").permitAll()
        .and().formLogin()
        .loginPage("/signin")
        .loginProcessingUrl("/doLogin")
        .defaultSuccessUrl("/user/userDashBoard")
        .and().csrf().disable();
	}
	
}
