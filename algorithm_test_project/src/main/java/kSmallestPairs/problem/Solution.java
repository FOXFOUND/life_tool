package kSmallestPairs.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定两个以升序排列的整数数组 nums1 和 nums2 , 以及一个整数 k 。
 * <p>
 * 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。
 * <p>
 * 请找到和最小的 k 个数对 (u1,v1),  (u2,v2)  ...  (uk,vk) 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/qn8gGX
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        int leftPoint = 0, rightPoint = 0;
        int i = 0;
        while (i < k
                && leftPoint < nums1.length
                && rightPoint < nums2.length) {
            List<Integer> pairList = new ArrayList<>();
            pairList.add(nums1[leftPoint]);
            pairList.add(nums2[rightPoint]);
            res.add(pairList);
            i++;


            if (leftPoint == nums1.length - 1) {
                rightPoint++;
                continue;
            }

            if (rightPoint == nums2.length - 1) {
                leftPoint++;
                continue;
            }


            //左右指针都没有到边界
            if (leftPoint < nums1.length - 1
                    && rightPoint < nums2.length - 1) {
                //左加
                if (nums1[leftPoint + 1] + nums2[rightPoint] < nums1[leftPoint] + nums2[rightPoint + 1]) {
                    leftPoint++;
                } else {
                    rightPoint++;
                }
                continue;
            }


        }

        return res;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[] nums1 = new int[]{1, 7, 11};
//        int[] nums2 = new int[]{2, 4, 6};    // [1,2],[1,4],[1,6]
//        int k = 3;

//        int[] nums1 = new int[]{1, 2};
//        int[] nums2 = new int[]{3};    // [1,3],[2,3]
//        int k = 3;

        int[] nums1 = new int[]{1, 1, 2};    // [[1,1],[1,1],[1,2],[1,3],[2,3]]
        int[] nums2 = new int[]{1, 2, 3};    // [[1,1],[1,1],[2,1],[1,2],[1,2],[2,2],[1,3],[1,3],[2,3]]
        int k = 10;
        List<List<Integer>> res = solution.kSmallestPairs(nums1, nums2, k);
        System.out.println(JSON.toJSONString(res));
    }
}
