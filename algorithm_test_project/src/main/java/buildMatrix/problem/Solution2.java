package buildMatrix.problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution2 {
    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
        // 行排序
        int[] rows = sort(k, rowConditions);
        if (rows.length == 0) {
            return new int[0][0];
        }

        // 列排序
        int[] cols = sort(k, colConditions);
        if (cols.length == 0) {
            return new int[0][0];
        }

        // 处理结果
        return build(k, rows, cols);
    }

    private int[] sort(int k, int[][] cs) {
        // 拓扑排序
        int[] ans = new int[k + 1];
        int idx = 0;

        // 这里有坑，只能用邻接表，不能用邻接矩阵
        // 测试用例中有重复的边，比如：[[1,2],[7,3],[4,3],[5,8],[7,8],[8,2],[5,8],[3,2],[1,3],[7,6],[4,3],[7,4],[4,8],[7,3],[7,5]]
        // 用邻接矩阵会导致入度多算而边没有多算
        List<Integer>[] g = new List[k + 1];
        for (int i = 1; i <= k; i++) {
            g[i] = new ArrayList<>();
        }
        int[] indeg = new int[k + 1];
        for (int[] c : cs) {
            g[c[0]].add(c[1]);
            indeg[c[1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        //由于indeg[i] == 0 表示,入度为0, 这种点没有依赖顺序,所以从小到大遍历,还是从大到小都是可以的,随机也是可以的

        for (int i = 1; i <= k; i++) {
            if (indeg[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            // 存储的是哪个数字放在哪一行，而不是哪行存储哪个数字
            //将拓扑排序的结果看成每个数字的行号即可,最大的点应该在最上边行,并且最大的点在栈的栈底,通过               //这个关系,维护了最大点所在的行
            ans[node] = idx++;

            //删除当前点对应边的入度关系
            for (int next : g[node]) {
                //如果删除完成后,变成入度为0的点
                if (--indeg[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        // 有环时idx肯定到不了k
        // 如果有环,idx一定小于k,因为一定有对应的点的没有加入栈里
        if (idx != k) {
            return new int[0];
        }
        //ans维护了一个最大数所在的行的关系, a[i]的值表示所在二维数组所在的行
        return ans;
    }

    private int[][] build(int k, int[] rows, int[] cols) {
        int[][] ans = new int[k][k];

        for (int i = 1; i <= k; i++) {
            // i存储在哪行哪列
            // 通过rows[i] 获取i所在的行 , 通过cols[i]获取i所在的列,确定二维数组中i的位置

            ans[rows[i]][cols[i]] = i;
        }

        return ans;
    }
}

/**
 * 作者：tong-zhu
 * 链接：https://leetcode.cn/problems/build-a-matrix-with-conditions/solution/by-tong-zhu-4uv6/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */