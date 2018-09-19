package com.walmart.ticket.model;

public class Customer {
	
	private String email;
	public Customer(String email) {
		super();
		this.email = email;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (email != null)
			builder.append(email);
		return builder.toString();
	}
	
	
	
}
