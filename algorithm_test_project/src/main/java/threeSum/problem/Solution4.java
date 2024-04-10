package threeSum.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution4 {
    public List<List<Integer>> threeSum(int[] nums) {

        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {

            int current = nums[i];

            if (current > 0) {
                break;
            }

            int left = i + 1;
            int right = n - 1;

            while (left < right) {

                if (nums[left] + nums[right] + current < 0) {
                    left++;
                }

                if (nums[left] + nums[right] + current > 0) {
                    right--;
                }

                if (left < right && nums[left] + nums[right] + current == 0) {

                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[left]);
                    temp.add(nums[right]);
                    temp.add(current);
                    res.add(temp);

                    while (i + 1 < n && nums[i + 1] == nums[i]) {
                        i++;
                    }

                    while (left + 1 < n && nums[left + 1] == nums[left]) {
                        left++;
                    }

                    while (right - 1 >= 0 && nums[right - 1] == nums[right]) {
                        right--;
                    }

                    left++;
                    right--;
                }
            }

        }

        return res;

    }
}
