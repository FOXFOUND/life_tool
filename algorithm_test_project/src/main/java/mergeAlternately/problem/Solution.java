package mergeAlternately.problem;

public class Solution {
    public String mergeAlternately(String word1, String word2) {

        int word1Current = 0;
        int word2Current = 0;

        StringBuilder stringBuilder = new StringBuilder();
        while (word1Current < word1.length()
                || word2Current < word2.length()) {

            if (word1Current < word1.length()) {
                stringBuilder.append(word1.charAt(word1Current));
                word1Current++;
            }
            if (word2Current < word2.length()) {
                stringBuilder.append(word2.charAt(word2Current));
                word2Current++;
            }
        }

        return stringBuilder.toString();

    }
}
