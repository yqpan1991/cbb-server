package com.apollo.cbb.bean;

import org.apache.commons.lang.StringUtils;

public class UserInfo {
	private String userName;
	private String password;
	private int type;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean checkValidate() {
		return !StringUtils.isBlank(userName) && !StringUtils.isBlank(password);
	}
}
