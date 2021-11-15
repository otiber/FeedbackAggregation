package edu.biskra.simulator;

import java.util.ArrayList;

import edu.biskra.services.Service;
import edu.biskra.users.User;

/***
 * This class (Thread) simulate User service Interaction 
 * 
 * @author okba Tibermacine, Biskra University, Algeria
 *
 */
public class Interaction implements Runnable {

	ArrayList<Service> services;
	ArrayList<User> raters;
	int selectedUser;
	int selectedService;
	 public Interaction(int user, int service, ArrayList<User> Rs, ArrayList<Service> Ss) {
		this.raters=Rs;
		this.services=Ss;
		this.selectedUser=user;
		this.selectedService=service;
	//	System.out.println("?SVS ="+this.services.size());
		
	}
	@Override
	public void run() {
	
		try {
			Thread.currentThread().sleep((long) services.get(selectedService).getResponseTime());
			//System.out.println(Thread.currentThread().getName()+" is Wake up now");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//this.leep();
		byte rank = raters.get(selectedUser).doGenerate_rankValue(services.get(selectedService).getDailyReputation());
		// Add the rank in the service
		services.get(selectedService).add_rate(raters.get(selectedUser).getUserID(), rank);
		// Mark the user 
		raters.get(selectedUser).addRankedPair(services.get(selectedService).getService_ID(), rank);
		//***		System.out.println(raters.get(selectedUser).toString()+"# rank service "+selectedService +" by "+rank+" : "+services.get(selectedService).toString()+ "|user :"+raters.get(selectedUser).getUserID()+ " origin :"+services.get(selectedService).getDailyReputation());
		//***	journal.println("User "+selectedUser+" rank service "+selectedService +" by "+rank);	
		//System.out.println(services.get(selectedService).allRates.size());
	}	

}
