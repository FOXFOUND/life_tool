package movingCount.problem;

import java.util.ArrayDeque;
import java.util.Queue;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.movingCount(2, 3, 1);
    }

    public int movingCount(int m, int n, int k) {

        int[][] route = new int[m][n];
        int[][] routeRes = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (check(i, j, k)) {
                    route[i][j] = 1;
                }
            }
        }

        int[] x = new int[]{0, 0, 1, -1};
        int[] y = new int[]{1, -1, 0, 0};
        Queue<Position> positionQueue = new ArrayDeque<>();
        Position start = new Position();
        start.x = 0;
        start.y = 0;
        positionQueue.add(start);

        while (!positionQueue.isEmpty()) {
            Position position = positionQueue.poll();
            int positionX = position.x;
            int positionY = position.y;

            if (positionX < 0 || positionX >= m) {
                continue;
            }

            if (positionY < 0 || positionY >= n) {
                continue;
            }

            if (routeRes[positionX][positionY] == 1) {
                continue;
            }

            if (route[positionX][positionY] == 1) {
                routeRes[positionX][positionY] = 1;
                for (int i = 0; i < x.length; i++) {
                    Position positionNext = new Position();
                    positionNext.x = positionX + x[i];
                    positionNext.y = positionY + y[i];
                    positionQueue.add(positionNext);
                }
            }


        }

        int num = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (routeRes[i][j] == 1) {
                    num++;
                }

            }
        }

        return num;
    }

    class Position {
        public int x;
        public int y;
    }

    private boolean check(int i, int j, int k) {

        int sum = 0;
        while (i != 0) {
            int a = i % 10;
            sum += a;
            i = i / 10;
        }
        while (j != 0) {
            int a = j % 10;
            sum += a;
            j = j / 10;
        }

        return sum <= k;
    }
}
