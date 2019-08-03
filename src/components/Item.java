/*

Class:			Movie 
Description:	The class represents all the common attributes of both movie and game classes.
Author:			Milindi Kodikara - s3667779
 */

package s3667779_A2;

public abstract class Item {
	// Declare instance variables common to both movie and game classes
	private String id;
	private String title;
	private String description;
	private String genre;
	private double fee;
	private HiringRecord currentlyBorrowed;

	private HiringRecord[] hireHistory = new HiringRecord[10];

	protected int borrowingPeriod = 0;
	protected String borrowingRecord = "";
	protected String onALoan = "";
	protected String loanString = "";

	private final int MAX_HIRING_RECORD_LENGTH = 10;

	public Item(String id, String title, String genre, String description, double fee) throws IdException {
		// Validate the Id entered by the user
		if (id.length() == 5 && (id.charAt(0) == 'M' || id.charAt(0) == 'G') && id.charAt(1) == '_') {
			this.id = id;
		} else {

			throw new IdException("Error:Invalid Id:" + id);
		}

		this.title = title;
		this.genre = genre;
		this.description = description;
		this.fee = fee;

	}

	public String getId() {
		return this.id;

	}

	public double getFee() {

		return this.fee;
	}

	public HiringRecord getCurrentlyBorrowed() {

		return currentlyBorrowed;

	}

	public HiringRecord[] getHireHistory() {

		return hireHistory;

	}

	public int getHireHistoryLength() {

		return MAX_HIRING_RECORD_LENGTH;

	}

	/*
	 * ALGORITHM 
	 * 			BEGIN 
	 * 				IF currentlyBorrowed is not null 
	 * 					THROW new BorrowException
	 * 				CREATE a new Hire Record 
	 * 				UPDATE the historical hiring records by calling the
	 * 				method updateHiringRecord 
	 * 				RETURN fee 
	 * 			END
	 * 
	 */

	public double borrow(String memberId, int advDays) throws BorrowException {

		if (currentlyBorrowed != null) {
			throw new BorrowException("Error:The item with id " + id + " is currently on a loan.");

		}

		currentlyBorrowed = new HiringRecord(id, memberId, fee, new DateTime(advDays));
		updateHiringRecord();
		return fee;
	}

	/*
	 * Update the hire history for each item using a for loop 
	 * iterating over the existing array and deleting the oldest hire record 
	 * and inserting the newest record and maintaining them in order of most recent
	 */
	public void updateHiringRecord() {

		for (int i = 0; i < MAX_HIRING_RECORD_LENGTH - 1; i++) {
			hireHistory[i] = hireHistory[i + 1];
		}
		hireHistory[MAX_HIRING_RECORD_LENGTH - 1] = currentlyBorrowed;

	}

	public abstract String getActualReturnDate();

	public abstract double returnItem(DateTime returnDate) throws BorrowException;

	protected double returnItem(DateTime returnDate, double lateFee) throws BorrowException {

		if (currentlyBorrowed == null) {
			throw new BorrowException("Error:The item with id " + id + " is NOT on a loan.");
		}
		// Update the currently borrowed attribute and the historic hiring records
		currentlyBorrowed.returnItem(returnDate, lateFee);
		currentlyBorrowed = null;
		return lateFee;
	}

	// Calculate the difference between the return date and the borrow date
	protected int getDiffDays(DateTime returnDate) throws BorrowException {

		if (currentlyBorrowed == null) {
			throw new BorrowException("Error:The item with id " + id + " is NOT on a loan.");
		}

		int diff = DateTime.diffDays(returnDate, currentlyBorrowed.getBorrowDate());
		// Validate diff
		if (diff < 0) {
			throw new BorrowException("Difference between the days cannot be less than zero");
		}

		return diff;
	}

	public DateTime returnDate(DateTime returnDate) {

		currentlyBorrowed.getReturnDate();
		return returnDate;
	}

	public String getDetails() {

		String getDetails;
		getDetails = "";
		String temp2 = "";

		if (currentlyBorrowed == null) {
			onALoan = "NO";
		} else if (currentlyBorrowed != null) {
			onALoan = "YES";
		}

		if (getHireHistory()[getHireHistory().length - 1] == null) {
			borrowingRecord = "NONE";

			// Get all the details of previous hiring records of the item
		} else {

			for (int i = getHireHistory().length - 1; i >= 0; i--) {
				if (getHireHistory()[i] != null) {
					String temp = getHireHistory()[i].getDetails();

					temp2 += String.format("%s\n%s", getDetails,
							temp + " \n -------------------------------------------------------\n");
				}

			}
			borrowingRecord = " \n -------------------------------------------------------\n" + temp2;
		}

		getDetails = String.format("%s\n%-30s%s", getDetails, "ID:", this.id);
		getDetails = String.format("%s\n%-30s%s", getDetails, "Title:", this.title);
		getDetails = String.format("%s\n%-30s%s", getDetails, "Genre:", this.genre);
		getDetails = String.format("%s\n%-30s%s", getDetails, "Description:", this.description);
		getDetails = String.format("%s\n%-30s%s%.2f", getDetails, "Standard Fee:", "$ ", this.getFee());

		return getDetails;

	}

	public String toString() {

		String toString = "";

		if (currentlyBorrowed == null) {
			loanString = "N";
		} else if (currentlyBorrowed != null) {
			loanString = "Y";
		}

		toString = id + ":" + title + ":" + description + ":" + genre + ":";

		return toString;

	}

}
