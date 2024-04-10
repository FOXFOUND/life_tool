package isItPossible.problem;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean isItPossible(String word1, String word2) {
        Map<Character, Integer> m1 = new HashMap<>(), m2 = new HashMap<>();
        //计数，方便后续工作
        for (char c : word1.toCharArray()) m1.put(c, m1.getOrDefault(c, 0) + 1);
        for (char c : word2.toCharArray()) m2.put(c, m2.getOrDefault(c, 0) + 1);

        /*
        需要用到的变量，提前声明好
        t1,t2代表w1和w2中不同字符的总数
        n1,n2代表字符串中某个字符的数量
        c,d分别代表w1和w2要交换过去的字符
        */
        int t1, t2, n1, n2;
        char c, d;


        /**
         * 通过枚举法,判断是否存在
         */
        for (Map.Entry<Character, Integer> i : m1.entrySet()) {
            c = i.getKey();
            n1 = i.getValue();

            for (Map.Entry<Character, Integer> j : m2.entrySet()) {
                t1 = m1.size();
                t2 = m2.size();
                d = j.getKey();
                n2 = j.getValue();

                //如果两个元素相等,那么没有交换的必要,只需要判断是否符合条件,符条件就结束,不符则交换其他的元素
                if (c == d) {//细节1，用例：w1 = "aa",w2 = "a"
                    if (t1 == t2)
                        return true;
                    continue;
                }

                //细节2，当原本字符不包含交换过来的字符，总字符数+1
                if (!m1.containsKey(d)) ++t1;
                if (!m2.containsKey(c)) ++t2;

                //由于只能交换一次,当n1不为1的时候,不会影响t1
                //细节3，当原本字符换走后就不存在了，总字符数-1
                if (n1 == 1) --t1;
                if (n2 == 1) --t2;

                //t1 == t2这样就可以返回true了
                if (t1 == t2) return true;
            }
        }
        return false;
    }
}

//作者：AYD-0521
//        链接：https://leetcode.cn/problems/make-number-of-distinct-characters-equal/solution/-by-ayd-0521-eogk/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。