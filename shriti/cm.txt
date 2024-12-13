

def is_valid(state):
    M, C, B = state
    if (M > 3 or C > 3 or B > 1 or M < 0 or C < 0 or B < 0 or 
        (0 < M < C) or (0 < (3 - M) < (3 - C))):
        return False
    return True

def generate_next_states(M, C, B):
    moves = [[1, 0, 1], [0, 1, 1], [2, 0, 1], [0, 2, 1], [1, 1, 1]]
    valid_states = []
    for each in moves:
        if B == 1:  # Boat is on the left side
            next_state = [x1 - x2 for (x1, x2) in zip([M, C, B], each)]
        else:       # Boat is on the right side
            next_state = [x1 + x2 for (x1, x2) in zip([M, C, B], each)]
        if is_valid(next_state):
            valid_states.append(next_state)
    return valid_states

def find_sol(M, C, B, visited, path):
    if [M, C, B] == [0, 0, 0]:  # Everyone crossed successfully
        solutions.append(path + [[0, 0, 0]])
        return True
    if (M, C, B) in visited:  # Prevent looping
        return False
    visited.add((M, C, B))
    next_states = generate_next_states(M, C, B)
    found_solution = False
    for each_s in next_states:
        # Call recursively to explore each valid state
        found_solution = find_sol(each_s[0], each_s[1], each_s[2], visited, path + [[M, C, B]])
        if found_solution:
            break
    return found_solution

solutions = []
visited_set = set()
find_sol(3, 3, 1, visited_set, [])

solutions.sort()
for each_sol in solutions:
    print(each_sol)
