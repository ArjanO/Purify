/**
 * Refactor example. 
 * 
 * This code is from Automated Method-Extraction Refactoring by Using Block-Based Slicing
 * Author: Katsuhisa Maruyama
 * 
 * The example well-established (Identification of extract method 
 * refactoring opportunities for the decomposition of methods. 
 * Author: Nikolaos Tsantalis and Alexander Charzigeorgiou). 
 * 
 * For example used in:
 * http://people.cs.aau.dk/~jeremy/SOE2011/resources/Refactoring-Example.pdf
 */
class Customer
{
	private Vector _rentals = new Vector();

	public String statement() {
		double totalAmount = 0;
		int renterPoints = 0;
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record\n";

		while (rentals.hasMoreElements()) {
			Rental each = (Rental)rentals.nextElement();
			double thisAmount = each.getCharge();

			if (each.getMovie().getPriceCode() == Movie.NEW_RELEASE)
				renterPoints = renterPoints + 2;
			else
				renterPoints++;

			result = result + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";

			totalAmount = totalAmount + thisAmount;
		}

		result = result + "Amount: " + String.valueOf(totalAmount) + "\n";
		result = result + "Points: " + String.valueOf(renterPoints);

		return result;
	}
}