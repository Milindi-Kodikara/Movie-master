/*

Class:			MovieMaster
Description:	The class which is used as the main method to run the program
Author:			Milindi Kodikara - s3667779
 */

package main;

import menu.MovieMaster;

public class Driver {
	public static void main(String[] args) {

		MovieMaster movie = new MovieMaster();
		movie.readItems();
		movie.menu();

	}

}
