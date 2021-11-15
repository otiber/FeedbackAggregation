package edu.biskra.services;

import java.util.ArrayList;

/***
 * 
 * @author Okba Tibermacine
 * the Service class captures the web service behavior during the simulation process 
 */
public abstract class Service {

	protected short service_ID;			// Service identifier
	protected float dailyReputation;		// Based on Qos performances (the probability that user get satisfacted from offered Qos)
	protected short dayNumber;
	private float assessedReputation; 
	private int NumberOfPostiveRanks =0;
	private int NumberOfNegativeRanks =0;
	protected short LastRankingdayNumber=0;
	protected float responseTime=0;
	public ArrayList<Rate> allRates = new ArrayList<Rate>();
	
	// the attribute holds the service's life duration
	protected int duration =100;
		
	// generic constructor
	public Service()
	{
		
	}
	
	//new instance with specific ID
	public Service(short id) 
	{
		this.service_ID=id;
		generate_responseTime();
	}

	public abstract void generate_new_reputation();
	
	public abstract void generate_responseTime();
	
	public synchronized void add_rate(short user_Id, byte value )
	{
		Rate r= new Rate(user_Id, this.service_ID, value, this.dayNumber);
		allRates.add(r);
		if (value>=5)
			this.NumberOfPostiveRanks++;
		else
			this.NumberOfNegativeRanks++;
		
		this.LastRankingdayNumber=dayNumber;
	}
	
	public short getLastRankingdayNumber() {
		return LastRankingdayNumber;
	}

	public short getService_ID() {
		return service_ID;
	}

	public void setService_ID(short service_ID) {
		this.service_ID = service_ID;
	}

	public float getDailyReputation() {
		return dailyReputation;
	}

	public void setDailyReputation(float dailyReputation) {
		this.dailyReputation = dailyReputation;
	}

	public float getAssessedReputation() {
		return assessedReputation;
	}

	public void setAssessedReputation(float assessedReputation) {
		this.assessedReputation = assessedReputation;
	}

	public short getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(short dayNumber) {
		this.dayNumber = dayNumber;
		generate_new_reputation();
	}

	public int getNumberOfPostiveRanks() {
		return NumberOfPostiveRanks;
	}

	public int getNumberOfNegativeRanks() {
		return NumberOfNegativeRanks;
	}

	public ArrayList<Rate> getAllRates() {
		return allRates;
	}
	
	public void clearAllRates()
	{
		allRates.clear();
	}
	abstract public String toString();

	public float getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(float responseTime) {
		this.responseTime = responseTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
