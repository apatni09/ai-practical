# Initial Setup: Create an empty 8x8 chessboard represented by a 2D array, where each element is initially set to 0 (indicating an empty spot).

# Backtracking:

# Start with placing a queen in the first column of the first row.
# For each subsequent row, try placing a queen in every column.
# For each placement, check if the queen is safe:
# No queen should be placed in the same column (i.e., no column can have more than one queen).
# No queen should be placed in a diagonal that is already threatened by another queen.
# If a queen can be placed safely, recursively attempt to place queens in the next row.
# If placing a queen leads to a solution, the process stops.
# If a conflict occurs (i.e., no safe spot is found for a queen), backtrack to the previous row and try a different column.
# Base Case: If all 8 queens are placed safely, print the solution.

# Backtrack: If a queen placement results in no valid solution for subsequent rows, backtrack and try a new position for the previous queen.

N = 8  # Number of queens (8x8 board)

# Function to print the chessboard
def print_board(board):
    for row in board:
        print(" ".join(['Q' if x else '.' for x in row]))

# Function to check if it's safe to place a queen at board[row][col]
def is_safe(board, row, col):
    # Check the column
    for i in range(row):
        if board[i][col] == 1:
            return False

    # Check the upper-left diagonal
    for i, j in zip(range(row-1, -1, -1), range(col-1, -1, -1)):
        if board[i][j] == 1:
            return False

    # Check the upper-right diagonal
    for i, j in zip(range(row-1, -1, -1), range(col+1, N)):
        if board[i][j] == 1:
            return False

    return True

# Function to solve the 8 Queens problem using backtracking
def solve_queens(board, row):
    # If all queens are placed, return True
    if row == N:
        return True

    for col in range(N):
        # Check if it's safe to place the queen at [row][col]
        if is_safe(board, row, col):
            board[row][col] = 1  # Place queen

            # Recursively place the next queen
            if solve_queens(board, row + 1):
                return True

            # If placing queen leads to no solution, backtrack
            board[row][col] = 0

    return False

# Function to solve the 8 Queens problem
def solve():
    board = [[0 for _ in range(N)] for _ in range(N)]

    # Start the process from the first row
    if solve_queens(board, 0):
        print_board(board)
    else:
        print("Solution does not exist")

# Run the program
solve()
