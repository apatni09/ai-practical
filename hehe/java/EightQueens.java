package java;
// Start with the first row (row = 0).
// For each column in the current row:
// Check if placing a queen in that column is valid (no queens in the same column, row, or diagonals).
// If valid, place the queen and move to the next row recursively.
// If placing the queen does not lead to a solution, backtrack and remove the queen.
// Repeat the process until either all 8 queens are placed, or all possibilities are exhausted.
// If a solution is found, print the board.

public class EightQueens {

    static final int N = 8; // Chessboard size (8x8)

    // Function to print the board
    static void printBoard(int[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print((board[i][j] == 1 ? "Q " : "- "));
            }
            System.out.println();
        }
    }

    // Function to check if placing a queen at (row, col) is safe
    static boolean isSafe(int[][] board, int row, int col) {
        // Check the same column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }

        // Check upper left diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }

        // Check upper right diagonal
        for (int i = row, j = col; i >= 0 && j < N; i--, j++) {
            if (board[i][j] == 1) return false;
        }

        return true;
    }

    // Recursive function to solve the problem
    static boolean solveNQueens(int[][] board, int row) {
        if (row == N) { // Base case: All queens are placed
            printBoard(board);
            return true; // Return true to indicate a solution is found
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 1; // Place queen

                // Recursively solve for the next row
                if (solveNQueens(board, row + 1)) {
                    return true; // Stop once a solution is found
                }

                // Backtrack: Remove the queen
                board[row][col] = 0;
            }
        }

        return false; // No solution for the current configuration
    }

    public static void main(String[] args) {
        int[][] board = new int[N][N];

        if (!solveNQueens(board, 0)) {
            System.out.println("No solution exists.");
        }
    }
}
