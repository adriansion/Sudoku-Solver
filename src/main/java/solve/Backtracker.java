package solve;

import java.util.Stack;

import structure.Grid;
import structure.Square;

/**
 * One solving algorithm; uses backtracking to solved puzzle.
 * 
 * Backtracking:
 * 
 * Start at the first square in the grid that is unsolved. Set its value to 1.
 * If that value does not violate grid verification, move to the next square and
 * repeat; otherwise, increase its value by 1 and try again, until a value up to
 * 9 is valid. If no values between 1 and 9 are valid, move to the last square
 * that was unsolved at the beginning, and increase its value by 1, repeating
 * the process until valid values are found for each square, and until the
 * entire grid is solved.
 * 
 * Algorithm:
 * 
 * Determine whether or not each square in grid is preSolved; save them in
 * ArrayList. Fill stack2 with these squares in reverse order. While stack2 is
 * not empty, exchange squares to stack1. When exchanging, verify that square
 * will be valid. If valid, complete exchange; if invalid, backtrack.
 * 
 * @author Adrian
 *
 */
public class Backtracker {

	private int counter = 0;
	private Grid grid;
	private Stack<Square> stack1 = new Stack<Square>(), stack2 = new Stack<Square>();

	public Backtracker() {

	}

	public Grid Solve(Grid grid) {
		this.grid = grid;
		this.grid.getSquareList().forEach((n) -> n.setPreSolved(!(n.getValue() == -1)));

		stack2 = this.organizeNonClues();

		this.exchangeSquares();

		return this.grid;
	}

	private void exchangeSquares() {

		while (!stack2.isEmpty()) {
			for (Square square : stack2) {

				square.getCandidates().forEach((n) -> square.removeCandidate(n));

				int candidate = (square.getValue() == -1 ? 1 : square.getValue());

				for (int i = candidate; i <= 9; i++) {
					square.addCandidate(i);
				}

			}
			Square c = stack2.peek();
			boolean v = false;

			while (!v && !c.getCandidates().isEmpty()) {
				for (int candidate : c.getCandidates()) {
					for (Square b : this.grid.getBandList().get(c.getBand()).getSquareList()) {
						if (b.getValue() == candidate) {
							c.removeCandidate(candidate);
							v = false;
							break;
						} else {
							for (Square s : this.grid.getStackList().get(c.getStack()).getSquareList()) {
								if (s.getValue() == candidate) {
									c.removeCandidate(candidate);
									v = false;
									break;
								} else {
									for (Square r : this.grid.getRegionList().get(c.getRegion()).getSquareList()) {
										if (r.getValue() == candidate) {
											c.removeCandidate(candidate);
											v = false;
											break;
										} else {
											v = true;
										}
									}
								}
							}
						}
					}

					if (v) {
						counter++;
						stack2.peek().setValue(candidate);
						stack1.push(stack2.pop());
						break;
					}
				}
			}
			if (!v) {
				this.Backtrack();
			}
		}
	}

	/**
	 * Recursive, performs stack changes in backtracking
	 */
	private void Backtrack() {
		
		counter++;
		stack2.peek().setValue(-1);
		if (!stack1.isEmpty()) {
			stack2.push(stack1.pop());
		}
		if (stack2.peek().getValue() == -1) { // This if statement might be redundant
			stack2.peek().setValue(1);
		} else {
			if (stack2.peek().getValue() < 9) {
				stack2.peek().setValue(stack2.peek().getValue() + 1);
			} else {
				stack2.peek().setValue(-1);
				Backtrack();
			}
		}
	}

	/**
	 * Returns a Stack of any squares in the grid's square list whose values were
	 * not provided in advance.
	 */
	private Stack<Square> organizeNonClues() {
		Stack<Square> stack2 = new Stack<Square>();

		for (int i = this.grid.getSquareList().size() - 1; i >= 0; i--) {
			if (!this.grid.getSquareList().get(i).isPreSolved()) {
				stack2.push(this.grid.getSquareList().get(i));
			}
		}

//		for (Square square : this.grid.getSquareList()) {
//			if (!square.isPreSolved()) {
//				stack2.push(square);
//			}
//		}

		return stack2;
	}

	public int getIterations() {
		return counter;
	}
}