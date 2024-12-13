import heapq
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

# Best-First Search
def best_first_search(initial, goal):
    open_list = []
    heapq.heappush(open_list, (heuristic(initial, goal), initial, []))
    closed_set = set()

    while open_list:
        _, current, path = heapq.heappop(open_list)
        state_tuple = tuple(map(tuple, current))

        if state_tuple in closed_set:
            continue

        closed_set.add(state_tuple)

        if is_goal(current, goal):
            return path + [current]

        for successor in generate_successors(current):
            if tuple(map(tuple, successor)) not in closed_set:
                heapq.heappush(open_list, (heuristic(successor, goal), successor, path + [current]))

    return []

# Example Usage
if __name__ == "__main__":
    # initial_state = [
    #     [1, 2, 3],
    #     [4, 0, 5],
    #     [6, 7, 8]
    # ]

    initial_state = [
        [1, 2, 3],
        [4, 5, 6],
        [0, 7, 8]
    ]
    goal_state = [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 0]
    ]


    print("Best-First Search Solution:")
    path = best_first_search(initial_state, goal_state)
    for step in path:
        print_puzzle(step)