package kSmallestPairs.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Solution3 {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        int leftPoint = 0, rightPoint = 0;
        List<Integer> pairList = new ArrayList<>();
        pairList.add(nums1[leftPoint]);
        pairList.add(nums2[rightPoint]);
        res.add(pairList);
        int i = 1;
        HashSet<String> existKey = new HashSet<>();
        existKey.add(0 + "_" + 0);
        boolean numOneArrFlag = false;
        boolean numTwoArrFlag = false;
        while (i < k) {


            if (leftPoint == nums1.length - 1) {
                numOneArrFlag = true;
            }

            if (rightPoint == nums2.length - 1) {
                numTwoArrFlag = true;
            }


            //左加
            if (numTwoArrFlag || (!numOneArrFlag
                    && nums1[leftPoint + 1] + nums2[rightPoint] < nums1[leftPoint] + nums2[rightPoint + 1])) {
                leftPoint++;
                for (int j = 0; j <= rightPoint; j++) {
                    String key = leftPoint + "_" + j;
                    if (existKey.contains(key)) {
                        continue;
                    }
                    if (leftPoint >= nums1.length) {
                        numOneArrFlag = true;
                        leftPoint = nums1.length - 1;
                        break;
                    }
                    pairList = new ArrayList<>();
                    pairList.add(nums1[leftPoint]);
                    pairList.add(nums2[j]);
                    res.add(pairList);
                    existKey.add(key);
                    i++;
                    if (i >= k) {
                        break;
                    }
                }
            } else {
                rightPoint++;
                for (int j = 0; j <= leftPoint; j++) {
                    String key = j + "_" + rightPoint;
                    if (existKey.contains(key)) {
                        continue;
                    }
                    if (rightPoint >= nums2.length) {
                        numTwoArrFlag = true;
                        rightPoint = nums2.length - 1;
                        break;
                    }
                    pairList = new ArrayList<>();
                    pairList.add(nums1[j]);
                    pairList.add(nums2[rightPoint]);
                    res.add(pairList);
                    existKey.add(key);
                    i++;
                    if (i >= k) {
                        break;
                    }
                }

            }

            if (numTwoArrFlag && numOneArrFlag) {
                break;
            }

        }
        return res;
    }

    public static void main(String[] args) {
        Solution3 solution = new Solution3();
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
