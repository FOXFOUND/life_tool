package closeStrings.problem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution2 {
    public boolean closeStrings(String word1, String word2) {

        Map<Character, Integer> word1Map = new HashMap<>();
        Map<Character, Integer> word2Map = new HashMap<>();
        for (int i = 0; i < word1.length(); i++) {
            int value = word1Map.getOrDefault(word1.charAt(i), 0);
            word1Map.put(word1.charAt(i), value + 1);
        }

        for (int i = 0; i < word2.length(); i++) {
            int value = word2Map.getOrDefault(word2.charAt(i), 0);
            word2Map.put(word2.charAt(i), value + 1);
        }

        List<Integer> word1Size = new ArrayList<>();
        List<Integer> word2Size = new ArrayList<>();

        Set<Character> word1Set = new HashSet<>();
        Set<Character> word2Set = new HashSet<>();

        word1Map.forEach((k, v) -> {
            word1Set.add(k);
            word1Size.add(v);
        });

        word2Map.forEach((k, v) -> {
            word2Set.add(k);
            word2Size.add(v);
        });

        if (!word1Set.containsAll(word2Set)) {
            return false;
        }

        word1Size.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.intValue() - o2.intValue();
            }
        });

        word2Size.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.intValue() - o2.intValue();
            }
        });


        int index = 0;
        for (int i = 0; i < word1Size.size(); i++) {
            if (word1Size.get(i).intValue() != word2Size.get(i).intValue()) {
                return false;
            }
            index++;
        }
        return index == word2Size.size();

    }
}
