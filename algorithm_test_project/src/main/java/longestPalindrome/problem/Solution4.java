package longestPalindrome.problem;

public class Solution4 {
    public String longestPalindrome(String s) {

        int current = 0;
        int n = s.length();

        String res = null;
        int max =0;
        while (current < n) {
            int left = current, right = current;
            while (right < n && s.charAt(right) == s.charAt(current)) {
                if (right + 1 < n) {
                    right++;
                }else {
                    break;
                }
            }

            while (left >= 0 && s.charAt(left) == s.charAt(current)) {
                if (left - 1 >= 0) {
                    left--;
                }else {
                    break;
                }
            }

            while (right < n && left >= 0 && s.charAt(right) == s.charAt(left)) {

                if (left - 1 < 0) {
                    break;
                }
                if (right + 1 >= n) {
                    break;
                }
                left--;
                right++;
            }

            if(right - left + 1 > max){
                max = right -left + 1;
                res = s.substring(left,right+1);
            }


            current++;
        }
        return res;
    }
}
