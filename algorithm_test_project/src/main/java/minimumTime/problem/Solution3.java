package minimumTime.problem;

public class Solution3 {
    public long minimumTime(int[] time, int totalTrips) {
        // 其实下界是min(time)，我们可以直接将1作为下界，就不用去顺序遍历一次了。因为二分遍历的时间复杂度O(logn)总是小于顺序遍历的时间复杂度O(n)，所以我们这样处理虽然可能会导致下界更小，但总体来看更省时间。
        long l = 1;
        // 更为准确的上界是 (long)totalTrips * min(time)。我们直接用time[0]计算，就不用去顺序遍历一次了。因为二分遍历的时间复杂度O(logn)总是小于顺序遍历的时间复杂度O(n)，所以我们这样处理虽然可能会导致上界更大，但总体来看更省时间。
        long r = (long)totalTrips * time[0];

        while(l <= r) {
            long mid = l + ((r - l) >> 1);
            if (cal(time, mid, totalTrips) < totalTrips) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }

    // curTime时刻，已经完成的趟数
    private long cal(int[] time, long curTime, int totalTrips) {
        int res = 0;
        for (int t : time) {
            res += curTime / t;
            if (res > totalTrips) {
                return res;
            }
        }
        return res;
    }


//    作者：xiaoxi666
//    链接：https://leetcode.cn/problems/minimum-time-to-complete-trips/solution/er-fen-ji-ke-by-xiaoxi666-0bf9/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
