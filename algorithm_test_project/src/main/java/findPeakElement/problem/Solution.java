package findPeakElement.problem;

class Solution {
    public int findPeakElement(int[] nums) {
        int idx = 0;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] > nums[idx]) {
                idx = i;
            }
        }
        return idx;
    }
}

//作者：LeetCode-Solution
//        链接：https://leetcode.cn/problems/find-peak-element/solution/xun-zhao-feng-zhi-by-leetcode-solution-96sj/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。