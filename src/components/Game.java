/*

Class:			Movie 
Description:	The class represents an item of type game.
Author:			Milindi Kodikara - s3667779
 */

package s3667779_A2;

public class Game extends Item {

	// Declare instance variables
	private String[] platforms;

	private boolean extended = false;
	private int difference;

	private final int DUE_DATE = 30;
	private final static double STANDARD_FEE = 20.00;

	public Game(String id, String title, String genre, String description) throws IdException {
		super(id = "G_" + id, title, genre, description, STANDARD_FEE);

	}

	// Set the new platforms array from movie master
	public void setPlatforms(String[] platformsFromMovieMaster) {

		this.platforms = new String[platformsFromMovieMaster.length];
		this.platforms = platformsFromMovieMaster;

	}

	public String getPlatforms() {
		String platformArray = "";

		for (String plat : platforms) {

			platformArray += plat + ",";

		}
		platformArray = platformArray.substring(0, platformArray.length() - 1);

		return platformArray;

	}

	public void setIfExtended(boolean extended) {

		this.extended = extended;
	}

	/*Compute the return date from the borrow date
	by calculating the difference between the borrow date and the due date */
	public String getActualReturnDate() {

		DateTime actualReturnDate = new DateTime(super.getCurrentlyBorrowed().getBorrowDate(), DUE_DATE);
		return actualReturnDate.getFormattedDate();

	}
	/*
	 * ALGORITHM
	 * 			BEGIN 
	 * 			GET the difference between the return date and the borrow date from the item class
	 * 			IF currentlyBorrowed is null
	 * 				THROW new exception
	 * 			IF difference between the dates is greater than the due date 
	 * 				COMPUTE remaining days as the difference between the difference and the due date
	 * 				COMPUTE lateFee as $1.00 fee for every day past the due date
	 * 				COMPUTE the number of weeks passed by dividing the remaining days by the number of days in a week
	 * 				COMPUTE additionalLateFee as $5.00 for every 7 days past the due date 
	 * 				IF extended is true 
	 * 					lateFee is 50% of the lateFee calculated
	 * 			IF the difference between the dates is less than the due date
	 *  			lateFee is zero
	 *  		UPDATE the currently borrowed attribute and the historic hiring records 
	 *  		RETURN lateFee 
	 *  
	 *  TEST 
	 *  	difference is 15 , difference is less than the due date , lateFee is zero
	 *  	difference  is 60 , not an extended hire , difference is greater than the due date , lateFee is 30+(5*4) = $60
	 *  	difference is 60 , an extended hire , difference is greater than the due date , lateFee is (30+(5*4))*0.5 = $30
	 *  
	 */

	public double returnItem(DateTime returnDate) throws BorrowException {

		double lateFee = 0.00;
		double additionalLateFee = 0.00;

		difference = super.getDiffDays(returnDate);

		if (super.getCurrentlyBorrowed() == null) {
			throw new BorrowException("Error:The item with id " + this.getId() + " is NOT on a loan.");
		}
		if (difference > 0) {
		if (difference > DUE_DATE) {

			int remainingDays = difference - DUE_DATE;

			lateFee += remainingDays;

			int weeksPassed = (int) (remainingDays / 7);
			additionalLateFee += weeksPassed * 5;

			lateFee = lateFee + additionalLateFee;

			if (extended) {
				lateFee = lateFee * 0.5;

			}

		} else if (difference < DUE_DATE) {

			lateFee = 0.00;

		}
		}
		super.returnItem(returnDate, lateFee);
		return lateFee;

	}

	//Method for getting all the details of a game 
	public String getDetails() {

		String getDetails = "";
		getDetails = super.getDetails();

		if (super.getCurrentlyBorrowed() != null) {
			if (extended == true) {
				onALoan = "EXTENDED";
			}
			}

		getDetails = String.format("%s\n%-30s%s", getDetails, "On loan:", onALoan);
		getDetails = String.format("%s\n%-30s%s", getDetails, "Platforms:", this.getPlatforms());
		getDetails = String.format("%s\n%-30s%s", getDetails, "Rental Period:", DUE_DATE + " days");
		getDetails = String.format("%s\n%-30s%s\n%-30s%s\n", getDetails, "", "BORROWING RECORD", "", borrowingRecord);

		return getDetails;

	}

	public String toString() {

		String toString = "";
		toString = super.toString();

		if (extended == true) {
			loanString = "E";
		}

		toString = toString + this.getPlatforms() + ":" + STANDARD_FEE + ":";

		toString = toString + loanString;

		return toString;

	}

}
