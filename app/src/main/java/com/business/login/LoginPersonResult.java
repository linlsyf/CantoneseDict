package com.business.login;

import java.io.Serializable;
/**
 * 
* 创建者：ldh
* 修改时间：2015-6-3 上午10:05:06
* 作用：
 */
public class LoginPersonResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**用于后续 加入公司登录使用*/
	public String accessToken;
	public String refreshToken;
	public String user;
	public String expires_in;
	public Object data;
	/**应用ID , 登陆企业管理后台为 ADMINISTRATOR, 平台为 PLATFORM, 其它为对应的APP ID*/
	
	/**所属企业列表*/
	private  Object companies;
	/**可以编辑的企业列表*/
	private  Object manageCompanies;
	
	public String getExpires_in() {
		return expires_in;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Object getCompanies() {
		return companies;
	}
	public void setCompanies(Object companies) {
		this.companies = companies;
	}
	public Object getManageCompanies() {
		return manageCompanies;
	}
	public void setManageCompanies(Object manageCompanies) {
		this.manageCompanies = manageCompanies;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Object getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
