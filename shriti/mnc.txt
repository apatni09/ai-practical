# State Representation: A state is represented as a tuple (M_left, C_left, M_right, C_right, boat_position) where:

# M_left: Number of missionaries on the left bank.
# C_left: Number of cannibals on the left bank.
# M_right: Number of missionaries on the right bank.
# C_right: Number of cannibals on the right bank.
# boat_position: 0 for left bank and 1 for right bank.
# Goal Condition: The goal is to have all the missionaries and cannibals on the right bank with the boat on the right bank.

# BFS Search: BFS is used to explore the states level by level. Starting from the initial state (3, 3, 0, 0, 0) (all missionaries and cannibals on the left bank), we explore all possible valid transitions until the goal state (0, 0, 3, 3, 1) is found.

# State Transitions: From any given state, we can move the boat with:

# Two missionaries (if there are at least two on the current side).
# Two cannibals (if there are at least two on the current side).
# One missionary and one cannibal (if there is at least one of each).
# One missionary (if there is at least one).
# One cannibal (if there is at least one).
# Validity Check: Ensure that no side has more cannibals than missionaries at any point.

# BFS Implementation: We use a queue to simulate BFS and a set to keep track of visited states to avoid revisiting the same state.

from collections import deque

# Function to check if the current state is valid
def is_valid(state):
    M_left, C_left, M_right, C_right, boat_position = state
    # Check if the number of missionaries is non-negative and doesn't exceed the limits
    if M_left < 0 or C_left < 0 or M_right < 0 or C_right < 0:
        return False
    if M_left > 3 or C_left > 3 or M_right > 3 or C_right > 3:
        return False
    # Check if missionaries are safe (cannibals should not outnumber missionaries on any bank)
    if (M_left > 0 and M_left < C_left) or (M_right > 0 and M_right < C_right):
        return False
    if M_left < 0 or M_right < 0 or C_left < 0 or C_right < 0:
        return False
    return True

# Function to perform BFS for Missionaries and Cannibals problem
def bfs():
    # Initial state: 3 missionaries, 3 cannibals, boat on the left (represented as 0)
    initial_state = (3, 3, 0, 0, 0)
    # Goal state: All 3 missionaries and 3 cannibals on the right, boat on the right (represented as 1)
    goal_state = (0, 0, 3, 3, 1)
    
    # Queue to store the states and the path to reach that state
    queue = deque([(initial_state, [])])
    
    # Set to store visited states to avoid loops
    visited = set()
    visited.add(initial_state)
    
    while queue:
        current_state, path = queue.popleft()
        
        # If we have reached the goal state, return the path
        if current_state == goal_state:
            print("Solution found:")
            for state in path + [current_state]:
                print(state)
            return True
        
        # Generate all possible valid next states
        M_left, C_left, M_right, C_right, boat_position = current_state
        
        # If the boat is on the left, we move people from the left to the right
        if boat_position == 0:
            moves = [
                (M_left - 2, C_left, M_right + 2, C_right, 1),  # 2 missionaries
                (M_left, C_left - 2, M_right, C_right + 2, 1),  # 2 cannibals
                (M_left - 1, C_left - 1, M_right + 1, C_right + 1, 1),  # 1 missionary and 1 cannibal
                (M_left - 1, C_left, M_right + 1, C_right, 1),  # 1 missionary
                (M_left, C_left - 1, M_right, C_right + 1, 1)   # 1 cannibal
            ]
        else:  # If the boat is on the right, we move people from the right to the left
            moves = [
                (M_left + 2, C_left, M_right - 2, C_right, 0),  # 2 missionaries
                (M_left, C_left + 2, M_right, C_right - 2, 0),  # 2 cannibals
                (M_left + 1, C_left + 1, M_right - 1, C_right - 1, 0),  # 1 missionary and 1 cannibal
                (M_left + 1, C_left, M_right - 1, C_right, 0),  # 1 missionary
                (M_left, C_left + 1, M_right, C_right - 1, 0)   # 1 cannibal
            ]
        
        # For each possible move, check if the state is valid and not visited before
        for next_state in moves:
            if is_valid(next_state) and next_state not in visited:
                visited.add(next_state)
                queue.append((next_state, path + [current_state]))
    
    print("No solution found.")
    return False

# Example usage
bfs()
