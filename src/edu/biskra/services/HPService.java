package edu.biskra.services;

import java.util.Random;

/**
 * HPService class simulate a service with consistent high performance (very good QOS).
 * Thus, the randomly generated reputation [0.8, 1] 
 * @author Okba Tibermacine, Biskra University Algeria 
 */
public class HPService extends Service {
	
	public HPService(short service_id)
	{
		this.service_ID=service_id;
	}

	@Override
	public void generate_new_reputation() {
		
		float minX = 0.7f;
		float maxX = 1.f;

		Random rand = new Random();

		// I added math.round recently  *******
		this.dailyReputation = Math.round((rand.nextFloat() * (maxX - minX) + minX)*10);
		this.dailyReputation/=10;
		
	}
	
	// main method to test generated reputation based on days 
	public static void main(String[] args) {
		
		HPService hps = new HPService((short) 1);
		for (int i=0;i<15;i++)
		{
			hps.generate_new_reputation();
			System.out.println(hps.getDailyReputation());
		}
	}

	@Override
	
	public String toString() {
		return "HPService "+service_ID+" Day:"+dayNumber+" Origin:"+dailyReputation+" Assessed:"+getAssessedReputation();
		
	}

	@Override
	public void generate_responseTime() {

		float minX = 20.0f;
		float maxX = 60.0f;

		Random rand = new Random();

		// I added math.round recently  *******
		this.responseTime= (rand.nextFloat() * (maxX - minX) + minX);

		
		//this.responseTime = 
		
	}
}
