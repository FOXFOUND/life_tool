package numSubarrayProductLessThanK.problem;

public class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k == 0) {
            return 0;
        }

        int left = 0;
        int right = 0;
        int mul = 0;
        int n = nums.length;
        int res = 0;
        while (right < n) {
            if (mul == 0) {
                mul = nums[right];
            } else {
                mul = mul * nums[right];
            }

            while (mul >= k) {
                int tempLeft = left;
                int tempMul = nums[tempLeft];

                while (tempLeft <= right && tempMul < k) {

                    if (tempMul < k) {
                        res++;
                    }
                    tempLeft++;
                    tempMul = tempMul * tempLeft;
                }


                mul = mul / left;
                left++;

            }

            right++;
        }




        while (left <= right ){

        }


        return res;


    }
}
