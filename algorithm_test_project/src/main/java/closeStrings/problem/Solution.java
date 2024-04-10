package closeStrings.problem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {
    public boolean closeStrings(String word1, String word2) {

        Map<Character, Integer> word1Map = new HashMap<>();
        for (int i = 0; i < word1.length(); i++) {
            int value = word1Map.getOrDefault(word1.charAt(i), 0);
            word1Map.put(word1.charAt(i), value + 1);
        }

        for (int i = 0; i < word2.length(); i++) {
            int value = word1Map.getOrDefault(word2.charAt(i), 0);
            word1Map.put(word2.charAt(i), value - 1);
        }

        AtomicInteger num = new AtomicInteger();
        word1Map.forEach((k, v) -> {
            num.addAndGet(v);
        });

        return num.get() == 0;
    }
}
