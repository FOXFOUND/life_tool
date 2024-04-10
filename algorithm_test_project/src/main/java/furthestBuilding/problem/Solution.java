package furthestBuilding.problem;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个整数数组 heights ，表示建筑物的高度。另有一些砖块 bricks 和梯子 ladders 。
 * <p>
 * 你从建筑物 0 开始旅程，不断向后面的建筑物移动，期间可能会用到砖块或梯子。
 * <p>
 * 当从建筑物 i 移动到建筑物 i+1（下标 从 0 开始 ）时：
 * <p>
 * 如果当前建筑物的高度 大于或等于 下一建筑物的高度，则不需要梯子或砖块
 * 如果当前建筑的高度 小于 下一个建筑的高度，您可以使用 一架梯子 或 (h[i+1] - h[i]) 个砖块
 * 如果以最佳方式使用给定的梯子和砖块，返回你可以到达的最远建筑物的下标（下标 从 0 开始 ）。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/furthest-building-you-can-reach
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        Map<String, Integer> cacheMap = new HashMap<>();
        int res = furthestBuildingSub(heights, bricks, ladders, 0, cacheMap);
        return res;
    }

    private int furthestBuildingSub(int[] heights, int bricks, int ladders, int current, Map<String, Integer> cacheMap) {
        if (current >= heights.length - 1) {
            return heights.length - 1;
        }

        String key = current + "_" + bricks + "_" + ladders;
        if (cacheMap.get(key) != null) {
            return cacheMap.get(key);
        }

        //子问题
        if (heights[current] >= heights[current + 1]) {
            int res = furthestBuildingSub(heights, bricks, ladders, current + 1, cacheMap);
            if (cacheMap.get(key) == null) {
                cacheMap.put(key, res);
            }
            return res;
        }

        //选择砖块
        int sub = heights[current + 1] - heights[current];
        int res = current, resBrick = current, resLadders = current;
        if (heights[current] < heights[current + 1] && bricks >= sub) {
            resBrick = furthestBuildingSub(heights, bricks - sub, ladders, current + 1, cacheMap);
            if (cacheMap.get(key) == null) {
                cacheMap.put(key, res);
            }
        }
        res = resBrick;

        if (res == heights.length - 1) {
            return res;
        }
        if (heights[current] < heights[current + 1] && ladders >= 1) {
            resLadders = furthestBuildingSub(heights, bricks, ladders - 1, current + 1, cacheMap);
            if (cacheMap.get(key) == null) {

                cacheMap.put(key, res);
            }
        }
        if (resLadders > res) {
            res = resLadders;
        }
        return res;


    }

    public static void main(String[] args) {
        Solution solution = new Solution();

//        int[] heights = new int[]{4, 2, 7, 6, 9, 14, 12};
//        int bricks = 5;
//        int ladders = 1; //4


//        int[] heights = new int[]{4, 12, 2, 7, 3, 18, 20, 3, 19};
//        int bricks = 10;
//        int ladders = 2; //7

        int[] heights = new int[]{14, 3, 19, 3};
        int bricks = 17;
        int ladders = 0; //3

        int res = solution.furthestBuilding(heights, bricks, ladders);
        System.out.println(res);
    }
}
