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

	private int iterations = 0;
	private Grid grid;
	private Stack<Square> stack1 = new Stack<>(), stack2 = new Stack<>();

	public Grid Solve(Grid grid) {
		this.grid = grid;

		this.grid.getSquareList().forEach((n) -> n.setPreSolved(!(n.getValue() == -1)));
		stack2 = this.organizeNonClues();
		this.exchangeSquares();

		return this.grid;
	}

	/**
	 * Contains main solving algorithm.
	 */
	private void exchangeSquares() {

		while (!stack2.isEmpty()) {
			for (Square square : stack2) {

				square.getCandidates().forEach(square::removeCandidate);

				int candidate = (square.getValue() == -1 ? 1 : square.getValue());

				for (int i = candidate; i <= 9; i++) {
					square.addCandidate(i);
				}

			}
			Square currentSquare = stack2.peek();
			boolean validSquare = false;

			while (!validSquare && !currentSquare.getCandidates().isEmpty()) {
				for (int candidate : currentSquare.getCandidates()) {
					for (Square bandSquare : this.grid.getBandList().get(currentSquare.getBand()).getSquareList()) {
						if (bandSquare.getValue() == candidate) {
							currentSquare.removeCandidate(candidate);
							validSquare = false;
							break;
						} else {
							for (Square stackSquare : this.grid.getStackList().get(currentSquare.getStack()).getSquareList()) {
								if (stackSquare.getValue() == candidate) {
									currentSquare.removeCandidate(candidate);
									validSquare = false;
									break;
								} else {
									for (Square regionSquare : this.grid.getRegionList().get(currentSquare.getRegion()).getSquareList()) {
										if (regionSquare.getValue() == candidate) {
											currentSquare.removeCandidate(candidate);
											validSquare = false;
											break;
										} else {
											validSquare = true;
										}
									}
								}
							}
						}
					}

					if (validSquare) {
						iterations++;
						stack2.peek().setValue(candidate);
						stack1.push(stack2.pop());
						break;
					}
				}
			}
			if (!validSquare) {
				this.Backtrack();
			}
		}
	}

	/**
	 * Recursive, performs stack changes in backtracking
	 */
	private void Backtrack() {
		
		iterations++;
		stack2.peek().setValue(-1);
		if (!stack1.isEmpty()) {
			stack2.push(stack1.pop());
		}
			if (stack2.peek().getValue() < 9) {
				stack2.peek().setValue(stack2.peek().getValue() + 1);
			} else {
				Backtrack();
		}
	}

	/**
	 * Returns a Stack of any squares in the grid's square list whose values were
	 * not provided in advance.
	 */
	private Stack<Square> organizeNonClues() {
		Stack<Square> stack2 = new Stack<>();

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
		return iterations;
	}
}