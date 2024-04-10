package maxScoreIndices.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一个下标从 0 开始的二进制数组 nums ，数组长度为 n 。nums 可以按下标 i（ 0 <= i <= n ）拆分成两个数组（可能为空）：numsleft 和 numsright 。
 * <p>
 * numsleft 包含 nums 中从下标 0 到 i - 1 的所有元素（包括 0 和 i - 1 ），而 numsright 包含 nums 中从下标 i 到 n - 1 的所有元素（包括 i 和 n - 1 ）。
 * 如果 i == 0 ，numsleft 为 空 ，而 numsright 将包含 nums 中的所有元素。
 * 如果 i == n ，numsleft 将包含 nums 中的所有元素，而 numsright 为 空 。
 * 下标 i 的 分组得分 为 numsleft 中 0 的个数和 numsright 中 1 的个数之 和 。
 * <p>
 * 返回 分组得分 最高 的 所有不同下标 。你可以按 任意顺序 返回答案。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/all-divisions-with-the-highest-score-of-a-binary-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public List<Integer> maxScoreIndices(int[] nums) {

        //代表第i个位置0,和1的个数
        int[][] dp = new int[nums.length + 1][2];


        int oneNum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                oneNum++;
            }
        }

        //初始条件
        dp[0][0] = 0;
        dp[0][1] = oneNum;


        List<Integer> res = new ArrayList<>();
        res.add(0);
        int max = oneNum;
        for (int i = 1; i <= nums.length; i++) {
            if (nums[i-1] == 0) {
                dp[i][0] = dp[i - 1][0] + 1;
                dp[i][1] = dp[i - 1][1];
            } else {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1] - 1;
            }
            int sum = dp[i][0] + dp[i][1];
            if (sum > max) {
                max = sum;
                res = new ArrayList<>();
                res.add(i);
            } else if (sum == max) {
                res.add(i);
            }

        }
        return res;


    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {0, 0, 1, 0};
        //int[] nums = {0, 0, 0};
        //int[] nums = {1,1};
        List res = solution.maxScoreIndices(nums);
        System.out.println(JSON.toJSONString(res));
    }
}
