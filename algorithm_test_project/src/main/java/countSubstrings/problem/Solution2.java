package countSubstrings.problem;

public class Solution2 {
    public int countSubstrings(String s) {

        int n = s.length();

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                String temp = s.substring(j, i + 1);
                boolean checkFlag = check(temp);
                if (checkFlag) {
                    res++;
                }
            }
        }
        return res;
    }

    private boolean check(String temp) {

        int left = 0, right = temp.length() - 1;
        while (left < right) {

            if (temp.charAt(left) != temp.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }
        return true;
    }
}
