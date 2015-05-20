package com.redhat.test.brms.model;

import java.util.ArrayList;
import java.util.List;


public class Customer {
	private String firstName;
	private String lastName;
	private List<Account> accounts;
	
	public Customer() {
		if(accounts == null) 
			accounts = new ArrayList<Account>();

	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public void addAccount(Account a) {
		this.accounts.add(a);
	}
}
