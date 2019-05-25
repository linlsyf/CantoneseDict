package com.business.login;

import java.io.Serializable;

/**
 * 
* 创建者：ldh
* 修改时间：2015-6-3 上午10:05:06
* 作用：
 */
public class LoginPerson extends LoginPersonResult{
	public String name;
	public String pwd;
	public String loginId;
	/**用于后续 加入公司登录使用*/
	public String access_token;
	public String refresh_token;
	/**应用ID , 登陆企业管理后台为 ADMINISTRATOR, 平台为 PLATFORM, 其它为对应的APP ID*/
	public String app_id;
	
	
	private  String id;
	private  String email;
	private  String mobile;
	private  String sex;
	private  String avatar_id;
	private  String avatar_url;
	private  String phone;
	private  String login_time;
	
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAvatar_id() {
		return avatar_id;
	}
	public void setAvatar_id(String avatar_id) {
		this.avatar_id = avatar_id;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLogin_time() {
		return login_time;
	}
	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}

	

}
