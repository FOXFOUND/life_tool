package heap.sort;

import com.alibaba.fastjson.JSON;

public class Solution2 {


    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 6, 8, 2, 3};
        System.out.println(JSON.toJSONString(nums));
        Solution2 solution = new Solution2();
        solution.sort(nums);
        System.out.println(JSON.toJSONString(nums));
    }

    private void sort(int[] nums) {


        //获取数组末级父节点
        int start = nums.length / 2;

        for (int i = nums.length - 1; i >= 0; i--) {
            buildHeap(nums, start, i);
            swap(nums, 0, i);
        }
    }

    private void buildHeap(int[] nums, int start, int limit) {

        for (int j = start; j >= 0; j--) {
            int left = 2 * j + 1;
            int right = 2 * j + 2;
            int max = j;

            //防止越界
            if (right <= limit) {
                max = nums[left] > nums[right] ? left : right;
            } else if (left <= limit) {
                max = left;
            }

            if (nums[max] > nums[j]) {
                swap(nums, max, j);
            }

        }

    }

    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}
