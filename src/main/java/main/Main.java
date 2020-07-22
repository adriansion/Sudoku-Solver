package main;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

import structure.Grid;
import solve.Backtracker;

/**
 * Solver notes: human method: use candidate system
 * @author Adrian
 *
 */
public class Main {

//	@SuppressWarnings("unused")
	public static void main(String[] args) {
		GridFileReader fileReader = new GridFileReader();
		Verifier verifier = new Verifier();
		
		// Solvers
		Backtracker backtracker = new Backtracker();

		Grid grid = fileReader.createGrid("grid1");
		Grid unsolved = fileReader.createGrid("grid6");

		verifier.verify(grid);
		verifier.verify(unsolved);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SS");
		LocalDateTime start = LocalDateTime.now();
		
		Grid solved = backtracker.Solve(unsolved);
		
		
		LocalDateTime finish = LocalDateTime.now();
		
		verifier.verify(solved);
		
//		System.out.println("Iterations: " + backtracker.getIterations());
		System.out.println("Start time: " + formatter.format(start));
		System.out.println("Finish time: " + formatter.format(finish));
	}
}