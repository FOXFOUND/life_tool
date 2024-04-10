package subarraySum.problem;

import java.util.HashMap;

class Solution4 {
    public int findMaxLength(int[] nums) {

        int n = nums.length;
        int[] pre = new int[n + 1];
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                nums[i] = -1;
            }
        }
        // pre[i] == nums[0] +...+nums[i-1]
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + nums[i - 1];
        }
        // 题目转变为连续数组和为0
        // 此时这个连续数组中1的个数和-1的个数相等
        // nums[j...i]  pre[i] - pre[j-1] = 0  ==> pre[i] == pre[j]
        int ans = 0;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < pre.length; i++) {
            int key = pre[i];
            if (!map.containsKey(key)) {
                map.put(key, i);
            } else {
                ans = Math.max(ans, i - map.get(key));
            }

        }
        return ans;
    }
}

//作者：msf_LeetCode
//        链接：https://leetcode.cn/problems/A1NYOS/solution/java-chao-hao-li-jie-de-qian-zhui-he-by-uf5jb/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
