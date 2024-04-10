package longestOnes.problem;

class Solution2 {
    public int longestOnes(int[] nums, int k) {
        int ans = 0, left = 0, cnt0 = 0, n = nums.length;
        for (int right = 0; right < n; ++right) {
            cnt0 += 1 - nums[right]; // 0 变成 1，用来统计 cnt0
            while (cnt0 > k)
                cnt0 -= 1 - nums[left++];
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}

//作者：endlesscheng
//        链接：https://leetcode.cn/problems/max-consecutive-ones-iii/solution/hua-dong-chuang-kou-yi-ge-shi-pin-jiang-yowmi/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
