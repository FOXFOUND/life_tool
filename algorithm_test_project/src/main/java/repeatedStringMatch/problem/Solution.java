package repeatedStringMatch.problem;

/**
 * 给定两个字符串 a 和 b，寻找重复叠加字符串 a 的最小次数，使得字符串 b 成为叠加后的字符串 a 的子串，如果不存在则返回 -1。
 * <p>
 * 注意：字符串 "abc" 重复叠加 0 次是 ""，重复叠加 1 次是 "abc"，重复叠加 2 次是 "abcabc"。
 * <p>
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/repeated-string-match
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class Solution {
    public int repeatedStringMatch(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int ans = 0;
        //不断地重复添加a,直到a的长度超过b
        while (sb.length() < b.length() && ++ans > 0) sb.append(a);
        sb.append(a);
        //计算hash值
        int idx = strHash(sb.toString(), b);
        if (idx == -1) return -1;

        // idx = a+b 字符串的起始位置,但是idx小于a的长度,即 0<=idx<=a.length,因此最多加一次a就可以了
        if (idx + b.length() > a.length() * ans) {
            //超过了目前a的长度,所以加一次a就可以包含b
            return ans + 1;
        } else {
            //目前的a已经可以包含b
            return ans;
        }
    }

    /**
     * https://www.dotcpp.com/course/964
     *
     * @param ss
     * @param b
     * @return
     */
    int strHash(String ss, String b) {
        //进制 PPP 常用的值有31、131、1313、13131、131313等，用这些数值能有效避免碰撞。
        int P = 131;
        int n = ss.length(), m = b.length();
        String str = ss + b;
        int len = str.length();
        //h【i】的意义就是求区间【0~i】的字符串的哈希值
        //p【i】 是记录第i位字符的乘以P的次数-权值
        int[] h = new int[len + 10], p = new int[len + 10];
        p[0] = 1;
        //通过前缀和 h(i) 记录hash值,方便计算
        for (int i = 0; i < len; i++) {
            p[i + 1] = p[i] * P;
            h[i + 1] = h[i] * P + str.charAt(i);
        }
        int r = len, l = r - m + 1;

        // h[i]=h[i-1] * P + str.charAt(i);
        // h[i+1]=h[i-1] * P^2 + str.charAt(i) * P +str.charAt(i + 1)
        // h[i+2]=h[i-1] * P^3 + str.charAt(i) * P^2 +str.charAt(i + 1) * P + str.charAt(i + 2)

        //推论 - 相当于对字符串求hash值
        //h[r] - h[l - 1] * p[r - l + 1] = str.charAt(l - 1) * P ^ (r-l) +  str.charAt(l) * P ^ r +....+  str.charAt(r);
        int target = h[r] - h[l - 1] * p[r - l + 1]; // b 的哈希值
        //通过右移一位,减少边界条件0的判断
        for (int i = 1; i <= n; i++) {
            int j = i + m - 1;
            int cur = h[j] - h[i - 1] * p[j - i + 1]; // 子串哈希值
            //返回满足条件的开始位置 - 在a+b字符串中的位置
            if (cur == target) return i - 1;
        }
        //没有找到
        return -1;
    }
}

//作者：AC_OIer
//        链接：https://leetcode.cn/problems/repeated-string-match/solution/gong-shui-san-xie-yi-ti-san-jie-qia-chan-3hbr/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
