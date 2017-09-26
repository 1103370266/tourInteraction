package com.tourInteraction.entity;

import com.tourInteraction.entity.base.Base;

public class User extends Base{

	private static final long serialVersionUID = 1L;
	private String userName;
	private String passWord;
	private String email;
	private String phoneNumber;
	private int roleId;
	private String roleName;
	private String userIconPath;
	private long integration;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getUserIconPath() {
		return userIconPath;
	}
	public void setUserIconPath(String userIconPath) {
		this.userIconPath = userIconPath;
	}
	public long getIntegration() {
		return integration;
	}
	public void setIntegration(long integration) {
		this.integration = integration;
	}
	
}
