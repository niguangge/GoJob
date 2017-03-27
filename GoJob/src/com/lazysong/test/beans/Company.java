package com.lazysong.test.beans;

public class Company {
	private int company_id;
	private String company_name;
	public static final String name="Company";

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Company(int company_id, String company_name) {
		super();
		this.company_id = company_id;
		this.company_name = company_name;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
}
