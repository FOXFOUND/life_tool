package threeSum.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution3 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return ans;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i - 1] == nums[i]) continue;
            if (nums[i] > 0) break;
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    //left指向最后一个相同的数
                    while (left < right && nums[left + 1] == nums[left]) left++;
                    //right指向最后一个相同的数
                    while (left < right && nums[right - 1] == nums[right]) right--;

                    // left++,当前数一定大于0,所以left++,之后需要right--
                    left++;
                    right--;
                }

                if (sum < 0) left++;
                if (sum > 0) right--;
            }

        }
        return ans;
    }
}
