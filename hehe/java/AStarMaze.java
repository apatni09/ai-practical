package java;
// # Initialization:
// # Create an open list (priority queue) to store the nodes to be evaluated.
// # Create a closed list to store the nodes that have already been evaluated.
// # The starting node is added to the open list.
// # Loop until goal is found or open list is empty:

// # Get the node with the lowest f = g + h from the open list, where:
// # g is the cost to reach the current node.
// # h is the heuristic cost (estimate) from the current node to the goal.
// # If the current node is the goal, reconstruct the path and exit.
// # Otherwise, move the current node to the closed list.
// # For each neighbor of the current node:
// # If the neighbor is already in the closed list, skip it.
// # Otherwise, calculate the g, h, and f values and add the neighbor to the open list if it isn’t already there or has a better f value.
// # Heuristic Function: For the heuristic function (H-cost), a common choice for a maze is the Manhattan distance, which is the sum of the absolute differences of the x and y coordinates between the current node and the goal node.

import java.util.*;

public class AStarMaze {

    // Directions for up, right, down, left movement
    static final int[] dx = {0, 1, 0, -1};
    static final int[] dy = {1, 0, -1, 0};

    // Manhattan heuristic function
    public static int manhattanHeuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    // Function to find the shortest path using A* algorithm
    public static List<int[]> aStar(int[][] maze, int[] start, int[] goal) {
        int startX = start[0], startY = start[1];
        int goalX = goal[0], goalY = goal[1];
        
        // Open list stores nodes to be evaluated based on the f cost (g + h)
        PriorityQueue<int[]> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n[2] + n[3]));
        
        // Closed list stores nodes that have been evaluated
        Set<String> closedList = new HashSet<>();
        
        // A map to track the parent of each node for path reconstruction
        Map<String, int[]> parentMap = new HashMap<>();
        
        // g is the cost from the start to the current node, h is the heuristic estimate to the goal
        int[] startNode = new int[]{startX, startY, 0, manhattanHeuristic(startX, startY, goalX, goalY)};
        openList.add(startNode);
        
        while (!openList.isEmpty()) {
            int[] current = openList.poll();
            int x = current[0], y = current[1], g = current[2], h = current[3];
            
            if (x == goalX && y == goalY) {
                // Goal found, reconstruct path
                List<int[]> path = new ArrayList<>();
                while (parentMap.containsKey(x + "," + y)) {
                    path.add(new int[]{x, y});
                    int[] parent = parentMap.get(x + "," + y);
                    x = parent[0];
                    y = parent[1];
                }
                path.add(new int[]{startX, startY});
                Collections.reverse(path);
                return path;
            }

            closedList.add(x + "," + y);
            
            // Explore neighbors (up, down, left, right)
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i], ny = y + dy[i];
                
                if (nx < 0 || nx >= maze.length || ny < 0 || ny >= maze[0].length || maze[nx][ny] == 1 || closedList.contains(nx + "," + ny)) {
                    continue;
                }
                
                // g cost (1 step from current node)
                int newG = g + 1;
                int newH = manhattanHeuristic(nx, ny, goalX, goalY);
                int[] neighbor = new int[]{nx, ny, newG, newH};
                
                parentMap.put(nx + "," + ny, new int[]{x, y});
                openList.add(neighbor);
            }
        }
        
        return null;  // No path found
    }

    // Main function to test A* algorithm
    public static void main(String[] args) {
        int[][] maze = {
            {0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 1, 0},
            {0, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0}
        };

        int[] start = {0, 0};  // Start position
        int[] goal = {4, 5};   // Goal position

        List<int[]> path = aStar(maze, start, goal);
        if (path != null) {
            System.out.println("Path found:");
            for (int[] p : path) {
                System.out.println(Arrays.toString(p));
            }
        } else {
            System.out.println("No path found");
        }
    }
}

