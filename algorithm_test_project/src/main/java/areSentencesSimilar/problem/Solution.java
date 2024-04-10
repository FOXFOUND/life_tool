package areSentencesSimilar.problem;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public boolean areSentencesSimilar(String sentence1, String sentence2) {
        String[] sentence1Arr = sentence1.split(" ");
        String[] sentence2Arr = sentence2.split(" ");

        Set<String> wordSet = new HashSet();
        String[] bigWordArr = null;
        String[] shortWordArr = null;

        if (sentence1Arr.length > sentence2Arr.length) {
            bigWordArr = sentence1Arr;
            shortWordArr = sentence2Arr;
        } else {
            bigWordArr = sentence2Arr;
            shortWordArr = sentence1Arr;
        }

        for (int i = 0; i < bigWordArr.length; i++) {
            wordSet.add(bigWordArr[i]);
        }

        int addNum = 0;
        for (int i = 0; i < shortWordArr.length; i++) {
            if (!wordSet.contains(shortWordArr[i])) {
                return false;
            }
        }
        return true;

    }
}
