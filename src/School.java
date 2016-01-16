import java.util.ArrayList;
public class School extends Participant  {

	
	private double alpha; // GPA weight
//	private int[] rankings; // rankings of students
//	private int student; // index of matched student
	//private int regret; // regret
//	private boolean matchingHappened = false;

	// constructors
	public School() { this.alpha = 0;  }

	public School(String name, double alpha, int nStudents, int maxMatches) {
		super (name, maxMatches, nStudents);
		this.alpha = alpha;		
	}

	// getters and setters
	public double getAlpha() { return this.alpha; }
	public void setAlpha(double alpha) { this.alpha = alpha; }

	// get new info from the user
	public void editSchoolInfo(ArrayList<Student> S, boolean canEditRankings) {
		//if we can't edit rankings, return an error or some shit
	}


	// print school info and assigned student in tabular format
  
	public void print(ArrayList<Student> S) {

			System.out.format("%-47s", this.getName()); 

		
			System.out.format("%d", this.getMaxMatches());
			System.out.format("    %.2f  ", this.getAlpha()); //Print spots instead
			if( this.isFull()){
				System.out.format("%-40s", this.printMatches( S ));
			}
			

			else if ( !this.isFull()) {

			System.out.print("-                                       ");

			}
/*
		else if ((!super.isFull()) && S.size()!= 0) {

			System.out.print("  -                          ");

			printRankings(S);

		}
		else if (!super.isFull()) {

			System.out.print("-");

		}
		/*
		else if (super.isFull()) {

			System.out.format("  %-40s", S.get(super.getMatch(0)).getName());
		} */
		if(S.size()!= 0){
			printRankings(S);
		}
		else
			System.out.print("-");

		

	}
	public String printMatches( ArrayList<Student> S){
		String matchesString = "";
		for (int i = 0; i< super.getMaxMatches(); i++){
			matchesString+= S.get( super.getMatch(i) ).getName();
			if ( i!= super.getMaxMatches() -1){
				matchesString+= ", ";
			}
		}
		return matchesString;
	}

	// print the rankings separated by a comma
	public void printRankings(ArrayList<Student> S) {
		// Loop through scores array, compare w each student score by looping
		// through each student and calculating their score again
		for (int r = 1; r <= S.size(); r++) {
		
			for (int studentIndex = 0; studentIndex < S.size(); studentIndex++) {
				
				if (r == super.getRanking(studentIndex)) {
					
					
					
					
					System.out.print(S.get(studentIndex).getName());

					if (r == S.size()) {
					

						break;

					}
				
					else {

						System.out.print(", ");

					}

				}

			}

		}

	}
	
}