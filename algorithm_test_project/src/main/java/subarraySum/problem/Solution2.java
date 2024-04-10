package subarraySum.problem;


import java.util.HashMap;

class Solution2 {
    /*
    然后这种数组问题不然就使用前缀和，不然就使用滑动窗口来做，这里滑动窗口用不了只能用前缀和
    前缀和公式： preSum[j] - preSum[i] = k => preSum[j] - k = preSum[i]
     */
    public int subarraySum(int[] nums, int k) {
   //     return fun2(nums, k);
        return fun1(nums,k);
    }

    /*
    使用哈希表储存之前的前缀和 preSum[j] - k = preSum[i]
     */
    private int fun2(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        // 初始化
        map.put(0, 1);
        // 储存preSum[j]
        int preSum = 0;
        int res = 0;
        // 开始遍历储存前缀和
        for (int num : nums) {
            preSum += num;
            // 计算是否存在 preSum[i]
            if (map.containsKey(preSum - k))
                res += map.get(preSum - k);
            // 将这个前缀和存入哈希表
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }
        return res;
    }

    /*
    不使用哈希表的方法
     */
    private int fun1(int[] nums, int k) {
        // 先计算出所有的前缀和
        int[] preSum = new int[nums.length + 1];
        for (int i = 1; i <= nums.length; i++)
            preSum[i] = preSum[i - 1] + nums[i - 1];
        int res = 0;
        // 然后根据每个前缀和，判断以 i 开头的子数组能否满足条件
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j <= nums.length; j++) {
                if (preSum[j] - preSum[i] == k)
                    res++;
            }
        }
        return res;
    }
}

//作者：kun-kun-kun-kun-kun
//        链接：https://leetcode.cn/problems/QTMn0o/solution/bian-yu-li-jie-qian-zhui-he-bu-shi-yong-uwugj/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
