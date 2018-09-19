
package com.walmart.ticket.helper;

import java.util.Scanner;
import java.util.UUID;

import org.apache.commons.validator.routines.EmailValidator;

import com.walmart.ticket.model.Seat;
import com.walmart.ticket.model.Hold;


public class TicketServiceHelper {
	public  boolean isValidEmail(String email) {
		if (email == null || "".equals(email))
			return false;

		email = email.trim();

		EmailValidator ev = EmailValidator.getInstance();
		return ev.isValid(email);
	}
	
	
	
	public  boolean isValidNumber(String number) {
		if(number == null){
			return false;
		}
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	
	public  boolean validateCustomer(String inputCustomer, String storedCustomer){
		if(inputCustomer == null || storedCustomer == null){
			return false;
		}
		return inputCustomer.equalsIgnoreCase(storedCustomer);
	}
	public String reservationCode(Hold hold){
		StringBuilder sb = new StringBuilder();
		sb.append("Congratulations! Your seats have been reserved!\n");
		sb.append("Details:\n");
		sb.append("Confirmation no: " + UUID.randomUUID().toString() + "\n");
		sb.append("seats: [ ");
		for(Seat st: hold.getSeatsHeld()){
			sb.append(st.getSeatNumber()); sb.append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
}
