package edu.biskra.services;

import java.util.Random;

/**
 * The RPService simulates a service that provides oscillates Qos value
 * 
 * @author Tibermacine okba
 */
public class RPService extends Service {
	
	//public int daynumber=0;
	public RPService(short service_id)
	{
		this.service_ID=service_id;
	}
	@Override
	public void generate_new_reputation() {
		float minX = 0f;
		float maxX = 0.99f;
		Random rand = new Random();
		
	//	float x =(float) Math.sin((Math.PI * this.dayNumber) / 14);     // using sin function
	//	this.dailyReputation = (x+1)/2;
		
		this.dailyReputation = Math.round((rand.nextFloat() * (maxX - minX) + minX)*10);
		this.dailyReputation/=10;

		if (this.dailyReputation ==0)
		{
			this.dailyReputation = Math.round((rand.nextFloat() * (maxX - minX) + minX)*10);
			this.dailyReputation/=10;

		}
		else
		{
		 if (rand.nextBoolean())
			{
			float x = Math.round((rand.nextFloat() * (0.5 - 0.1) + 0.1));
			if (rand.nextBoolean())
			{
				if(this.dailyReputation+x >0.99)
					this.dailyReputation-=x;
				else
					this.dailyReputation +=x;
			}
			else
			{
				if(this.dailyReputation-x <0.1)
					this.dailyReputation+=x;
				else
					this.dailyReputation -=x;
			}
		}	
		 
		}
	}

	// for test generated qos
	public static void main(String[] args) {

	RPService rp = new RPService((short)1);
	
	for (int i=1;i<101;i++)
	{
		rp.setDayNumber((short) i);
		rp.generate_new_reputation();
		System.out.println(rp.getDailyReputation());
	}
	}
	@Override
	public String toString() {
		return "RPService "+service_ID+" Day:"+dayNumber+" Origin:"+dailyReputation+" Assessed:"+getAssessedReputation();
	}
	@Override
	public void generate_responseTime() {
		float minX = 20.0f;
		float maxX = 120.0f;

		Random rand = new Random();

		// I added math.round recently  *******
		this.responseTime= (rand.nextFloat() * (maxX - minX) + minX);

		
	}
}
