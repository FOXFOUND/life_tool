package minimumTime.problem;

import java.util.Arrays;

public class Solution4 {
    public long minimumTime(int[] time, int totalTrips) {
        Arrays.sort(time);
        long left = 0;
        // 记录当前最大完成旅途的时间
        long right = 1L*  time[0] * totalTrips ;
        // 在最小时间和最大时间之间搜索符合条件的时间
        while (left < right ){
            long mid = left + (right - left) /2;
            // 记录当前完成旅途的车
            long trips = 0;
            // 遍历每个车次需要完成的时间
            for(int t : time){
                if(mid < t){
                    break;
                }
                // 记录当前时间能完成的趟数
                trips += mid / t;
            }
            // 如果当前完成的车次已经到达了完成的次数则缩小范围 搜索前面时间范围
            if(trips >= totalTrips){
                right = mid;
            } else {
                // 反之搜索后面时间范围
                left = mid + 1;
            }
        }
        return left;
    }

//    作者：alascanfu
//    链接：https://leetcode.cn/problems/minimum-time-to-complete-trips/solution/zhou-sai-di-282chang-zhou-sai-pu-tao-che-s4eo/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
