package java;
// Initialization:

// Start with the initial state (0, 0) where both jugs are empty.
// Set the goal state (x, y) where x is the target amount in Jug1 and y is the target amount in Jug2.
// Push the initial state onto the stack (this represents the DFS stack).
// DFS Search:

// Pop the current state from the stack and check if it matches the goal state. If it does, print the solution and exit.
// If the current state is not the goal, generate all possible states (transitions) from the current state:
// Fill Jug1: Fill Jug1 to its full capacity.
// Fill Jug2: Fill Jug2 to its full capacity.
// Empty Jug1: Empty Jug1 completely.
// Empty Jug2: Empty Jug2 completely.
// Transfer from Jug1 to Jug2: Pour water from Jug1 into Jug2 until either Jug1 is empty or Jug2 is full.
// Transfer from Jug2 to Jug1: Pour water from Jug2 into Jug1 until either Jug2 is empty or Jug1 is full.
// For each generated state, check if it is valid and not visited before.
// Push each valid and unvisited state onto the stack.
// Repeat:

// Repeat the process of popping the stack, checking for the goal state, and generating new states until the goal is found or the stack becomes empty.
// Termination:

// If the stack is empty and the goal state has not been found, print "No solution exists."

import java.util.*;

public class WaterJugDFS {

    // Perform Depth First Search (DFS) for the Water Jug Problem
    private static void dfs(int jug1Capacity, int jug2Capacity, int target, int x, int y, Set<String> visited, List<String> path) {
        // If the target is achieved, print the path and exit
        if (x == target || y == target) {
            path.add("(" + x + ", " + y + ")");
            System.out.println("Solution Path: " + path);
            System.exit(0);
        }

        // Mark the current state as visited
        String state = x + "," + y;
        if (visited.contains(state)) return;

        visited.add(state);
        path.add("(" + x + ", " + y + ")");

        // Generate all possible moves
        List<int[]> moves = Arrays.asList(
            new int[]{jug1Capacity, y}, // Fill Jug 1
            new int[]{x, jug2Capacity}, // Fill Jug 2
            new int[]{0, y},            // Empty Jug 1
            new int[]{x, 0},            // Empty Jug 2
            new int[]{Math.max(0, x - (jug2Capacity - y)), Math.min(jug2Capacity, y + x)}, // Pour Jug 1 -> Jug 2
            new int[]{Math.min(jug1Capacity, x + y), Math.max(0, y - (jug1Capacity - x))}  // Pour Jug 2 -> Jug 1
        );

        // Explore each move recursively
        for (int[] move : moves) {
            dfs(jug1Capacity, jug2Capacity, target, move[0], move[1], visited, path);
        }

        // Backtrack
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Input capacities and target
            System.out.println("Enter capacity of Jug 1: ");
            int jug1Capacity = scanner.nextInt();
            System.out.println("Enter capacity of Jug 2: ");
            int jug2Capacity = scanner.nextInt();
            System.out.println("Enter target amount of water: ");
            int target = scanner.nextInt();

            // Initialize variables
            int x = 0, y = 0; // Initial state
            Set<String> visited = new HashSet<>(); // Track visited states
            List<String> path = new ArrayList<>(); // Store the path

            // Start DFS
            dfs(jug1Capacity, jug2Capacity, target, x, y, visited, path);
        }

        // If no solution is found
        System.out.println("No solution found.");
    }
}
