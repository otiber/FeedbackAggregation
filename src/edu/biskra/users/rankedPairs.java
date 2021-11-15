package edu.biskra.users;

/**
 * 
 * @author Okba Tibermacine
 * class that holds which service is ranked and the type of Feedback (positive or negative)
 */

public class rankedPairs {
	
	public short serviceID;
	public boolean isPositiveRate;
	
	public rankedPairs(short serviceid, boolean p)
	{
		this.serviceID=serviceid;
		this.isPositiveRate=p;
	}

	public short getServiceID() {
		return serviceID;
	}

	public void setServiceID(short serviceID) {
		this.serviceID = serviceID;
	}

	public boolean isPositiveRate() {
		return isPositiveRate;
	}

	public void setPositiveRate(boolean isPositiveRate) {
		this.isPositiveRate = isPositiveRate;
	}
	
}

