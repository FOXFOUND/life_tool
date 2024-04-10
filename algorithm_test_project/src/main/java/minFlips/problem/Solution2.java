package minFlips.problem;

class Solution2 {
    public int minFlips(int a, int b, int c) {
        int val1 = 0, val2 = 0, val3 = 0;
        //记录翻转的次数
        int ans = 0;
        while (a != 0 || b != 0 || c != 0) {
            //获取a,b,c的最低位va11,val2,val3
            val1 = a & 1;
            val2 = b & 1;
            val3 = c & 1;
            //讨论val3的情况
            if (val3 == 0) {
                if (val1 == 1) {
                    if (val2 == 1) ans += 2;
                    else ans++;
                } else if (val2 == 1) {
                    ans++;
                }
            } else {
                if (val1 == 0 && val2 == 0) ans++;
            }
            a >>= 1;
            b >>= 1;
            c >>= 1;
        }
        return ans;
    }
}
//
//作者：yu_0527
//        链接：https://leetcode.cn/problems/minimum-flips-to-make-a-or-b-equal-to-c/solution/wei-by-yu_0527-swho/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。