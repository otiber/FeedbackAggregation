package edu.biskra.services;
/**
 * 
 * @author okba
 * We use instances of the Rate class to simulate the rank operation for a service. 
 * The Rate class hold the User_Id, the Service_Id and the Rank value [0,10], and the timeStamp of the rate
 */
public class Rate {
	
	private short user_ID;
	private short service_ID;
	private byte rank_value;  
	private short day_Number;
	
	/****************************************************************/
	       // other information can be added here (Time Stamp)
	/****************************************************************/
	
	// Public constructor 
	public Rate(){
		
	}
	
	public Rate(short userID, short serviceId, byte rank, short numdays){
		
		this.user_ID=userID;
		this.service_ID=serviceId;
		this.rank_value=rank;
		this.day_Number=numdays;
		
	}
	
	// attribute Getters and Setters.
	public short getUser_ID() {
		return user_ID;
	}
	public void setUser_ID(short user_ID) {
		this.user_ID = user_ID;
	}
	public short getService_ID() {
		return service_ID;
	}
	public void setService_ID(short service_ID) {
		this.service_ID = service_ID;
	}
	public byte getRank_value() {
		return rank_value;
	}
	public void setRank_value(byte rank_value) {
		this.rank_value = rank_value;
	}
	public short getDay_Number() {
		return day_Number;
	}
	public void setDay_Number(short day_Number) {
		this.day_Number = day_Number;
	}
	
	public String toString()
	{
		return this.user_ID + " "+this.service_ID+" "+ this.rank_value+" "+this.day_Number;
	}
}
