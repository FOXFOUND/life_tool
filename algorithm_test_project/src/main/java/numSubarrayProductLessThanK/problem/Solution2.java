package numSubarrayProductLessThanK.problem;

class Solution2 {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1)
            return 0;
        int n = nums.length, ans = 0, prod = 1, left = 0;
        for (int right = 0; right < n; ++right) {
            prod *= nums[right];
            while (prod >= k) // 不满足要求
                prod /= nums[left++];
            ans += right - left + 1;
        }
        return ans;
    }
}

//作者：endlesscheng
//        链接：https://leetcode.cn/problems/ZVAVXX/solution/yi-xie-jiu-cuo-qing-kan-zhe-pythonjavacg-qtx2/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
