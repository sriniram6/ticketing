package com.walmart.ticket.model;

import com.walmart.ticket.model.enums.STATUS;


public class Seat {
	Location seatNumber;
	Customer reservedBy;
	STATUS status;
	
	public Seat(Location seatNumber) {
		super();
		this.seatNumber = seatNumber;
	}
	
	public Seat(Location seatNumber, STATUS status) {
		this(seatNumber);
		this.status = status;
	}

	public Location getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(Location seatNo) {
		this.seatNumber = seatNo;
	}
	public Customer getReservedBy() {
		return reservedBy;
	}
	public void setReservedBy(Customer reservedBy) {
		this.reservedBy = reservedBy;
	}
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seat<");
		if (seatNumber != null)
			builder.append(seatNumber).append(", ");
		if (reservedBy != null)
			builder.append("reservedBy=").append(reservedBy).append(", ");
		if (status != null)
			builder.append("status=").append(status);
		builder.append(">");
		return builder.toString();
	}
	
	
}
