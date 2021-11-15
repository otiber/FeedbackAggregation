package edu.biskra.services;

import java.util.Random;

/**
 * HLPService class simulate a service with consistent Low performance (bad QOS) during the half duration 
 * then, consistent low performance for the second half of life duration
 * So, the randomly generated reputation are included in [0,2] then [0.8, 1].
 * @author Okba Tibermacine, Biskra University Algeria  
 */
public class LHPService extends Service {

	
		public LHPService(short service_id)
		{
			this.service_ID=service_id;
		}
		
	@Override
	public void generate_new_reputation() {
		// TODO Auto-generated method stub

		float minX = 0.78f;
		float maxX = 0.95f;

		Random rand = new Random();
				
		if(this.dayNumber<(this.duration/2))
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
		
		LHPService hlp = new LHPService((short)1);
		for (int i=40;i<60;i++)
		{
			hlp.setDayNumber((short) i);
			hlp.generate_new_reputation();
			System.out.println(hlp.getDailyReputation());
		}
	}

	@Override
	public String toString() {
		return "LHPService "+service_ID+" Day:"+dayNumber+" Origin:"+dailyReputation+" Assessed:"+getAssessedReputation();
	}

	@Override
	public void generate_responseTime() {
		
		float minX = 20.0f;
		float maxX = 80.0f;

		Random rand = new Random();

		// I added math.round recently  *******
		this.responseTime= (rand.nextFloat() * (maxX - minX) + minX);


		
	}
}
