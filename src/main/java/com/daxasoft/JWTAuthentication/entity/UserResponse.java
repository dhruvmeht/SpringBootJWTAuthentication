package com.daxasoft.JWTAuthentication.entity;

import lombok.Data;

@Data
public class UserResponse {

	private String token;
	private String msg;
	public UserResponse(String token2, String string) {
		// TODO Auto-generated constructor stub
		this.token=token2;
		this.msg=string;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
