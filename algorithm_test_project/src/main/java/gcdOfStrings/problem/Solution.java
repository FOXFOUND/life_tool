package gcdOfStrings.problem;

public class Solution {
    public String gcdOfStrings(String str1, String str2) {

        int index = 0;
        int step = 0;
        while (step < str1.length() && step < str2.length()) {
            //公因子
            if (str1.charAt(index) == str2.charAt(index)) {
                index++;
            }
            step++;
        }

        if (index == 0) {
            return "";
        }

        if (index != str1.length()
               &&  index != str2.length()) {
            return "";
        }
        String s = str1.substring(0,index);
        int pivot = 0;
        while (pivot <= index) {
            String temp = str1.substring(0,pivot);
            if (s.replace(temp, "").equals("")) {
                return temp;
            }
            pivot++;
        }
        return s;


    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        //String res = solution.gcdOfStrings("ABCABC", "ABC");
        String res = solution.gcdOfStrings("ABCDEF","ABC");
        System.out.println(res);
    }
}
