package com.feign.demo.domain;

public class Customer {
	private Long customerId;
	private String firstName;
	private String secondName;
	private String email;

	public Customer(Long customerId, String firstName, String secondName, String email) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.secondName = secondName;
		this.email = email;
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
