package edu.biskra.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.biskra.services.HLPService;
import edu.biskra.services.HPService;
import edu.biskra.services.LHPService;
import edu.biskra.services.LPService;
import edu.biskra.services.RPService;
import edu.biskra.services.Rate;
import edu.biskra.services.Service;
import edu.biskra.users.DishonestUser;
import edu.biskra.users.HonestUser;
import edu.biskra.users.User;
import edu.biskra.users.rankedPairs;


/**
 * This class creates Services, Users and simulates the ratings actions.
 * in addition, it assess the reputation scores daily and the honesty factors.
 *  
 * @author Okba Tibermacine, Biskra University, Algeria.
 *
 */
public class Simulator {

	private ArrayList<Service> services = new ArrayList<Service>();     // List of Web services
	private ArrayList<User> raters = new ArrayList<User>();             // List of users

	// Simulation Parameters
	private short numberOfService = 500;                                 // Number of Services in the Simulation 100
	private short numberOfUsers = 1000;			                        // Number of users in the Simulation
	private short numberOfDays = 100;	                      			// watching service during a Number of days
	private int numberOfRatesPerDay=10000; 								// Number of Rates
	private short honestUserRate = 75;									// pourcentage of honest users amont all users. the ramining are dishonest users
	private double lamda =0.75;
	private int IterNbr =15; 												// Number of iteration 

	
	private int day=0;
	private int iternum =0;
	
	public Timer executionTime = new Timer();							// for calculating the execution time
	
	float [][][] originReputation ;
	float [][][] assessdReputation ;
	float [][][][] recall_Meanerror ;
	float [] execTimes;
	File fj= new File("Journal.txt");
	PrintWriter journal=null;

	public Simulator() throws FileNotFoundException 
	{
			
	}
		
	public void start_simulation() throws FileNotFoundException
	{
		execTimes = new float [IterNbr];
		originReputation  = new float[IterNbr+1][numberOfDays][5];    // reputation of each day for the five classe
		assessdReputation  = new float[IterNbr+1][numberOfDays][5];   // Assessed reputation each day for the 5  classes
		recall_Meanerror = new float [IterNbr+1][numberOfDays][5][2];              // store recall values
	    //***	 journal = new PrintWriter(fj);
		create_Raters();
		create_Services();
		
	for(iternum=0;iternum <IterNbr; iternum++)
	{   executionTime.start(); // start Round execution time
		initiatRaters();
		initiateService();
	  for (int i=0;i<numberOfDays;i++)
		  for (int j=0;j<5;j++)
			{originReputation[iternum][i][j]=0; assessdReputation[iternum][i][j]=0;}
			
			Run();
			executionTime.end();	// End of Round execution Time
			execTimes[iternum]=executionTime.getTotalTime();
	}		//***		journal.close();
		
		// aggregate results
			
		for (int i=0;i<numberOfDays;i++)
			  for (int j=0;j<5;j++)
			  {
				  float sum =0;
				  float sum2=0;
				  float sum3=0;
				  float sum4=0;
				  
				  for (int k=0;k<IterNbr;k++)
					  {sum+=originReputation[k][i][j];
					  sum2+=assessdReputation[k][i][j];
					  sum3+=recall_Meanerror[k][i][j][0];
					  sum4+=recall_Meanerror[k][i][j][1];
					  }
				  originReputation[IterNbr][i][j]=sum/IterNbr;
				  assessdReputation[IterNbr][i][j]=sum2/IterNbr;
				  recall_Meanerror[IterNbr][i][j][0]=sum3/IterNbr;    // recall
				  recall_Meanerror[IterNbr][i][j][1]=sum4/IterNbr;    // Absolute mean error 
			   }
	  }
	
	
	private void initiateService() {
		
		for (int i=0;i<services.size();i++)
			services.get(i).clearAllRates();
		
	}

	// preparation for new round
	private void initiatRaters() {
		for (int i=0;i<raters.size();i++)
			raters.get(i).clearRates();
	}

	/*-******************************************************************************************************************************-*/
	/**
	 * the function creates users based on HonestUserRate and user number
	 */
	private void create_Raters()
	{
		int nbrhonest = (numberOfUsers * honestUserRate)/100;
		int nbrdishonest = numberOfUsers  - nbrhonest;
		
		Random rm = new Random();
		for (int i=0;i<numberOfUsers;i++)
		{
			if((nbrhonest!=0)&&(nbrdishonest!=0))
			{
				int choice = rm.nextInt();
				if ((choice%2)==0)
					{raters.add(new HonestUser((short) i)); nbrhonest--;}
				else
					{raters.add(new DishonestUser((short) i)); nbrdishonest--;}
			}
			else
			{
				if (nbrhonest!=0)
					raters.add(new HonestUser((short) i));
				else
					raters.add(new DishonestUser((short) i));
			}
		}
		System.out.println("Info :"+numberOfUsers+" user are created successfully");
	}

	/*-********************************************************************************************************-*/
	/**
	 * the function creates 5 classes of services (High, Low, HighTenLow, LowThenHigh, Random).  
	 */
	private void create_Services()
	{
		int nbrInclass = numberOfService/5;
		for (int i=0;i<numberOfService;i++)
		{
			if (i<(nbrInclass))
				services.add(new HPService((short) i));
			else
				if(i<(nbrInclass*2))
					services.add(new LPService((short) i));
				else
					if (i<(nbrInclass*3))
						services.add(new HLPService((short) i));
					else
						if(i<(nbrInclass*4))
							services.add(new LHPService((short) i));
						else
							services.add(new RPService((short) i));
			services.get(i).setDuration(numberOfDays);
		}
	
		System.out.println("Info :"+numberOfService+" are created successfully");	
	}
	/*-********************************************************************************************************-*/
	/**
	 * Simulation run
	 */
	private void Run()
	{
		int nbrInclass = numberOfService/5;
		ArrayList <Thread> threads = new ArrayList<Thread>();
		for (int d=1;d<=numberOfDays;d++)
		{
			//System.out.println("Info: Simulating day:"+d);
			this.day=d-1;
			// generate Qos performance rate for every service 
			for (int i=0;i<numberOfService;i++)
			{			
				services.get(i).setDayNumber((short)d);
				services.get(i).generate_new_reputation();
				//***journal.println(services.get(i).toString());
			}
			
			// make rates
			int RateNbrforClass = numberOfRatesPerDay/5;
			int nbrOfServicesInClass =numberOfService/5;
			short selectedService=0;
			short selectedUser =0;

			for (int i=0;i<numberOfRatesPerDay;i++)
			{
				if (i<(RateNbrforClass))   // select service from the first class 
					selectedService = (short)Randomizer.randomIndex(0, nbrOfServicesInClass);
				else  // select service from 2nd class
					if(i<(RateNbrforClass*2))
						selectedService = (short)Randomizer.randomIndex(nbrOfServicesInClass,nbrOfServicesInClass*2);
					else // select service from the 3rd class
						if (i<(RateNbrforClass*3))
							selectedService = (short)Randomizer.randomIndex(nbrOfServicesInClass*2,nbrOfServicesInClass*3);
						else //select service from the 4th class
							if(i<(RateNbrforClass*4))
								selectedService = (short)Randomizer.randomIndex(nbrOfServicesInClass*3,nbrOfServicesInClass*4);
							else // select service from the 5th class
								selectedService = (short)Randomizer.randomIndex(nbrOfServicesInClass*4,numberOfService);
				
				/** - Select Random user*/
				selectedUser= (short) Randomizer.randomIndex(0, numberOfUsers);
				
				/** The task has to be parallel here*/
				Interaction in = new Interaction(selectedUser, selectedService, raters, services);
				//es.execute(new Interaction(selectedUser, selectedService, raters, services));
				//es.shutdown();
				Thread t = new Thread(in);
				threads.add(t);
				t.start();
			/*	byte rank = raters.get(selectedUser).generate_rankValue(services.get(selectedService).getDailyReputation());
				// Add the rank in the service
				services.get(selectedService).add_rate(raters.get(selectedUser).getUserID(), rank);
				// Mark the user 
				raters.get(selectedUser).addRankedPair(services.get(selectedService).getService_ID(), rank);
				//***		System.out.println(raters.get(selectedUser).toString()+"# rank service "+selectedService +" by "+rank+" : "+services.get(selectedService).toString()+ "|user :"+raters.get(selectedUser).getUserID()+ " origin :"+services.get(selectedService).getDailyReputation());
				//***	journal.println("User "+selectedUser+" rank service "+selectedService +" by "+rank);		*/				
			}
			
			// check if all threads are finished.
			for (int w=0;w<threads.size();w++) {
			    Thread t=threads.get(w);
			    try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(int k=0;k<raters.size();k++)
				{assessUserHonesty((short) raters.get(k).getUserID()); 
				//***	System.out.println("User "+raters.get(k).getUserID()+" :"+ raters.get(k).getHonestyCoefficient());
				}	
			 //	evaluateUsersHonesty((short) raters.get(k).getUserID());
			
			// assess for each services its origine reputation and its calculated reputation using our proposed formula.
			assessServiceReputation();    /// was 1	
			
			//***	for (int z=0;z<services.size();z++)
			//***	System.out.println(services.get(z).toString());
		}	
		// sauvegarder
		//*** saveData();
	}
	/*-********************************************************************************************************-*/
	/**
	 * function for service reputation assessment
	 */
	private void assessServiceReputation() {
		
		float reputation=0;
		double pos=0,neg=0;
		int Rpos,Rneg;
		float Fpos=0,Fneg=0;
		
		for (int i=0;i<services.size();i++)
		{
			//float coef =(services.get(i).getNumberOfPostiveRanks()-services.get(i).getNumberOfNegativeRanks())/(services.get(i).getNumberOfPostiveRanks()+services.get(i).getNumberOfNegativeRanks());
			pos=0; Rpos =0; Fpos=0;
			neg=0; Rneg=0; Fneg =0;
			ArrayList<Rate> serviceRates=services.get(i).getAllRates();
		//%	System.out.println("Ws "+services.get(i).getService_ID()+" :");
			for (int j=0;j<serviceRates.size();j++)
			{
		//%		System.out.println("_______________________________ : User :"+ serviceRates.get(j).getUser_ID()+" H :"+raters.get(serviceRates.get(j).getUser_ID()).getHonestyCoefficient() +" Rank :"+serviceRates.get(j).getRank_value()+" , day:"+serviceRates.get(j).getDay_Number());
				if(serviceRates.get(j).getRank_value()<=5) //nagative
				{
					short rankV = serviceRates.get(j).getRank_value();
					Rneg+=rankV;  // sum of negatives ranks
					float hfactor = raters.get(serviceRates.get(j).getUser_ID()).getHonestyCoefficient();
					neg+= rankV * Math.pow(lamda, services.get(i).getLastRankingdayNumber()-serviceRates.get(j).getDay_Number())* hfactor;
					Fneg +=Math.pow(lamda, services.get(i).getLastRankingdayNumber()-serviceRates.get(j).getDay_Number())* hfactor;
				}
				else
				{
					short rankV = serviceRates.get(j).getRank_value();
					Rpos+=rankV;
					float hfactor = raters.get(serviceRates.get(j).getUser_ID()).getHonestyCoefficient();
					pos+= rankV * Math.pow(lamda, services.get(i).getLastRankingdayNumber()-serviceRates.get(j).getDay_Number())* hfactor;
					Fpos +=Math.pow(lamda, services.get(i).getLastRankingdayNumber()-serviceRates.get(j).getDay_Number())* hfactor;
				}
			}
			
			/*if ((pos+neg)!=0)
				reputation=(float) (((pos-neg)/(pos+neg))+1)/2;
			else
				reputation=0;*/
			
			// Computing Reputation
			switch (serviceRates.size()) 
			{
			case 0: reputation =-1; System.err.println("a service without reputation") ; break;    //**** should be not calculated
			case 1: reputation = serviceRates.get(0).getRank_value()/10; break;   //  ranks ranges between [0.10] so reputation = rank/10;
			default :
					if (Rpos==0 && Rneg!=0)
						reputation = (float) (neg/(Fneg*10));
					else
						if (Rpos!=0 && Rneg==0)
							reputation = (float) (pos/(Fpos*10));
						else
							if (Rpos!=0 && Rneg!=0)
								reputation=(float) ((pos+neg)/((Fpos+Fneg)*10));
							else reputation =0;
					break;
			}
			
		//%	System.out.println("_______________________________ : Reputation :"+reputation);
			services.get(i).setAssessedReputation(reputation);
		}
	
		// Let's compute the reputation of class
		assessClassReputation();
	}
	
 // agregate reputation services for each classe
	private void assessClassReputation() {
		int nbrInclass = numberOfService/5;
		int nbrGoodRates=0;
		float absoluterror=0;
		// class 1
		for (int i=0;i<nbrInclass;i++)
		{
			originReputation[iternum][day][0]+=services.get(i).getDailyReputation();
			assessdReputation[iternum][day][0]+=services.get(i).getAssessedReputation();
			absoluterror += Math.abs(services.get(i).getDailyReputation()-services.get(i).getAssessedReputation());
			if(Math.abs(services.get(i).getAssessedReputation()-services.get(i).getDailyReputation())*10 <1.0)
				nbrGoodRates ++;				
				//System.err.println("Ideal ="+services.get(i).getDailyReputation()+" , Assessed ="+services.get(i).getAssessedReputation()+" abs :"+Math.abs(services.get(i).getAssessedReputation()-services.get(i).getDailyReputation()));
						
		}
		
		originReputation[iternum][day][0]/=nbrInclass;
		assessdReputation[iternum][day][0]/=nbrInclass;
		recall_Meanerror[iternum][day][0][0]= (float) (((nbrGoodRates*1.0)/(nbrInclass*1.0))*100);      // recall
		recall_Meanerror[iternum][day][0][1]= (float) ((absoluterror*1.0)/nbrInclass*1.0);     // store the absolute mean error
		
	//	System.err.println("Classe A : recall ="+recall[iternum][day][1]);
		// class 2
		
		absoluterror =0; nbrGoodRates =0;
		for (int i=nbrInclass;i<nbrInclass*2;i++)
		{
			originReputation[iternum][day][1]+=services.get(i).getDailyReputation();
			assessdReputation[iternum][day][1]+=services.get(i).getAssessedReputation();
			absoluterror += Math.abs(services.get(i).getDailyReputation()-services.get(i).getAssessedReputation());
			if(Math.abs(services.get(i).getAssessedReputation()-services.get(i).getDailyReputation())*10 <1.0)
				nbrGoodRates ++;
		}
		originReputation[iternum][day][1]/=nbrInclass;
		assessdReputation[iternum][day][1]/=nbrInclass;
		recall_Meanerror[iternum][day][1][0]= (float) (((nbrGoodRates*1.0)/(nbrInclass*1.0))*100);
		recall_Meanerror[iternum][day][1][1]= absoluterror/nbrInclass;

	//	System.err.println("Classe B : recall ="+recall[iternum][day][1]);
		
		// class 3
		absoluterror =0; nbrGoodRates =0;
		for (int i=nbrInclass*2;i<nbrInclass*3;i++)
		{
			originReputation[iternum][day][2]+=services.get(i).getDailyReputation();
			assessdReputation[iternum][day][2]+=services.get(i).getAssessedReputation();
			absoluterror += Math.abs(services.get(i).getDailyReputation()-services.get(i).getAssessedReputation());

			if(Math.abs(services.get(i).getAssessedReputation()-services.get(i).getDailyReputation())*10 <1.0)
				nbrGoodRates ++;
		}
		originReputation[iternum][day][2]/=nbrInclass;
		assessdReputation[iternum][day][2]/=nbrInclass;	
		recall_Meanerror[iternum][day][2][1]= absoluterror/nbrInclass;                // absolute mean error
		recall_Meanerror[iternum][day][2][0]= (float) (((nbrGoodRates*1.0)/(nbrInclass*1.0))*100);   // recall
	//	System.err.println("Classe C : recall ="+recall[iternum][day][2]);
		
		// class 4
		absoluterror =0; nbrGoodRates =0;
		for (int i=nbrInclass*3;i<nbrInclass*4;i++)
		{
			originReputation[iternum][day][3]+=services.get(i).getDailyReputation();
			assessdReputation[iternum][day][3]+=services.get(i).getAssessedReputation();
			if(Math.abs(services.get(i).getAssessedReputation()-services.get(i).getDailyReputation())*10 <1.0)
				nbrGoodRates ++;
			absoluterror += Math.abs(services.get(i).getDailyReputation()-services.get(i).getAssessedReputation());

		}
		originReputation[iternum][day][3]/=nbrInclass;
		assessdReputation[iternum][day][3]/=nbrInclass;
		recall_Meanerror[iternum][day][3][1]= absoluterror/nbrInclass;  // absolute mean error
		recall_Meanerror[iternum][day][3][0]= (float) (((nbrGoodRates*1.0)/(nbrInclass*1.0))*100); //recall
	//	System.err.println("Classe D : recall ="+recall[iternum][day][3]);
		
		absoluterror =0; nbrGoodRates =0;
		// class 5
		for (int i=nbrInclass*4;i<numberOfService;i++)
		{
			originReputation[iternum][day][4]+=services.get(i).getDailyReputation();
			assessdReputation[iternum][day][4]+=services.get(i).getAssessedReputation();
			absoluterror += Math.abs(services.get(i).getDailyReputation()-services.get(i).getAssessedReputation());
			if(Math.abs(services.get(i).getAssessedReputation()-services.get(i).getDailyReputation())*10 <1.0)
				nbrGoodRates ++;
		}
		originReputation[iternum][day][4]/=numberOfService -(nbrInclass*4);
		assessdReputation[iternum][day][4]/=numberOfService -(nbrInclass*4);
		recall_Meanerror[iternum][day][4][1]= absoluterror/nbrInclass;
		recall_Meanerror[iternum][day][4][0]= (float) (((nbrGoodRates*1.0)/(nbrInclass*1.0))*100);
//		System.err.println("Classe E : recall ="+recall[iternum][day][4]);
			
	}


	/**
	 * save results in files
	 */
	public void saveData()
	{
		File f = new File("ReputationAveragesByday.txt");
		File f1 = new File("ReputationAveragesC1.txt");
		File f2 = new File("ReputationAveragesC2.txt");
		File f3 = new File("ReputationAveragesC3.txt");
		File f4 = new File("ReputationAveragesC4.txt");
		File f5 = new File("ReputationAveragesC5.txt");
		try {
			PrintWriter output = new PrintWriter(f);
			PrintWriter output1 = new PrintWriter(f1);
			PrintWriter output2 = new PrintWriter(f2);
			PrintWriter output3 = new PrintWriter(f3);
			PrintWriter output4 = new PrintWriter(f4);
			PrintWriter output5 = new PrintWriter(f5);
			
			for (int i=0;i<numberOfDays;i++)
			{
				output.println((i+1)+" C1 "+originReputation[iternum][i][0]+" "+assessdReputation[iternum][i][0]);
				output1.println((i+1)+" C1 "+originReputation[iternum][i][0]+" "+assessdReputation[iternum][i][0]);
				
				output.println((i+1)+" C2 "+originReputation[iternum][i][1]+" "+assessdReputation[iternum][i][1]);
				output2.println((i+1)+" C2 "+originReputation[iternum][i][1]+" "+assessdReputation[iternum][i][1]);
				
				output.println((i+1)+" C3 "+originReputation[iternum][i][2]+" "+assessdReputation[iternum][i][2]);
				output3.println((i+1)+" C3 "+originReputation[iternum][i][2]+" "+assessdReputation[iternum][i][2]);
				
				output.println((i+1)+" C4 "+originReputation[iternum][i][3]+" "+assessdReputation[iternum][i][3]);
				output4.println((i+1)+" C4 "+originReputation[iternum][i][3]+" "+assessdReputation[iternum][i][3]);
				
				output.println((i+1)+" C5 "+originReputation[iternum][i][4]+" "+assessdReputation[iternum][i][4]);
				output5.println((i+1)+" C5 "+originReputation[iternum][i][4]+" "+assessdReputation[iternum][i][4]);
			}
			
			output.close();output1.close();output2.close();output3.close();output4.close();output5.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
	// Save Ranks. 
		
	}
	
	/*
	* All users that ranked the service (selectedService) has to update thier Honesty coefficient
	*/
	/*private void evaluateUsersHonesty(short selectedService) {
		// obtain service rates
		ArrayList<Rate> serviceRates = services.get(selectedService).getAllRates();
		
		for (int i=0; i<serviceRates.size();i++)
		{
			Rate r = serviceRates.get(i);
			assessUserHonesty(r.getUser_ID());			
		}	
		//System.out.println(services.get(selectedService).toString());
	}*/

	/*-********************************************************************************************************-*/
	/**
	 * Assess The honesty factor of user (User_ID)
	 * @param user_ID
	 */
	private void assessUserHonesty(short user_ID) {
		// get user historical ranks
		ArrayList<rankedPairs> userRankPairs = raters.get(user_ID).getMyRates();
		float hFactor=0;
		
		if (userRankPairs.size()<=1)
			raters.get(user_ID).setHonestyCoefficient((float) 0.50); //0.5 by default
		else
		{
		for (int i=0;i<userRankPairs.size();i++)
		{
			rankedPairs rp = userRankPairs.get(i);
			float pos; float neg;
			pos =services.get(rp.serviceID).getNumberOfPostiveRanks();
			neg =services.get(rp.serviceID).getNumberOfNegativeRanks();
			
			if (rp.isPositiveRate())
				{hFactor+= (pos/(pos+neg)); if(hFactor==0) System.err.println("+ i="+i+" hfactor =0"+" pos: "+pos+" neg:"+neg);}
			else
				{hFactor+= (neg/(pos+neg));
			if(hFactor==0) System.err.println("- i="+i+" hfactor =0"+" pos: "+pos+" neg:"+neg);}
		}
		if((hFactor/userRankPairs.size())>=0.5)
			raters.get(user_ID).setHonestyCoefficient(hFactor/userRankPairs.size());
		else
			raters.get(user_ID).setHonestyCoefficient(0);    // punish User who has low credibility
			//  System.out.println(raters.get(user_ID).toString());
		}
		
		//System.out.println(raters.get(user_ID).toString());
	}
	
	
	public float[][][] getOriginReputation() {
		return originReputation;
	}

	public float[][][] getAssessdReputation() {
		return assessdReputation;
	}

	public float[][][][] getRecall() {
		return recall_Meanerror;
	}

	
public short getNumberOfService() {
		return numberOfService;
	}

	public void setNumberOfService(short numberOfService) {
		this.numberOfService = numberOfService;
	}

	public short getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(short numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public short getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(short numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public int getNumberOfRatesPerDay() {
		return numberOfRatesPerDay;
	}

	public void setNumberOfRatesPerDay(int numberOfRatesPerDay) {
		this.numberOfRatesPerDay = numberOfRatesPerDay;
	}

	public short getHonestUserRate() {
		return honestUserRate;
	}

	public void setHonestUserRate(short honestUserRate) {
		this.honestUserRate = honestUserRate;
	}

	public double getLamda() {
		return lamda;
	}

	public void setLamda(double lamda) {
		this.lamda = lamda;
	}
	
	public float getExecutionTime(){
		
		float mean =0;
		
		for (int i=0;i<IterNbr;i++)
			mean+=execTimes[i];
		return (mean/IterNbr);
		
	}
	
	public int getNumberOfIterations() {
		return IterNbr;
	}
	
	public void setNumberOfIterations(int iters) {
		this.IterNbr= iters;
	}
/*-********************************************************************************************************-*/
public static void main(String[] args) throws FileNotFoundException {
		
	Simulator sm = new Simulator();
	sm.start_simulation();
	System.out.println("Fin.");
	}


	
}
