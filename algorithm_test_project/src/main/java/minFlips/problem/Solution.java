package minFlips.problem;

public class Solution {

    public static void main(String[] args) {

    }

    public int minFlips(int a, int b, int c) {

        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        StringBuilder s3 = new StringBuilder();

        while (a != 0) {
            if ((a & 1) == 1) {
                s1.append("1");
            } else {
                s1.append("0");
            }
            a = a >> 1;
        }


        while (b != 0) {
            if ((b & 1) == 1) {
                s2.append("1");
            } else {
                s2.append("0");
            }
            b = b >> 1;
        }


        while (c != 0) {
            if ((c & 1) == 1) {
                s3.append("1");
            } else {
                s3.append("0");
            }
            c = c >> 1;
        }

        //对齐
        int maxLength = Math.max(Math.max(s1.length(), s2.length()), s3.length());
        for (int i = s1.length(); i < maxLength; i++) {
            s1.append("0");
        }
        for (int i = s2.length(); i < maxLength; i++) {
            s2.append("0");
        }
        for (int i = s3.length(); i < maxLength; i++) {
            s3.append("0");
        }

        s1.reverse();
        s2.reverse();
        s3.reverse();

        int num = 0;
        for (int i = 0; i < s3.length(); i++) {
            if (s1.charAt(i) == s3.charAt(i) && s3.charAt(i) == '0') {
                if (s2.charAt(i) != '0') {
                    num++;
                }
            }

            if (s1.charAt(i) == s3.charAt(i) && s3.charAt(i) == '1') {
                if (s2.charAt(i) == '1') {
                    num++;
                }
            }

            if (s1.charAt(i) != s3.charAt(i) && s3.charAt(i) == '1') {
                if (s2.charAt(i) != '1') {
                    num++;
                }
            }


            if (s1.charAt(i) != s3.charAt(i) && s3.charAt(i) == '0') {
                num++;
                if (s2.charAt(i) == '1') {
                    num++;
                }
            }


        }


        return num;
    }
}
