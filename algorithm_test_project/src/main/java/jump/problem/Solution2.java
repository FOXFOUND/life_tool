package jump.problem;

public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        int[] nums = new int[]{2, 3, 1, 1, 4};
        solution2.canJump(nums);
    }

    public boolean canJump(int[] nums) {

        int current = 0;
        int n = nums.length;

        while (current < nums.length - 1) {

            int jumpSize = nums[current];

            if(jumpSize ==0){
                return false;
            }
            int nextCurrent = current + 1;
            int temp = nextCurrent;
            while (temp <= current + jumpSize && temp < n) {
                if (nextCurrent + nums[nextCurrent] < temp + nums[temp]) {
                    nextCurrent = temp;
                }
                temp++;
            }
            current = nextCurrent;


        }

        return current >= n - 1;
    }
}
