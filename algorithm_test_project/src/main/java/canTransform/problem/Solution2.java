package canTransform.problem;

class Solution2 {
    public boolean canTransform(String start, String end) {
        int n = start.length(), i = 0, j = 0;
        while (i < n || j < n) {
            //将i移动到不是x的位置
            while (i < n && start.charAt(i) == 'X') i++;
            //将j移动到不是x的位置
            while (j < n && end.charAt(j) == 'X') j++;
            //如果i或者j到结尾了,判断另一个是否到结尾
            if (i == n || j == n) return i == j;
            //由于i和j相差1,但是不知道谁大,只知道i位置的元素和j位置的元素相同
            if (start.charAt(i) != end.charAt(j)) return false;
            //针对i的两种情况进行讨论,排除异常情况
            if (start.charAt(i) == 'L' && i < j) return false;
            if (start.charAt(i) == 'R' && i > j) return false;
            //当前i和j满足start和end相同,继续判断下一个位置
            i++; j++;
        }

        return i == j;
    }
}


//作者：AC_OIer
//        链接：https://leetcode.cn/problems/swap-adjacent-in-lr-string/solution/by-ac_oier-ye71/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。