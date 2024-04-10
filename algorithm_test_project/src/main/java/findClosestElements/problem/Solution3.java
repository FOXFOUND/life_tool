package findClosestElements.problem;

import java.util.ArrayList;
import java.util.List;

class Solution3 {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // right 取的是low的位置,即right的位置一定小于x
        int right = binarySearch(arr, x);
        // left 取right的左侧的一位
        int left = right - 1;
        //经历k次操作
        while (k-- > 0) {
            //由于right的位置可能是0,所以left可能是负数
            if (left < 0) {
                right++;
                //由于right的位置可能是 n-1
            } else if (right >= arr.length) {
                left--;
            } else if (x - arr[left] <= arr[right] - x) {
                left--;
            } else {
                right++;
            }
        }
        List<Integer> ans = new ArrayList<Integer>();
        /**
         * 定义分析:由于left可能是负数,所以要从left+1开始, right可能是n- 1,所以小于right
         */
        /**
         * 数据分析: 假设经过二分搜索的时候 1<=right<n-1, 第一次right + 1,到达了middle的位置,此时arr[right]>=x,等于的情况不用考虑
         * 需要考虑arr[right]>x的情况, | arr[right] -x | > | arr [left] - x|,此时left--,目前包含的元素是 low,low-1, 此时right = low+1, left = low-2;
         * 可以推论出经过k次操作,如果要输出k个数, 需要 [left+1,right)
         */
        for (int i = left + 1; i < right; i++) {
            ans.add(arr[i]);
        }
        return ans;
    }

    public int binarySearch(int[] arr, int x) {

        int low = 0, high = arr.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            //high的右侧,一定大于等于x
            if (arr[mid] >= x) {
                high = mid;
            } else {
                //low < arr[mid], low的左侧一定小于x
                low = mid + 1;
            }
        }
        return low;
    }
}

//作者：LeetCode-Solution
//        链接：https://leetcode.cn/problems/find-k-closest-elements/solution/zhao-dao-k-ge-zui-jie-jin-de-yuan-su-by-ekwtd/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。