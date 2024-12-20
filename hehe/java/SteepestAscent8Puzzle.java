package java;
// Initialization:

// Start with the initial configuration of the puzzle.
// Define the goal configuration.
// Set the current state as the initial state.
// Generate Successor States:

// Generate all valid successor states by moving the blank tile up, down, left, or right.
// For each possible move, calculate the heuristic value based on the number of misplaced tiles.
// Evaluate Neighboring States:

// For each successor state, calculate the heuristic value (misplaced tiles).
// Select the state with the minimum heuristic value (fewest misplaced tiles).
// Move to Best Successor:

// Move to the successor with the lowest heuristic value.
// Repeat this process until the goal state is reached or no better states can be found.
// Termination:

// The algorithm terminates when the goal state is reached or no better state exists (i.e., the algorithm has reached a local optimum).

import java.util.*;

public class SteepestAscent8Puzzle {

    // Goal state for the puzzle
    static final int[][] GOAL = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };

    // Calculate the number of misplaced tiles as the heuristic
    static int calculateHeuristic(int[][] state) {
        int misplaced = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] != 0 && state[i][j] != GOAL[i][j]) {
                    misplaced++;
                }
            }
        }
        return misplaced;
    }

    // Check if the current state is the goal state
    static boolean isGoal(int[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] != GOAL[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Generate successors of the current state
    static List<int[][]> generateSuccessors(int[][] state) {
        List<int[][]> successors = new ArrayList<>();
        int blankRow = 0, blankCol = 0;

        // Find the blank tile (0)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
        }

        // Possible moves: up, down, left, right
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] move : moves) {
            int newRow = blankRow + move[0];
            int newCol = blankCol + move[1];

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int[][] newState = copyState(state);
                // Swap blank tile with the target tile
                newState[blankRow][blankCol] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;
                successors.add(newState);
            }
        }

        return successors;
    }

    // Deep copy a state
    static int[][] copyState(int[][] state) {
        int[][] copy = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(state[i], 0, copy[i], 0, 3);
        }
        return copy;
    }

    // Perform Steepest Ascent Heuristic Search
    static void steepestAscent(int[][] initialState) {
        int[][] currentState = initialState;
        int currentHeuristic = calculateHeuristic(currentState);

        System.out.println("Initial State:");
        printState(currentState);

        while (!isGoal(currentState)) {
            List<int[][]> successors = generateSuccessors(currentState);
            int[][] bestSuccessor = null;
            int bestHeuristic = Integer.MAX_VALUE;

            // Find the best successor
            for (int[][] successor : successors) {
                int heuristic = calculateHeuristic(successor);
                if (heuristic < bestHeuristic) {
                    bestHeuristic = heuristic;
                    bestSuccessor = successor;
                }
            }

            // If no improvement, terminate
            if (bestHeuristic >= currentHeuristic) {
                System.out.println("Reached local maximum or plateau. Search terminated.");
                return;
            }

            // Move to the best successor
            currentState = bestSuccessor;
            currentHeuristic = bestHeuristic;

            System.out.println("Next State (Heuristic = " + currentHeuristic + "):");
            printState(currentState);
        }

        System.out.println("Goal State Reached!");
    }

    // Print the state
    static void printState(int[][] state) {
        for (int[] row : state) {
            for (int tile : row) {
                System.out.print(tile + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] initialState = {
            {1, 2, 3},
            {4, 0, 5},
            {6, 7, 8}
        };

        steepestAscent(initialState);
    }
}
