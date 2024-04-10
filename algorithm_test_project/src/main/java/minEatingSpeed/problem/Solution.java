package minEatingSpeed.problem;

public class Solution {


    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[] nums = new int[]{30, 11, 23, 4, 20};
//        int k = 5;
        int[] nums = new int[]{3, 6, 7, 11};
        int k = 8;
        int res = solution.minEatingSpeed(nums, k);
        System.out.println(res);
    }


    public int minEatingSpeed(int[] piles, int h) {

        int max = 0;
        for (int i = 0; i < piles.length; i++) {

            max = Math.max(max, piles[i]);
        }

        int left = 1, right = max;
        while (left < right) {
            int middle = left + (right - left) / 2; // 防止计算时溢出
            if (check(piles, middle, h)) {
                right = middle;
            } else {
                left = middle + 1;
            }

        }
        return left;

    }

    private boolean check(int[] piles, int middle, int h) {

        long time = 0;
        for (int pile : piles) {
            //向上取整写的好巧妙
            int curTime = (pile + middle - 1) / middle;
            time += curTime;
        }
        return time <= h;

    }
}
