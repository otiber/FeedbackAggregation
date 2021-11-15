package edu.biskra.services;

import java.util.Random;
/**
 * LPService class simulate a service with consistent low performance (bad QOS)
 * so the randomly generated reputation [0, 2]  
 * @author Okba Tibermacine, Biskra University, Algeria.
 * @version 0.1 - 02/04/2014
 */
public class LPService extends Service {

	public LPService(short service_id)
	{
		this.service_ID=service_id;
	}
	
	@Override
	public void generate_new_reputation() {
		
		float minX = 0.00f;
		float maxX = 0.21f;
		Random rand = new Random();
		this.dailyReputation = Math.round((rand.nextFloat() * (maxX - minX) + minX)*10);
		this.dailyReputation/=10;
	}

	
	// main method to test the generated reputation values  
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			LPService lps = new LPService((short)1);
			for (int i=0;i<15;i++)
			{
				lps.generate_new_reputation();
				System.out.println(lps.getDailyReputation());
			}

		}

		@Override
		public String toString() {
			return "LPService "+service_ID+" Day:"+dayNumber+" Origin:"+dailyReputation+" Assessed:"+getAssessedReputation();
		}

		@Override
		public void generate_responseTime() {
			float minX = 80.0f;
			float maxX = 150.0f;

			Random rand = new Random();

			// I added math.round recently  *******
			this.responseTime= (rand.nextFloat() * (maxX - minX) + minX);

			
		}

}
