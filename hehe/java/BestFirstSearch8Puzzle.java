package java;
// Define the State Representation:
// Represent the 8-puzzle state as a 3x3 matrix.
// Use 0 to represent the empty tile.

// Heuristic Function:
// Use Number of Misplaced Tiles as the heuristic function.
// Count the tiles that are not in their goal positions (excluding the blank tile).

// Initialization:
// Start with the initial state of the puzzle.
// Compute the heuristic value for the initial state.
// Add the initial state to a priority queue (min-heap) based on its heuristic value.

// Search Process:
// While the priority queue is not empty:
// Remove the state with the lowest heuristic value (best state).
// If this state is the goal state, terminate and print the solution.
// Generate all possible successor states by moving the blank tile (0) up, down, left, or right.

// For each successor:
// Compute its heuristic value.
// Add it to the priority queue.

// Goal Check:
// If the current state matches the goal state, terminate and print the solution.

// Output:
// Display the path from the initial state to the goal state.

import java.util.*;

public class BestFirstSearch8Puzzle {

    // Goal state for the puzzle
    static final int[][] GOAL = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };

    // Calculate the number of misplaced tiles (heuristic)
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

    // Check if two states are the same
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

    // Perform Best First Search
    static void bestFirstSearch(int[][] initialState) {
        PriorityQueue<int[][]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(BestFirstSearch8Puzzle::calculateHeuristic));
        Map<int[][], Integer> visited = new HashMap<>();
        
        // Add the initial state to the priority queue
        priorityQueue.add(initialState);
        visited.put(initialState, calculateHeuristic(initialState));

        System.out.println("Initial State:");
        printState(initialState);

        while (!priorityQueue.isEmpty()) {
            int[][] currentState = priorityQueue.poll();

            // Check if the current state is the goal
            if (isGoal(currentState)) {
                System.out.println("Goal State Reached!");
                printState(currentState);
                return;
            }

            // Generate successors and add them to the priority queue
            for (int[][] successor : generateSuccessors(currentState)) {
                int heuristic = calculateHeuristic(successor);
                if (!visited.containsKey(successor) || visited.get(successor) > heuristic) {
                    priorityQueue.add(successor);
                    visited.put(successor, heuristic);
                }
            }

            System.out.println("Next State (Heuristic = " + calculateHeuristic(currentState) + "):");
            printState(currentState);
        }

        System.out.println("Solution not found.");
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
            {7, 8, 6}
        };

        bestFirstSearch(initialState);
    }
}

