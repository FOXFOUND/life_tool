package maxScore.problem;

import java.util.Arrays;
import java.util.PriorityQueue;

class Solution2 {
    public long maxScore(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;

        Integer[] idxs = new Integer[n];
        for (int i = 0; i < n; i++) {
            idxs[i] = i;
        }
        Arrays.sort(idxs, (i, j) -> nums2[j] - nums2[i]);

        long res = 0L;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        long sum1 = 0L;
        for (int idx : idxs) {
            int x = nums1[idx];
            int y = nums2[idx];

            while (minHeap.size() > k - 1) {
                sum1 -= minHeap.poll();
            }
            minHeap.offer(x);
            sum1 += x;

            if (minHeap.size() == k) {
                long cur = sum1 * y;
                res = Math.max(res, cur);
            }
        }

        return res;
    }
}

//作者：XingHe_XingHe
//        链接：https://leetcode.cn/problems/maximum-subsequence-score/solution/cpython3java-1-by-xinghe_xinghe-l1c2/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
