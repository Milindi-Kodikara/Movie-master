
/*

Class:			Movie 
Description:	The class represents an item of type movie.
Author:			Milindi Kodikara - s3667779
 */

package s3667779_A2;

public class Movie extends Item {

	// Declaration of instance variables
	private boolean isNewRelease;

	// Constants declared to avoid using hard coded numbers
	static private final double NEW_RELEASE_SURCHARGE = 2.00;

	private final int NEW_RELEASE_BORROW_DATE = 2;
	private final int WEEKLY_BORROW_DATE = 7;
	//Constants declared as static in order to be used in the constructor 
	private final static double WEEKLY_BASE_RATE = 3.00;
	private final static double NEW_RELEASE_BASE_RATE = NEW_RELEASE_SURCHARGE + WEEKLY_BASE_RATE;

	private int difference;

	public Movie(String id, String title, String genre, String description, boolean isNewRelease) throws IdException {

		super(id = "M_" + id, title, genre, description, isNewRelease ? NEW_RELEASE_BASE_RATE : WEEKLY_BASE_RATE);

		this.isNewRelease = isNewRelease;

	}
	/*
	 * Calculate the due date when a movie has been borrowed according to the
	 * rental period for the movie types
	 */

	public String getActualReturnDate() {

		if (isNewRelease == true) {
			DateTime actualReturnDate = new DateTime(super.getCurrentlyBorrowed().getBorrowDate(), 2);
			return actualReturnDate.getFormattedDate();
		} else {
			DateTime actualReturnDate = new DateTime(super.getCurrentlyBorrowed().getBorrowDate(), 7);
			return actualReturnDate.getFormattedDate();
		}

	}

	/*
	 * ALGORITHM 
	 * 			BEGIN 
	 * 			GET difference between the date the movie has been borrowed and returned
	 *  		DECLARE local variable lateFee and initialise it to 0.00 
	 * 				IF currenlyBorrowed is null
	 * 					 THROW a new BorrowException 
	 * 				VALIDATE that the difference is correct using sanity checks 
	 * 				IF the movie is a new release 
	 * 					IF the difference is greater than the the rental period 
	 * 					COMPUTE lateFee as 50% of the rental fee for everyday past the due date
	 * 					ELSE IF the difference is less than the rental period lateFee is zero 
	 * 				IF the movie is not a new release i.e a weekly movie IF difference is greater than the rental period 
	 * 					COMPUTE lateFee as 50% of the rental fee for every day past the due date
	 * 				ELSE IF difference is less than the rental period lateFee is zero 
	 * 			UPDATE the currently borrowed attribute and the historic hiring records 
	 * 			RETURN lateFee
	 * 			END
	 * 
	 * TEST 
	 * 		difference is equal to 0, computation should not be performed
	 * 		difference is 1, new release , lateFee is zero as difference is not greater than the rental period 
	 * 		difference is 3, new release , lateFee is (5*0.5)*(3-2) = $2.5
	 * 		difference is 6, weekly , lateFee is zero as difference is not greater than the rental period
	 * 		difference is 8, weekly , lateFee is (3*0.5)*(8-1) = $1.5
	 * 
	 */
	public double returnItem(DateTime returnDate) throws BorrowException {

		difference = super.getDiffDays(returnDate);

		double lateFee = 0.00;

		if (super.getCurrentlyBorrowed() == null) {

			throw new BorrowException("Error:The item with id " + this.getId() + " is currently NOT on a loan.");

		}

		if (difference > 0) {
			if (isNewRelease == true) {
				if (difference > NEW_RELEASE_BORROW_DATE) {

					lateFee = (NEW_RELEASE_BASE_RATE * (0.5)) * (difference - NEW_RELEASE_BORROW_DATE);

				} else if (difference < NEW_RELEASE_BORROW_DATE) {
					lateFee = 0.00;

				}
			}
			if (isNewRelease != true) {
				if (difference > WEEKLY_BORROW_DATE) {

					lateFee = (WEEKLY_BASE_RATE * (0.5)) * (difference - WEEKLY_BORROW_DATE);

				} else if (difference < WEEKLY_BORROW_DATE) {
					lateFee = 0.00;

				}
			}

		}
		super.returnItem(returnDate, lateFee);
		return lateFee;
	}

	// Method for getting all the details of a movie
	public String getDetails() {

		String getDetails = "";
		getDetails = super.getDetails();

		String movieType = "";
		String rentalPeriod = "";

		if (isNewRelease == true) {
			movieType = "New Release";
			rentalPeriod = "2 days";
		} else {
			movieType = "Weekly";
			rentalPeriod = "7 days";
		}
		getDetails = String.format("%s\n%-30s%s", getDetails, "On loan:", onALoan);

		getDetails = String.format("%s\n%-30s%s\n%-30s%s\n", getDetails, "Movie Type:", movieType, "Rental Period",
				rentalPeriod);
		getDetails = String.format("%s\n%-30s%s\n%-30s%s\n", getDetails, "", "BORROWING RECORD", "", borrowingRecord);

		return getDetails;

	}

	public String toString() {

		String toString = "";
		toString = super.toString();
		toString = toString + super.getFee() + ":";

		if (isNewRelease == true) {
			toString = toString + "NR:" + loanString;

		} else {
			toString = toString + "WK:" + loanString;

		}

		return toString;

	}

}
