package findMaxFish.problem;


/**
 * 并查集
 */
class Solution2 {
    public int findMaxFish(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        UnionFind uf = new UnionFind(m * n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) {
                    uf.initFish(i * n + j, grid[i][j]);
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) {
                    if (i > 0 && grid[i - 1][j] > 0) {
                        uf.union(i * n + j, (i - 1) * n + j);
                    }
                    if (j > 0 && grid[i][j - 1] > 0) {
                        uf.union(i * n + j, i * n + j - 1);
                    }
                }
            }
        }
        return uf.getMaxFish();
    }
}

class UnionFind {
    private int[] parent;
    private int[] rank;
    private int[] fish;

    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        rank = new int[n];
        fish = new int[n];
    }

    public void initFish(int x, int num) {
        fish[x] = num;
    }

    public void union(int x, int y) {
        int rootx = find(x);
        int rooty = find(y);
        if (rootx != rooty) {
            if (rank[rootx] > rank[rooty]) {
                parent[rooty] = rootx;
                fish[rootx] += fish[rooty];
            } else if (rank[rootx] < rank[rooty]) {
                parent[rootx] = rooty;
                fish[rooty] += fish[rootx];
            } else {
                parent[rooty] = rootx;
                rank[rootx]++;
                fish[rootx] += fish[rooty];
            }
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public int getMaxFish() {
        int maxFish = 0;
        for (int val : fish) {
            maxFish = Math.max(maxFish, val);
        }
        return maxFish;
    }
}

//作者：stormsunshine
//        链接：https://leetcode.cn/problems/maximum-number-of-fish-in-a-grid/solution/2658-wang-ge-tu-zhong-yu-de-zui-da-shu-m-k967/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。