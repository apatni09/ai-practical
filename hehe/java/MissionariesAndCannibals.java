package java;
// Initialization:

// Start with the initial state where all missionaries and cannibals are on the left side of the river.
// Define the goal state, which is where all missionaries and cannibals are on the right side of the river.
// Use a queue to explore states in breadth-first order and a set to track visited states.
// State Representation:

// A state can be represented as a tuple of (missionaries_left, cannibals_left, boat_position), where:
// missionaries_left: Number of missionaries on the left side.
// cannibals_left: Number of cannibals on the left side.
// boat_position: A flag indicating whether the boat is on the left (1) or right (0) side of the river.
// State Transitions:

// From a given state, generate all possible valid states by moving combinations of missionaries and cannibals across the river:
// Move 2 missionaries.
// Move 2 cannibals.
// Move 1 missionary and 1 cannibal.
// Move 1 missionary.
// Move 1 cannibal.
// After each move, check if the new state is valid (i.e., the number of cannibals does not exceed the number of missionaries on either side of the river).
// Goal Check:

// The goal is to reach a state where all the missionaries and cannibals are on the right side of the river.
// BFS Search:

// Begin with the initial state and explore the possible states level by level.
// For each state, generate its valid neighbors (next possible states), and if they haven’t been visited before, add them to the queue.
// If the goal state is reached, print the sequence of moves.
// Termination:

// If the queue becomes empty and the goal state hasn’t been reached, print "No solution exists."

import java.util.*;

public class MissionariesAndCannibals {
    
    static class State {
        int leftM, leftC, rightM, rightC, boatPosition;

        State(int leftM, int leftC, int rightM, int rightC, int boatPosition) {
            this.leftM = leftM;
            this.leftC = leftC;
            this.rightM = rightM;
            this.rightC = rightC;
            this.boatPosition = boatPosition;
        }
    }

    // Check if the state is valid
    static boolean isValidState(int leftM, int leftC, int rightM, int rightC) {
        if (leftM < 0 || leftC < 0 || rightM < 0 || rightC < 0 || leftM > 3 || leftC > 3 || rightM > 3 || rightC > 3)
            return false;
        if ((leftM < leftC && leftM > 0) || (rightM < rightC && rightM > 0))
            return false;
        return true;
    }

    // Perform BFS to solve the problem
    static boolean bfs() {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.add(new State(3, 3, 0, 0, 1)); // Initial state: 3 missionaries and 3 cannibals on the left side
        visited.add("3,3,0,0,1");

        while (!queue.isEmpty()) {
            State state = queue.poll();
            
            // Check if the goal state is reached
            if (state.leftM == 0 && state.leftC == 0) {
                System.out.println("Missionaries and Cannibals problem solved!");
                return true;
            }
            
            // Possible transitions based on boat movements
            for (int m = 0; m <= 2; m++) {
                for (int c = 0; c <= 2; c++) {
                    if (m + c > 0 && m + c <= 2) {
                        int newLeftM = state.leftM - (state.boatPosition == 1 ? m : -m);
                        int newLeftC = state.leftC - (state.boatPosition == 1 ? c : -c);
                        int newRightM = state.rightM + (state.boatPosition == 1 ? m : -m);
                        int newRightC = state.rightC + (state.boatPosition == 1 ? c : -c);
                        
                        if (isValidState(newLeftM, newLeftC, newRightM, newRightC)) {
                            State newState = new State(newLeftM, newLeftC, newRightM, newRightC, state.boatPosition == 1 ? 0 : 1);
                            String stateStr = newLeftM + "," + newLeftC + "," + newRightM + "," + newRightC + "," + (state.boatPosition == 1 ? 0 : 1);
                            if (!visited.contains(stateStr)) {
                                queue.add(newState);
                                visited.add(stateStr);
                                
                                // Print the transition
                                if (state.boatPosition == 1) {
                                    System.out.println("Move " + m + " missionaries and " + c + " cannibals from Left to Right.");
                                } else {
                                    System.out.println("Move " + m + " missionaries and " + c + " cannibals from Right to Left.");
                                }
                            }
                        }
                    }
                }
            }
        }
        
        System.out.println("No solution found.");
        return false;
    }

    public static void main(String[] args) {
        bfs(); // Call BFS to solve the Missionaries and Cannibals problem
    }
}
