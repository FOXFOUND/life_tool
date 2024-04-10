package nearestExit.problem;

import java.util.LinkedList;
import java.util.Queue;

class Solution2 {
    class Point {
        /**
         * 横坐标
         */
        int x;
        /**
         * 纵坐标
         */
        int y;
        /**
         * 步数
         */
        int step;

        public Point(int x, int y, int step) {
            this.x = x;
            this.y = y;
            this.step = step;
        }
    }

    public int nearestExit(char[][] maze, int[] entrance) {
        return bfs(maze, entrance[0], entrance[1]);
    }

    public int bfs(char[][] maze, int i, int j) {
        //可以移动的方向
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        int m = maze.length;
        int n = maze[0].length;
        Queue<Point> queue = new LinkedList<>();
        //入口入队
        queue.offer(new Point(i, j, 0));
        //标记为已访问过
        maze[i][j] = '+';
        while (!queue.isEmpty()) {
            Point poll = queue.poll();
            //不是入口，且是边界
            if (!(poll.x == i && poll.y == j) && (poll.x == 0 || poll.x == m - 1 || poll.y == 0 || poll.y == n - 1)) {
                return poll.step;
            }
            //枚举四个方向
            for (int k = 0; k < dx.length; k++) {
                int x = poll.x + dx[k];
                int y = poll.y + dy[k];
                //没越界且未访问过
                if (x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == '.') {
                    queue.offer(new Point(x, y, poll.step + 1));
                    //标记为已访问过
                    maze[x][y] = '+';
                }
            }
        }
        //程序运行到这里，说明不存在这样的路径，返回 -1
        return -1;
    }
}
//
//作者：songhouhou
//        链接：https://leetcode.cn/problems/nearest-exit-from-entrance-in-maze/solution/javashi-yong-bfsqiu-mi-gong-zui-duan-lu-pkxxp/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
