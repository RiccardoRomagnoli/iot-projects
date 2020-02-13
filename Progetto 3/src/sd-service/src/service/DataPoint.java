package service;

import java.util.*;

class DataPoint {
	private String date;
	private String type;
	
	@SuppressWarnings("deprecation")
	public DataPoint(String type, String date) {
		this.type = type;
		this.date = date;
	}
	
	public String getType() {
		return type;
	}
	
	public String getDate() {
		return date;
	}
}
