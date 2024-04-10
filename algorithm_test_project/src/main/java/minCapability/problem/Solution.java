package minCapability.problem;

class Solution {

    /**
     * 1.题目中的房子里的金额对于本题是迷惑人的
     *
     * 实际可以将问题转换为 是否存在某个数字x,使用一个跳跃区间(偷/不偷)中满足以下条件
     * 1.跳跃区间的长度是k
     * 2.跳跃区间的最小值是数字x
     *
     * 根据以上的结论,那么就可以引出二分查找,取定位这个数字x, 由于题目有解,那么这个x一定就存在
     * 所以只要不断的二分通过遍及数组统计大于x的数量和k的关系,一定存在某个x使用,区间长度为x,那么这个x就是问题的解
     * 并且将这个x,给映射到对应区间里面的最小值了.即和题目的金额值联系起来了
     *
     * 作者：anonymous-39
     * 链接：https://leetcode.cn/problems/house-robber-iv/solution/yi-yu-dian-xing-meng-zhong-ren-by-anonym-e3yn/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param nums
     * @param k
     * @return
     */
    public int minCapability(int[] nums, int k) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        int left = 1, right = (int) 1e9, ans = right;
        while (left <= right) {
            int mid = (left + right) >> 1;
            int[] dp = new int[n];
            if (nums[0] <= mid) dp[0] = 1;
            //dp代表对于遍历到i个元素时存在小于mid的节点数量(累计值)

            /**
             *  case 1: num[1] = num[1] < mid ? 假设 dp[0]是1,但是由于是偷窃问题,所以num[1] 设置为1 ;
             *  case 2: num[1] = num[1] > mid ? 假设 dp[0]是1,根据dp的定义,num[1] = 1;
             *  case 3: num[1] = num[1] > min ? 假设 dp[0]是0,但是由于是偷窃问题,所以num[1] 设置为1 ;
             *  case 4: num[1] = num[1] > mid ? 假设 dp[0]是0,根据dp的定义,num[1] = 0;
             *
             *  所以综上:
             *  当num[0] > mid && num[1] > mid , nums[1] 设置为0,其余的情况都设置为1
             */
            dp[1] = Math.min(nums[0], nums[1]) <= mid ? 1 : 0;
            for (int i = 2; i < n; i++) {
                if (nums[i] > mid) {
                    // 此房屋的金额nums[i]大于窃取能力mid，无法窃取，只能顺延dp[i-1]保证尽量大
                    dp[i] = dp[i - 1];
                } else {
                    // 此房屋可以窃取, 可选择窃取（dp[i-2]+1）和不窃取（dp[i-1]）的最优值
                    dp[i] = Math.max(dp[i - 1], dp[i - 2] + 1);
                }
            }
            if (dp[n - 1] >= k) {
                // 只要窃取的房屋数量>=k即可成功，保存答案，继续寻找更小的符合条件的窃取能力
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }
}