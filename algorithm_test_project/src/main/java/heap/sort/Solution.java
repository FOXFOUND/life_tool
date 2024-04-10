package heap.sort;

import com.alibaba.fastjson.JSON;

public class Solution {

    public void sort(int[] nums) {
        //堆排序的思想是 将最大的数或最小的数放到堆顶 , 堆是将二叉树的结构进行抽象到数组中

        //构建大顶堆 ,当前元素为 i , 他的左节点为 2 *i +1 , 他的右节点是 2 * i + 2 , 举例 [1,2,3]


        //当只有一个元素 start = 0
        int start = nums.length / 2;

        for (int i = nums.length - 1; i >= 0; i--) {
            buildHeap(nums, start, i);
            swap(nums, 0, i);

        }


    }

    private void buildHeap(int[] nums, int start, int limit) {
        for (int i = start; i >= 0; i--) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int max = i;
            //当三者中的最大值,送达根部,需要防止数组越界
            if (right <= limit) {
                max = nums[left] > nums[right] ? left : right;
            } else if(left <=limit) {
                max = left;
            }

            if (nums[max] > nums[i]) {
                swap(nums, max, i);
            }

        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 6, 8, 2, 3};
        System.out.println(JSON.toJSONString(nums));
        Solution solution = new Solution();
        solution.sort(nums);
        System.out.println(JSON.toJSONString(nums));

    }

}
