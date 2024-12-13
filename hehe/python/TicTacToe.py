# Define the Game Board: A 3x3 grid representing the Tic-Tac-Toe board.

# Define the Utility Function (Evaluation): The utility function evaluates the game state:

# If the maximizing player wins (i.e., 'X' wins), return +1.
# If the minimizing player wins (i.e., 'O' wins), return -1.
# If it's a draw, return 0.
# Min-Max Function:

# For each possible move:
# Recursively evaluate the board by simulating the opponent's turn.
# For maximizing player ('X'), choose the move with the highest value (best outcome).
# For minimizing player ('O'), choose the move with the lowest value (worst outcome).
# Return the best value for the current player.
# Base Case: If the game is over (either a win, loss, or draw), return the score (1, -1, or 0).

# Generate All Possible Moves: For the current player, generate all possible valid moves (empty spaces) and recursively calculate the Min-Max value.

# Choose the Best Move: From the possible moves, choose the move with the best value for the current player.

import math

# Define the game board as a 3x3 matrix
board = [
    [' ', ' ', ' '],
    [' ', ' ', ' '],
    [' ', ' ', ' ']
]

# Define the winning combinations (rows, columns, diagonals)
winning_combinations = [
    [(0, 0), (0, 1), (0, 2)],  # Top row
    [(1, 0), (1, 1), (1, 2)],  # Middle row
    [(2, 0), (2, 1), (2, 2)],  # Bottom row
    [(0, 0), (1, 0), (2, 0)],  # Left column
    [(0, 1), (1, 1), (2, 1)],  # Middle column
    [(0, 2), (1, 2), (2, 2)],  # Right column
    [(0, 0), (1, 1), (2, 2)],  # Diagonal top-left to bottom-right
    [(0, 2), (1, 1), (2, 0)]   # Diagonal top-right to bottom-left
]

# Function to evaluate the current state of the board
def evaluate(board):
    for combination in winning_combinations:
        # Check for 'X' win
        if board[combination[0][0]][combination[0][1]] == 'X' and \
           board[combination[1][0]][combination[1][1]] == 'X' and \
           board[combination[2][0]][combination[2][1]] == 'X':
            return 1
        # Check for 'O' win
        elif board[combination[0][0]][combination[0][1]] == 'O' and \
             board[combination[1][0]][combination[1][1]] == 'O' and \
             board[combination[2][0]][combination[2][1]] == 'O':
            return -1
    return 0  # If no winner

# Check if the game is over
def is_game_over(board):
    if evaluate(board) != 0:
        return True  # If either 'X' or 'O' wins
    for row in board:
        if ' ' in row:
            return False  # There are still moves to be made
    return True  # It's a draw

# Minimax function to find the best move
def minimax(board, depth, is_maximizing_player):
    score = evaluate(board)
    
    # If 'X' wins, return +1
    if score == 1:
        return score
    # If 'O' wins, return -1
    if score == -1:
        return score
    # If it's a draw
    if is_game_over(board):
        return 0
    
    if is_maximizing_player:
        best = -math.inf
        # Try all possible moves for 'X' (Maximizing player)
        for i in range(3):
            for j in range(3):
                if board[i][j] == ' ':
                    board[i][j] = 'X'
                    best = max(best, minimax(board, depth + 1, False))
                    board[i][j] = ' '
        return best
    else:
        best = math.inf
        # Try all possible moves for 'O' (Minimizing player)
        for i in range(3):
            for j in range(3):
                if board[i][j] == ' ':
                    board[i][j] = 'O'
                    best = min(best, minimax(board, depth + 1, True))
                    board[i][j] = ' '
        return best

# Function to find the best move for the current player
def find_best_move(board):
    best_val = -math.inf
    best_move = (-1, -1)
    
    # Try all possible moves for 'X' (Maximizing player)
    for i in range(3):
        for j in range(3):
            if board[i][j] == ' ':
                board[i][j] = 'X'
                move_val = minimax(board, 0, False)
                board[i][j] = ' '
                if move_val > best_val:
                    best_move = (i, j)
                    best_val = move_val
    
    return best_move

# Function to print the board
def print_board(board):
    for row in board:
        print(' | '.join(row))
        print('---------')

# Function for player's move
def player_move(board):
    while True:
        try:
            row = int(input("Enter the row (0, 1, 2) for your move: "))
            col = int(input("Enter the column (0, 1, 2) for your move: "))
            if board[row][col] == ' ':
                board[row][col] = 'X'
                break
            else:
                print("The spot is already taken. Try again.")
        except (ValueError, IndexError):
            print("Invalid input! Please enter row and column values between 0 and 2.")

# Main function to play the game
def play_game():
    print("Welcome to Tic-Tac-Toe!")
    print("You are 'X'. AI is 'O'.")
    
    while not is_game_over(board):
        # Player 'X' (you) makes the move
        print("\nYour turn (Player 'X'):")
        print_board(board)
        player_move(board)
        
        if is_game_over(board):
            break
        
        # AI (Player 'O') makes the move
        print("\nAI's turn (Player 'O'):")
        i, j = find_best_move(board)
        board[i][j] = 'O'
        print_board(board)
    
    print("\nFinal Board:")
    print_board(board)
    if evaluate(board) == 1:
        print("You win!")
    elif evaluate(board) == -1:
        print("AI wins!")
    else:
        print("It's a draw!")

# Start the game
play_game()
