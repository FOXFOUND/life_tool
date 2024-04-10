package removeStars.problem;

import java.util.Stack;

public class Solution {
    public String removeStars(String s) {

        Stack<Character> characters = new Stack<>();

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == '*') {
                if (!characters.isEmpty()) {
                    characters.pop();
                }
                continue;
            }
            characters.push(s.charAt(i));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < characters.size(); i++) {
            stringBuilder.append(characters.get(i));
        }
        return stringBuilder.toString();
    }
}
