package minCapability.problem;

import java.util.Arrays;

class Solution2 {
    public int minCapability(int[] nums, int k) {
        int low = 1, high = Arrays.stream(nums).max().getAsInt();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (canStealKHouses(nums, k, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public boolean canStealKHouses(int[] nums, int k, int capability) {
        int count = 0;
        int n = nums.length;
        int prev = -2;
        for (int i = 0; i < n && count < k; i++) {
            if (i - prev >= 2 && nums[i] <= capability) {
                count++;
                prev = i;
            }
        }
        return count >= k;
    }
}

//作者：stormsunshine
//        链接：https://leetcode.cn/problems/house-robber-iv/solution/2560-da-jia-jie-she-iv-by-stormsunshine-u0rx/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。