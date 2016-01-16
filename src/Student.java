import java.util.ArrayList;
public class Student extends Participant {

	private String name; // name
	private double GPA; // GPA
	private int ES; // extracurricular score

	// constructors
	public Student() {}

	public Student(String name, double GPA, int ES, int nSchools) {
		super(name, 1, nSchools);
		this.GPA = GPA;
		this.ES = ES;
	}
	// getters and setters
	public double getGPA() { return this.GPA; }
	public int getES() { return this.ES; }
	public void setGPA(double GPA) { this.GPA = GPA; }
	public void setES(int ES) { this.ES = ES; }
	

	// print student info and assigned school in tabular format
	public void print(ArrayList<School> H, boolean rankingsSet) {
		System.out.format("%-44s", this.getName());
		System.out.format("%.2f", this.getGPA());
		System.out.format("%4d", this.getES()); // changed from 4 to 5
		// System.out.println("get school function returning " +
		// this.getSchool());
		// System.out.println("The this.GetSchool is returning " +
		// this.getSchool());

		// System.out.println("RS" + rankingsSet);

		if (!super.isFull()) {

			System.out.print("  -                                       ");
		} else if (super.isFull()) {
			// System.out.println("System.out in else statement's this.School is "
			// + this.getSchool());
			System.out.format("  %-40s", H.get(super.getMatch(0)).getName());
			
			
			//matches size will only be 1 for students
		
		}



	//	else if ((!super.isFull()) ) {

		//	System.out.format("   %-28s", H.get(super.getMatch(0)).getName());
	//	}
		
	
	
			printRankings(H);
		
		//	System.out.println(super.getNParticipants());
	  // else {
			//System.out.println("-");

		//}

	}

	// print the rankings separated by a comma
	//Called by print function
	public void printRankings(ArrayList<School> H) {

		for (int r = 1; r <= super.getNParticipants(); r++) { // Loop through scores
															// array, compare w
															// each student
															// score
			// by looping through each student and calculating their score again

			for (int studentIndex = 0; studentIndex < super.getNParticipants(); studentIndex++) {

				if (r == super.getRanking(studentIndex)) {
					System.out.print(H.get(studentIndex).getName());
					if (r != super.getNParticipants())
						System.out.print(", ");
					else
						System.out.println("");

				}
				

			}
		}
	}
}
