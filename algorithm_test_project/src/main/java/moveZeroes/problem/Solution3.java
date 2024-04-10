package moveZeroes.problem;

import com.alibaba.fastjson.JSON;

public class Solution3 {

    public static void main(String[] args) {
        Solution3 solution3 = new Solution3();
        int[] nums = new int[]{0, 1, 0, 3, 12};
        solution3.moveZeroes(nums);
        System.out.println(JSON.toJSONString(nums));
    }

    public void moveZeroes(int[] nums) {
        int zeroPoint = 0, notZeroPoint = 0;
        int n = nums.length;
        while (notZeroPoint < n) {
            while (notZeroPoint < n && nums[notZeroPoint] == 0) {

                notZeroPoint++;


            }
            while (notZeroPoint < n && zeroPoint < notZeroPoint) {
                if (nums[zeroPoint] == 0) {
                    nums[zeroPoint] = nums[notZeroPoint];
                    nums[notZeroPoint] = 0;
                    break;
                }
                zeroPoint++;
            }


        }
    }
}
