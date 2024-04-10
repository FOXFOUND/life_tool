package bignumber.problem;

public class Solution {


    public static void main(String[] args) {

        String s1 = "1123456789012345";
        String s2 = "9876543210123445";

        Solution solution = new Solution();
        String res = solution.bigIntegerAdd(s1, s2);
        System.out.println(res);
//        System.out.println(res.length());   //10999999999135790
//        System.out.println("9999999999135790".length());
        //System.out.println(res.equals("9999999999135790"));
    }


    /**
     * ### 字符串加法
     * <p>
     * 需要有完整的思路和代码。
     * <p>
     * 输入两个长度不等的字符串，计算两个字符串的数值和，不用 int long bigDecimal
     * <p>
     * 输入：`"123456789012345"`,`"9876543210123445"`
     * <p>
     * 输出：`"9999999999135790"`
     *
     * @param s1
     * @param s2
     * @return
     */
    public String bigIntegerAdd(String s1, String s2) {

        StringBuilder stringBuilderS1 = new StringBuilder(s1);
        stringBuilderS1 = stringBuilderS1.reverse();

        //System.out.println("s1 = " + s1);
        StringBuilder stringBuilderS2 = new StringBuilder(s2);
        stringBuilderS2 = stringBuilderS2.reverse();

        //System.out.println("s2 = " + s2);

        int aboveBit = 0;

        int s1Length = stringBuilderS1.length();
        int s2Length = stringBuilderS2.length();

        int index = 0;

        StringBuilder stringBuilderRes = new StringBuilder();

        while (index < s1Length && index < s2Length) {
            int s1Num = Integer.valueOf(stringBuilderS1.charAt(index) - '0' );
            int s2Num = Integer.valueOf(stringBuilderS2.charAt(index) - '0');
            index++;
            int currentNum = (s1Num + s2Num + aboveBit) % 10;
            stringBuilderRes.append(currentNum);
            aboveBit = (s1Num + s2Num + aboveBit) / 10;
        }

        while (index < s1Length) {
            int s1Num = Integer.valueOf(stringBuilderS1.charAt(index) - '0');
            index++;
            int currentNum = (s1Num + aboveBit) % 10;
            stringBuilderRes.append(currentNum);
            aboveBit = (s1Num + aboveBit) / 10;
        }

        while (index < s2Length) {
            int s2Num = Integer.valueOf(stringBuilderS2.charAt(index) - '0');
            index++;
            int currentNum = (s2Num + aboveBit) % 10;
            stringBuilderRes.append(currentNum);
            aboveBit = (s2Num + aboveBit) / 10;
        }

        if(aboveBit > 0){
            stringBuilderRes.append(aboveBit);
        }


        return stringBuilderRes.reverse().toString();
    }
}
