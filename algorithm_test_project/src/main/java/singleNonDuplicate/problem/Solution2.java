package singleNonDuplicate.problem;

class Solution2 {
    public int singleNonDuplicate(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = (high - low) / 2 + low;
            if (nums[mid] == nums[mid ^ 1]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return nums[low];
    }
}

//作者：LeetCode-Solution
//        链接：https://leetcode.cn/problems/skFtm2/solution/pai-xu-shu-zu-zhong-zhi-chu-xian-yi-ci-d-jk8w/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
