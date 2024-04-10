package tribonacci.problem;

public class Solution {
    public int tribonacci(int n) {

        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int preTwo = 1;
        int preOne = 1;
        for (int i = 3; i <= n; i++) {
            int temp = preOne + preTwo;
            if (i == n) {
                return temp;
            }
            preTwo = preOne;
            preOne = temp;
        }

        return 0;

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.tribonacci(4);
    }
}
