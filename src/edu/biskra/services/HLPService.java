package edu.biskra.services;

import java.util.Random;

/**
 * HLPService class simulate a service with consistent high performance (very good QOS) during the half duration 
 * then, consistent low performance for the second half of life duration 
 * consistent low performance for the remaining days 
 * So, the randomly generated reputation are included in [0.8, 1] then [0,2]
 * @author Okba Tibermacine, Biskra University Algeria  
 */
public class HLPService extends Service {

	public HLPService(short service_id)
	{
		this.service_ID=service_id;
	}
	
	/**
	 * this function generates randomly high Qos performance values then low values (1/5,1/5)  
	 */
	@Override
	public void generate_new_reputation() {
		
		float minX = 0.78f;
		float maxX = 0.95f;

		Random rand = new Random();
				
		if(this.dayNumber>(this.duration/2))
		{
			 minX = 0.05f;
			 maxX = 0.22f;
		}
		this.dailyReputation = Math.round((rand.nextFloat() * (maxX - minX) + minX)*10);
		this.dailyReputation/=10;
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HLPService hlp = new HLPService((short)1);
		for (int i=40;i<60;i++)
		{
			hlp.setDayNumber((short) i);
			hlp.generate_new_reputation();
			System.out.println(hlp.getDailyReputation());
		}
	}

	@Override
	public String toString() {
		return "HLPService "+service_ID+" Day:"+dayNumber+" Origin:"+dailyReputation+" Assessed:"+getAssessedReputation();
	}

	@Override
	public void generate_responseTime() {
		// TODO Auto-generated method stub
		float minX = 20.0f;
		float maxX = 80.0f;

		Random rand = new Random();

		// I added math.round recently  *******
		this.responseTime= (rand.nextFloat() * (maxX - minX) + minX);

	}
}
