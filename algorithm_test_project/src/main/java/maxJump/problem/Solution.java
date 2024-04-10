package maxJump.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个下标从 0 开始的整数数组 stones ，数组中的元素 严格递增 ，表示一条河中石头的位置。
 * <p>
 * 一只青蛙一开始在第一块石头上，它想到达最后一块石头，然后回到第一块石头。同时每块石头 至多 到达 一次。
 * <p>
 * 一次跳跃的 长度 是青蛙跳跃前和跳跃后所在两块石头之间的距离。
 * <p>
 * 更正式的，如果青蛙从 stones[i] 跳到 stones[j] ，跳跃的长度为 |stones[i] - stones[j]| 。
 * 一条路径的 代价 是这条路径里的 最大跳跃长度 。
 * <p>
 * 请你返回这只青蛙的 最小代价 。
 * <p>
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/frog-jump-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class Solution {


    public int maxJump(int[] stones) {

        List<List<Integer>> arrivedList = new ArrayList<>();
        for (int i = 1; i < stones.length; i++) {
            List<Integer> arriveTempList = new ArrayList<>();
            arriveTempList.add(stones[0]);
            boolean[] arrivedFlag = new boolean[stones.length];
            Arrays.fill(arrivedFlag, false);
            arriveTempList.add(stones[i]);
            arrivedFlag[i] = true;
            maxJumpSon(stones, arriveTempList, arrivedFlag, i, arrivedList);
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

    private void maxJumpSon(int[] stones, List<Integer> arriveTempList, boolean[] arrivedFlag, int current, List<List<Integer>> arrivedList) {

        //找回路
        if (arrivedFlag[stones.length - 1]) {
            for (int i = stones.length - 1; i >= 0; i--) {
                if (arrivedFlag[i]) {
                    continue;
                }
                arriveTempList.add(stones[i]);
                arrivedFlag[i] = true;
                if (i == 0) {
                    arrivedList.add(arriveTempList);
                    return;
                }
                maxJumpSon(stones, arriveTempList, arrivedFlag, i, arrivedList);
            }
        }

        //找到尾部的位置
        for (int i = current; i < stones.length; i++) {
            if (arrivedFlag[i]) {
                continue;
            }
            arriveTempList.add(stones[i]);
            arrivedFlag[i] = true;
            maxJumpSon(stones, arriveTempList, arrivedFlag, i, arrivedList);
        }
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        //int[] stones = new int[]{0, 2, 5, 6, 7};  //5
        // int[] stones = new int[]{0, 3, 9}; // 9
        int[] stones = new int[]{0,5,12,25,28,35}; // 20
        int res = solution.maxJump(stones);
        System.out.println(res);
    }
}
