package com.walmart.ticket.helper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.walmart.ticket.model.Customer;
import com.walmart.ticket.model.Location;
import com.walmart.ticket.model.Seat;
import com.walmart.ticket.model.Hold;

public class TicketServiceHelperTest {
	
	TicketServiceHelper systemUnderTest;

	@Before
	public void setUp() throws Exception {
		systemUnderTest = new TicketServiceHelper();
	}

	@Test
	public void testIsValidEmailValidTest() {
		boolean actual = systemUnderTest.isValidEmail("test@test.com");
		assertEquals(true, actual);
	}
	@Test
	public void testIsValidEmailInvalidTest() {
		boolean actual = systemUnderTest.isValidEmail("test@@");
		assertEquals(false, actual);
	}

	@Test
	public void testIsValidNumberValidTest() {
		boolean actual = systemUnderTest.isValidNumber("10");
		assertEquals(true, actual);
	}
	
	@Test
	public void testIsValidNumberInvalid() {
		boolean actual = systemUnderTest.isValidNumber("ab");
		assertEquals(false, actual);
	}


	@Test
	public void testValidateCustomerValidTest() {
		boolean actual = systemUnderTest.validateCustomer(null, null);
		assertEquals(false, actual);
	}
	
	@Test
	public void testValidateCustomerInvalidTest() {
		boolean actual = systemUnderTest.validateCustomer("test@gmail.com", "test@gmail.com");
		assertEquals(true, actual);
	}
	
	@Test
	public void testReservationCode() {
		Customer customer = new Customer("test@test.com");
		
		Location location = new Location(0, 2);
		Seat seat = new Seat(location);
		List<Seat> seats = new ArrayList<>();
		seats.add(seat);
		
		Hold hold = new Hold();
		hold.setCustomer(customer);
		hold.setId(1);
		hold.setSeatsHeld(seats);
		systemUnderTest.reservationCode(hold);
	}

}
