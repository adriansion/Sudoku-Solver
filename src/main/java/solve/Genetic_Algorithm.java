package solve;

import main.Log;
import structure.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * First attempt at a solving algorithm using a stochastic technique.
 * This algorithm is genetic.
 *
 * @author Adrian
 */
public class Genetic_Algorithm {

    private Grid unsolved, solution;
    private Map<Grid, Integer> reproductionPool = new HashMap<Grid, Integer>();
    Random random = new Random();

    public Grid solve(Grid grid) {
        this.unsolved = grid;

        this.produceFirstGeneration(50);


        reproductionPool.forEach((n, m) -> {
            reproductionPool.put(n, this.determineFitness(n));
        });
        Log.logger.info("Production of first generation complete.");


        return this.unsolved;
    }

    /**
     * This method will create a specified number of theoretically possible solutions to the puzzle.
     * Each solution is an individual and each non-presolved square is a gene.
     * Each solution's genes will be randomly decided, according to the following two restrictions,
     * in their inception:
     * 1. Non-presolved squares shall be populated with integers between 1 and 9.
     * 2. Non-presolved squares shall not be populated with integers that conflict with the integers
     * in the presolved squares' respective rows, columns or regions.
     */
    private void produceFirstGeneration(int individuals) {
        for (int i = 0; i < individuals; i++) {
            Grid newGrid = new Grid();

            for (Square square : this.unsolved.getSquareList()) {
                if (square.getValue() != -1) {
                    newGrid.getSquareList().get(this.unsolved.getSquareList().indexOf(square)).setValue(square.getValue());
                    newGrid.getSquareList().get(this.unsolved.getSquareList().indexOf(square)).setPreSolved(true);
                }
            }

            for (Square square : newGrid.getSquareList()) {
                if (!square.isPreSolved()) {

                    ArrayList<Integer> preSolvedSquares = new ArrayList<>();

                    for (Square square1 : newGrid.getBandList().get(square.getBand()).getSquareList()) {
                        if (square1.isPreSolved()) {
                            preSolvedSquares.add(square1.getValue());
                        }
                    }

                    for (Square square1 : newGrid.getStackList().get(square.getStack()).getSquareList()) {
                        if (square1.isPreSolved()) {
                            preSolvedSquares.add(square1.getValue());
                        }
                    }

                    for (Square square1 : newGrid.getRegionList().get(square.getRegion()).getSquareList()) {
                        if (square1.isPreSolved()) {
                            preSolvedSquares.add(square1.getValue());
                        }
                    }


                    int newGene = random.nextInt(9) + 1;
                    while (preSolvedSquares.contains(newGene)) {
                        newGene = random.nextInt(9) + 1;
                    }

                    square.setValue(newGene);
                }
            }

            newGrid.setGeneration(1);
            reproductionPool.put(newGrid, 101);


        }
    }

    /**
     * Assigns a numerical fitness score to each grid in the reproduction pool.
     * - two points added per duplicated value per row, column and region
     *
     * @param grid
     * @return
     */
    private int determineFitness(Grid grid) {
        int score = 0;
        ArrayList<HashMap<Integer, Integer>> bandFitness = new ArrayList<>();
        ArrayList<HashMap<Integer, Integer>> stackFitness = new ArrayList<>();
        ArrayList<HashMap<Integer, Integer>> regionFitness = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            bandFitness.add(new HashMap<>());
            stackFitness.add(new HashMap<>());
            regionFitness.add(new HashMap<>());

            for (int j = 1; j <= 9; j++) {
                bandFitness.get(bandFitness.size() - 1).put(j, -1);
            }
            for (int j = 1; j <= 9; j++) {
                stackFitness.get(stackFitness.size() - 1).put(j, -1);
            }
            for (int j = 1; j <= 9; j++) {
                regionFitness.get(regionFitness.size() - 1).put(j, -1);
            }
        }

        for (int i = 0; i < grid.getBandList().size(); i++) {
            for (Square square : grid.getBandList().get(i).getSquareList()) {
                bandFitness.get(i).put(square.getValue(), bandFitness.get(i).get(square.getValue()) + 1);
            }
        }
        for (int i = 0; i < grid.getStackList().size(); i++) {
            for (Square square : grid.getStackList().get(i).getSquareList()) {
                stackFitness.get(i).put(square.getValue(), stackFitness.get(i).get(square.getValue()) + 1);
            }
        }
        for (int i = 0; i < grid.getRegionList().size(); i++) {
            for (Square square : grid.getRegionList().get(i).getSquareList()) {
                regionFitness.get(i).put(square.getValue(), regionFitness.get(i).get(square.getValue()) + 1);
            }
        }

//        for (HashMap<Integer, Integer> h : bandFitness) {
//            System.out.println(h.toString());
//        }

        for (int i = 0; i < 9; i++) {
            for (int j = 1; j <= 9; j++) {
                int multiplier = bandFitness.get(i).get(j);
                if (multiplier == -1) {
                    score += 2;
                } else {
                    score += multiplier * 2;
                }
                multiplier = stackFitness.get(i).get(j);
                if (multiplier == -1) {
                    score += 2;
                } else {
                    score += multiplier * 2;
                }
                multiplier = regionFitness.get(i).get(j);
                if (multiplier == -1) {
                    score += 2;
                } else {
                    score += multiplier * 2;
                }
            }
        }

        score /= 4;
//        System.out.println("Score :" + score);
        return score;

        // This is for testing purposes only, but could be worked into a fitness function!
//        for (Grid g : reproductionPool) {
//            int[] counts = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
//            for (Square square : g.getSquareList()) {
//                counts[square.getValue() - 1]++;
//            }
//            for (int k = 0; k < 9; k++){
//                System.out.println(counts[k]);
//
//            }
//            System.out.println();
//        }
    }
}
