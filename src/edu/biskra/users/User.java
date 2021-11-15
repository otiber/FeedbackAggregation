package edu.biskra.users;

import java.util.ArrayList;

public abstract class User {
	
	protected short userID;
	protected float HonestyCoefficient=(float) 0.5;

	// a list that holds all user ratings.
	public ArrayList<rankedPairs> MyRates = new ArrayList<rankedPairs>(); 
    
	public User(){}
	
	public User(short userId){
		this.userID=userId;
	}

	/**
	 * @param originReputation (satisfaction probability of user by offered Qos)
	 * @return rank
	 * abstract method to be implemented by children classes,
	 * this function generate a possible rank based on Qos provided by the service
	 */
	
	public synchronized final byte doGenerate_rankValue(float originReputation){
		//System.err.println("yessss");
		return generate_rankValue(originReputation);
	}
	
	abstract public byte generate_rankValue(float originReputation);

	public synchronized void addRankedPair(short serviceId, byte rankValue)
	{
		rankedPairs rp;
		
		if (rankValue<5)
			rp = new rankedPairs(serviceId, false);  // negative rank
		else
			rp = new rankedPairs(serviceId, true);   // positive rank
		
		MyRates.add(rp);
	}
	
	// getter and setter
	public short getUserID() {
		return userID;
	}

	public void setUserID(short userID) {
		this.userID = userID;
	}

	public float getHonestyCoefficient() {
		return HonestyCoefficient;
	}

	public void setHonestyCoefficient(float honestyCoefficient) {
		//HonestyCoefficient = honestyCoefficient;
		HonestyCoefficient = (HonestyCoefficient+ honestyCoefficient)/2;
	}

	public ArrayList<rankedPairs> getMyRates() {
		return MyRates;
	}
	
	public void clearRates()
	{
		MyRates.clear();
	}
	
	abstract public String toString();

}