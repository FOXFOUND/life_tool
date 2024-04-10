package maximumSwap.problem;

public class Solution2 {
    public int maximumSwap(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        int[] last = new int[10];
        //记录digits中每个数字最后一次出现的位置
        for (int i = 0; i < digits.length; i++) {
            last[digits[i] - '0'] = i;
        }
        //从高位到低位找到第一个比后面数字小的数字
        for (int i = 0; i < digits.length; i++) {
            for (int j = 9; j > digits[i] - '0'; j--) {
                if (last[j] > i) {
                    char temp = digits[i];
                    digits[i] = digits[last[j]];
                    digits[last[j]] = temp;
                    return Integer.parseInt(new String(digits));
                }
            }
        }
        return num;
    }
}
