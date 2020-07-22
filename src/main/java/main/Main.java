package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import solve.Backtracker;
import structure.Grid;

/**
 * Main
 * @author Adrian
 *
 */
public class Main {

	private static final Logger logger = LogManager.getLogger("Main");

	public static void main(String[] args) {
		logger.info("Starting application.");
		GridFileReader fileReader = new GridFileReader();
		Verifier verifier = new Verifier();

		// Brute-force solving algorithm
		Backtracker backtracker = new Backtracker();


		logger.info("Reading and verifying grid.");
		Grid unsolved = fileReader.createGrid("grid6");
		verifier.verify(unsolved);

		logger.info("Solving grid.");
		Grid solved = backtracker.Solve(unsolved);
		verifier.verify(solved);

		logger.info("Grid solved.");
//		System.out.println("Iterations: " + backtracker.getIterations());
	}
}