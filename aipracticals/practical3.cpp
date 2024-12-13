#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

// Function to print the puzzle state
void printPuzzle(const vector<vector<int>>& puzzle) {
    for (const auto& row : puzzle) {
        for (int val : row) {
            cout << val << " ";
        }
        cout << endl;
    }
    cout << endl;
}

// Function to calculate the misplaced tile heuristic
int calculateHeuristic(const vector<vector<int>>& puzzle, const vector<vector<int>>& goal) {
    int misplaced = 0;
    for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 3; ++j) {
            if (puzzle[i][j] != 0 && puzzle[i][j] != goal[i][j]) {
                misplaced++;
            }
        }
    }
    return misplaced;
}

// Function to get all possible moves from the current puzzle state
vector<vector<vector<int>>> getPossibleMoves(const vector<vector<int>>& puzzle) {
    vector<vector<vector<int>>> moves;
    int x = 0, y = 0;

    // Find the position of the empty space (0)
    for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 3; ++j) {
            if (puzzle[i][j] == 0) {
                x = i;
                y = j;
                break;
            }
        }
    }

    // Directions for up, down, left, right moves
    vector<pair<int, int>> directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // Try moving the empty space in each of the four directions
    for (const auto& dir : directions) {
        int newX = x + dir.first;
        int newY = y + dir.second;

        // Check if the new position is within bounds
        if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
            vector<vector<int>> newPuzzle = puzzle;
            swap(newPuzzle[x][y], newPuzzle[newX][newY]);
            moves.push_back(newPuzzle);
        }
    }

    return moves;
}

// Function to perform the Steepest Ascent Hill Climbing
vector<vector<int>> steepestAscent(const vector<vector<int>>& initialState, const vector<vector<int>>& goalState) {
    vector<vector<int>> current = initialState;
    int currentHeuristic = calculateHeuristic(current, goalState);

    while (true) {
        printPuzzle(current);

        // Get all possible moves from the current puzzle state
        vector<vector<vector<int>>> neighbors = getPossibleMoves(current);

        vector<vector<int>> bestNeighbor = current;
        int bestHeuristic = currentHeuristic;

        // Check each neighbor's heuristic and pick the best one (lowest heuristic)
        for (const auto& neighbor : neighbors) {
            int heuristic = calculateHeuristic(neighbor, goalState);
            if (heuristic < bestHeuristic) {
                bestHeuristic = heuristic;
                bestNeighbor = neighbor;
            }
        }

        // If no better neighbor is found, stop the search (local maximum)
        if (bestHeuristic >= currentHeuristic) {
            cout << "Reached local maximum or no better neighbor found." << endl;
            break;
        }

        // Move to the best neighbor
        current = bestNeighbor;
        currentHeuristic = bestHeuristic;

        // If the goal state is reached, break out of the loop
        if (currentHeuristic == 0) {
            cout << "Goal state reached!" << endl;
            break;
        }
    }

    return current;
}

int main() {
    // Initial puzzle state
    vector<vector<int>> initialState = {
        {1, 2, 3},
        {5, 6, 0},
        {7, 8, 4}
    };

    // Goal puzzle state
    vector<vector<int>> goalState = {
        {1, 2, 3},
        {0, 8, 6},
        {5, 7, 4}
    };

    // Solve the puzzle using Steepest Ascent Hill Climbing
    vector<vector<int>> solution = steepestAscent(initialState, goalState);

    // Print the final solution (goal state)
    cout << "Goal State is:" << endl;
    printPuzzle(solution);

    return 0;
}
