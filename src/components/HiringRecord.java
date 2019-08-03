/*

Class:			Hiring Record
Description:	The class represents a single hiring record for any type of item that can be hired.

Author:			Milindi Kodikara - s3667779
 */

package s3667779_A2;

public class HiringRecord {
	// Declare instant variables
	private String id;
	private double rentalFee;
	private double lateFee;
	private DateTime borrowDate;
	private DateTime returnDate;
	private String memberId;

	public HiringRecord(String id, String memberId, double rentalFee, DateTime borrowDate) {

		// Id is the concatenation of the id , member id and the borrow date
		this.id = id + "_" + memberId + "_" + borrowDate.getEightDigitDate() ;
		this.memberId = memberId;
		this.rentalFee = rentalFee;
		this.borrowDate = borrowDate;

	}

	public DateTime getBorrowDate() {
		return borrowDate;
	}

	// Updates the hire record when movie is returned
	public double returnItem(DateTime returnDate, double lateFee) throws BorrowException {

		this.returnDate = returnDate;
		this.lateFee = lateFee;
		// Validate if lateFee is less that zero
		if (lateFee < 0) {

			throw new BorrowException("Error: Late Fee cannot be less than zero");
		}
		return lateFee;
	}

	public DateTime getReturnDate() {

		return returnDate;
	}

	// Method for getting all the details of a Hiring Record
	public String getDetails() {

		String getDetails = null;

		getDetails = String.format("%-30s%s\n%-30s%s", "Borrow Id:", id, "Borrow Date:", borrowDate.getFormattedDate());
		// Borrowing record for a hire that has not been returned
		if (this.returnDate == null) {
			return getDetails;
			// Borrowing record for a hire that has been returned
		} else {

			getDetails = String.format("%s\n%-30s%s\n%-30s%s%.2f\n%-30s%s%.2f\n%-30s%s%.2f", getDetails, "Return Date:",
					returnDate.getFormattedDate(), "Fee:", "$", this.rentalFee, "Late Fee:", "$", this.lateFee,
					"Total Fees:", "$", (lateFee + rentalFee));

			return getDetails;
		}

	}

	public String toString() {

		String toString = "";
		// Borrowing record for an item currently on hire
		if (this.returnDate == null) {
			toString = id + ":" + borrowDate.getEightDigitDate() + ":" + "none" + ":" + "none" + ":" + "none";
			// Borrowing record for an item which has been returned
		} else if (this.returnDate != null) {
			toString = id + ":" + borrowDate.getEightDigitDate() + ":" + returnDate.getEightDigitDate() + ":"
					+ rentalFee + ":" + lateFee;
		}
		return toString;

	}

}
