package longestSubarray.problem;

public class Solution {

    int[][] dp = new int[100001][2];

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[]{1, 1, 0, 1};
        int res = solution.longestSubarray(nums);
        System.out.println(res);
    }


    public int longestSubarray(int[] nums) {


        //不删除当前元素
        dp[0][0] = nums[0] == 1 ? 1 : 0;
        dp[0][1] = 0;

        dfs(1, false, nums);
        dfs(1, true, nums);

        return Math.max(dp[nums.length - 1][0], dp[nums.length - 1][1]);
    }

    public void dfs(int index, boolean deleteFlag, int[] nums) {

        if (index >= nums.length) {
            return;
        }

        if (deleteFlag) {
            dp[index][1] = Math.max(Math.max(dp[index - 1][0], dp[index - 1][1]), dp[index][1]);
            dfs(index + 1, false, nums);
        }

        dp[index][0] = Math.max(Math.max(dp[index - 1][0], dp[index - 1][1]), dp[index][0]) + nums[index] == 1 ? 1 : 0;

        dfs(index + 1, false, nums);
        dfs(index + 1, true, nums);

    }

}
