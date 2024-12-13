package java;
// Define the State Representation:
// Represent the 8-puzzle state as a 3x3 matrix.
// Use 0 to represent the blank tile.

// Heuristic Function:
// Use Number of Misplaced Tiles as the heuristic function.
// Count the tiles not in their goal positions (excluding the blank tile).

// Cost Function:
// Define the cost f(n) = g(n) + h(n), where:
// g(n) is the cost to reach the current state from the initial state (path cost).
// h(n) is the heuristic value (number of misplaced tiles).

// Initialization:
// Start with the initial state of the puzzle.
// Compute f(n) for the initial state.
// Add the initial state to a priority queue (min-heap) based on f(n).

// Search Process:
// While the priority queue is not empty:
// Remove the state with the lowest f(n) value.
// If this state is the goal state, terminate and print the solution path.
// Generate all possible successor states by moving the blank tile (0) up, down, left, or right.
// For each successor:
// Compute g(n) and h(n).
// Compute f(n) = g(n) + h(n).
// Add the successor to the priority queue if not visited or if a better f(n) is found.

// Goal Check:
// If the current state matches the goal state, terminate.
// Output:
// Display the solution path from the initial state to the goal state.

import java.util.*;

public class AStar8Puzzle {

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

    // Perform A* Search
    static void aStarSearch(int[][] initialState) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Set<String> visited = new HashSet<>();

        // Add the initial state to the priority queue
        Node startNode = new Node(initialState, 0, calculateHeuristic(initialState));
        priorityQueue.add(startNode);

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();

            // Check if the current state is the goal
            if (isGoal(currentNode.state)) {
                System.out.println("Goal State Reached!");
                printState(currentNode.state);
                System.out.println("Path Cost: " + currentNode.g);
                return;
            }

            // Mark current state as visited
            visited.add(stateToString(currentNode.state));

            // Generate successors and add them to the priority queue
            for (int[][] successor : generateSuccessors(currentNode.state)) {
                if (!visited.contains(stateToString(successor))) {
                    int g = currentNode.g + 1;
                    int h = calculateHeuristic(successor);
                    int f = g + h;
                    priorityQueue.add(new Node(successor, g, f));
                }
            }
        }

        System.out.println("Solution not found.");
    }

    // Convert state to string for hashing
    static String stateToString(int[][] state) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : state) {
            for (int tile : row) {
                sb.append(tile);
            }
        }
        return sb.toString();
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

    // Node representation for A* Search
    static class Node {
        int[][] state;
        int g; // Cost to reach this node
        int f; // Total cost (g + h)

        Node(int[][] state, int g, int f) {
            this.state = state;
            this.g = g;
            this.f = f;
        }
    }

    public static void main(String[] args) {
        int[][] initialState = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };

        aStarSearch(initialState);
    }
}
