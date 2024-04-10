package quick.sort;

import java.util.Arrays;

public class Solution3 {

    public static void main(String[] args) {

        int[] arr = {5, 3, 8, 4, 2};
        Solution3 solution3 = new Solution3();
        solution3.quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public void quickSort(int[] nums) {
        int n = nums.length;
        quick(nums, 0, n - 1);
    }

    private void quick(int[] nums, int left, int right) {
        if (left < right) {
            int pivot = partion(nums, left, right);
            quick(nums, left, pivot - 1);
            quick(nums, pivot + 1, right);
        }
    }

    private int partion(int[] nums, int left, int right) {

        int pivot = nums[left];
        while (left < right) {
            if (left < right && nums[right] > pivot) {
                right--;
            }
            nums[left] = nums[right];
            if (left < right && nums[left] <= pivot) {
                left++;
            }
            nums[right] = nums[left];
        }

        nums[left] = pivot;
        return left;
    }
}
