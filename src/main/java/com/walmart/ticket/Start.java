package com.walmart.ticket;
/**
 * Ticketing Command Line Interface
 * 
 */

import java.util.Scanner;

import com.walmart.ticket.helper.TicketServiceHelper;
import com.walmart.ticket.model.Hold;
import com.walmart.ticket.model.Theatre;
import com.walmart.ticket.service.TicketService;
import com.walmart.ticket.service.impl.TicketServiceImpl;

public class Start {
	public final static String OPTIONS = "\nOptions: \n1. Start \n2. Available Seats "
			+ "\n3. Request for Hold \n4. Request For Reservation \n5. Exit.\n";
	private int rows;
	private int seatsPerRow;
	private Theatre theatre;
	private TicketService ticketService;
	private TicketServiceHelper helper;
	 
	

	public Start() {
		helper = new TicketServiceHelper();
	}
	
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("\t\t\t TicketService System");
		System.out.println("\t\t\t ====================");
		Start main = new Start();
		main.initializeDefaultValues();
		boolean loop = true;
		while(loop) {
			System.out.println(OPTIONS);
			String str = sc.next();
			boolean isValidInput = main.getHelper().isValidNumber(str);
			if(!isValidInput){
				System.out.println("Select only numbers.");
				continue;
			}
			int input = Integer.parseInt(str);
			switch(input){
			case 1:{
				main.startOperation(sc);
				break;
			 }
			case 2:
				main.noOfSeatsAvailable();
				break;
			case 3:
				main.holdRequest(sc);
				break;
			case 4:
				main.commitTicket(sc);
				break;
			case 5:
				loop = false;
				System.out.println("\nBye!");
				break;
			default:
				System.out.println("Please enter valid Option.");
			}
		}
		sc.close();
		
	
	}
	
	public void startOperation(Scanner sc) {
		System.out.println("How many rows?");
		String inputRow = sc.next();
		rows = loopUntilValidNumberEntered(sc, inputRow);
		System.out.println("How many seats per rows?");
		String inputSeatsPerRow = sc.next();
		seatsPerRow = loopUntilValidNumberEntered(sc, inputSeatsPerRow);
		System.out.println("Expiration seconds (Optional (default value 100 : Enter s to skip)");
		String holdTimeout = sc.next();
		theatre = new Theatre(rows, seatsPerRow);
		int secs = 100;
		if(helper.isValidNumber(holdTimeout)) {
			secs = Integer.parseInt(holdTimeout);
		}
		System.out.println("Hold Timeout (in secs) ::::::::" + secs);
		ticketService = new TicketServiceImpl(theatre, secs);
		
	}
	
	public void noOfSeatsAvailable() {
		System.out.println("\nNo of seats available now: " + ticketService.numSeatsAvailable());
	}
	
	public void holdRequest(Scanner sc) {
		System.out.println("How many seats for hold?");
		String inputSeatsNumbers = sc.next();
		int seats = loopUntilValidNumberEntered(sc, inputSeatsNumbers);
		System.out.println("Please enter Customer Email.");
		String email = sc.next();
		email = loopUntilValidEmailEntered(sc, email);
		Hold hold = ticketService.findAndHoldSeats(seats, email);
		if(hold!=null){
			System.out.println("\n" + seats + " held!\n" + hold);
		}else{
			System.out.println("\nYour request has been failed! Please try again!");
		}
	}
	
	public void commitTicket(Scanner sc) {
		System.out.println("SeatHold Id?");
		String input = sc.next();
		boolean isValidDigit = helper.isValidNumber(input);
		if(!isValidDigit){
			while(!isValidDigit){
				System.out.println("Invalid no pattern.");
				System.out.println("Enter valid no:");
				input = sc.next();
				isValidDigit = helper.isValidNumber(input);
			}
		}
		int id = Integer.parseInt(input);
		System.out.println("Associated with which customer email?");
		String customerEmail = sc.next();
		customerEmail = loopUntilValidEmailEntered(sc, customerEmail);
		System.out.println("\n" + ticketService.reserveSeats(id, customerEmail));
	}
	
	public void initializeDefaultValues() {
		rows = 5;
		seatsPerRow = 5;
		theatre = new Theatre(rows, seatsPerRow);
		ticketService = new TicketServiceImpl(theatre);
		
	}
	
	public int loopUntilValidNumberEntered(Scanner sc, String input) {
		boolean isValid = helper.isValidNumber(input);
		if(!isValid){
			while(!isValid){
				System.out.println("Enter Valid Number.");
				input = sc.next();
				isValid = helper.isValidNumber(input);
			}
		}
		return Integer.parseInt(input);
	}
	
	public String loopUntilValidEmailEntered(Scanner sc, String email) {
		boolean isvalid = helper.isValidEmail(email);
		if(!isvalid){
			while(!isvalid){
				System.out.println("Invalid email pattern.");
				System.out.println("Enter valid email:");
				email = sc.next();
				isvalid = helper.isValidEmail(email);
			}
		}
		return email;
	}
	
	public TicketServiceHelper getHelper() {
		return helper;
	}
	

}
