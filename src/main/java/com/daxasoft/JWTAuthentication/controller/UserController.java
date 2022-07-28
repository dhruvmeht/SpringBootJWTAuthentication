package com.daxasoft.JWTAuthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daxasoft.JWTAuthentication.entity.User;
import com.daxasoft.JWTAuthentication.entity.UserRequest;
import com.daxasoft.JWTAuthentication.entity.UserResponse;
import com.daxasoft.JWTAuthentication.service.UserService;
import com.daxasoft.JWTAuthentication.util.JWTUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;
	
	@Autowired
	private JWTUtil util;
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user){
		
		Integer userId = service.saveUser(user);
		return new ResponseEntity<String>("Record '"+userId+"' Successfully Saved!!", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody UserRequest request){
		
		authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(),
				request.getPassword()));
		String token = util.generateToken(request.getUserName());
		return new ResponseEntity<UserResponse>(new UserResponse(token,"Token Generated successfully!!"),HttpStatus.OK);
	}
	
	@PostMapping("/welcome")
	public ResponseEntity<String> welcomeAuthentication(){
		return new ResponseEntity<String>("Welcome to DaxaSoft!!",HttpStatus.OK);
	}
}
