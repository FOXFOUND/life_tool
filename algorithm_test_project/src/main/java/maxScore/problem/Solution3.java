package maxScore.problem;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.PriorityQueue;

class Solution3 {

    public static void main(String[] args) {
        Solution3 solution = new Solution3();
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
        int n = nums1.length;
        long ans = 0L;
        Integer[] sorts = new Integer[n];
        for (int i = 0; i < n; i++) sorts[i] = i;
        //System.out.println(JSON.toJSONString(sorts));
        //nums2按照降序排序
        Arrays.sort(sorts, (a, b) -> nums2[b] - nums2[a]);
        //System.out.println(JSON.toJSONString(sorts));



        PriorityQueue<Integer> pq = new PriorityQueue<>();
        long sum = 0L;
        //维护了num2按照排序nums1的关系
        for (int i = 0; i < k - 1; i++) {
            sum += nums1[sorts[i]];
            pq.offer(nums1[sorts[i]]);
        }

        //
        for (int i = k - 1; i < n; i++) {
            //System.out.println(sorts[i]);
            sum += nums1[sorts[i]];
            pq.offer(nums1[sorts[i]]);
            ans = Math.max(ans, nums2[sorts[i]] * sum);
            sum -= pq.poll();
        }

        return ans;
    }
}

//作者：yu-niang-niang
//        链接：https://leetcode.cn/problems/maximum-subsequence-score/solution/yu-niang-niang-2542-zui-da-zi-xu-lie-de-vdxv6/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
