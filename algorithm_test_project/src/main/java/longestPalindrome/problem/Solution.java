package longestPalindrome.problem;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.longestPalindrome("aaaa"));
    }

    public String longestPalindrome(String s) {
        int leftPoint = 0;
        int rightPoint = 0;

        for (int i = 0; i < s.length(); i++) {

            //奇数的情况
            int left = i, right = i;
            while (left - 1 >= 0
                    && right + 1 < s.length()) {
                if (s.charAt(left - 1) == s.charAt(right + 1)) {
                    left = left - 1;
                    right = right + 1;
                    if (right - left > rightPoint - leftPoint) {
                        leftPoint = left;
                        rightPoint = right;
                    }

                } else {
                    break;
                }
            }


            //偶数的情况
            if (i + 1 < s.length()) {
                left = i;
                right = i + 1;
                if (s.charAt(left) == s.charAt(right)) {
                    if (right - left > rightPoint - leftPoint) {
                        leftPoint = left;
                        rightPoint = right;
                    }
                } else {
                    continue;
                }
                while (left - 1 >= 0
                        && right + 1 < s.length()) {
                    if (s.charAt(left - 1) == s.charAt(right + 1)) {
                        left = left - 1;
                        right = right + 1;
                        if (right - left > rightPoint - leftPoint) {
                            leftPoint = left;
                            rightPoint = right;
                        }

                    } else {
                        break;
                    }
                }


            }
        }
        return s.substring(leftPoint, rightPoint + 1);
    }
}
