package minSubarray.problem;


import java.util.Arrays;

/**
 * 给你一个正整数数组 nums，请你移除 最短 子数组（可以为 空），使得剩余元素的 和 能被 p 整除。 不允许 将整个数组都移除。
 * <p>
 * 请你返回你需要移除的最短子数组的长度，如果无法满足题目要求，返回 -1 。
 * <p>
 * 子数组 定义为原数组中连续的一组元素。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/make-sum-divisible-by-p
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {

    public int minSubarray(int[] nums, int p) {
        //排序
        Arrays.sort(nums);
        //前缀和
        int[] numsSum = new int[nums.length];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            numsSum[i] = sum;
        }
        //判断是否能整除
        if (numsSum[nums.length - 1] < p) {
            return -1;
        }

        if (numsSum[nums.length - 1] % p == 0) {
            return 0;
        }

        int checkFlag = 1;
        for (int i = numsSum.length - 1; i >= 0; i--) {

            if (numsSum[i] % p == 0) {
                checkFlag = 0;
            }
        }

        if (checkFlag == 1) {
            return -1;
        }

        //可以整除,找到移除的最小元素
        int remainder = numsSum[numsSum.length - 1] % p;
        int leftThrow = Integer.MAX_VALUE, rightThrow = Integer.MAX_VALUE;

        //从左找余数
        for (int i = 0; i < numsSum.length; i++) {
            if (numsSum[i] == remainder) {
                leftThrow = i;
                break;
            }
        }

        //从右找最近的倍数

        for (int i = numsSum.length - 1; i >= 0; i--) {
            if (numsSum[i] % p == 0) {
                rightThrow = numsSum.length - 1 - i;
                break;
            }
        }

        int res = leftThrow < rightThrow ? leftThrow : rightThrow;

        //防止没找到
        if (res == Integer.MAX_VALUE) {
            return -1;
        }

        return res;

    }


    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[] nums = new int[]{3, 1, 4, 2};
//        int p = 6;  //1

        int[] nums = new int[]{6, 3, 5, 2};
        int p = 9;  //2

        // 错误原因,可能不是移除最小的数
        int res = solution.minSubarray(nums, p);
        System.out.println(res);
    }
}
