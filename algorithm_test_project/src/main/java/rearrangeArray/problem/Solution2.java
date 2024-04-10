package rearrangeArray.problem;

public class Solution2 {
    public int[] rearrangeArray(int[] nums) {
        // flag=true表示当前元素应该大于下一元素
        boolean flag = true;
        for(int i=0;i<nums.length-1;i++) {
            if(flag!=(nums[i]>nums[i+1])) {
                int tmp = nums[i];
                nums[i] = nums[i+1];
                nums[i+1] = tmp;
            }
            flag = !flag;
        }
        return nums;
    }
}
