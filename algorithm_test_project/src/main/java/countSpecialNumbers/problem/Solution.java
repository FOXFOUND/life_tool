package countSpecialNumbers.problem;

import java.util.HashSet;
import java.util.Set;

/**
 * 如果一个正整数每一个数位都是 互不相同 的，我们称它是 特殊整数 。
 * <p>
 * 给你一个 正 整数 n ，请你返回区间 [1, n] 之间特殊整数的数目。
 * <p>
 * https://leetcode.cn/problems/count-special-integers/
 */
public class Solution {
    public int countSpecialNumbers(int n) {


        int res = 0;
        Set<Character> characters = null;

        for (int i = 1; i <= n; i++) {
            String s = String.valueOf(i);
            characters = new HashSet<>();
            int index = 0;
            for (int j = 0; j < s.length(); j++) {
                if (characters.contains(s.charAt(j))) {
                    break;
                }
                characters.add(s.charAt(j));
                index++;
            }
            if (index == s.length()) {
                res++;
            }

        }
        return res;

    }
}
