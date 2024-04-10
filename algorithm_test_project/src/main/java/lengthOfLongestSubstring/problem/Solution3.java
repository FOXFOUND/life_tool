package lengthOfLongestSubstring.problem;

import java.util.HashSet;
import java.util.Set;

public class Solution3 {
    public int lengthOfLongestSubstring(String s) {

        int n = s.length();

        int res = 0;
        int left = 0, right = 0;

        Set<Character> characterSet = new HashSet<>();
        while (right < n) {

            if (characterSet.contains(s.charAt(right))) {
                characterSet.remove(s.charAt(left));
                left++;
            } else {
                characterSet.add(s.charAt(right));
                res = Math.max(res, right - left + 1);
                right++;
            }

        }

        return res;
    }
}
