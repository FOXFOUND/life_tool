package longestOnes.problem;

public class Solution3 {
    public int longestOnes(int[] A, int K) {
        int left = 0;//窗口左边的位置
        int maxWindow = 0;//窗口的最大值
        int zeroCount = 0;//窗口中0的个数
        for (int right = 0; right < A.length; right++) {
            if (A[right] == 0) {
                zeroCount++;
            }
            //如果窗口中0的个数超过了K，要缩小窗口的大小，直到0的个数
            //不大于K位置
            while (zeroCount > K) {
                if (A[left++] == 0)
                    zeroCount--;
            }
            //记录最大的窗口
            maxWindow = Math.max(maxWindow, right - left + 1);
        }
        return maxWindow;
    }

//    作者：sdwwld
//    链接：https://leetcode.cn/problems/max-consecutive-ones-iii/solution/hua-dong-chuang-kou-de-liang-chong-jie-j-8ses/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
