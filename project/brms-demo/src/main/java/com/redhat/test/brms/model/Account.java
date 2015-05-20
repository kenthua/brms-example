package com.redhat.test.brms.model;

public class Account {
	public static final String CHECKING = "CHECKING";
	public static final String SAVINGS = "SAVINGS";
	private String type;
	private double balance;
	private String number;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Account [type=" + type + ", balance=" + balance + "]";
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getBalance() {
		return balance;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
