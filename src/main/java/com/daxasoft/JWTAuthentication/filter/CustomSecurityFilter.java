package com.daxasoft.JWTAuthentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.daxasoft.JWTAuthentication.util.JWTUtil;

@Component
public class CustomSecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtil util;
	
	@Autowired
	private UserDetailsService detailsService ;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader("Authorization");
		if (token != null) {
			String tokenUserName = util.getUserName(token); 
			if (tokenUserName != null) {
				UserDetails dbUserDetails = detailsService.loadUserByUsername(tokenUserName);
				Boolean isValid = util.validateUser(token, dbUserDetails.getUsername());
				if(isValid && SecurityContextHolder.getContext().getAuthentication() == null) {
					UsernamePasswordAuthenticationToken auth= new UsernamePasswordAuthenticationToken(
							dbUserDetails.getUsername(), dbUserDetails.getPassword(), dbUserDetails.getAuthorities());
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
					
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
