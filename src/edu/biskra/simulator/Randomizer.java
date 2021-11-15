package edu.biskra.simulator;

import java.util.Random;

public class Randomizer {
	
	/**
	 * the function randomRank returns a random rank that ranges between i and J [i,j[ (j not included)
	 * @param i
	 * @param j
	 * @return
	 */
	static public int randomRank(int i, int j)
	{
		java.util.Random r= new Random();
		
		return (r.nextInt(j-i)+i);			
	}
	
	static public int randomIndex(int min, int max)
	{
		Random rand = new Random();
		return rand.nextInt(max - min)+ min;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int i=0;i<20;i++)
		 System.out.println(Randomizer.randomIndex(0, 6));	
		
		
		/*java.util.Random r = new Random();
		
		float a= r.nextFloat();
		 System.out.println(a+": "+((int)(a*10)) );
	*/	
		
	}
}
