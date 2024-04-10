package countSubstrings.problem;

public class Solution5 {

    public int countSubstrings(String s) {

        int n = s.length();
        int res = 0;
        for (int i = 0; i < n; i++) {
            int num1 = centerRound(s, i, i);
            int num2 = centerRound(s, i, i + 1);
            res += num1;
            res += num2;

        }
        return res;

    }

    private int centerRound(String s, int left, int right) {

        int res = 0;

        while (left >= 0 && right < s.length()) {

            if (s.charAt(left) == s.charAt(right)) {
                res++;
                left--;
                right++;
                continue;
            }

            break;

        }
        return res;
    }
}
