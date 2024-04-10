package maxJump.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Solution2 {

    public int maxJump(int[] stones) {

        List<List<Integer>> arrivedList = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(stones[0]);
        for (int i = 1; i < stones.length; i++) {

            boolean[] arrivedFlag = new boolean[stones.length];
            Arrays.fill(arrivedFlag, false);
            stack.push(stones[i]);
            arrivedFlag[i] = true;
            maxJumpSon(stones, arrivedFlag, i, arrivedList, stack);
            stack.pop();
            arrivedFlag[i] = false;
        }


        //获取最小值
        int min = stones[stones.length - 1];
        for (int i = 0; i < arrivedList.size(); i++) {
            List<Integer> arrivedTemp = arrivedList.get(i);
            int max = -1;
            for (int j = 0; j < arrivedTemp.size() - 1; j++) {
                int temp;
                if (arrivedTemp.get(j + 1) > arrivedTemp.get(j)) {
                    temp = arrivedTemp.get(j + 1) - arrivedTemp.get(j);
                } else {
                    temp = arrivedTemp.get(j) - arrivedTemp.get(j + 1);
                }

                if (max < temp) {
                    max = temp;
                }
            }
            if (max < min) {
                min = max;
            }
        }

        return min;
    }

    private void maxJumpSon(int[] stones, boolean[] arrivedFlag, int current, List<List<Integer>> arrivedList, Stack<Integer> stack) {

        //找回路
        if (arrivedFlag[stones.length - 1]) {
            for (int i = stones.length - 1; i >= 0; i--) {
                if (arrivedFlag[i]) {
                    continue;
                }
                stack.push(stones[i]);
                arrivedFlag[i] = true;
                if (i == 0) {

                    List<Integer> tempList = new ArrayList<>();
                    for (int j = 0; j < stack.size(); j++) {
                        tempList.add(stack.get(j));
                    }
                    arrivedList.add(tempList);
                    return;
                }
                maxJumpSon(stones, arrivedFlag, i, arrivedList, stack);
                stack.pop();
                arrivedFlag[i] = false;
            }
        }

        //找到尾部的位置
        for (int i = current; i < stones.length; i++) {
            if (arrivedFlag[i]) {
                continue;
            }
            stack.push(stones[i]);
            arrivedFlag[i] = true;
            maxJumpSon(stones, arrivedFlag, i, arrivedList, stack);
            stack.pop();
            arrivedFlag[i] = false;
        }
    }

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        int[] stones = new int[]{0, 5, 12, 25, 28, 35}; // 20
        int res = solution2.maxJump(stones);
        System.out.println(res);
    }
}
