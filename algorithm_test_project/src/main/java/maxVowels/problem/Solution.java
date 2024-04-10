package maxVowels.problem;

public class Solution {
    public int maxVowels(String s, int k) {

        int metaNum = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'a') {
                metaNum++;
            }
            if (s.charAt(i) == 'e') {
                metaNum++;
            }
            if (s.charAt(i) == 'i') {
                metaNum++;
            }
            if (s.charAt(i) == 'o') {
                metaNum++;
            }
            if (s.charAt(i) == 'u') {
                metaNum++;
            }
        }

        if (k >= metaNum) {
            return metaNum;
        }
        return k;
    }
}
