package smallestNumber.problem;

/**
 * 给你下标从 0 开始、长度为 n 的字符串 pattern ，它包含两种字符，'I' 表示 上升 ，'D' 表示 下降 。
 * <p>
 * 你需要构造一个下标从 0 开始长度为 n + 1 的字符串，且它要满足以下条件：
 * <p>
 * num 包含数字 '1' 到 '9' ，其中每个数字 至多 使用一次。
 * 如果 pattern[i] == 'I' ，那么 num[i] < num[i + 1] 。
 * 如果 pattern[i] == 'D' ，那么 num[i] > num[i + 1] 。
 * 请你返回满足上述条件字典序 最小 的字符串 num。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/construct-smallest-number-from-di-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {


    public String smallestNumber(String pattern) {

        int min = smallestNumberSon(pattern, -1, "", Integer.MAX_VALUE);
        return min + "";
    }

    private int smallestNumberSon(String pattern, int index, String s, int minNum) {
        //终止条件
        if (index == pattern.length()) {
            boolean[] check = new boolean[10];
            //题目要求-不能有重复
            for (int i = 0; i < s.length(); i++) {

                if (check[s.charAt(i) - '0']) {
                    return Integer.MAX_VALUE;
                }

                check[s.charAt(i) - '0'] = true;
            }

            //返回最小值
            if (minNum < Integer.valueOf(s).intValue()) {
                return Integer.MAX_VALUE;
            }

            minNum = Integer.valueOf(s).intValue();
            return minNum;
        }

        if (index == -1) {
            int tempNum = Integer.MAX_VALUE;
            for (int i = 1; i <= 9; i++) {
                String temp = s + "" + i;
                int res = smallestNumberSon(pattern, index + 1, temp, tempNum);
                if (tempNum > res) {
                    tempNum = res;
                }
            }
            return tempNum;
        }

        int pre = index <= 0 ? Integer.valueOf(s.charAt(0) + "") : Integer.valueOf(s.charAt(index) + "");

        //增加
        if (pattern.charAt(index) == 'I') {
            for (int i = pre + 1; i <= 9; i++) {
                String temp = s + "" + i;
                int res = smallestNumberSon(pattern, index + 1, temp, minNum);
                if (minNum > res) {
                    minNum = res;
                }
            }

        }

        //减少
        if (pattern.charAt(index) == 'D') {

            for (int i = pre - 1; i >= 1; i--) {
                String temp = s + "" + i;
                int res = smallestNumberSon(pattern, index + 1, temp, minNum);
                if (minNum > res) {
                    minNum = res;
                }
            }

        }

        return minNum;

    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "IIIDIDDD";   // 123549876
        //String s = "DDD";   // 4321
        String res = solution.smallestNumber(s);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(res);  //126759483

    }
}
