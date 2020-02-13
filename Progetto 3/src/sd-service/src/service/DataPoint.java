package service;


class DataPoint {
	private String date;
	private String type;
	private int weight;
	
	
	public DataPoint(String type, String date, int weight) {
		this.type = type;
		this.date = date;
		this.weight = weight;
	}
	
	public String getType() {
		return type;
	}
	
	public String getDate() {
		return date;
	}
	
	public int getWeight() {
		return weight;
	}
}
