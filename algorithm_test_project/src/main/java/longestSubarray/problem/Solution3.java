package longestSubarray.problem;

class Solution3 {
    public int longestSubarray(int[] nums) {

        /**
         * 看似滑窗问题，滑动窗口也一定能做。
         *
         * 但是当作以当前位置为结尾的最长含有一个0的子数组的长度，写起来更简单。
         *
         * z2是前一个0的位置， z1是z2前一个0的位置
         *
         * 当前位置和z1的距离就是可能的答案
         *
         * 作者：wa-pian-d
         * 链接：https://leetcode.cn/problems/longest-subarray-of-1s-after-deleting-one-element/solution/java-1493-shan-diao-yi-ge-yuan-su-yi-hou-sol0/
         * 来源：力扣（LeetCode）
         * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
         */
        int ans = 0, z1 = -1, z2 = -1;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num == 0) {
                z1 = z2;
                z2 = i;
            }
            // z1(0)----z2(0) --- i , 减去1代表把z2减去,变成 z1----------i
            // 由于z1代表1之前的那个0,所以统计1的时候  (i-1) - (z1 +1 ) +1  = (right -1) - left + 1

            //整体的思路是 : 右边是不断增大的,不管什么情况,我先减去1个长度,只要找到left即可,left只要找到left左边的0即可
            //通过ans记录最大值
            ans = Math.max(ans, (i - 1) - z1);
        }
        return ans;
    }
}

//作者：wa-pian-d
//        链接：https://leetcode.cn/problems/longest-subarray-of-1s-after-deleting-one-element/solution/java-1493-shan-diao-yi-ge-yuan-su-yi-hou-sol0/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
