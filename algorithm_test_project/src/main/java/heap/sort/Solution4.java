package heap.sort;

import com.alibaba.fastjson.JSON;

public class Solution4 {

    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 6, 8, 2, 3};
        System.out.println(JSON.toJSONString(nums));
        Solution4 solution = new Solution4();
        solution.heapSort(nums);
        System.out.println(JSON.toJSONString(nums));
    }

    public void heapSort(int[] nums) {
        int n = nums.length;

        //构建大顶堆
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            swap(nums, 0, i);
            heapify(nums, i, 0);
        }

    }

    private static void heapify(int[] nums, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && nums[left] > nums[largest]) {
            largest = left;
        }
        if (right < n && nums[right] > nums[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(nums, i, largest);
            heapify(nums, n, largest);
        }

    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;

    }
}
