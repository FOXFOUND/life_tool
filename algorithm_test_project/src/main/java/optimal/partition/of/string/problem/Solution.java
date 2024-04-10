package optimal.partition.of.string.problem;

import java.util.Arrays;
import java.util.Stack;

public class Solution {

    /**
     * 给你一个字符串 s ，请你将该字符串划分成一个或多个 子字符串 ，并满足每个子字符串中的字符都是 唯一 的。也就是说，在单个子字符串中，字母的出现次数都不超过 一次 。
     * <p>
     * 满足题目要求的情况下，返回 最少 需要划分多少个子字符串。
     * <p>
     * 注意，划分后，原字符串中的每个字符都应该恰好属于一个子字符串。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/optimal-partition-of-string
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param s
     * @return
     */
    public int partitionString(String s) {

        //字符串
        Stack<String> stringStack = new Stack<>();
        int pre = 0;
        int start = 0;
        while (start <= s.length() - 1) {
            String currentStr = s.substring(pre, start + 1);
            boolean[] check = new boolean[currentStr.length()];
            boolean pushFlag = true;
            Arrays.fill(check, false);
            for (int i = 0; i < currentStr.length(); i++) {
                if (check[i]) {
                    pre = start;
                    pushFlag = false;
                    break;
                }else {
                    
                }
            }
            if (!pushFlag) {
                start = pre;
                continue;
            }

            if (stringStack.empty()) {
                stringStack.push(currentStr);
            }
            if (!stringStack.empty() && pushFlag && start != pre) {
                stringStack.pop();
                stringStack.push(currentStr);
            }
            start++;
        }
        return stringStack.size();
    }

    public static void main(String[] args) {
        String s = "abacaba";
        Solution solution = new Solution();
        int res = solution.partitionString(s);
        System.out.println(res);
    }
}
