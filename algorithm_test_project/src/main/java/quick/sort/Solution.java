package quick.sort;

import com.alibaba.fastjson.JSON;

public class Solution {

    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 6, 8, 2, 3};
        System.out.println(JSON.toJSONString(nums));
        Solution solution = new Solution();
        solution.quickSort(nums);
        System.out.println(JSON.toJSONString(nums));
    }

    private void quickSort(int[] nums) {
        int n = nums.length;
        quickSortSub(nums, 0, n - 1);
    }

    private void quickSortSub(int[] nums, int i, int n) {

        if (n <= 0) {
            return;
        }

        int middle = nums[i];
        int left = i;
        int right = n;

        while (left < right) {

            while (nums[right] >= middle && right > 0) {
                right--;
            }

            nums[left] = nums[right];

            while (nums[left] < middle && left < n) {
                left++;
            }
            nums[right] = nums[left];

        }
        nums[left] = middle;
        quickSortSub(nums, i, left - 1);
        quickSortSub(nums, left + 1, n);
    }


}
