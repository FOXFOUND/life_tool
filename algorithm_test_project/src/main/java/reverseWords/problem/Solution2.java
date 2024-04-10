package reverseWords.problem;

public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        //solution2.reverseWords("the sky is blue");
        String s = solution2.reverseWords("  hello world!  ");
        System.out.println(s);
    }

    public String reverseWords(String s) {

        StringBuilder stringBuilder = new StringBuilder();

        int right = s.length() - 1;
        while (right >= 0) {
            if (s.charAt(right) == ' ') {
                right--;
                continue;
            }


            int left = right;
            while (left >= 0 && s.charAt(left) != ' ') {
                left--;
            }

            stringBuilder.append(s.substring(left + 1, right + 1));
            stringBuilder.append(" ");
            right = left;
        }
        return stringBuilder.toString().trim();

    }
}
