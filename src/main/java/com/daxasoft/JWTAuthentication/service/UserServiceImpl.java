package com.daxasoft.JWTAuthentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.daxasoft.JWTAuthentication.entity.User;
import com.daxasoft.JWTAuthentication.repo.UserDAO;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	@Autowired
	private UserDAO dao;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	

	
	@Override
	public Integer saveUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(encoder.encode(user.getPassword()));
		return dao.save(user).getUserId();
	}
	@Override
	public Optional<User> findByUserName(String userName) {
		return dao.findByUserName(userName);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> optUser = findByUserName(username);
		if (optUser == null )
			throw new UsernameNotFoundException("User doesnt exists!!");
		User user=optUser.get();
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				user.getUserName(),
				user.getPassword(),
				user.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles)).toList());
		
		return userDetails;
		
	}

}
