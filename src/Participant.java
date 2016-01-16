import java.util.ArrayList;

public class Participant {
	private String name; //name
	private int[] rankings; // rankings of participants
	private ArrayList<Integer> matches = new ArrayList<Integer>(); // match indices
	private int regret; 	//total regret
	private int maxMatches; // Max # of allowed matches/openings
	private boolean matchingHappened = false;
	private int NextProposal =1;

	
	//constructors 
	public Participant(){}
	public Participant(String name, int maxMatches, int nParticipants){
		
		this.name = name;
		this.maxMatches = maxMatches;
		this.rankings = new int[nParticipants];
		
		
		
	}
	
	// getters
	public String getName(){ return this.name; }
	public int getRanking( int i ){ return this.rankings[i];	}
	public int getMatch (int i) {	return matches.get(i); }
	public int getRegret() {	return this.regret;	}
	public int getMaxMatches (){	return this.maxMatches; }
	public int getNMatches () {	return this.matches.size(); }
	public int getNParticipants () {	return this.rankings.length;  } // return length of rankings array
	public boolean isFull(){ 
			if( matches.size() == maxMatches){
				return true;
			}
		return false; 
	}
	public int getNextProposal(){ //Returning index of the next person to propose to 
		int prop = 0;
		for(  prop = 0; prop < this.getNParticipants(); prop ++){

			if ( this.getRanking(prop) == this.NextProposal){
			//	System.out.print("Prop: " +  this.getRanking(prop) + " proposal:" + NextProposal);
				return prop;
			}


		}
	    
		return -1;
	}
	
	public ArrayList<Integer> getMatchesList (){
		return this.matches;
	}
	
	
	public int getNextRankProposal(){ return this.NextProposal; }
	
	//setters 
	public void setName (String name){ this.name = name;}
	public void setRanking (int i , int r){ 
		this.rankings[i] = r;
	}
	public void setMatch (int m ) { matches.add(m);}
	public void setRegret (int r ){}
	public void setNParticipants (int n) { this.rankings = new int[n];} // set rankings array size
	public void setMaxMatches (int n) { this.maxMatches = n;}
	public void updateNextProposal( ){ this.NextProposal++; }
	
	//methods to handle matches
	public void clearMatches(){ this.matches.clear(); } //Clear all matches
	public int findRankingByID( int k ){ return 1;} // Find rank of participant k     
	public int getWorstMatch(){  //Return the index of the suitor that has highest value rank AKA the worst
		
		 	int highest = -1;
		   
		 	int highestIndex = 0;

		    for (int s = 0; s < matches.size(); s++){
		        int curValue = this.getRanking(  matches.get(s)  ); // returns index of a matched school
		        if (curValue > highest) {
		            highest = curValue;
		            highestIndex = matches.get(s);
		           
		        }
		        
		    }
		  
		    return highestIndex;
		
	} 
	/*
	public int getWorstMatch(){
		
	int worstMatch = -1, worstMatchIndex = -1;
	
	for (int mc = 0; mc < this.matches.size(); mc++){ // iterate through the matches array and get index of match
		int matchRank = this.rankings[this.matches.get(mc)]; // get the rank of that match
		//	System.out.println("Match rank: " + matchRank);
		if (matchRank > worstMatch){ // if the match's rank is worse than the current worst match rank
			worstMatch = matchRank; // this is the new worst match rank
			worstMatchIndex = mc; // keep track of the participant index with the worst rank
			
		}
		
			
	}
//	System.out.println("Worst Match in the matches array is at index: " + worstMatchIndex + " || Which means worst match in participant array is: " + this.getMatch(worstMatchIndex));
	
	
	return this.getMatch(worstMatchIndex); //return the index  in the participant array of the lowest ranked match
	}
	*/
	public void unmatch(int k ){
		//Remove the match that has the value k where k is the index of the match in their respective arrayList.
		
		this.matches.remove(this.matches.indexOf(k));
		
	} // Remove the match with participant k 
	public boolean matchExists(int k) { 
		if(this.matches.contains(k)){
			return true;
		}
		
		return false; 
		
	} // Check if match to participant k exists
	public int getSingleMatchedRegret (int k ){ //pass in the index of our match in here
		int ranking = this.getRanking(k);
		return (ranking -1);
	
	
	} // get regret from match with k 
	public void calcRegret (){
		//Loop through matches and subtract 1 to calc total regret for the student
		int regret = 0;
		
		
		for(int i = 0; i < matches.size(); i ++){
			this.regret += getSingleMatchedRegret(this.matches.get(i)) ;
		}
		
		
	} // Calculate total regret over all matches
	
	//methods to edit data from the user
	public void editInfo (ArrayList <? extends Participant> P ){}
	public void editRankings(ArrayList <? extends Participant> P){}
	
	// print methods
	
	public String getMatchNames (ArrayList<? extends Participant> P){ 
		String matchesName = "";
		for(int i =0; i < this.getMaxMatches(); i ++){
			matchesName += P.get( this.matches.get(i) ).getName();
			if(i!= (this.getMaxMatches()-1)){
				matchesName+=", ";
			}
		}
		return matchesName;
	}
	
	
	
}
