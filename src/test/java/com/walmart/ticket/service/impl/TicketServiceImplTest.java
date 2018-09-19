
package com.walmart.ticket.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.ticket.model.Hold;
import com.walmart.ticket.model.Theatre;
import com.walmart.ticket.service.impl.TicketServiceImpl;

public class TicketServiceImplTest {
	private TicketServiceImpl systemUnderTest;
	private int second = 1;
	private int wait = 2000;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		systemUnderTest = new TicketServiceImpl(new Theatre(1,1), second);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void numSeatsAvailable() throws InterruptedException{
		int no = systemUnderTest.numSeatsAvailable();
		assert(no == 1);
		systemUnderTest = new TicketServiceImpl(new Theatre(2,3), second);
		no = systemUnderTest.numSeatsAvailable();
		assert(no == (2*3));
		systemUnderTest.findAndHoldSeats(2, "srini@test.com");
		no = systemUnderTest.numSeatsAvailable();
		assert(no == ((2*3)-2));
		Thread.sleep(wait); // default expire time is 5s. prior hold should be gone. (Timing is check on the scale of seconds)
		System.out.println("After waiting: " + systemUnderTest.numSeatsAvailable());
		assert((2*3) == systemUnderTest.numSeatsAvailable());
		systemUnderTest.findAndHoldSeats((2*3), "srini@test.com");
		assert(0 == systemUnderTest.numSeatsAvailable());
		Thread.sleep(wait); // default expire time is 5s. prior hold should be gone.
		assert((2*3) == systemUnderTest.numSeatsAvailable());
		Hold sh = systemUnderTest.findAndHoldSeats((2*3), "srini@test.com");
		no = systemUnderTest.numSeatsAvailable();
		//invalid test
		systemUnderTest.reserveSeats(sh.getId(), "srini@test1.com");
		//
		systemUnderTest.reserveSeats(sh.getId(), "srini@test.com");
		assert(no == systemUnderTest.numSeatsAvailable()); // reserving seats should not change no of seats. (assuming we don`t pass expiry)
		systemUnderTest = new TicketServiceImpl(new Theatre(2,3), second);
		sh = systemUnderTest.findAndHoldSeats((2*3), "srini@test.com");
		no = systemUnderTest.numSeatsAvailable();
		Thread.sleep(wait);
		systemUnderTest.reserveSeats(sh.getId(), "srini@test.com");
		assert((no + sh.getSeatsHeld().size()) == systemUnderTest.numSeatsAvailable()); // After expiration, all held seats should be available.
	}
	
	@Test
	public void findAndHoldSeats() throws InterruptedException{
		Hold s = systemUnderTest.findAndHoldSeats(1, "xyz@abc.com");
		assertNotNull(s);
		assert(1 == s.getSeatsHeld().size());
		s = systemUnderTest.findAndHoldSeats(1, "xyz@abc.com");
		assert(null == s);
		Thread.sleep(wait);
		s = systemUnderTest.findAndHoldSeats(1, "xyz@abc.com");
		assertNotNull(s);
		assert(1 == s.getSeatsHeld().size());
		Thread.sleep(wait);
		s= systemUnderTest.findAndHoldSeats(2, "xyz@abc.com");
		assert(null == s);
	}
	
	@Test
	public void reserveSeats() throws InterruptedException{
		Hold s = systemUnderTest.findAndHoldSeats(1, "xyz@abc.com");
		String conf = systemUnderTest.reserveSeats(s.getId(), "xyz@abc.com");
		assertNotNull(conf);
		assertTrue(conf.contains("reserved!"));
		conf = systemUnderTest.reserveSeats(0, "xyz@abc.com");
		assert(null == conf);
	}
	
	
}
