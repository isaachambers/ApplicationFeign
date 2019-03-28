package com.feign.demo.domain;

import java.util.Date;

public class Payment {

	private Long paymentId;

	private Long customerId;

	private double amount;

	private String paymentMethod;

	private Date paymentDate;

	public Payment() {

	}

	public Payment(Long paymentId, Long customerId, double amount, String paymentMethod, Date paymentDate) {
		super();
		this.paymentId = paymentId;
		this.customerId = customerId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentDate = paymentDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
