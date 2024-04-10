package threeSum.problem;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

public class Solution2 {

    Set<String> existSet = new HashSet<>();
    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> threeSum(int[] nums) {


        Arrays.sort(nums);
        int n = nums.length;
        existNum(nums, 0, n-1);
        return res;

    }

    private void existNum(int[] nums, int left, int right) {

        if (left >= right) {
            return;
        }


        for (int i = left + 1; i < right; i++) {

            if (nums[left] + nums[right] + nums[i] == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(nums[left] + "_");
                stringBuilder.append(nums[i] + "_");
                stringBuilder.append(nums[right] + "_");
                if (existSet.contains(stringBuilder.toString())) {
                    continue;
                }
                List<Integer> ans = new ArrayList<>();
                ans.add(nums[left]);
                ans.add(nums[right]);
                ans.add(nums[i]);
                existSet.add(stringBuilder.toString());
                res.add(ans);
            }
        }

        existNum(nums, left + 1, right);
        existNum(nums, left, right - 1);
    }
}
