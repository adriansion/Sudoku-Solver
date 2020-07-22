package main;

import solve.Backtracker;
import structure.Grid;

/**
 * Main
 * @author Adrian
 *
 */
public class Main {

	public static void main(String[] args) {
		Log.logger.info("Starting application.");
		GridFileReader fileReader = new GridFileReader();
		Verifier verifier = new Verifier();

		// Brute-force solving algorithm
		Backtracker backtracker = new Backtracker();


		Log.logger.info("Reading and verifying grid.");
		Grid unsolved = fileReader.createGrid("grid6");
		verifier.verify(unsolved);

		Log.logger.info("Solving grid.");
		Grid solved = backtracker.Solve(unsolved);
		verifier.verify(solved);

		Log.logger.info("Grid solved.");
//		System.out.println("Iterations: " + backtracker.getIterations());
	}
}