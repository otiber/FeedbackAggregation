package edu.biskra.users;

import edu.biskra.simulator.Randomizer;

public class HonestUser extends User {
	
	public HonestUser(short userid)
	{
		this.userID=userid;
	}

	@Override
	public byte generate_rankValue(float originReputation) {
		
		int rank = ((int)(Math.round(originReputation*10)));
		
		
		 // The honest user ranks are always in 2 degree of difference.
		 
		
		if (rank>=2)
		{
			if (rank<=9)
				return ((byte) Randomizer.randomRank(rank-1, rank+2));
			else
				return ((byte) Randomizer.randomRank(rank-1, 11));
		}
		else
		{	if (rank<=9)		
			return ((byte) Randomizer.randomRank(1, rank+2));
			else
			return ((byte) Randomizer.randomRank(1, 11));
		} 
		
		
	//	return (byte) Math.round(originReputation*10);
	}

	// Main Class for testing ranks of these users
	
	public static void main(String[] args) {
		HonestUser ds= new HonestUser((short) 1);
		
		for (int i=0;i<15;i++)
			System.out.println(ds.generate_rankValue((float) 0.754));
	}

	@Override
	public String toString() {		
		return "HonestRater N+"+userID+" MyHonesty="+HonestyCoefficient;
	}

}
