package canVisitAllRooms.problem;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {

        boolean[] checkList = new boolean[rooms.size()];
        checkList[0] = true;
        List<Integer> keyList = rooms.get(0);
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < keyList.size(); i++) {
            queue.add(keyList.get(i));
        }

        while (!queue.isEmpty()) {
            Integer roomNum = queue.poll();
            if (checkList[roomNum.intValue()] == true) {
                continue;
            }
            checkList[roomNum.intValue()] = true;
            keyList = rooms.get(roomNum);
            for (int i = 0; i < keyList.size(); i++) {
                queue.add(keyList.get(i));
            }
        }

        for (int i = 0; i < checkList.length; i++) {
            if (!checkList[i]) {
                return false;
            }
        }

        return true;
    }
}
