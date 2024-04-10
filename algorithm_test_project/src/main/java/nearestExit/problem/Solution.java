package nearestExit.problem;

import java.util.ArrayDeque;
import java.util.Queue;

public class Solution {

    class Position {
        public int[] entrance;
        public int step;


    }

    public int nearestExit(char[][] maze, int[] entrance) {

        int[] x = new int[]{-1, 1, 0, 0};
        int[] y = new int[]{0, 0, 1, -1};

        Queue<Position> positionQueue = new ArrayDeque<>();

        int xTemp = entrance[0];
        int yTemp = entrance[1];
        maze[xTemp][yTemp] = 0;
        for (int i = 0; i < 4; i++) {
            int[] tempPosition = new int[]{xTemp + x[i], yTemp + y[i]};
            Position position = new Position();
            position.entrance = tempPosition;
            position.step = 1;
            positionQueue.add(position);
        }


        while (!positionQueue.isEmpty()) {
            Position position = positionQueue.poll();
            int []  entranceTemp=  position.entrance;


            if (maze[position.entrance[0]][position.entrance[1]] != '.') {
                continue;
            }

        }
        return 1;


    }
}
