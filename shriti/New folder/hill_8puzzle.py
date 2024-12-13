import copy

# Utility functions
def print_puzzle(state):
    for row in state:
        print(row)
    print()

def find_blank(state):
    for i in range(len(state)):
        for j in range(len(state[i])):
            if state[i][j] == 0:
                return i, j

def is_goal(state, goal):
    return state == goal

def generate_successors(state):
    moves = []
    x, y = find_blank(state)
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]  # Up, Down, Left, Right

    for dx, dy in directions:
        nx, ny = x + dx, y + dy
        if 0 <= nx < 3 and 0 <= ny < 3:
            new_state = copy.deepcopy(state)
            new_state[x][y], new_state[nx][ny] = new_state[nx][ny], new_state[x][y]
            moves.append(new_state)
    return moves

def heuristic(state, goal):
    # Manhattan distance heuristic
    distance = 0
    for i in range(3):
        for j in range(3):
            if state[i][j] != 0:
                for x in range(3):
                    for y in range(3):
                        if state[i][j] == goal[x][y]:
                            distance += abs(i - x) + abs(j - y)
    return distance

def hill_climbing(initial, goal):
    current = initial
    path = [current]

    while not is_goal(current, goal):
        successors = generate_successors(current)
        successors.sort(key=lambda state: heuristic(state, goal))

        # Pick the best successor (lowest heuristic value)
        best_successor = successors[0]

        if heuristic(best_successor, goal) >= heuristic(current, goal):
            # If no better successor exists, terminate
            break

        current = best_successor
        path.append(current)

    return path if is_goal(current, goal) else []

# Example Usage
if __name__ == "__main__":
    initial_state = [
        [1, 2, 3],
        [5, 6, 0],
        [7, 8, 4]
    ]
    goal_state = [
        [1, 2, 3],
        [5, 8, 6],
        [0, 7, 4]
    ]

    print("Initial State:")
    print_puzzle(initial_state)

    print("Goal State:")
    print_puzzle(goal_state)

    print("Hill Climbing Solution:")
    path = hill_climbing(initial_state, goal_state)
    if path:
        for step in path:
            print_puzzle(step)
    else:
        print("No solution found.")
