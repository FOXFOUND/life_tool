package widthOfBinaryTree.problem;

import java.util.Arrays;

class Solution3 {
    public int maximumTastiness(int[] price, int k) {
        Arrays.sort(price);

        // 二分模板·其三（开区间写法）https://www.bilibili.com/video/BV1AP41137w7/
        int left = 0, right = price[price.length - 1];
        while (left + 1 < right) { // 开区间不为空
            // 循环不变量：
            // f(left) >= k
            // f(right) < k
            int mid = left + (right - left) / 2;
            if (f(price, mid) >= k) left = mid; // 下一轮二分 (mid, right)
            else right = mid; // 下一轮二分 (left, mid)
        }
        return left;
    }

    private int f(int[] price, int d) {
        //cnt代表有多少个 最大值-最小值大于k的相邻区间个数,由于pre是price[0] 即数组的最小的价格
        //,所以至少有一个这种区间即 price[n-1] - print[0]
        int cnt = 1, pre = price[0];
        for (int p : price) {
            if (p - pre >= d) {
                cnt++;
                pre = p;
            }
        }
        return cnt;
    }
}

//作者：endlesscheng
//        链接：https://leetcode.cn/problems/maximum-tastiness-of-candy-basket/solution/er-fen-da-an-by-endlesscheng-r418/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。