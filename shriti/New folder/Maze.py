# Initialization:

# Create an open list (priority queue) to store the nodes to be evaluated.
# Create a closed list to store the nodes that have already been evaluated.
# The starting node is added to the open list.
# Loop until goal is found or open list is empty:

# Get the node with the lowest f = g + h from the open list, where:
# g is the cost to reach the current node.
# h is the heuristic cost (estimate) from the current node to the goal.
# If the current node is the goal, reconstruct the path and exit.
# Otherwise, move the current node to the closed list.
# For each neighbor of the current node:
# If the neighbor is already in the closed list, skip it.
# Otherwise, calculate the g, h, and f values and add the neighbor to the open list if it isn’t already there or has a better f value.
# Heuristic Function: For the heuristic function (H-cost), a common choice for a maze is the Manhattan distance, which is the sum of the absolute differences of the x and y coordinates between the current node and the goal node.

import heapq

# Directions for 4 possible moves (up, down, left, right)
DIRECTIONS = [(0, 1), (1, 0), (0, -1), (-1, 0)]

# Class to represent a node
class Node:
    def __init__(self, x, y, g, h, parent=None):
        self.x = x
        self.y = y
        self.g = g  # Cost from start to current node
        self.h = h  # Heuristic cost from current node to goal
        self.f = g + h  # Total cost
        self.parent = parent

    # Comparison for priority queue based on f value
    def __lt__(self, other):
        return self.f < other.f

# Manhattan distance heuristic
def manhattan_heuristic(x1, y1, x2, y2):
    return abs(x1 - x2) + abs(y1 - y2)

# A* algorithm to find the shortest path in a maze
def a_star(maze, start, goal):
    start_x, start_y = start
    goal_x, goal_y = goal
    
    # Create the open and closed lists
    open_list = []
    closed_list = set()
    
    # Create start node and add to the open list
    start_node = Node(start_x, start_y, 0, manhattan_heuristic(start_x, start_y, goal_x, goal_y))
    heapq.heappush(open_list, start_node)
    
    while open_list:
        current_node = heapq.heappop(open_list)  # Node with the lowest f value
        
        if (current_node.x, current_node.y) == (goal_x, goal_y):
            # Reconstruct the path
            path = []
            while current_node:
                path.append((current_node.x, current_node.y))
                current_node = current_node.parent
            return path[::-1]  # Return path from start to goal
        
        closed_list.add((current_node.x, current_node.y))
        
        # Explore neighbors
        for dx, dy in DIRECTIONS:
            nx, ny = current_node.x + dx, current_node.y + dy
            
            # Check if the neighbor is within bounds and not an obstacle
            if 0 <= nx < len(maze) and 0 <= ny < len(maze[0]) and maze[nx][ny] == 0:
                if (nx, ny) in closed_list:
                    continue
                
                g_cost = current_node.g + 1
                h_cost = manhattan_heuristic(nx, ny, goal_x, goal_y)
                neighbor = Node(nx, ny, g_cost, h_cost, current_node)
                
                # Add neighbor to the open list if not already present
                heapq.heappush(open_list, neighbor)
    
    return None  # Return None if no path found

# Define the maze (0 = free space, 1 = obstacle)
maze = [
    [0, 0, 0, 0, 0, 0],
    [0, 1, 1, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 1, 1, 0, 0, 0],
    [0, 0, 0, 0, 0, 0],
]

# Define start and goal positions
start = (0, 0)
goal = (4, 5)

# Run A* algorithm
path = a_star(maze, start, goal)
if path:
    print("Path found:", path)
else:
    print("No path found")
