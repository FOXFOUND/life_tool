package heap.sort;

import com.alibaba.fastjson.JSON;

public class Solution5 {

    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 6, 8, 2, 3};
        System.out.println(JSON.toJSONString(nums));
        Solution5 solution = new Solution5();
        solution.heapSort(nums);
        System.out.println(JSON.toJSONString(nums));
    }

    public void heapSort(int[] nums) {

        int n = nums.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapfiy(nums, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            swap(nums, 0, i);
            heapfiy(nums, i, 0);
        }
    }

    private void heapfiy(int[] nums, int n, int i) {

        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int max = i;
        if (left < n && nums[left] > nums[max]) {
            max = left;
        }
        if (right < n && nums[right] > nums[max]) {
            max = right;
        }

        if (max != i) {
            swap(nums, i, max);
            heapfiy(nums, n, max);
        }

    }

    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}
