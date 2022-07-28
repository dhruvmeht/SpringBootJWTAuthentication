package com.daxasoft.JWTAuthentication.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daxasoft.JWTAuthentication.entity.User;

public interface UserDAO extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String UserName);
}