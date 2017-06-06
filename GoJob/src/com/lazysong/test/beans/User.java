package com.lazysong.test.beans;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;


public class User implements Serializable{
	@SerializedName("user_id")
	protected String user_id;
	@SerializedName("nickname")
	protected String nickname;
	@SerializedName("img_name")
	protected String img_name;//´æ´¢url
	@SerializedName("sex")
	protected byte sex;
	@SerializedName("birthday")
	protected Date birthday;
	@SerializedName("sign")
	protected String sign;
	@SerializedName("password")
	protected String password;

	public static final String name="User";
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String user_id, String password, String nickname, String sign,
			String img_name, byte sex, Date birthday) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.nickname = nickname;
		this.sign = sign;
		this.img_name = img_name;
		this.sex = sex;
		this.birthday = birthday;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getImg_name() {
		return img_name;
	}
	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}
	public byte getSex() {
		return sex;
	}
	public void setSex(byte sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
