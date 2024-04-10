package findClosestElements.problem;

import java.util.ArrayList;
import java.util.List;

class Solution2 {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int n = arr.length, l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (arr[mid] <= x) l = mid;
            else r = mid - 1;
        }
        r = r + 1 < n && Math.abs(arr[r + 1] - x) < Math.abs(arr[r] - x) ? r + 1 : r;
        int i = r - 1, j = r + 1;
        while (j - i - 1 < k) {
            if (i >= 0 && j < n) {
                if (Math.abs(arr[j] - x) < Math.abs(arr[i] - x)) j++;
                else i--;
            } else if (i >= 0) {
                i--;
            } else {
                j++;
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int p = i + 1; p <= j - 1; p++) ans.add(arr[p]);
        return ans;
    }
}

//作者：AC_OIer
//        链接：https://leetcode.cn/problems/find-k-closest-elements/solution/by-ac_oier-8xh5/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。