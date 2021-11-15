package edu.biskra.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/***
 * 
 * This Class run a simulation with different Honest user Density
 * 
 * @author Okba tibermacine, Biskra University
 *
 */
public class GlobalSimulator {

	public static void main(String[] args) {
		
		for(int density =10; density <=100; density=density+5)
		{
			System.err.println("== EXperiment with HUD ="+ density);
			try {
				Simulator sm = new Simulator();
				sm.setHonestUserRate((short) density);
				sm.start_simulation();
				
				//  Reputation
				int iterNbr = sm.getAssessdReputation().length -1;
				PrintWriter output = new PrintWriter(new File(density+"Rep.txt"));
				
				for(int k=1;k<=sm.getNumberOfDays();k++)
				      output.println(k+"#"+sm.getOriginReputation()[iterNbr][k-1][0]+"#"+sm.getAssessdReputation()[iterNbr][k-1][0]+"#"+sm.getRecall()[iterNbr][k-1][0][0]+"#"+sm.getRecall()[iterNbr][k-1][0][1]+"#"+sm.getExecutionTime());
				
				for(int k=1;k<=sm.getNumberOfDays();k++)
				      output.println(k+"#"+sm.getOriginReputation()[iterNbr][k-1][1]+"#"+sm.getAssessdReputation()[iterNbr][k-1][1]+"#"+sm.getRecall()[iterNbr][k-1][1][0]+"#"+sm.getRecall()[iterNbr][k-1][1][1]+"#"+sm.getExecutionTime());
				
				for(int k=1;k<=sm.getNumberOfDays();k++)
				      output.println(k+"#"+sm.getOriginReputation()[iterNbr][k-1][2]+"#"+sm.getAssessdReputation()[iterNbr][k-1][2]+"#"+sm.getRecall()[iterNbr][k-1][2][0]+"#"+sm.getRecall()[iterNbr][k-1][2][1]+"#"+sm.getExecutionTime());
				
				for(int k=1;k<=sm.getNumberOfDays();k++)
				      output.println(k+"#"+sm.getOriginReputation()[iterNbr][k-1][3]+"#"+sm.getAssessdReputation()[iterNbr][k-1][3]+"#"+sm.getRecall()[iterNbr][k-1][3][0]+"#"+sm.getRecall()[iterNbr][k-1][3][1]+"#"+sm.getExecutionTime());
				
				for(int k=1;k<=sm.getNumberOfDays();k++)
				      output.println(k+"#"+sm.getOriginReputation()[iterNbr][k-1][4]+"#"+sm.getAssessdReputation()[iterNbr][k-1][4]+"#"+sm.getRecall()[iterNbr][k-1][4][0]+"#"+sm.getRecall()[iterNbr][k-1][4][1]+"#"+sm.getExecutionTime());
				      
				output.close();
				
				//  Recall and MAE
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
