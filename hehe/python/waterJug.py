# State Representation: A state is represented as a tuple of two integers, (x, y), where x is the amount of water in jug 1 and y is the amount of water in jug 2.
# Goal Condition: The goal is to find a state where either x == c or y == c.
# DFS Search: Start from the initial state (0, 0) and explore all possible operations until the goal is reached or all possibilities are exhausted.
# State Transitions: From any state (x, y), we can perform the following transitions:
# Fill jug 1: (a, y)
# Fill jug 2: (x, b)
# Empty jug 1: (0, y)
# Empty jug 2: (x, 0)
# Pour water from jug 1 to jug 2: The new state would be (x - pour, y + pour) where pour = min(x, b - y)
# Pour water from jug 2 to jug 1: The new state would be (x + pour, y - pour) where pour = min(y, a - x)
# DFS Implementation: We use a stack to simulate DFS and a set to keep track of visited states to avoid revisiting the same state.

def dfs(capacity1, capacity2, target):
    # Stack for DFS and set to track visited states
    stack = [(0, 0)]
    visited = set()

    # Function to print the path
    def print_path(state):
        print(f"Jug1: {state[0]}, Jug2: {state[1]}")

    while stack:
        x, y = stack.pop()

        # If the current state has already been visited, skip it
        if (x, y) in visited:
            continue
        visited.add((x, y))

        # Print the current state
        print_path((x, y))

        # If we have reached the target amount in either jug
        if x == target or y == target:
            print("Goal reached!")
            return True

        # Perform the possible operations and push the new states into the stack
        # Fill jug1
        if x != capacity1:
            stack.append((capacity1, y))
        # Fill jug2
        if y != capacity2:
            stack.append((x, capacity2))
        # Empty jug1
        if x != 0:
            stack.append((0, y))
        # Empty jug2
        if y != 0:
            stack.append((x, 0))
        # Pour water from jug1 to jug2
        if x > 0 and y < capacity2:
            pour = min(x, capacity2 - y)
            stack.append((x - pour, y + pour))
        # Pour water from jug2 to jug1
        if y > 0 and x < capacity1:
            pour = min(y, capacity1 - x)
            stack.append((x + pour, y - pour))

    print("No solution found.")
    return False

# Example Usage
capacity1 = 4  # Capacity of jug1
capacity2 = 3  # Capacity of jug2
target = 2     # Target amount to measure

# Calling the DFS function to solve the problem
dfs(capacity1, capacity2, target)
