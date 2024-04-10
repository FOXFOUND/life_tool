package circle.string.problem;

public class Solution {
    public boolean checkPartitioning(String s) {
        boolean res = false;
        if (check(s, true) || check(s,false)) {
            return true;
        }
        return res;
    }

    private boolean check(String s, boolean leftMoveFlag) {
        int left = 0;
        int right = s.length() - 1;
        while (left != right) {
            String leftStr = s.substring(0, left + 1);
            String middleStr = s.substring(left + 1, right);
            String rightStr = s.substring(right, s.length());
            if (leftStr.equals(reverse1(leftStr))
                    && middleStr.equals(reverse1(middleStr))
                    && rightStr.equals(reverse1(rightStr))) {
                return true;
            }
            if (leftMoveFlag) {
                left++;
                leftMoveFlag = false;
            } else {
                right--;
                leftMoveFlag = true;
            }
        }
        return false;
    }

    private String reverse1(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //String s = "abcbdd";
        String s = "juchzcedhfesefhdeczhcujzzvbmoeombv";
        Solution solution = new Solution();
        System.out.println(solution.checkPartitioning(s));
    }
}
