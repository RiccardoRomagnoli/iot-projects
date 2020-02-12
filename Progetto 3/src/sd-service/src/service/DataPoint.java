package service;

import java.util.*;

class DataPoint {
	private Date date;
	private String type;
	
	@SuppressWarnings("deprecation")
	public DataPoint(String type, String date) {
		this.type = type;
		this.date = new Date(date);
	}
	
	public String getType() {
		return type;
	}
	
	public Date getDate() {
		return date;
	}
}
