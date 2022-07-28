package com.daxasoft.JWTAuthentication.util;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	@Value("${app.key}")
	private String key;
	
	public String generateToken(String subject) {
		//Token - name(subject),Issued time,Expiry,Issuer

		return 	Jwts.builder()
				.setSubject(subject)
				.setIssuer("DaxaSoft")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512, key.getBytes())
				.compact();
	}
	
	public Claims getClaims(String token) {
		System.out.println("parsing token "+token);		
		String token1 = token.replace("\"", "");
		return Jwts.parser()
				.setSigningKey(key.getBytes())
				.parseClaimsJws(token1)
				.getBody();
	}
	
	public String getUserName(String token) {
		
		Claims claims = getClaims(token);
		String userName=claims.getSubject();
		return userName;
	}
	
	public Date getExpiryDate(String token) {
		Claims claims = getClaims(token);
		Date expiryDate = claims.getExpiration();
		return expiryDate;
	}
	
	public Boolean validateUser(String token,String userName) {
		
		String tokenUserName= getUserName(token);
		Date tokenExpiryDate = getExpiryDate(token);
		if((userName.equals(tokenUserName)) && (tokenExpiryDate.after(new Date(System.currentTimeMillis()))))
			return true;
		return false;
	}

}
