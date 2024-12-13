package java;
// Input: Current board state and the player's turn.
// Base Case:
// If the game is over (win or draw), return the score.
// Recursively Calculate:
// For each possible move:
// Apply the move.
// Call Minimax on the resulting state for the opponent.
// Undo the move.
// Return the best score for the player (maximize or minimize, depending on the player).
// Choose the Move:
// For the maximizing player, select the move with the maximum score.
// For the minimizing player, select the move with the minimum score.

import java.util.Scanner;

public class TicTacToePlay {

    static final int PLAYER_X = 1; // Maximizing player (AI)
    static final int PLAYER_O = -1; // Minimizing player (Human)
    static final int EMPTY = 0;

    // Function to check for a winner
    static int checkWinner(int[][] board) {
        for (int i = 0; i < 3; i++) {
            // Check rows and columns
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != EMPTY)
                return board[i][0];
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != EMPTY)
                return board[0][i];
        }
        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != EMPTY)
            return board[0][0];
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != EMPTY)
            return board[0][2];

        return 0; // No winner yet
    }

    // Check if the board is full
    static boolean isFull(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == EMPTY)
                    return false;
            }
        }
        return true;
    }

    // Minimax function
    static int minimax(int[][] board, boolean isMaximizing) {
        int winner = checkWinner(board);
        if (winner == PLAYER_X) return 10; // AI wins
        if (winner == PLAYER_O) return -10; // Human wins
        if (isFull(board)) return 0; // Draw

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER_X;
                        int score = minimax(board, false);
                        board[i][j] = EMPTY;
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER_O;
                        int score = minimax(board, true);
                        board[i][j] = EMPTY;
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
            return bestScore;
        }
    }

    // Find the best move for the AI
    static int[] findBestMove(int[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_X;
                    int score = minimax(board, false);
                    board[i][j] = EMPTY;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        return bestMove;
    }

    // Print the board
    static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == PLAYER_X) System.out.print("X ");
                else if (cell == PLAYER_O) System.out.print("O ");
                else System.out.print("- ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] board = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
        };

        Scanner scanner = new Scanner(System.in);
        System.out.println("You are 'O'. AI is 'X'.");
        boolean humanTurn = true;

        while (true) {
            printBoard(board);

            if (checkWinner(board) == PLAYER_X) {
                System.out.println("AI wins!");
                break;
            } else if (checkWinner(board) == PLAYER_O) {
                System.out.println("You win!");
                break;
            } else if (isFull(board)) {
                System.out.println("It's a draw!");
                break;
            }

            if (humanTurn) {
                System.out.println("Your turn! Enter your move (row and column): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != EMPTY) {
                    System.out.println("Invalid move. Try again.");
                } else {
                    board[row][col] = PLAYER_O;
                    humanTurn = false;
                }
            } else {
                System.out.println("AI's turn...");
                int[] bestMove = findBestMove(board);
                board[bestMove[0]][bestMove[1]] = PLAYER_X;
                humanTurn = true;
            }
        }

        scanner.close();
    }
}

