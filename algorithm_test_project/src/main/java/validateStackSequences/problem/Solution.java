package validateStackSequences.problem;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] pushed = new int[]{1, 2, 3, 4, 5};
        int[] poped = new int[]{4, 5, 3, 2, 1};
        boolean res = solution.validateStackSequences(pushed, poped);
        System.out.println(res);
    }

    public boolean validateStackSequences(int[] pushed, int[] popped) {

        int[] pushedIndex = new int[pushed.length + 1];
        for (int i = 0; i < pushed.length; i++) {
            pushedIndex[pushed[i]] = i;
        }


        for (int i = 0; i < popped.length; i++) {
            for (int j = i + 1; j < popped.length; j++) {
                int left = pushedIndex[popped[i]];
                int right = pushedIndex[popped[j]];

                if (left < right) {
                    return false;
                }

            }

        }

        return true;


    }
}
