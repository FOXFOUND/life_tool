package hIndex.problem;

class Solution3 {

    /**
     * 
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        int n = citations.length;
        int lo = 0, hi = citations.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (citations[mid] < n - mid) lo = mid + 1;
            else hi = mid - 1;
        }
        return n - lo;
    }
}
//
//作者：killer-cs
//        链接：https://leetcode.cn/problems/h-index-ii/solution/er-fen-by-killer-cs-wv8m/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。