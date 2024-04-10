package maxScore.problem;

import com.alibaba.fastjson.JSON;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[] num1 = new int[]{1, 3, 3, 2};
//        int[] num2 = new int[]{2, 1, 3, 4};
//        int k = 3;
        int[] num1 = new int[]{4, 2, 3, 1, 1};
        int[] num2 = new int[]{7, 5, 10, 9, 6};
        int k = 1;
        long res = solution.maxScore(num1, num2, k);
        System.out.println(res);
    }


    public long maxScore(int[] nums1, int[] nums2, int k) {
        //问题转换为找出num1长度为k的子序列
        int n = nums1.length;
        quick(nums1, nums2, 0, n - 1);
//
        System.out.println(JSON.toJSONString(nums1));
        System.out.println(JSON.toJSONString(nums2));


        long sum = 0;
        long max = 0;

        int[] arrMin = new int[k];
        for (int i = 0; i < k; i++) {
            sum += nums1[i];
            arrMin[i] = nums2[i];
        }


        max = sum * getMinValue(arrMin);

        for (int i = k; i < n; i++) {
            sum = sum + nums1[i] - nums1[i - k];
            arrMin[i % k] = nums2[i];
            long tempMax = sum * getMinValue(arrMin);
            if (tempMax > max) {
                max = tempMax;
            }
        }

        return max;


    }

    private int getMinValue(int[] arrMin) {
        int min = arrMin[0];
        for (int i = 0; i < arrMin.length; i++) {
            if (arrMin[i] < min) {
                min = arrMin[i];
            }
        }
        return min;
    }

    private void quick(int[] nums1, int[] nums2, int left, int right) {

        if (left < right) {

            int pivot = partition(nums1, nums2, left, right);
            quick(nums1, nums2, left, pivot - 1);
            quick(nums1, nums2, pivot + 1, right);
        }
    }

    private int partition(int[] nums1, int[] nums2, int left, int right) {

        int largest = nums1[left];
        int temp = nums2[left];
        while (left < right) {
            if (left < right && nums1[right] > largest) {
                right--;
            }
            nums1[left] = nums1[right];
            nums2[left] = nums2[right];
            if (left < right && nums1[left] <= largest) {
                left++;
            }
            nums1[right] = nums1[left];
            nums2[right] = nums2[left];
        }
        nums1[left] = largest;
        nums2[left] = temp;
        return left;
    }
}
