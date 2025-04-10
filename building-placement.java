// TC: O(n * 2^n) - in the worst case, we have to generate all combinations of buildings
// SC: O(n) - for the recursion stack and the result grid
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

public class Main {

    public static void main(String[] args) {
        BuildingPlacement buildingPlacement = new BuildingPlacement();
        System.out.print(buildingPlacement.findMinDistance(5, 5, 4));
    }

    public static class BuildingPlacement {
        int minDist = Integer.MAX_VALUE;
        int H, W;
        int[][] result;

        public int findMinDistance(int h, int w, int n) {
            this.H = h;
            this.W = w;
            int[][] grid = new int[h][w];

            // Initialize the grid with -1
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    grid[i][j] = -1;
                }
            }
            // Explore all combinations of placing buildings using backtracking
            backtrack(grid, n, 0);
            // Optionally, one may inspect result grid using: System.out.println(Arrays.deepToString(result));
            return minDist;
        }

        private void bfs(int[][] grid) {
            Queue<int[]> q = new LinkedList<>();
            boolean[][] visited = new boolean[H][W];
            int[][] dirs = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

            // Add all positions with a building (value == 0) to the queue
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (grid[i][j] == 0) {
                        q.add(new int[] { i, j });
                        visited[i][j] = true;
                    }
                }
            }

            int dist = 0;
            // BFS to measure the distance from buildings to farthest cell
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    int[] curr = q.poll();
                    for (int[] dir : dirs) {
                        int nr = curr[0] + dir[0];
                        int nc = curr[1] + dir[1];
                        if (nr >= 0 && nc >= 0 && nr < H && nc < W && !visited[nr][nc]) {
                            q.add(new int[] { nr, nc });
                            visited[nr][nc] = true;
                        }
                    }
                }
                dist++;
            }
            // Update the minimum distance if the current configuration is better.
            if (minDist > dist - 1) {
                result = grid;
            }
            minDist = Math.min(minDist, dist - 1);
        }

        private void backtrack(int[][] grid, int n, int idx) {
            // Base case: when all n buildings are placed, run BFS.
            if (n == 0) {
                bfs(grid);
                return;
            }
            // Try placing a building in each cell starting from index idx.
            for (int j = idx; j < H * W; j++) {
                int r = j / W;
                int c = j % W;
                grid[r][c] = 0;
                backtrack(grid, n - 1, j + 1);
                // Backtrack: remove the building from current position.
                grid[r][c] = -1;
            }
        }
    }
}
