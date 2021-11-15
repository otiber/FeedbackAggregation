package edu.biskra.users;

import java.util.Random;

import edu.biskra.simulator.Randomizer;

public class DishonestUser extends User {
	
	public DishonestUser(short userid)
	{
		this.userID=userid;
	}

	@Override
	public byte generate_rankValue(float originReputation) {
		
		int rank = ((int)(Math.round(originReputation*10)));
		
		/**
		 * Dishonest ranks are always far with 2 degree
		 */
		if (rank >=8) // Case of good Service  
		return (byte) Randomizer.randomIndex(1, 7);   // 7 is excluded  so value are randomly between 1 and 7
		else
			if (rank<=2) // Case of bad Service
				return (byte) Randomizer.randomIndex(1, 11);   // 11 excluded, so till 10.
			else
				if (new Random().nextBoolean())
				return (byte) Randomizer.randomIndex(1, rank-1);
				else
					return (byte) Randomizer.randomIndex(rank+2, 11);
		
	}
	
	/* Main mathod for testing the rankings provided by this user*/ 
	public static void main(String[] args) {

		DishonestUser ds= new DishonestUser((short) 1);
		
		for (int i=0;i<15;i++)
			System.out.println(ds.generate_rankValue((float) 0.254));
	}

	@Override
	public String toString() {
		return "DishonestRater N+"+userID+" MyHonesty="+HonestyCoefficient;
	}
}
