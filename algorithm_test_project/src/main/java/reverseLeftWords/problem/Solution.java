package reverseLeftWords.problem;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public String reverseLeftWords(String s, int n) {

        List<Character> characterList = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            characterList.add(s.charAt(i));
        }

        for (int i = 0; i < n; i++) {
            Character head = characterList.remove(0);
            characterList.add(head);
        }

        StringBuilder sb = new StringBuilder();
        for (Character ch : characterList) {
            sb.append(ch);
        }
        return sb.toString();


    }
}
