/*

Class:			MovieMaster
Description:	The class which is used for the user to interact with the program 
Author:			Milindi Kodikara - s3667779
 */

package menu;

import java.util.Scanner;

import s3667779_A2.BorrowException;
import s3667779_A2.DateTime;
import s3667779_A2.Game;
import s3667779_A2.IdException;
import s3667779_A2.Item;
import s3667779_A2.Movie;

import java.io.*;

public class MovieMaster {

	private Item[] item = new Item[100];

	private String input = null;
	private String id = null;
	private String title = null;
	private String genre = null;
	private String description = null;
	boolean isANewRelease = true;
	private String prefix = "";
	private boolean extended;

	boolean menuRunning = true;

	Scanner keyboard = new Scanner(System.in);

	public void menu() {

		while (menuRunning) {

			String line = "\n____________________________________\n";
			String title = "*** Movie Master System Menu ***\n";

			String menu1 = String.format("%-30s%s\n", " Add Item", "A ");
			String menu2 = String.format("%-30s%s\n", " Borrow Item", "B ");
			String menu3 = String.format("%-30s%s\n", " Return Item", "C ");
			String menu4 = String.format("%-30s%s\n", " Display Details", "D ");
			String menu5 = String.format("%-30s%s\n", " Seed Data", "E ");
			String menu6 = String.format("%-30s%s\n", " Exit Program", "X ");

			String choice = String.format("%-30s\n", " Enter Selection:");

			String menu = line + title + menu1 + menu2 + menu3 + menu4 + menu5 + menu6 + choice + line;

			System.out.println(menu);

			input = keyboard.nextLine();

			if ((input.compareToIgnoreCase("A") == 0) || (input.compareToIgnoreCase("A") == 0)) {
				input = "A";
				this.addItem();
			} else if ((input.compareToIgnoreCase("B") == 0) || (input.compareToIgnoreCase("b") == 0)) {
				input = "B";
				this.borrowItem();
			} else if ((input.compareToIgnoreCase("C") == 0) || (input.compareToIgnoreCase("c") == 0)) {
				input = "C";
				this.returnItem();
			} else if ((input.compareToIgnoreCase("D") == 0) || (input.compareToIgnoreCase("d") == 0)) {
				input = "D";
				this.displayDetails();
			} else if ((input.compareToIgnoreCase("E") == 0) || (input.compareToIgnoreCase("e") == 0)) {
				input = "E";
				this.seedData();

			} else if ((input.compareToIgnoreCase("X") == 0) || (input.compareToIgnoreCase("x") == 0)) {
				input = "X";
				writeItems();
				System.exit(0);

			} else if (input.equals("")) {
				menu();
				menuRunning = false;
			}
		}
	}

	// Validate if an item is a movie or a game using the first two characters of
	// the id
	public String getPrefix(int i) {

		if (item[i] instanceof Movie) {

			prefix = "M_";

		} else if (item[i] instanceof Game) {
			prefix = "G_";
		}
		return prefix;
	}

	public void addItem() {
		int index = 0;
		System.out.print("Enter id:");

		id = keyboard.nextLine();
		// Check if the id entered is three characters in length
		if (id.length() != 3) {

			System.out.print("The Id " + id + " is invalid. Please enter a 3 digit id.");
			this.menu();
		} else if (id.length() == 3) {
			/*Iterate over the existing array of items
			 * and check if the id entered already exists in the program
			 */
			for (int i = 0; i < item.length - 1; i++) {

				if (item[i] != null) {

					if (item[i].getId().equals(this.getPrefix(i) + id)) {
						System.out.println("Error - Id for " + id + " already exists in the system!");
						this.menu();
					}

				} else {
					index = i;
					break;
				}
			}
		}

		System.out.print("Enter title:");
		title = keyboard.nextLine();

		System.out.print("Enter genre:");
		genre = keyboard.nextLine();

		System.out.print("Enter description:");
		description = keyboard.nextLine();

		String mOrG = "";
		System.out.print("Movie or Game (M/G)?");
		String mOrGTemp = keyboard.nextLine();

		if ((mOrGTemp.compareTo("G") == 0) || (mOrGTemp.compareTo("M") == 0) || (mOrGTemp.compareTo("") == 0)) {

			if (mOrGTemp.equals("M")) {
				mOrG = "M";
			} else if (mOrGTemp.equals("G")) {
				mOrG = "G";
			} else if (mOrGTemp.equals("")) {
				menu();
			}

		} else {
			// If the user input is incorrect, user is re-prompted until they enter a
			// correct value or hit enter
			while ((mOrGTemp.compareTo("M") != 0) || (mOrGTemp.compareTo("G") != 0)) {
				System.out.print("\t Error: You must enter 'M' or 'G' \n Movie and Game (M/G) ? :");
				mOrGTemp = keyboard.nextLine();
				if ((mOrGTemp.compareTo("M") == 0) || (mOrGTemp.compareTo("G") == 0) || (mOrGTemp.compareTo("") == 0)) {

					if (mOrGTemp.equals("M")) {
						mOrG = "M";
					} else if (mOrGTemp.equals("G")) {
						mOrG = "G";
					} else if (mOrGTemp.equals("")) {
						menu();
					}
					break;
				}

			}
		}

		if (mOrG.equals("G")) {
			try {
				System.out.print("Enter Game Platforms:");

				String platforms = keyboard.nextLine();
				// Split the strings in to an array separated by commas
				String[] platformsFromMovieMaster = platforms.split(",");
				// A new game object is created with the supplied data
				item[index] = new Game(id, title, genre, description);
				((Game) item[index]).setPlatforms(platformsFromMovieMaster);
				System.out.println("New game added successfully for the game entitled: " + title);
			} catch (IdException e) {
				System.out.println(e.getMessage());
			}
		}

		else if (mOrG.equals("M")) {

			System.out.print("Enter new release (Y/N):");
			String temp = keyboard.nextLine();

			if ((temp.compareTo("Y") == 0) || (temp.compareTo("N") == 0) || (temp.compareTo("") == 0)) {

				if (temp.equals("Y")) {
					isANewRelease = true;
				} else if (temp.equals("N")) {
					isANewRelease = false;
				} else if (temp.equals("")) {
					menu();
				}

			} else {

				// If the user input is incorrect, user is re-prompted until they enter a
				// correct value or hit enter

				while ((temp.compareTo("Y") != 0) || (temp.compareTo("N") != 0)) {
					System.out.print("\t Error: You must enter 'Y' or 'N' \n Is New Release (Y/N) ? :");
					temp = keyboard.nextLine();
					if ((temp.compareTo("Y") == 0) || (temp.compareTo("N") == 0) || (temp.compareTo("") == 0)) {

						if (temp.equals("Y")) {
							isANewRelease = true;
						} else if (temp.equals("N")) {
							isANewRelease = false;
						} else if (temp.equals("")) {
							menu();
						}
						break;
					}

				}
			}

			try {
				// A new movie object is created with the supplied data
				item[index] = new Movie(id, title, genre, description, isANewRelease);
				System.out.println("New movie added successfully for the movie entitled: " + title);
			} catch (IdException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void borrowItem() {

		int index = 0;
		System.out.print("Enter id:");

		id = keyboard.nextLine();
		// Locate the corresponding object within the array of Item references
		for (int i = 0; i < item.length - 1; i++) {

			if (item[i] != null) {
				String prefixGot = this.getPrefix(i);
				if (item[i].getId().equals(prefixGot + id)) {
					index = i;
					break;
				}
			} else {
				System.out.println("The item with the id number: " + id + ",not found");
				this.menu();
				break;

			}
		}

		System.out.print("Enter member id:");
		String memberId = keyboard.nextLine();

		if (memberId.length() != 3) {

			System.out.print("The member id " + memberId + " is invalid. Please enter a 3 digit member id.");
			this.menu();
		} else if (memberId.length() == 3) {

			System.out.print("Advance borrow (days):");
			int advDays;
			// Validate that the input is a number using the method Integer.parseInt
			try {
				advDays = Integer.parseInt(keyboard.nextLine());
			} catch (NumberFormatException e) {

				System.out.println("Error: Not a number ");
				return;
			}

			try {
				if (item[index] instanceof Game) {
					System.out.print("Extended hire (Y/N) ?");
					String isExtended = keyboard.nextLine();

					if (((isExtended.compareTo("Y") == 0) || (isExtended.compareTo("N") == 0)
							|| (isExtended.compareTo("") == 0))) {
						if (isExtended.equals("Y")) {
							extended = true;
						} else if (isExtended.equals("N")) {
							extended = false;
						} else if (isExtended.equals("")) {
							menu();
						}
					} else {
						// If the user input is incorrect, user is re-prompted until they enter a
						// correct value or hit enter
						while ((isExtended.compareTo("Y") != 0) || (isExtended.compareTo("N") != 0)) {
							System.out.print("\t Error: You must enter 'Y' or 'N' \n Extended hire (Y/N) ? :");
							isExtended = keyboard.nextLine();
							if ((isExtended.compareTo("Y") == 0) || (isExtended.compareTo("N") == 0)
									|| (isExtended.compareTo("") == 0)) {

								if (isExtended.equals("Y")) {
									extended = true;
								} else if (isExtended.equals("N")) {
									extended = false;
								} else if (isExtended.equals("")) {
									menu();
								}
								break;
							}

						}
					}

					((Game) item[index]).setIfExtended(extended);
					// compare

				}
				// Update all the aspects related to borrowing an item i.e the historical hire
				// records and the currently borrowed aspect
				double rentalFee = item[index].borrow(memberId, advDays);

				System.out.println("The item " + title + " costs $" + rentalFee + " and is due on: "
						+ item[index].getActualReturnDate());

			} catch (BorrowException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/*
	 * ALGORITHM 
	 * 			BEGIN 
	 * 			INITIALISE local variable index to zero 
	 * 			PROMPT the user to enter Id 
	 * 			COMPUTE if the item is currently borrowed or not using a for loop
	 * 				IF the item[i] is not null 
	 * 					IF the item[i] is equal to the id entered by the user
	 * 						index is equal to i 
	 * 						BREAK out of the for loop
	 * 					ELSE display an error message
	 *					Return to the menu
	 *			END the for loop 
	 *			IF item[index] is null 
	 *			DISPLAY error message notifying the user that the item is not on loan 
	 *			ELSE
	 *			PROMPT the user to enter the number of days on loan 
	 *			DECLARE variable noOfDays 
	 *			Validate that the input is an integer using a try catch block 
	 *			COMPUTE the return date using the number of days on loan 
	 *			COMPUTE the lateFee by calling the returnItem  method of the Item class
	 *			DISPLAY a message notifying the user of the lateFee payable 
	 * 			END
	 */
	public void returnItem() {

		int index = 0;
		System.out.print("Enter id:");
		id = keyboard.nextLine();
		// Validate the existence of the Id
		for (int i = 0; i < item.length - 1; i++) {
			if (item[i] != null) {
				String prefixGot = this.getPrefix(i);

				if (item[i].getId().equals(prefixGot + id)) {

					index = i;
					break;
				}
			} else {
				System.out.println("Error - The item with id:" + id + ", not found");
				this.menu();
				break;

			}
		}

		if (item[index].getCurrentlyBorrowed() == null) {
			System.out.println("Error:The item with id " + this.getPrefix(index) + id + " is NOT currently on a loan.");
		} else {
			System.out.print("Enter the number of days on loan:");
			int noOfDays;

			try {
				noOfDays = Integer.parseInt(keyboard.nextLine());
			} catch (NumberFormatException e) {

				System.out.println("Error: Not a number ");
				return;
			}

			DateTime returnDate = item[index].returnDate(new DateTime(noOfDays));

			try {
				System.out.println("The total fee payable is $" + item[index].returnItem(returnDate));
			} catch (BorrowException e) {
				System.out.println(e.getMessage());
			}

		}

	}

	public void displayDetails() {
		// Display details of all the objects currently stored in the Item array
		for (int i = 0; i < item.length - 1; i++) {
			if (item[i] != null) {

				System.out.println(item[i].getDetails());
			}
		}

	}

	// Populate the Item array with hard coded movies and games
	public void seedData() {

		if (item[0] != null) {
			System.out.println("Error: The collection of Items already has data!");

		}

		else {
			int count = 0;

			try {
				item[count] = new Movie("IRM", "Iron Man", "Action",
						"A billionaire industrialist and genius inventor, Tony Stark (Robert Downey Jr.), is conducting weapons tests overseas, but terrorists kidnap him to force him to build a devastating weapon. . . ",
						false);

				count++;

				item[count] = new Movie("CAW", "Captain America The Winter Soldier", "Action",
						"After the cataclysmic events in New York with his fellow Avengers, Steve Rogers, aka Captain America (Chris Evans), lives in the nation's capital as he tries to adjust to modern times. . . ",
						false);
				try {
					item[count].borrow("AZQ", 0);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}

				count++;

				item[count] = new Movie("ATM", "Ant Man", "Action",
						"Forced out of his own company by former protégé Darren Cross, Dr. Hank Pym (Michael Douglas) recruits the talents of Scott Lang (Paul Rudd), a master thief just released from prison. . . ",
						false);
				try {
					item[count].borrow("XSW", 0);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				try {
					item[count].returnItem(item[count].returnDate(new DateTime(5)));
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}

				count++;

				item[count] = new Movie("CAC", "Captain America Civil War", "Action",
						"Political pressure mounts to install a system of accountability when the actions of the Avengers lead to collateral damage. . . ",
						false);
				try {
					item[count].borrow("EDC", 0);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				try {
					item[count].returnItem(item[count].returnDate(new DateTime(10)));
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				count++;

				item[count] = new Movie("AVG", "Avengers", "Action",
						"When Thor's evil brother, Loki (Tom Hiddleston), gains access to the unlimited power of the energy cube called the Tesseract, Nick Fury (Samuel L. Jackson), director of S.H.I.E.L.D. . . ",
						false);
				try {
					item[count].borrow("RFV", 0);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				try {
					item[count].returnItem(item[count].returnDate(new DateTime(10)));
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				try {
					item[count].borrow("RFV", 10);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}

				count++;

				item[count] = new Movie("GOG", "Guardians of the Galaxy", "Action",
						"Peter Quill and his fellow Guardians are hired by a powerful alien race, the Sovereign, to protect their precious batteries from invaders. . . ",
						true);
				count++;

				item[count] = new Movie("SMH", "Spider-Man Homecoming", "Action",
						"Thrilled by his experience with the Avengers, young Peter Parker returns home to live with his Aunt May. . . ",
						true);
				try {
					item[count].borrow("UJM", 0);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}

				count++;

				item[count] = new Movie("THR", "Thor Ragnarok", "Action",
						"Imprisoned on the other side of the universe, the mighty Thor finds himself in a deadly gladiatorial contest that pits him against the Hulk, his former ally and fellow Avenger. . . ",
						true);
				try {
					item[count].borrow("TGB", 0);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				try {
					item[count].returnItem(item[count].returnDate(new DateTime(1)));
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				count++;

				item[count] = new Movie("BLP", "Black Panther", "Action",
						"After the death of his father, T'Challa returns home to the African nation of Wakanda to take his rightful place as king. . . ",
						true);
				try {
					item[count].borrow("YHN", 0);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				try {
					item[count].returnItem(item[count].returnDate(new DateTime(3)));
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				count++;

				item[count] = new Movie("AIW", "Avengers Infinity War", "Action",
						"Iron Man, Thor, the Hulk and the rest of the Avengers unite to battle their most powerful enemy yet -- the evil Thanos . . . ",
						true);
				try {
					item[count].borrow("UKM", 0);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				try {
					item[count].returnItem(item[count].returnDate(new DateTime(3)));
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				try {
					item[count].borrow("UKM", 3);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}

				count++;

				item[count] = new Game("PNG", "Pong", "Sports", "One of the earliest arcade games . . . ");
				String[] tempPlatArray = { "Nintendo ", " Arcade " };
				((Game) item[count]).setPlatforms(tempPlatArray);
				count++;

				item[count] = new Game("SMB", "Super Mario Bros", "Platform",
						"A platform video game developed and published by Nintendo . . .");
				try {
					item[count].borrow("POL", 0);

					((Game) item[count]).setPlatforms(tempPlatArray);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				count++;

				item[count] = new Game("LOZ", "The Legend of Zelda", "Action-Adventure",
						"A game developed and published by Nintendo . . .");
				try {
					item[count].borrow("ZKU", 0);

					((Game) item[count]).setPlatforms(tempPlatArray);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				try {
					item[count].returnItem(item[count].returnDate(new DateTime(19)));
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}

				count++;

				item[count] = new Game("SNC", "Sonic the Hedgehog", "Platform",
						"A game developed and published by Sega . . .");
				try {
					item[count].borrow("YHT", 0);

					((Game) item[count]).setPlatforms(tempPlatArray);
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				try {
					item[count].returnItem(item[count].returnDate(new DateTime(32)));
				} catch (BorrowException e) {
					System.out.println(e.getMessage());
				}
				count++;
			} catch (IdException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public void writeItems() {

		String filename = "File.txt";
		PrintWriter outputStream = null;

		try {
			// create a new object of the printWriter class & assign to the object variable
			outputStream = new PrintWriter(new FileOutputStream(filename));

			for (int i = 0; i < item.length - 1; i++) {

				if (item[i] == null) {

					break;
				}
				// Write the information from the toString method of the item class for all
				// Items
				String stringToWrite = item[i].toString();
				outputStream.write(stringToWrite);

				// Writing out the full borrowing history for each object such that it is
				// associated with the parent object
				for (int x = item[i].getHireHistory().length - 1; x >= 0; x--) {
					if ((item[i].getHireHistory()[x] == null) && (x > 0)) {
						outputStream.write("&" + "No Hiring Record");
					} else if ((item[i].getHireHistory()[x] == null) && (x == 0)) {
						outputStream.write("&" + "No Hiring Record" + "\n");
					} else {
						outputStream.write("&" + item[i].getHireHistory()[x].toString());
					}
				}

			}
			System.out.println("File was written to " + filename + " without error");
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		outputStream.close();
		WriteItemsBackUp();
	}

	public void readItems()

	{

		int count = 0;
		String filename = "";

		filename = "File.txt";

		Scanner inputStream = null;
		try {
			// Create Scanner object and assign to variable
			inputStream = new Scanner(new File(filename));
			inputStream.useDelimiter("&");
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + filename);

			// If data file is not found check for the backup file

			filename = "File_BackUp.txt";

			try {

				inputStream = new Scanner(new File(filename));
				inputStream.useDelimiter("&");
				System.out.println("Data was loaded from the backup file.");

				// If backup file is not found message is displayed and continues on to the program
				// menu
			} catch (FileNotFoundException e1) {
				System.out.println("No backup file found. No data was loaded!");
				menu();
			}


		}



		while (inputStream.hasNextLine()) {
			String line = inputStream.nextLine();

			String[] temp = line.split("&");

			String movieOrGame = temp[0];
			String hR1 = temp[1];
			String hR2 = temp[2];
			String hR3 = temp[3];
			String hR4 = temp[4];
			String hR5 = temp[5];
			String hR6 = temp[6];
			String hR7 = temp[7];
			String hR8 = temp[8];
			String hR9 = temp[9];
			String hR10 = temp[10];

			int index = 0;
			// Use an array to iterate through the temp array in the reverse order of hiring
			// records
			String[] reverseTemp = { movieOrGame, hR10, hR9, hR8, hR7, hR6, hR5, hR4, hR3, hR2, hR1 };

			for (String tempLoop : reverseTemp) {
				// Reading in the object state of each object represented in the file and
				// restoration into the Item array
				if (reverseTemp[index] == reverseTemp[0]) {

					String[] tokens = movieOrGame.split(":");
					String id = tokens[0];
					String title = tokens[1];
					String description = tokens[2];
					String genre = tokens[3];

					if (id.charAt(0) == 'M') {
						id = id.substring(2, 5);
						Movie m1 = null;
						String fee = tokens[4];
						double feeInt = Double.parseDouble(fee);

						String type = tokens[5];

						if (type.equals("NR")) {
							try {
								m1 = new Movie(id, title, genre, description, true);
							} catch (IdException e) {

								System.out.println(e.getMessage());
								continue;
							}
						} else if (type.equals("WK")) {
							try {
								m1 = new Movie(id, title, genre, description, false);

							} catch (IdException e) {
								System.out.println(e.getMessage());
								continue;
							}
						}
						if (m1 != null) {

							item[count] = m1;

						}

					} else if (id.charAt(0) == 'G') {
						Game g1 = null;
						id = id.substring(2, 5);
						try {
							g1 = new Game(id, title, genre, description);
							String platforms = tokens[4];
							String[] platformsFromMovieMaster = platforms.split(",");
							g1.setPlatforms(platformsFromMovieMaster);
						} catch (IdException e) {
							System.out.println(e.getMessage());
							continue;
						}
						if (g1 != null) {
							item[count] = g1;

						}

					}
				}

				// Reading in and restoration of the full borrowing history for each object
				if ((reverseTemp[index] == hR1) || (reverseTemp[index] == hR2) || (reverseTemp[index] == hR3)
						|| (reverseTemp[index] == hR4) || (reverseTemp[index] == hR5) || (reverseTemp[index] == hR6)
						|| (reverseTemp[index] == hR7) || (reverseTemp[index] == hR1) || (reverseTemp[index] == hR8)
						|| (reverseTemp[index] == hR9) || (reverseTemp[index] == hR10)) {

					if (reverseTemp[index].equals("No Hiring Record")) {
						for (int z = 0; z < reverseTemp.length - 1; z++) {
							continue;
						}

					} else {
						DateTime returnDateHR = null;
						double lateFeeIntHR = 0.00;
						int diff = 0;

						String[] tokens2 = reverseTemp[index].split(":");

						String idHR = tokens2[0];
						String borrowDate = tokens2[1];
						int dayB = Integer.parseInt(borrowDate.substring(0, 2));
						int monthB = Integer.parseInt(borrowDate.substring(2, 4));
						int yearB = Integer.parseInt(borrowDate.substring(4, 8));
						DateTime borrowDateHR = new DateTime(dayB, monthB, yearB);

						String returnDate = tokens2[2];

						if (returnDate.equals("none")) {
							returnDateHR = null;
							diff = 0;
							// Compute the difference between the days by creating a new DateTime object
						} else {

							int dayR = Integer.parseInt(returnDate.substring(0, 2));
							int monthR = Integer.parseInt(returnDate.substring(2, 4));
							int yearR = Integer.parseInt(returnDate.substring(4, 8));
							returnDateHR = new DateTime(dayR, monthR, yearR);
							diff = DateTime.diffDays(returnDateHR, borrowDateHR);
						}

						double feeIntHR = 0.00;
						String feeHR = tokens2[3];
						if (feeHR.equals("none")) {
							feeIntHR = 0.00;
						} else {
							feeIntHR = Double.parseDouble(feeHR);
						}

						String lateFeeHR = tokens2[4];

						if (lateFeeHR.equals("none")) {
							lateFeeIntHR = 0.00;
						} else {
							lateFeeIntHR = Double.parseDouble(lateFeeHR);
						}

						try {
							// Restoring the borrowing history
							item[count].borrow(idHR.substring(6, 9), 0);
							if (diff != 0) {
								item[count].returnItem(item[count].returnDate(new DateTime(diff)));
							}

						} catch (Exception e) {
							System.out.println(e.getMessage());

						}
					}
				}
				index++;
			}
			count++;
			System.out.println(line);
		}

		inputStream.close();

	}

	// Method for writing out a back up file
	public void WriteItemsBackUp() {

		String filename = "File_Backup.txt";
		PrintWriter outputStream = null;

		try {
			// create a new object of the printWriter class & assign to the object variable
			outputStream = new PrintWriter(new FileOutputStream(filename));

			for (int i = 0; i < item.length - 1; i++) {

				if (item[i] == null) {

					break;
				}
				// Write the information from the toString method of the item class for all
				// Items
				String stringToWrite = item[i].toString();
				outputStream.write(stringToWrite);

				// Writing out the full borrowing history for each object such that it is
				// associated with the parent object
				for (int x = item[i].getHireHistory().length - 1; x >= 0; x--) {
					if ((item[i].getHireHistory()[x] == null) && (x > 0)) {
						outputStream.write("&" + "No Hiring Record");
					} else if ((item[i].getHireHistory()[x] == null) && (x == 0)) {
						outputStream.write("&" + "No Hiring Record" + "\n");
					} else {
						outputStream.write("&" + item[i].getHireHistory()[x].toString());
					}
				}

			}
			System.out.println("File was written to " + filename + " without error");
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		outputStream.close();

	}

}