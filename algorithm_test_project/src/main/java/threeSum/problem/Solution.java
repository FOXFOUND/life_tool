package threeSum.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        List res = solution.threeSum(nums);
        System.out.println(JSON.toJSONString(res));
    }

    public List<List<Integer>> threeSum(int[] nums) {

        Set<String> existSet = new HashSet<>();
        Arrays.sort(nums);
        int underIndex = 0;
        int n = nums.length - 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0) {
                underIndex++;
            } else {
                break;
            }
        }

        if (underIndex == n) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < underIndex; i++) {
            int left = underIndex, right = n;
            while (left < right) {
                if (nums[left] + nums[right] == -nums[i]) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(nums[left] + "_");
                    stringBuilder.append(nums[right] + "_");
                    stringBuilder.append(nums[i] + "_");
                    if (existSet.contains(stringBuilder.toString())) {
                        continue;
                    }
                    List<Integer> ans = new ArrayList<>();
                    ans.add(nums[left]);
                    ans.add(nums[right]);
                    ans.add(nums[i]);
                    existSet.add(stringBuilder.toString());
                    res.add(ans);
                } else if (nums[left] + nums[right] > - -nums[i]) {
                    right--;
                } else {
                    left++;
                }
            }

        }
        return res;

    }
}
