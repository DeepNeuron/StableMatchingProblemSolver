import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.io.*;
import java.util.ArrayList;

public class Pro5_prasadd5 {
	public static BufferedReader input = new BufferedReader(
			new InputStreamReader(System.in));

	public static int numStudentsAddedToAdd = 0;
	public static int numStudentsAdded = 0;
	// public static int loadedSchoolsNumToAdd = 0;
	public static int loadedSchoolsNum = 0;

	public static int schoolsEntered = 0;
	public static int studentsEntered = 0;
	public static int totalNumMatches = 0;
	public static boolean didMatchingHappen = false;

	public static double totalSchoolRegret = 0;
	public static double averageSchoolRegret = 0;
	public static double averageRegretPerPerson = 0;
	public static double averageStudentRegret = 0;

	public static boolean checkUserInput(String s) {
		if (s.equalsIgnoreCase("l")) {
			return true;
		}
		if (s.equalsIgnoreCase("e")) {
			return true;
		}
		if (s.equalsIgnoreCase("p")) {
			return true;
		}
		if (s.equalsIgnoreCase("m")) {
			return true;
		}
		if (s.equalsIgnoreCase("d")) {
			return true;
		}
		if (s.equalsIgnoreCase("r")) {
			return true;
		}
		if (s.equalsIgnoreCase("q")) {
			return true;
		}
		if (s.equalsIgnoreCase("X")) {
			return true;
		} else
			return false;
	}

	public static String getUserInput(String prompt) {
		String userinput = "";
		boolean valid;
		do {
			valid = true;
			System.out.print(prompt);
			try {

				userinput = input.readLine();
				if (checkUserInput(userinput)) {

					return userinput;

				} else

					System.out.println("");
				System.out.println("ERROR: Invalid menu choice!");
				System.out.println("");
				displayMenu(); // Display the menu again
				valid = false;
			} catch (NumberFormatException e) {

				System.out.println("");
				System.out.println("ERROR: Invalid menu choice!");
				System.out.println("");
				displayMenu();
				valid = false;

			} catch (IOException e) {

				System.out.println("");
				System.out.println("ERROR: Invalid menu choice!");
				System.out.println("");
				displayMenu();
				valid = false;

			}
		} while (!valid);

		return userinput;
	}

	public static void displayMenu() {

		System.out.println("JAVA STABLE MARRIAGE PROBLEM v3");
		System.out.println("");
		System.out.println("L - Load students and schools from file");
		System.out.println("E - Edit students and schools");
		System.out.println("P - Print students and schools");
		System.out
				.println("M - Match students and schools using Gale-Shapley algorithm");
		System.out.println("D - Display matches");
		System.out
				.println("X - Compare student-optimal and school-optimal matches");
		System.out.println("R - Reset database");
		System.out.println("Q - Quit");
		System.out.println("");

	} // Display the menu

	public static boolean checkEditInput(String s) {
		if (s.equalsIgnoreCase("H")) {
			return true;
		}
		if (s.equalsIgnoreCase("S")) {
			return true;
		}
		if (s.equalsIgnoreCase("Q")) {
			return true;
		} else
			return false;
	}

	public static String getEditInput(String prompt) {
		String userinput = "";
		boolean valid;

		do {
			valid = true;
			System.out.print(prompt);
			try {

				userinput = input.readLine();
				if (checkEditInput(userinput)) {

					return userinput;

				} else {
					valid = false;
					System.out.println("");

					System.out.println("ERROR: Invalid menu choice!");

					displayEditMenu(); // Display the menu again

				}
			} catch (NumberFormatException e) {

				System.out.println("");
				System.out.println("ERROR: Invalid menu choice!");
				System.out.println("");

				displayEditMenu();
				valid = false;

			} catch (IOException e) {

				System.out.println("");
				System.out.println("ERROR: Invalid menu choice!");
				// System.out.println("");
				displayEditMenu();
				valid = false;
			}
		} while (!valid);

		return userinput;
	}

	public static boolean isRankingLoaded(int[] rankingsArrayThusFar) {
		for (int i = 0; i < loadedSchoolsNum; i++) {
			for (int j = i + 1; j < loadedSchoolsNum; j++) {
				if (j != i
						&& rankingsArrayThusFar[i] == rankingsArrayThusFar[j]) {
					return true;
				}
			}

		}

		return false;
	}

	public static int loadStudents(ArrayList<Student> S, ArrayList<School> H) {
		int[] lineRankings = new int[loadedSchoolsNum];

		boolean isValid = false;
		String values[] = new String[4002];
		String studentFile = "";
		String line = "";
		double GPA = 0.00;
		int ES = 0;
		int successfulStudent = 0;
		studentsEntered = 0;

		do {
			isValid = true;
			try {
				System.out.print("Enter student file name (0 to cancel): ");
				studentFile = input.readLine();
				if (studentFile.equalsIgnoreCase("0")) {
					System.out.println("");
					System.out.println("File loading process canceled.");

					return 0;
				}
				BufferedReader fin = new BufferedReader(new FileReader(
						studentFile));
				while ((line = fin.readLine()) != null) {

					values = line.split(",");
					// grab the first 3 pieces of data of every line
					// value array for one line should only be as long as 3 data
					// points + num of schools we're looking for

					if (values.length == loadedSchoolsNum + 3) {

						if (Double.parseDouble(values[1]) <= 4.00
								&& Double.parseDouble(values[1]) >= 0.00) {
							// If it reaches here, there is a valid GPA entered
							// for the current line, now check ES

							if (Integer.parseInt(values[2]) >= 0
									&& Integer.parseInt(values[2]) <= 5) {
								// Now we're expecting a number of schools to be
								// added. if violation is broken, we won't put
								// anything in.
								// Todo this, we'll loop from the index of the
								// 1st school to what should be the last. If
								// there are more, we

								for (int rankingParse = 3; rankingParse < loadedSchoolsNum + 3; rankingParse++) {
									// Check if the ranking is valid
									if (Integer.parseInt(values[rankingParse]) >= 1
											&& Integer
													.parseInt(values[rankingParse]) <= loadedSchoolsNum) {

										lineRankings[rankingParse - 3] = Integer
												.parseInt(values[rankingParse]);
										// Pass this array of rankings into our
										// checker to see if it's been set

									}

								}
								// after checking for correct gpa, and
								// populating an array with rankings, we check
								// to see if there are any repeats in the
								// rankings
								if (!isRankingLoaded(lineRankings)) {
									// If there aren't any repeats, we can parse
									// thru linerankings array and put it into
									// our student object
									// S[studentsEntered] = new Student(
									// values[0], Double.parseDouble(values[1]),
									// Integer.parseInt(values[2]),
									// loadedSchoolsNum);

									S.add(new Student(values[0], Double
											.parseDouble(values[1]), Integer
											.parseInt(values[2]),
											loadedSchoolsNum));
									for (int r = 0; r < loadedSchoolsNum; r++) {

										// S[studentsEntered].setRanking(
										// lineRankings[r]-1, r +1) ;

										S.get(studentsEntered).setRanking(
												lineRankings[r] - 1, r + 1);

										// S[studentsEntered].setPreferenceList(r,
										// lineRankings[r] -1);

										// S.get(studentsEntered).setPreferenceList(r,
										// lineRankings[r]-1);

										// S[0].schoolsLoaded =
										// loadedSchoolsNum;
										// The first index of preference list
										// will tell two things
										// 1.First index means it's the first
										// school we want to propose to
										// The value at the first index is the
										// exact index to propose to

									}
									numStudentsAdded++;
									successfulStudent++;
								}

							}

						}

					} // end of first if statement

					studentsEntered++;
				} // End of while loop
				fin.close();

			}

			catch (FileNotFoundException e) {
				System.out.println("");
				System.out.println("ERROR: File not found!");
				System.out.println("");
				isValid = false;

			} catch (IOException s) {
				System.out.println("");
				System.out.println("ERROR: File not found!");
				System.out.println("");
				isValid = false;

			}

		} while (!isValid);
		System.out.println("");
		System.out.println(successfulStudent + " of " + studentsEntered
				+ " students loaded!");
		return successfulStudent;

	}

	public static int loadSchools(ArrayList<School> H) {
		// do loop to keep trying to read in some file. Keep prompting till a
		// file is found that matches the name entered.

		boolean isValid = false;

		String values[] = new String[4002];

		String line = "";
		String strLine = "";
		double alpha = 0.00;
		int i = 0;
		int successfulSchools = 0;
		schoolsEntered = 0; // as soon as loadSchools is called
		// schoolsEntered += loadedSchoolsNum;

		String school_file = "";

		do {
			isValid = true;

			try {
				System.out.print("Enter school file name (0 to cancel): ");
				school_file = input.readLine();
				if (school_file.equalsIgnoreCase("0")) {
					System.out.println("");
					System.out.println("File loading process canceled.");
					System.out.println("");
					return 0;
				}

				BufferedReader fin = new BufferedReader(new FileReader(
						school_file));
				// After file has been read, we'll start parsing through it and
				// populating our School Object with the relevant data.
				// get number of schools with a while loop
				while ((line = fin.readLine()) != null) {

					values = line.split(",");
					if (values.length == 3) {

						if ((Double.parseDouble(values[1]) <= 1.00)
								&& (Double.parseDouble(values[1]) >= 0.00)) {
							// H[schoolsEntered] = new School( values[0],
							// Double.parseDouble(values[1]), numStudentsAdded);
							// System.out.println(H[schoolsEntered].getName());
							if (Integer.parseInt(values[2]) >= 1) {
								// took out loadedSchoolsNum
								H.add(new School(values[0], Double
										.parseDouble(values[1]),
										numStudentsAdded, Integer
												.parseInt(values[2])));
								loadedSchoolsNum++;
								successfulSchools++;
							}
						}

					}

					schoolsEntered++;
				}

				fin.close();

			} catch (FileNotFoundException e) {
				System.out.println("");
				System.out.println("ERROR: File not found!");
				System.out.println("");
				isValid = false;
			} catch (IOException e) {

			}

		} while (!isValid);

		System.out.println("");
		System.out.println(successfulSchools + " of " + schoolsEntered
				+ " schools loaded!");
		System.out.println("");

		return successfulSchools;

	}

	public static double calcStudentScore(double alpha, double GPA, int ES) {
		double score = ((alpha * (GPA)) + ((1 - alpha) * ES));

		return score;
	}

	public static boolean assignRankings(ArrayList<Student> S,
			ArrayList<School> H, int numStudentsAdded, int nSchools) {
		// Get each student�s school rankings and calculate each school�s
		// rankings of students, and return whether
		// or not ranking happened
		// Even if we got the rank before, we get the rank again.

		// Use nSchools to assign ranking size for Student class in the
		// following for loop
		boolean isAlreadyEntered = true;
		boolean valid = true;
		if (!didMatchingHappen) {
			totalSchoolRegret = 0;
			averageSchoolRegret = 0;
			averageRegretPerPerson = 0;
			averageStudentRegret = 0;

			// Let's clear regret for each student
			for (int i = 0; i < numStudentsAdded; i++) {
				S.get(i).setRegret(0);
			}
		}

		/*
		 * if (numStudentsAdded <= 0) { System.out.println("");
		 * System.out.println("ERROR: No students are loaded!");
		 * System.out.println("");
		 * 
		 * return false; } if (nSchools <= 0) { System.out.println("");
		 * System.out.println("ERROR: No schools are loaded!");
		 * System.out.println("");
		 * 
		 * return false; }
		 */
		/*
		 * for (int i = 0; i < numStudentsAdded; i++) { System.out.println("");
		 * System.out.println("Student " + S[i].getName() + "'s rankings:");
		 * S[i].setNSchools(nSchools);
		 * 
		 * for (int j = 0; j < nSchools; j++) {
		 * 
		 * do { valid = true; int rating = getInteger( ("School " +
		 * H[j].getName() + ": "), 1, nSchools); isAlreadyEntered =
		 * isUniqueRank(S, rating, numStudentsAdded, nSchools, i);
		 * 
		 * if (!isAlreadyEntered) { valid = false; }
		 * 
		 * else { S[i].setRanking(j, rating); valid = true;
		 * 
		 * } if (didMatchingHappen) {
		 * 
		 * // System.out.println("Pulling out " + i + //
		 * " and the rankung returned is " + j);
		 * 
		 * int currentSchoolRanking = S[i].getRanking(j); //
		 * System.out.println("The currentSchool rating is " + //
		 * currentSchoolRanking);
		 * 
		 * int studentRegret = currentSchoolRanking - 1; //
		 * System.out.println("The student " + S[i].getName() + //
		 * " 's regret is "+ S[i].getRegret()); S[i].setRegret(studentRegret);
		 * 
		 * }
		 * 
		 * } while (!valid);
		 * 
		 * }
		 * 
		 * }
		 */

		// Schools rank students now.
		for (int schoolsIndex = 0; schoolsIndex < loadedSchoolsNum; schoolsIndex++) {

			double[] scoreArray = new double[numStudentsAdded];

			for (int i = 0; i < numStudentsAdded; i++) {
				// Populate unsorted score of arrays (arr1 in our diagram)
				// H[schoolsIndex not 0]
				scoreArray[i] = calcStudentScore(
						H.get(schoolsIndex).getAlpha(), S.get(i).getGPA(), S
								.get(i).getES());
			}
			Arrays.sort(scoreArray); // now that the array is sorted, let's loop
										// through the students again
			// And compare with the sorted array
			// loop thru each student again

			for (int j = 0; j < numStudentsAdded; j++) { // Loop through scores
															// array, compare w
															// each student
															// score
				// by looping through each student and calculating their score
				// again
				for (int studentIndex = 0; studentIndex < numStudentsAdded; studentIndex++) { // Loop
																								// thru
																								// sorted
																								// array
					// Find first element's value/score stored, search and find
					// out which student it is.
					// Store the student's index in student objects and assign
					// it a rank.
					if (scoreArray[j] == calcStudentScore(H.get(schoolsIndex)
							.getAlpha(), S.get(studentIndex).getGPA(),
							S.get(studentIndex).getES())) {

						// System.out.println("The school index is" +
						// schoolsIndex +
						// " and student index printed right now is " +
						// studentIndex + " and the rank is: " +
						// (numStudentsAdded-j));

						H.get(schoolsIndex).setRanking((studentIndex),
								(numStudentsAdded - j));

						// THIS WORKS!!!
						// System.out.println( H[schoolsIndex].getName() +
						// "'s rank for student " + studentIndex + " is " +
						// (numStudentsAdded-j));

						// int studentRegret =
						// S[studentIndex].getRanking(schoolsIndex);
						// updateStudentRegret(S,studentIndex, studentRegret);

						// int schoolRegret =
						// H[schoolsIndex].getRanking(studentIndex);
						// updateSchoolRegret(H, schoolsIndex, schoolRegret);

					}
					if (didMatchingHappen) {
						// updateSchoolRegret (H, schoolsIndex, schoolRegret);

						int currentStudentRanking = H.get(schoolsIndex)
								.getRanking(studentIndex);
						int schoolRegret = currentStudentRanking - 1;

						H.get(schoolsIndex).setRegret(schoolRegret);
						// System.out.println("School1 " +
						// H[schoolsIndex].getName() + " 's regret is" +
						// schoolRegret);

					}

				}
			}

		}

		System.out.println("");
		return true;

	}

	public static void displayEditMenu() {

		System.out.println("");
		System.out.println("Edit data");
		System.out.println("---------");
		System.out.println("S - Edit students");
		System.out.println("H - Edit high schools");
		System.out.println("Q - Quit");
		System.out.println("");

	}

	public static void editData(ArrayList<Student> S, ArrayList<School> H,
			int numStudentsAdded, int nSchools, boolean rankingsSet) {
		String optionChosen = "";
		boolean valid = true;

		do {

			displayEditMenu();
			optionChosen = getEditInput("Enter choice: ");
			valid = true;

			if (checkEditInput(optionChosen)) {

				if (optionChosen.equalsIgnoreCase("Q")) {
					System.out.println("");
					// System.out.println("");
					// System.out.println("");
					return;
				}

				if (optionChosen.equalsIgnoreCase("S")) {

					if (S.size() == 0) {
						System.out.println("");
						System.out.println("ERROR: No students are loaded!");

						// displayEditMenu();

						// optionChosen = getEditInput("Enter choice: ");
						valid = false;
					}

					else {

						editStudents(S, H, numStudentsAdded, nSchools,
								rankingsSet);
						// System.out.println("Hello 5");

						// displayEditMenu();
						// valid = true;
						// System.out.println("");
						// optionChosen = getEditInput("Enter choice: ");
						if (checkEditInput(optionChosen)) {
							valid = true;
						}
						valid = true;
						if (optionChosen.equalsIgnoreCase("Q")) {
							System.out.println("");
							System.out.println("");
							// System.out.println("");
							return;
						}

					}

				}
				if (optionChosen.equalsIgnoreCase("H")) {

					// System.out.println("N schools is " + nSchools);
					if (H.size() == 0) {

						System.out.println("");
						System.out.println("ERROR: No schools are loaded!");
						// displayEditMenu();
						valid = false;
					}

					else {

						editSchools(S, H, nSchools, rankingsSet);
						// System.out.println("Hello 5");
						displayEditMenu();
						// valid = true;
						// System.out.println("");
						optionChosen = getEditInput("Enter choice: ");
						if (checkEditInput(optionChosen)) {
							valid = true;
						}

						if (optionChosen.equalsIgnoreCase("Q")) {
							System.out.println("");
							System.out.println("");
							// System.out.println("");
							return;
						}

					}

					// valid = true;
				}

			}
			valid = false;

		} while (!valid);
	}

	// Sub-area menu to edit students and schools

	public static void editStudents(ArrayList<Student> S, ArrayList<School> H,
			int numStudentsAdded, int nSchools, boolean rankingsSet) {
		String name = "";
		String editRankingsOption = "";
		boolean valid = false;

		do {
			System.out.println("");

			System.out
					.println(" #   Name                                         GPA  ES  Assigned school                         Preferred school order");

			System.out
					.println("---------------------------------------------------------------------------------------------------------------------------");

			for (int i = 0; i < numStudentsAdded; i++) {
				System.out.print("  " + (i + 1) + ". ");
				S.get(i).print(H, rankingsSet);
				// [i].printRankings(H);
			}
			System.out
					.println("---------------------------------------------------------------------------------------------------------------------------");

			int studentIndToEdit = getInteger("Enter student (0 to quit): ", 0,
					numStudentsAdded);
			if (studentIndToEdit == 0) {

				return;
			}

			System.out.println("");
			System.out.print("Name: ");
			try {
				name = input.readLine();
			} catch (NumberFormatException e) {

				System.exit(0);
			} catch (IOException e) {
				System.exit(0);
			}

			S.get(studentIndToEdit - 1).setName(name);
			S.get(studentIndToEdit - 1).setGPA(getDouble("GPA: ", 0.00, 4.00));
			S.get(studentIndToEdit - 1).setES(
					getInteger("Extracurricular score: ", 0, 5));
			getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE);

			do {
				System.out.print("Edit rankings (y/n): ");
				valid = true;
				try {
					editRankingsOption = input.readLine();
					if (!editRankingsOption.equalsIgnoreCase("y")
							|| !editRankingsOption.equalsIgnoreCase("n")) {
						System.out.println("ERROR: Choice must be 'y' or 'n'!");
						valid = false;
					}
				} catch (IOException e) {
					System.out.println("ERROR: Choice must be 'y' or 'n'!");
					valid = false;
				} catch (NumberFormatException e) {
					System.out.println("ERROR: Choice must be 'y' or 'n'!");
					valid = false;
				}
				if (editRankingsOption.equalsIgnoreCase("y")) {

					System.out.println("");
					System.out.println("Student "
							+ S.get(studentIndToEdit - 1).getName()
							+ "'s rankings:");
					// S.get(studentIndToEdit - 1).setNSchools(nSchools);

					for (int j = 0; j < nSchools; j++) {
						int rating = getInteger(
								("School " + H.get(j).getName() + ": "), 1,
								nSchools);

						S.get(studentIndToEdit - 1).setRanking(j, rating);

						int currentSchoolRanking = S.get(studentIndToEdit - 1)
								.getRanking(j);
						int studentRegret = currentSchoolRanking - 1;

						// System.out.println("The student " +
						// S[studentIndToEdit].getName() + " 's regret is "+
						// S[studentIndToEdit].getRegret());
						/*
						 * if (didMatchingHappen) {
						 * 
						 * S.get(studentIndToEdit - 1)
						 * .setRegret(studentRegret);
						 * 
						 * }
						 */

						// updateStudentRegret(S, )
						// int matchedSchoolIndex =
						// S[studentIndToEdit-1].getSchool();
						// int regret =
						// S[studentIndToEdit-1].getRanking(matchedSchoolIndex)-1;
						// System.out.println("The student's regret for this choice is : "
						// + regret);

						// S[studentIndToEdit-1].setRegret(r);

					}

				}
				if (editRankingsOption.equalsIgnoreCase("N")) {
					valid = true;
				}

			} while (!valid);

		} while (true);

	}

	// Sub-area to edit students. The edited student�s regret is updated if
	// needed. Any existing school
	// rankings and regrets are re-calculated after editing a student.

	public static void editSchools(ArrayList<Student> S, ArrayList<School> H,
			int nSchools, boolean rankingsSet) {
		String name = "";
		String editRankingsOption = "";
		boolean valid = false;

		if (nSchools <= 0) {

			System.out.println("ERROR: No schools are loaded!");
			System.out.println("");
			return;
		}

		do {
			System.out.println("");
			System.out
					.println(" #   Name                                     # spots  Weight  Assigned students                       Preferred student order");
			System.out
					.println("------------------------------------------------------------------------------------------------------------------------------");

			for (int i = 0; i < nSchools; i++) {
				System.out.print(" " + (i + 1) + ". ");
				// H.get(i).print(S, rankingsSet);
				System.out.println("");
				// [i].printRankings(H);
			}
			System.out
					.println("------------------------------------------------------------------------------------------------------------------------------");

			int schoolIndToEdit = getInteger("Enter school (0 to quit): ", 0,
					nSchools);
			if (schoolIndToEdit == 0) {
				return;
			}
			System.out.println("");

			System.out.print("Name: ");
			try {
				name = input.readLine();
			} catch (NumberFormatException e) {

				System.exit(0);
			} catch (IOException e) {
				System.exit(0);
			}
			// changing this array right hur
			H.get(schoolIndToEdit - 1).setName(name); // So far works
			H.get(schoolIndToEdit - 1).setAlpha(
					getDouble("GPA weight: ", 0.00, 1.00));
			H.get(schoolIndToEdit - 1).setMaxMatches(
					getInteger("Maximum number of matches: ", 1,
							Integer.MAX_VALUE));

			double[] scoreArray = new double[numStudentsAdded];

			for (int i = 0; i < numStudentsAdded; i++) {
				// Populate unsorted score of arrays (arr1 in our diagram)
				scoreArray[i] = calcStudentScore(H.get(schoolIndToEdit - 1)
						.getAlpha(), S.get(i).getGPA(), S.get(i).getES());
			}
			Arrays.sort(scoreArray); // now that the array is sorted, let's loop
										// through the students again
			// And compare with the sorted array
			// loop thru each student again

			for (int j = 0; j < numStudentsAdded; j++) { // Loop through scores
															// array, compare w
															// each student
															// score
				// by looping through each student and calculating their score
				// again
				for (int studentIndex = 0; studentIndex < numStudentsAdded; studentIndex++) { // Loop
																								// thru
																								// sorted
																								// array
					// Find first element's value/score stored, search and find
					// out which student it is.
					// Store the student's index in student objects and assign
					// it a rank.
					if (scoreArray[j] == calcStudentScore(
							H.get(schoolIndToEdit - 1).getAlpha(),
							S.get(studentIndex).getGPA(), S.get(studentIndex)
									.getES())) {

						// System.out.println("The school index is" +
						// schoolsIndex +
						// " and student index printed right now is " +
						// studentIndex + " and the rank is: " +
						// (numStudentsAdded-j));

						H.get(schoolIndToEdit - 1).setRanking((studentIndex),
								(numStudentsAdded - j));
						int currentStudentRanking = H.get(schoolIndToEdit - 1)
								.getRanking(studentIndex);
						int schoolRegret = currentStudentRanking - 1;

						if (didMatchingHappen) {
							H.get(schoolIndToEdit - 1).setRegret(schoolRegret);
							// updateSchoolRegret (H, schoolIndToEdit-1,
							// schoolRegret);
						}
						// System.out.println("School1 " +
						// H[schoolIndToEdit-1].getName() + " 's regret is" +
						// schoolRegret);

						// THIS WORKS!!!
						// System.out.println( H[schoolsIndex].getName() +
						// "'s rank for student " + studentIndex + " is " +
						// (numStudentsAdded-j));

						// int studentRegret =
						// S[studentIndex].getRanking(schoolsIndex);
						// updateStudentRegret(S,studentIndex, studentRegret);

						// int schoolRegret =
						// H[schoolsIndex].getRanking(studentIndex);
						// updateSchoolRegret(H, schoolsIndex, schoolRegret);

					}

				}
			}

			/*
			 * if(rankingsSet){
			 * 
			 * do{ System.out.print("Edit rankings (y/n): "); valid = true; try{
			 * editRankingsOption = input.readLine(); } catch(IOException e){
			 * System.out.println("ERROR: Choice must be 'y' or 'n'!"); valid =
			 * false; } catch(NumberFormatException e){
			 * System.out.println("ERROR: Choice must be 'y' or 'n'!"); valid =
			 * false; } if (editRankingsOption.equalsIgnoreCase("y")){
			 * 
			 * System.out.println(""); System.out.println("Student " +
			 * H[schoolIndToEdit-1].getName() + "'s rankings:"); //
			 * H[schoolIndToEdit-1].setNSchools(nSchools);
			 * 
			 * for(int j = 0; j < nSchools ; j ++ ){ int rating =
			 * getInteger(("School " + H[j].getName() + ": "),1, nSchools);
			 * 
			 * H[schoolIndToEdit-1].setRanking(j, rating); }
			 * 
			 * 
			 * } if(editRankingsOption.equalsIgnoreCase("N")){ valid = true; }
			 * 
			 * }while(!valid);
			 * 
			 * 
			 * 
			 * 
			 * }
			 */

		} while (true);

	}

	// Sub-area to edit schools. Any existing rankings and regret for the edited
	// school are updated.

	public static void printStudents(ArrayList<Student> S, ArrayList<School> H,
			int numStudentsAdded, boolean rankingsSet) {

		String name = "";
		String editRankingsOption = "";
		boolean valid = false;

		if (numStudentsAdded <= 0) {
			System.out.println("");
			System.out.println("ERROR: No students are loaded!");
			System.out.println("");
			return;

		}

		System.out.println("");
		System.out.println("STUDENTS:");
		System.out.println("");

		System.out
				.println(" #   Name                                         GPA  ES  Assigned school                         Preferred school order");
		System.out
				.println("---------------------------------------------------------------------------------------------------------------------------");

		for (int i = 0; i < numStudentsAdded; i++) {
			System.out.print("  " + (i + 1) + ". ");
			// rankingsSet = true; //Now rankings will always be set by the text
			// file
			S.get(i).print(H, rankingsSet);

			// [i].printRankings(H);
		}

		System.out
				.println("---------------------------------------------------------------------------------------------------------------------------");
		System.out.println("");

	}

	// Print students to the screen, including matched school (if one exists).

	public static void printSchools(ArrayList<Student> S, ArrayList<School> H,
			int nSchools, boolean rankingsSet) {

		String name = "";
		String editRankingsOption = "";
		boolean valid = false;

		if (loadedSchoolsNum <= 0) {

			System.out.println("ERROR: No schools are loaded!");
			System.out.println("");
			return;

		}

		System.out.println("SCHOOLS:");

		System.out.println("");
		System.out
				.println(" #   Name                                     # spots  Weight  Assigned students                       Preferred student order");
		System.out
				.println("------------------------------------------------------------------------------------------------------------------------------");
		for (int i = 0; i < loadedSchoolsNum; i++) {

			System.out.print("  " + (i + 1) + ". ");
			H.get(i).print(S);
			System.out.println("");
			// [i].printRankings(H);
		}
		System.out
				.println("------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("");

	}

	// Print schools to the screen, including matched student (if one exists).

	/*
	 * public static boolean isAlreadyEntered(ArrayList<Student> S, int
	 * schoolIndex, int numStudentsAdded) { for (int i = 0; i <
	 * numStudentsAdded; i++) { //
	 * System.out.println("Error in isAlreadyEntered1: schoolIndex is " // +
	 * schoolIndex + " while S[i].getSchool is " + S[i].getSchool());
	 * 
	 * if ((S.get(i).getSchool()) == (schoolIndex)) { // System.out.println("");
	 * // System.out.println("S[i].getSchool is " + S[i].getSchool()); //
	 * System.out.println("Error in isAlreadyEntered2: schoolIndex is " // +
	 * schoolIndex + " while S[i] is " + i); //
	 * System.out.println("Error! School index " + (schoolIndex) + //
	 * " is already entered."); return false;
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return true;
	 * 
	 * }
	 */
	// public static void updateStudentRegret(Student[] S, int i, int regret){
	// Index of student who's regret we want to update.
	// S[i].setRegret(regret);
	// }

	// public static void updateSchoolRegret(School [] H,int schoolIndex, int
	// schoolRegret){
	// H[schoolIndex].setRegret(schoolRegret);
	// }

	// Display matches and statistics.

	public static boolean matchingCanProceed(int numStudentsAdded,
			int nSchools, boolean rankingsSet) {
		if (numStudentsAdded <= 0) {
			return false;
		}
		if (nSchools <= 0) {
			return false;
		}
		if (numStudentsAdded != nSchools) {
			return false;
		}
		if (!rankingsSet) {
			return false;
		}

		return true;

	}

	// Check that the conditions to proceed with matching are satisfied.

	public static void main(String[] args) {
		String OptionChosen = "";

		boolean didRankingHappen = false;

		ArrayList<School> schoolArrayList = new ArrayList<School>(); // objects

		ArrayList<Student> studentArrayList = new ArrayList<Student>();
		boolean Quit = false;

		int loadedStudents = 0;
		int loadedSchools = 0;

		SMPSolver GSS = new SMPSolver(studentArrayList, schoolArrayList, true);
		SMPSolver GSH = new SMPSolver(schoolArrayList, studentArrayList, false);

		do {
			displayMenu();

			OptionChosen = getUserInput("Enter choice: ");

			if (checkUserInput(OptionChosen)) {

				if (OptionChosen.equalsIgnoreCase("Q")) {

					System.out.println("");
					System.out.println("Hasta luego!");
					System.exit(0);

				}

				if (OptionChosen.equalsIgnoreCase("L")) {

					didRankingHappen = false;

					studentArrayList.clear();

					System.out.println("");
					loadedSchools = loadSchools(schoolArrayList);

					loadedStudents = 0;

					loadedStudents = loadStudents(studentArrayList,
							schoolArrayList);
					// Loop through and update the school's number of
					// participants data field real quick
					for (int i = 0; i < schoolArrayList.size(); i++) {
						schoolArrayList.get(i).setNParticipants(loadedStudents);
					}

					didRankingHappen = assignRankings(studentArrayList,
							schoolArrayList, loadedStudents, loadedSchools);
					// Update participants messes up my matches :(
					if (loadedSchools != 0 || loadedStudents != 0) {
						GSS.setMatchesExist(false);
						GSH.setMatchesExist(false);
						// updateParticipants( GSS, GSH, studentArrayList,
						// schoolArrayList);
						GSS.clearMatches();
						GSH.clearMatches();
						updateParticipants(GSS, GSH, studentArrayList,
								schoolArrayList);
					}
					for (int i = 0; i < studentArrayList.size(); i++) {
						totalNumMatches += studentArrayList.get(i)
								.getMaxMatches();

					}
					for (int j = 0; j < schoolArrayList.size(); j++) {
						totalNumMatches += schoolArrayList.get(j)
								.getMaxMatches();
					}

				}
				if (OptionChosen.equalsIgnoreCase("E")) {

					editData(studentArrayList, schoolArrayList,
							numStudentsAdded, loadedSchoolsNum,
							didRankingHappen);
					if (studentArrayList.size() != 0
							|| schoolArrayList.size() != 0) {
						updateParticipants(GSS, GSH, studentArrayList,
								schoolArrayList);
					}

				}

				if (OptionChosen.equalsIgnoreCase("M")) {
					// didMatchingHappen = false;
					// clearMatches(studentArray, schoolArray, numStudentsAdded,
					// loadedSchoolsNum);
					// didMatchingHappen = match(studentArray, schoolArray,
					// numStudentsAdded, loadedSchoolsNum, didRankingHappen);

					// Create new SMP solver object

					// boolean canMatchingProceed = GSS.matchingCanProceed();

					long start = System.currentTimeMillis();

					System.out.println("");
					System.out.println("STUDENT-OPTIMAL MATCHING");
					boolean matchingCanProceed = GSS.matchingCanProceed();
					if (matchingCanProceed) {
						boolean didMatchingHappen1 = GSS.match();
						System.out.println("");
						GSS.print();
					}
					long elapsedTime = System.currentTimeMillis() - start;
					if (matchingCanProceed) {

						System.out.println(totalNumMatches
								+ " matches made in " + elapsedTime + "ms!");
						System.out.println("");
					}

					long start1 = System.currentTimeMillis();

					System.out.println("SCHOOL-OPTIMAL MATCHING");

					boolean matchingCanProceed2 = GSH.matchingCanProceed();
					if (matchingCanProceed2) {
						boolean didMatchingHappen1 = GSH.match();
						System.out.println("");
						GSH.print();
					}
					long elapsedTime2 = System.currentTimeMillis() - start;
					if (matchingCanProceed) {

						System.out.println(totalNumMatches
								+ " matches made in " + elapsedTime + "ms!");
						System.out.println("");
					}
					if (studentArrayList.size() != 0
							&& schoolArrayList.size() != 0) {
						updateParticipants(GSS, GSH, studentArrayList,
								schoolArrayList);
					}

					// didMatchingHappen = GSH.match();
					// boolean didMatchingHappen2 =

				}

				if (OptionChosen.equalsIgnoreCase("P")) {
					if (schoolArrayList.size() != 0
							&& studentArrayList.size() != 0) {
						if (GSS.matchesExist()) {
							copyAllMatches(GSS, studentArrayList,
									schoolArrayList);
						}
						// copyAllMatches(GSH, schoolArrayList,
						// studentArrayList);
					}

					if (studentArrayList.size() == 0) {
						System.out.println("");
						System.out.println("ERROR: No students are loaded!");
						System.out.println("");
					} else {
						printStudents(studentArrayList, schoolArrayList,
								loadedStudents, didRankingHappen);
					}

					if (schoolArrayList.size() == 0) {

						System.out.println("ERROR: No schools are loaded!");
						System.out.println("");
					} else {
						printSchools(studentArrayList, schoolArrayList,
								loadedSchools, didRankingHappen);
					}

				}
				if (OptionChosen.equalsIgnoreCase("D")) {

					System.out.println("");
					// solve.printMatches();
					// solve.printStats();
					// System.out.println("");

					System.out.println("STUDENT-OPTIMAL SOLUTION");
					System.out.println("");
					if (!GSS.matchesExist() || schoolArrayList.size() == 0
							|| studentArrayList.size() == 0) {
						System.out.println("ERROR: No matches exist!");
						System.out.println("");
					} else {
						if (studentArrayList.size() != 0
								&& schoolArrayList.size() != 0) {
							GSS.printMatches();
							GSS.print();
						}
					}

					System.out.println("SCHOOL-OPTIMAL SOLUTION");
					System.out.println("");
					if (!GSH.matchesExist() || schoolArrayList.size() == 0
							|| studentArrayList.size() == 0) {
						System.out.println("ERROR: No matches exist!");
						System.out.println("");
					} else {
						if (studentArrayList.size() != 0
								&& schoolArrayList.size() != 0) {
							GSH.printMatches();
							GSH.print();
						}
					}

				}
				if (OptionChosen.equalsIgnoreCase("x")) {
					// do this if matches happened other wise say
					// "no! and return"

					if (GSS.matchesExist() && studentArrayList.size() != 0
							&& schoolArrayList.size() != 0) {
						copyAllMatches(GSS, studentArrayList, schoolArrayList);
						copyAllMatches(GSH, schoolArrayList, studentArrayList);
						updateParticipants(GSS, GSH, studentArrayList,
								schoolArrayList);
						String stabilityWinner = "";
						String avgSchoolWinner = "";
						String avgStudentWinner = "";
						String avgTotalWinner = "";
						String timeWinner = "School-opt";
						String winner = "WINNER";
						if (GSS.getAvgReceiverRegret() < GSH
								.getAvgSuitorRegret()) {
							avgSchoolWinner = "Student-opt";
						} else
							avgSchoolWinner = "School-opt";
						if (GSS.getAvgSuitorRegret() < GSH
								.getAvgReceiverRegret()) {
							avgStudentWinner = "School-opt";
						} else
							avgStudentWinner = "Student-opt";
						if (GSS.getAvgTotalRegret() < GSH.getAvgTotalRegret()) {
							avgTotalWinner = "Student-opt";
						} else
							avgTotalWinner = "School-opt";
						if (GSS.isStable() && GSH.isStable()) {
							stabilityWinner = "TIE";
						}
						if (GSS.isStable() && !GSH.isEveryoneMatched()) {
							stabilityWinner = "School-opt";
						} else
							stabilityWinner = "Student-opt";

						System.out.println("");
						System.out
								.println("Solution              Stable    Avg school regret   Avg student regret     Avg total regret       Comp time (ms)");
						System.out
								.println("----------------------------------------------------------------------------------------------------------------");
						GSS.printStatsRow("Student optimal");
						GSH.printStatsRow("School-optimal");
						System.out
								.println("----------------------------------------------------------------------------------------------------------------");
						System.out.format("%-15s", winner);
						System.out.format("%13s%21s%21s%21s%21s\n",
								stabilityWinner, avgSchoolWinner,
								avgStudentWinner, avgTotalWinner, timeWinner);
						System.out
								.println("----------------------------------------------------------------------------------------------------------------");
						System.out.println("");
					} else if (!GSH.matchesExist()
							|| schoolArrayList.size() == 0
							|| studentArrayList.size() == 0) {
						System.out.println("");
						System.out.println("ERROR: No matches exist!");
						System.out.println("");
					}

				}

				if (OptionChosen.equalsIgnoreCase("R")) {

					GSS.reset();
					GSH.reset();
					schoolArrayList.clear();
					studentArrayList.clear();
					loadedSchoolsNum = 0;
					loadedStudents = 0;
					System.out.println("");
					System.out.println("Database cleared!");
					System.out.println("");

					// Remove everything from both array lists

				}

			}

		} while (!Quit);

	}

	// update participant arrays of solvers, creating independent participant
	// arrays for each solver to avoid conflicts
	public static void updateParticipants(SMPSolver GSS, SMPSolver GSH,
			ArrayList<Student> S, ArrayList<School> H) {

		// GSS solver duplicate arrays

		ArrayList<School> H1 = copySchools(H); // Copy the schools and students
												// into arrayLists
		ArrayList<Student> S1 = copyStudents(S);

		if (GSS.matchesExist())
			copyAllMatches(GSS, S1, H1); // Get the matches that GSS made
		GSS.updateParticipants(S1, H1); // Update the participants again

		// GSH solver duplicate arrays
		ArrayList<School> H2 = copySchools(H);
		ArrayList<Student> S2 = copyStudents(S);

		if (GSH.matchesExist())
			copyAllMatches(GSH, H2, S2);
		GSH.updateParticipants(H2, S2);

	}

	// copy matches from one solver into S and R to maintain original matching
	public static void copyAllMatches(SMPSolver GS,
			ArrayList<? extends Participant> S,
			ArrayList<? extends Participant> R) {
		copyMatches_oneGroup(GS.getSuitors(), S);
		copyMatches_oneGroup(GS.getReceivers(), R);
	}

	// copy participant matches in P into newP
	public static void copyMatches_oneGroup(ArrayList<Participant> P,
			ArrayList<? extends Participant> newP) {
		for (int i = 0; i < P.size(); i++) {
			newP.get(i).clearMatches();
			for (int j = 0; j < P.get(i).getNMatches(); j++)
				newP.get(i).setMatch(P.get(i).getMatch(j));
		}
	}

	public static ArrayList<Student> copyStudents(ArrayList<Student> P) {
		ArrayList<Student> newList = new ArrayList<Student>();
		for (int i = 0; i < P.size(); i++) {
			String name = P.get(i).getName();
			int ES = P.get(i).getES();
			double GPA = P.get(i).getGPA();
			int nSchools = P.get(i).getNParticipants();
			Student temp = new Student(name, GPA, ES, nSchools);
			for (int j = 0; j < nSchools; j++) {
				temp.setRanking(j, P.get(i).getRanking(j));
			}
			newList.add(temp);

		}
		return newList;
	}

	public static ArrayList<School> copySchools(ArrayList<School> P) {
		ArrayList<School> newList = new ArrayList<School>();
		for (int i = 0; i < P.size(); i++) {
			String name = P.get(i).getName();
			double alpha = P.get(i).getAlpha();
			int maxMatches = P.get(i).getMaxMatches();
			int nStudents = P.get(i).getNParticipants();

			School temp = new School(name, alpha, nStudents, maxMatches);
			for (int j = 0; j < nStudents; j++) {

				temp.setRanking(j, P.get(i).getRanking(j));
			}
			newList.add(temp);
		}
		return newList;

	}

	public static double getDouble(String prompt, double LB, double UB) {
		double value = 0;
		boolean valid;
		do {
			valid = true;
			System.out.print(prompt);
			try {
				value = Double.parseDouble(input.readLine());
				if (value < LB || value > UB) {
					valid = false;

					System.out.println("");
					System.out
							.printf("ERROR: Input must be a real number in [0.00, %.2f]!\n",
									UB);
					System.out.println("");

				}
			}

			catch (NumberFormatException e) {

				System.out.println("");
				System.out
						.printf("ERROR: Input must be a real number in [0.00, %.2f]!\n",
								UB);
				System.out.println("");
				valid = false;

			} catch (IOException e) {

				System.out.println("");
				System.out
						.printf("ERROR: Input must be a real number in [0.00, %.2f]!\n",
								UB);
				System.out.println("");
				valid = false;

			}

		} while (!valid);
		return value;

	}

	public static int getInteger(String prompt, int LB, int UB) {

		boolean valid;
		int value = -1;

		do {

			valid = true;
			System.out.print(prompt);

			try {
				value = Integer.parseInt(input.readLine());
				if ((prompt == "Number of schools to add: " || prompt == "Number of students to add: ")
						&& (value < LB || value > UB)) {

					System.out.println("");
					System.out.println("ERROR: Input must be an integer in ["
							+ LB + ", infinity]!");
					System.out.println("");
					valid = false;

				}

				else if (value < LB || value > UB) {
					valid = false;
					System.out.println("");
					System.out.println("ERROR: Input must be an integer in ["
							+ LB + ", " + UB + "]!");
					System.out.println("");

				}

			} catch (NumberFormatException e) {
				if (prompt == "Number of schools to add: "
						|| prompt == "Number of students to add: ") {

					System.out.println("");
					System.out.println("ERROR: Input must be an integer in ["
							+ LB + ", infinity]!");
					System.out.println("");
					valid = false;

				}

				else {
					System.out.println("");
					System.out.println("ERROR: Input must be an integer in ["
							+ LB + ", " + UB + "]!");
					System.out.println("");

					valid = false;
				}

			}

			catch (IOException e) {

				if (prompt == "Number of schools to add: "
						|| prompt == "Number of students to add: ") {

					System.out.println("");
					System.out.println("ERROR: Input must be an integer in ["
							+ LB + ", infinity]!");
					System.out.println("");
					valid = false;

				}

				else {
					System.out.println("");
					System.out.println("ERROR: Input must be an integer in ["
							+ LB + ", " + UB + "]!");
					System.out.println("");

					valid = false;
				}

			}

		} while (!valid);
		return value;
	}

}
