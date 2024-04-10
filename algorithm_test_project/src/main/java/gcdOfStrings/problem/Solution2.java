package gcdOfStrings.problem;

public class Solution2 {
    public String gcdOfStrings(String str1, String str2) {
        int index = 0;
        while (index <= str1.length() && index <= str2.length()) {
            String temp = str1.substring(0, index);
            if (str1.replace(temp, "").equals("")
                    && str2.replace(temp, "").equals("")) {

                if (str1.length() % str2.length() == 0) {
                    return str2;
                }
                return temp;
            }
            index++;
        }
        return "";
    }
}
