package circular.array.loop.problem;

class Solution2 {
    int n;
    int[] nums;
    public boolean circularArrayLoop(int[] _nums) {
        nums = _nums;
        n = nums.length;
        for (int i = 0; i < n; i++) {
            if (check(i)) return true;
        }
        return false;
    }
    boolean check(int start) {
        int cur = start;
        boolean flag = nums[start] > 0;
        int k = 1;
        while (true) {
            if (k > n) return false;
            int next = ((cur + nums[cur]) % n + n ) % n;
            if (flag && nums[next] < 0) return false;
            if (!flag && nums[next] > 0) return false;
            if (next == start) return k > 1;
            cur = next;
            k++;
        }
    }

    /**
     * 作者：AC_OIer
     *         链接：https://leetcode.cn/problems/circular-array-loop/solution/gong-shui-san-xie-yi-ti-shuang-jie-mo-ni-ag05/
     *         来源：力扣（LeetCode）
     *         著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}


