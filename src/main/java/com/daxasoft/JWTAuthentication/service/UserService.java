package com.daxasoft.JWTAuthentication.service;

import java.util.Optional;

import com.daxasoft.JWTAuthentication.entity.User;

public interface UserService {
	public Integer saveUser(User user);
	Optional<User> findByUserName(String userName);

}
