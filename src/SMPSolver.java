import java.util.ArrayList;
public class SMPSolver {
	private ArrayList <Participant> S = new ArrayList <Participant>();
	private ArrayList <Participant> R = new ArrayList <Participant>();
	private double avgSuitorRegret;
	private double avgReceiverRegret;
	private double avgTotalRegret;
	private boolean matchesExist;
	private boolean stable;
	private long compTime;
	private boolean suitorFirst; // This means Student is the suitor if true;
	
	//constructors 
	public SMPSolver(){}
	public SMPSolver( ArrayList<? extends Participant> S , ArrayList<? extends Participant> R, boolean suitorFirst){
		this.matchesExist = false;
		this.suitorFirst = suitorFirst; // If this  is true, students do the proposing.
		if (!this.suitorFirst){
			this.R = (ArrayList<Participant>) S;
			this.S = (ArrayList<Participant>) R; 
			
		}
		else {
		this.S = (ArrayList<Participant>) S;
		this.R = (ArrayList<Participant>) R;
		}
	}


	//getters
	public double getAvgSuitorRegret(){ 
		int totalRegret = 0;
		//find the regret of each suitor and then divide by s.size();
		for(int i =0; i < this.S.size(); i++){
			totalRegret += S.get(i).getRegret();
		}
		this.avgSuitorRegret = (totalRegret / S.size());
		
		return this.avgSuitorRegret;
	}
	public double getAvgReceiverRegret(){
		int totalRegret = 0;
		for(int i =0; i< this.R.size(); i++){
			totalRegret += R.get(0).getRegret();
		}
		avgReceiverRegret = totalRegret/R.size();
		return this.avgReceiverRegret; 
	
	}
	public double getAvgTotalRegret(){ 
		int totalRegret1 = 0;
		for(int i =0; i< this.R.size(); i++){
			totalRegret1 += R.get(0).getRegret();
		}
		int totalRegret2 = 0;
		//find the regret of each suitor and then divide by s.size();
		for(int i =0; i < this.S.size(); i++){
			totalRegret2 += S.get(i).getRegret();
		}
		
		avgTotalRegret =  (totalRegret1 + totalRegret2)/((S.size() + R.size()));
	
		return this.avgTotalRegret;
		
	}
	public boolean matchesExist(){  
		return this.matchesExist; 
	}
	public long getTime(){ return 69;}
	public int getNSuitorOpenings (){
			int nSuitorOpenings =0 ;
			for(int i =0; i < S.size(); i ++ ){
				nSuitorOpenings+=S.get(i).getMaxMatches();
			}
			return nSuitorOpenings;
	}
	public int getNReceiverOpenings(){ 
		int nReceiverOpenings =0;
		for(int i =0; i < R.size(); i ++ ){
			nReceiverOpenings += R.get(i).getMaxMatches();
		}
		return nReceiverOpenings;
	}
	public ArrayList<Participant> getSuitors(){
		return this.S;
	}
	public ArrayList<Participant> getReceivers(){
		return this.R;
	}

	
	//setters
	public void setMatchesExist(boolean b){}
	public void setSuitorFirst(boolean b){	
		
	
	}
	public void setParticipants (ArrayList<? extends Participant> S, ArrayList<? extends Participant> R){}
	public void updateParticipants(ArrayList<? extends Participant> S, ArrayList<? extends Participant> R){
		
		this.S = (ArrayList<Participant>) S;
		this.R = (ArrayList<Participant>) R;
	}
	
	
	
	
	// Reset everything with new suitors and receivers
	public void reset(){
		
	
		this.S.clear();
		this.R.clear();
		this.setMatchesExist(false);
		this.avgReceiverRegret = this.avgSuitorRegret = this.avgTotalRegret = -1;
		this.stable = false; 
		
		
		
	}
	
	//Method for matching
	public void clearMatches(){
	
		for(int i =0; i< this.S.size(); i ++){
			if(S.get(i)!= null){
			S.get(i).clearMatches();
			}
		}
		for(int j =0; j <this.R.size(); j++){
			if(R.get(j)!=null){
			R.get(j).clearMatches();
			}
		}
		
		
	}

	
	public boolean match(){ 
		
		this.clearMatches();
		
		int ProposeToNext = 0;
		this.matchesExist = false;
		boolean ProposalHappened = false;
		
		
		do{
	
		this.matchesExist = true;
		for ( int SI = 0;  SI < S.size(); SI ++){
			
				if( S.get(SI).getNMatches() != S.get(SI).getMaxMatches()){ //If there are matches left to be made for this suitor
				this.matchesExist = false;
				ProposeToNext = S.get( SI ).getNextProposal();// Get their top pick and propose to that person
				
				ProposalHappened = makeProposal (SI, ProposeToNext); //make proposal to most preferred pick	
					
				
				if(ProposalHappened){
						if(!R.get(ProposeToNext).isFull()){
							makeEngagement(SI, ProposeToNext);
							
						}
						else{
							
							int worstPartnerIndex = R.get(ProposeToNext).getWorstMatch();
							int worstPartnerRank = R.get(ProposeToNext).getRanking(worstPartnerIndex);
							
							
							
							
							S.get(worstPartnerIndex).unmatch(ProposeToNext);
							R.get(ProposeToNext).unmatch(worstPartnerIndex);
							// The relation b/w the worst partner and the receiver has been broken.
							makeEngagement(SI, ProposeToNext);
							
						}
						
						
					}
					else{
						
						S.get(SI).updateNextProposal();
					}
					
		        }
		   		
			/*
			if( SI == S.size() -1 ){ //If we're at the end of loop and not everyone is matched, reset the counter and start again.
				if(!isEveryoneMatched())
					SI = 0;
			}*/
	    }
		
	}while(!this.matchesExist);
		
		//this.matchesExist = true;
		this.matchesExist = true;
		return this.matchesExist;
	}
	
	
	
	
	public boolean isEveryoneMatched(){
		for(int i =0; i < S.size(); i++){
			if (S.get(i).getNMatches() != S.get(i).getMaxMatches()){
				return false;
			}
		}
		return true;
	}
	
	 
	public boolean makeProposal(int suitor, int receiver){  // Suitor proposes
		int proposalIndex = S.get(suitor).getNextRankProposal(); // Get the rank of the person we're looking for now
		
          
		if ( ! R.get(receiver).isFull() ) { //Are they free/do they have free spots?		
			return true; //They agreed to proposal
		
		}
		
		else if ( R.get(receiver). isFull() ) { //Check if they prefer the suitor we just passed in over their least liked match
			
		
			int worstPartnerIndex = R.get(receiver).getWorstMatch();
			int worstPartnerRank = R.get(receiver).getRanking(worstPartnerIndex);
			
			if (worstPartnerRank > R.get(receiver).getRanking(suitor) ){ //Means receiver prefers our current suitor to their worst partner
				
				//makeEngagement ( suitor, receiver, worstPartnerIndex);
			
				return true; //Proposal was accepted for suitor
				
		    }
			else{
				//S.get(suitor).updateNextProposal();
				return false;	// Suitor was rejected	
			}
		 
		}
	
		return false;
		
	
	} 
	/*
	private void makeEngagement (int suitor, int receiver, int oldSuitor){
		
			//First set the oldSuitor free and remove it form our current reciever's match
			System.out.format("The receiver being proposed to is: %d || The old suitor to be kicked out is: %d\n", receiver, oldSuitor);
			System.out.format("The matches array of the old suitor: \n");
			for (int i = 0; i <= 1; i++){
				System.out.println("\nSchool: " + i +" has rank: " +  S.get(oldSuitor).getRanking(i)+ "\n");
			}
			for (int i = 0; i < S.get(oldSuitor).getMatchesList().size(); i++){
				System.out.println("Index: " + i + "|| Match Value at Index: " + S.get(oldSuitor).getMatchesList().get(i) + "\n" );
			}
			S.get(oldSuitor).getMatchesList().remove(  this.S.get(oldSuitor).getMatchesList().indexOf(receiver));
			S.get(oldSuitor).updateNextProposal();
			
			R.get(receiver).getMatchesList().set(  this.R.get(receiver).getMatchesList().indexOf(oldSuitor)    , suitor   );
		
			
		      
			
			//Now match receiver to suitor.
			//R.get(receiver).setMatch(suitor);
			S.get(suitor).setMatch(receiver);
		
			
		
		
	}*/
	
	private void makeEngagement (int suitor, int receiver){
		
		//First set the oldSuitor free and remove it form our current reciever's match
	//	System.out.format("The receiver being proposed to is: %d || The old suitor to be kicked out is: %d\n", receiver, oldSuitor);
		//System.out.format("The matches array of the old suitor: \n");
		//for (int i = 0; i <= 1; i++){
		//	System.out.println("\nSchool: " + i +" has rank: " +  S.get(oldSuitor).getRanking(i)+ "\n");
	//	}
		
	//	S.get(oldSuitor).getMatchesList().remove(  this.S.get(oldSuitor).getMatchesList().indexOf(receiver));
		//S.get(oldSuitor).updateNextProposal();
		
	//	R.get(receiver).getMatchesList().set(  this.R.get(receiver).getMatchesList().indexOf(oldSuitor)    , suitor   );
	
		
	      
		
		//Now match receiver to suitor.
		//R.get(receiver).setMatch(suitor);
		S.get(suitor).setMatch(receiver);
		R.get(receiver).setMatch(suitor);
	
		
	
	
}
	
	
	
	
	
	
	
	
	 
	 // Make engagement/ set matches
	public boolean matchingCanProceed(){
		boolean matchingCanProceed = false;

		if( this.S == null || this.S.size() == 0)
			System.out.println("\nERROR: No suitors are loaded!\n");
		else if (this.R == null || this.R.size() == 0)
			System.out.println("\nERROR: No receivers are loaded!\n");
		else if (getNSuitorOpenings() != getNReceiverOpenings())
			System.out.println("\nERROR: The number of suitor and receiver openings must be equal!\n");
		else matchingCanProceed = true;
		
		return matchingCanProceed;
		
	} // Check that matching rules are satisfied, maybe use in do -while ? 
	public void calcRegrets() { //Calculate regrets
			double totalSuitorRegret = 0;
			double totalReceiverRegret = 0;
			for(int i =0; i < this.S.size(); i ++){
				S.get(i).calcRegret();
				totalSuitorRegret += (double) S.get(i).getRegret();
			}//calculate suitor avg regret
			this.avgSuitorRegret = (double) ( totalSuitorRegret / S.size());
		
			

			for(int j=0; j < this.R.size(); j++){	
				R.get(j).calcRegret();
				totalReceiverRegret += (double)R.get(j).getRegret();
			}
			this.avgReceiverRegret = (double) ( totalReceiverRegret / R.size());
		
			
			this.avgTotalRegret =(double)( totalSuitorRegret + totalReceiverRegret )/ (this.R.size() + this.S.size());
		
		
	}// Calculate regrets
	public boolean isStable () {
		if (this.determineStability()){
			return true;
		}
		else
			return false;
	
	// Check if a matching is stable 
	}
	public boolean determineStability(){
		
		//Check if our suitor's non-matches prefer us over their worst rank
		//And if the suitor prefers that non-match over their worst rank
		for(int SI=0; SI< S.size(); SI++){
			//Check if we're NOT matched to a Receiver
			for(int RI = 0; RI< R.size(); RI++){
				if(!S.get(SI).matchExists(RI)){
					//now check if they prefer us over their worst match
					int RIWorstMatchRank = R.get(RI).getRanking(R.get(RI).getWorstMatch());
					int ourRank = R.get(RI).getRanking(SI);
					if(RIWorstMatchRank > ourRank){ // They prefer us over their worst match
						//Check if we prefer them over our worst match
						int rankingForRI = S.get(SI).getRanking(RI);
						int ourWorstMatchRank = S.get(SI).getRanking(S.get(SI).getWorstMatch());
						if(ourWorstMatchRank < rankingForRI){
							return false;
						}
						
						
						
					}
					
					
					
				}
			}
			
			
			
			
		}
		
		
		
		
		return true;
		
		
		
		
			
		
		
		
		
	} // Calculate if a matching is stable
	
	public void print(){ //Print the matching results and statistics
			if(this.suitorFirst){
			
				boolean canProceed =this.matchingCanProceed();
			}
			if(!this.suitorFirst){
			
				boolean canProceed =this.matchingCanProceed();
			}
		
			
			//this.printMatches();
			
			printStats();
			System.out.println("");
		
		
	} //Print the matching results and statistics 
	public void printMatches (){ //Print matches
			System.out.println("Matches:");
			System.out.println("--------");
			if(!this.suitorFirst){
				for(int i =0; i <S.size(); i ++){
					System.out.print(S.get(i).getName() + ": ");
					System.out.println(S.get(i).getMatchNames(this.R));
					
					
					//Now print matches
					
				}
			}
			if(this.suitorFirst){
				for(int i =0; i < R.size(); i ++){
					System.out.print(R.get(i).getName() + ": ");
					System.out.println(R.get(i).getMatchNames(this.S));
				}
			}
			System.out.println("");
		
	} 
	public void printStats (){ // Print matching statistics
		if(this.isStable()){
			System.out.println("Stable matching? Yes");
		}
		else
			System.out.println("Stable matching? Yes");
		calcRegrets();
		System.out.printf("Average suitor regret: %.2f\n", this.avgSuitorRegret);
		System.out.printf("Average receiver regret: %.2f\n" , this.avgReceiverRegret);
		System.out.printf("Average total regret: %.2f\n", this.avgTotalRegret);
		
	} 
	
	public void printStatsRow(String rowHeading){ // print statistics as row 
		String output = "";
		this.calcRegrets();
	//	this.stableSolution();
		System.out.format("%-15s", rowHeading); //Print out the row heading
		if (this.isStable()) 
			output = "Yes"; 
		else output = "No";
		
		if (this.suitorFirst) // If the suitor is first - Student-Opt Solver
			System.out.format("%13s%21.2f%21.2f%21.2f%21d\n", output, this.avgReceiverRegret, this.avgSuitorRegret, this.avgTotalRegret, this.getTime());
		else if (!this.suitorFirst) // If the suitor is second - School-Opt Solver 
			System.out.format("%13s%21.2f%21.2f%21.2f%21d\n", output, this.avgSuitorRegret, this.avgReceiverRegret, this.avgTotalRegret, this.getTime());

	}	

		
	
		
	
	
	// Print stats as row
	
	
}