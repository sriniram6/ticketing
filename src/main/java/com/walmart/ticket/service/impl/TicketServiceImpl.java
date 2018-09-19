package com.walmart.ticket.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

import com.walmart.ticket.helper.TicketServiceHelper;
import com.walmart.ticket.model.Customer;
import com.walmart.ticket.model.Seat;
import com.walmart.ticket.model.Hold;
import com.walmart.ticket.model.Theatre;
import com.walmart.ticket.model.enums.STATUS;
import com.walmart.ticket.service.TicketService;

public class TicketServiceImpl implements TicketService {
	private int available;
	private Theatre theatre;
	private Map<Integer, Hold> seatHoldMapper;
	private int seconds = 100;
	private TicketServiceHelper helper;
	private int counter; 

	public TicketServiceImpl(Theatre v, int secs) {
		super();
		this.theatre = v;
		this.available = v.getCapacity();
		seatHoldMapper = new ConcurrentSkipListMap<Integer, Hold>();
		helper = new TicketServiceHelper();
		this.seconds = secs;
		counter  = 0;
		
	}
	
	public TicketServiceImpl(Theatre v) {
		this(v, 100);
	}

	public int numSeatsAvailable() {
		expiryCheck();
		System.out.println(theatre.displaySeats());
		return available;
	}

	private void expiryCheck() {
		Set<Map.Entry<Integer, Hold>> seatHoldMapperSet = seatHoldMapper.entrySet();
		seatHoldMapperSet.forEach(m -> expiryCheckforSeatHold(m.getValue().getId()));
	}

	private void expiryCheckforSeatHold(int seatHoldId) {
		Hold tempSH = seatHoldMapper.get(seatHoldId);
		if(tempSH!=null){
			long now = Instant.now().getEpochSecond();
			if((now - tempSH.getCreatedAt().getEpochSecond())> this.seconds){

				updateStatus(tempSH.getSeatsHeld(), STATUS.AVAILABLE);
				this.available += tempSH.getSeatsHeld().size();
				seatHoldMapper.remove(seatHoldId);
			}
		}
	}

	public Hold findAndHoldSeats(int numSeats, String customerEmail) {
		expiryCheck();
		List<Seat> holdingSeats = findAvailableSeats(numSeats);
		updateStatus(holdingSeats, STATUS.HOLD);
		this.available -= holdingSeats.size();
		Hold hold = generateSeatHold(holdingSeats, customerEmail);
		if(hold!=null) {
			seatHoldMapper.put(hold.getId(), hold);
		}
		return hold;
	}

	private void updateStatus(List<Seat> seats, STATUS status){
		for(Seat st: seats){
			st.setStatus(status);
		}
	}
	
	private Hold generateSeatHold(List<Seat> holdingSeats, String customerEmail){
		if(holdingSeats.size()<1){
			return null;
		}
		Hold hold = new Hold();
		hold.setCustomer(new Customer(customerEmail));
		hold.setSeatsHeld(holdingSeats);
		hold.setCreatedAt(Instant.now());
		synchronized(this) {
			hold.setId(++counter);
		}
		return hold;
	}
	
	private List<Seat> findAvailableSeats(int numSeats){
		if(this.available < numSeats){
			System.out.println("There are only " + this.available + " seats available now!");
			return new LinkedList<Seat>(); 
		}
		Seat[][] seats = theatre.getSeats();
		List<Seat> storeSeats = new LinkedList<Seat>();
		boolean breakFlag = false;
		for(int i=0; i < theatre.getRows(); i++){
			if(breakFlag){
				break;
			}
			for(int j=0; j < theatre.getSeatsPerRow(); j++){
				Seat st = seats[i][j];
				if(STATUS.AVAILABLE == st.getStatus()){
					storeSeats.add(st);
					if(--numSeats == 0){
						breakFlag = true;
						break;
					}
				}
			}
		}
		return storeSeats;
	}
	public String reserveSeats(int seatHoldId, String customerEmail) {
		expiryCheckforSeatHold(seatHoldId);
		Hold hold = finder(seatHoldId);
		if(hold == null){
			System.out.println("Either seatHoldId is invalid OR is expired! ");
			return null;
		}
		boolean isValidCustomer = helper.validateCustomer(customerEmail, hold.getCustomer().getEmail());
		if(!isValidCustomer){
			return "cannot verify customer. Please request reservation with correct customer email.";
		}
		updateStatus(hold.getSeatsHeld(), STATUS.RESERVED);
		String result =  helper.reservationCode(hold);
		seatHoldMapper.remove(seatHoldId);
		return result;
	}
	
	private Hold finder(int seatHoldId){
		return seatHoldMapper.get(seatHoldId);
	}
}
