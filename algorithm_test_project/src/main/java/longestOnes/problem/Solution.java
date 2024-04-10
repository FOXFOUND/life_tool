package longestOnes.problem;

import java.util.ArrayDeque;
import java.util.Queue;

public class Solution {
    public int longestOnes(int[] nums, int k) {

        Queue<Integer> positionQueue = new ArrayDeque<>();
        int current = 0;
        int n = nums.length;
        int max = 0;
        int prePosition = 0;
        while (current < n) {
            if (nums[current] == 0) {

                if (positionQueue.size() <= k) {
                    nums[current] = 1;
                    positionQueue.add(current);
                } else {
                    int position = positionQueue.poll();
                    nums[position] = 0;
                    nums[current] = 1;
                    positionQueue.add(current);
                    int numOfOne = 0;
                    if (prePosition == 0) {
                        prePosition = position;
                    }
                    if (position - prePosition <= 1) {
                        numOfOne = check(nums, position, current);
                    } else {
                        numOfOne = Math.max(check(nums, prePosition, position), check(nums, position, current));
                    }
                    prePosition = position;
                    max = Math.max(max, numOfOne);
                }
            }
            if (nums[current] == 1) {
                current++;
            }
        }
        return max;
    }

    private int check(int[] nums, int position, int current) {

        int res = 0;
        for (int i = position; i <= current; i++) {
            if (nums[i] == 1) {
                res++;
            }
        }
        return res;
    }
}
