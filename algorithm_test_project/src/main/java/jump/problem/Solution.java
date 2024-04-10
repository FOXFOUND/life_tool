package jump.problem;

public class Solution {
    public int jump(int[] nums) {

        int current = 0;
        int n = nums.length;
        int res = 0;
        while (current < nums.length -1) {
            res++;
            int jumpSize = nums[current];

            if (current + jumpSize >= n - 1) {
                break;
            }

            int nextCurrent = current + 1;
            int temp = current + 1;
            while (temp <= current + jumpSize && temp < n) {
                if (nextCurrent + nums[nextCurrent] < temp + nums[temp]) {
                    nextCurrent = temp;
                }
                temp++;
            }
            current = nextCurrent;


        }

        return res;

    }
}
