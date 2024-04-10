package hIndex.problem;

import java.util.Arrays;

class Solution5 {
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int h = 0, i = citations.length - 1;
        while (i >= 0 && citations[i] > h) {
            h++;
            i--;
        }
        return h;
    }
}

//作者：LeetCode-Solution
//        链接：https://leetcode.cn/problems/h-index/solution/h-zhi-shu-by-leetcode-solution-fnhl/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。