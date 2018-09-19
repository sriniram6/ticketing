package com.walmart.ticket.model;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.walmart.ticket.model.enums.STATUS;

/**
 * Theatre Seating arrangement: assuming 0th row is last row(farthest from screen) in seating. 
 * 
 *
 */
public class Theatre {
	private int rows;
	private int seatsPerRow;
	private Seat[][] seats;
	private int capacity;

	public Theatre(int rows, int seatsPerRow) {
		super();
		this.rows = rows;
		this.seatsPerRow = seatsPerRow;
		this.capacity = (this.rows * this.seatsPerRow);
		init();
	}
	private void init(){
		seats = new Seat[rows][seatsPerRow];
	    IntStream.range(0, seats.length).forEach(i -> 
	        IntStream.range(0, seats[i].length).forEach(j -> 
	            seats[i][j] = new Seat(new Location(i, j), STATUS.AVAILABLE)));
	 }
  
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getSeatsPerRow() {
		return seatsPerRow;
	}
	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}
	public Seat[][] getSeats() {
		return seats;
	}
	public void setSeats(Seat[][] seats) {
		this.seats = seats;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Venue [rows=").append(rows).append(", seatsPerRow=")
				.append(seatsPerRow).append(", ");
		if (seats != null)
			builder.append("seats=").append(Arrays.toString(seats));
		builder.append("]");
		return builder.toString();
	}
	public String displaySeats(){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i< rows; i++){
			for(int j=0; j < seatsPerRow; j++){
				String s = seats[i][j].getStatus().name().substring(0, 1);
				sb.append(s); sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString().trim();
	}
}
