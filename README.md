# Sudoku-Solver

~~Current version from two months ago. Will review and refactor.~~

To do:
- ~~Add log4j2~~

- Verifier:
    - Add solveability feature
    - Deduce whether grid is logic solvable (count clues, see if l-1 values are present)
- Improve backtracking algorithm
- ~~Begin stochastic solving algorithm~~
    - ~~Early stage complete.~~
    - ~~Very Important: Adjust genetic algorithm's fitness function~~
    - First version of Genetic Algorithm completed.
        - Solves very (very) easy puzzles
        - Needs work for more difficult puzzles
            - Tournament system will be examined
            - Reproduction system will be examined
            - Mutations will be re-worked
                - It was discovered not all pre-solved integers are preserved in mutation
- Explore UI
