package utils;

public class Rating implements Comparable<Rating> {
	long timestamp;
	float rating;
	long itemID;
	public Rating(long timestamp, float rating, long itemID) {
		this.timestamp = timestamp;
		this.rating = rating;
		this.itemID = itemID;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public long getItemID() {
		return itemID;
	}
	
	public float getRating(){
		return rating;
	}
	
	@Override
	public int compareTo(Rating t) {
		if (t == null) {
			return Integer.MIN_VALUE;
		}
		if (t.getTimestamp() > this.getTimestamp()) {
			return -1;
		} 
		else if(t.getTimestamp() == this.getTimestamp()){
			return 0;
		}
		else {
			return 1;
		}
	}
	
	@Override
	public String toString() {
		String s = this.itemID + "," + this.rating + "," + this.timestamp ; 
		return s;
	}
}