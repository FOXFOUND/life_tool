package canTransform.problem;

/**
 * 在一个由 'L' , 'R' 和 'X' 三个字符组成的字符串（例如"RXXLRXRXL"）中进行移动操作。一次移动操作指用一个"LX"替换一个"XL"，或者用一个"XR"替换一个"RX"。现给定起始字符串start和结束字符串end，请编写代码，当且仅当存在一系列移动操作使得start可以转换成end时， 返回True。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/swap-adjacent-in-lr-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public boolean canTransform(String start, String end) {

        int current = 0, differentNum = 0;

        char[] startArr = start.toCharArray();
        char[] endArr = end.toCharArray();

        while (current < startArr.length) {

            if (startArr[current] != endArr[current]) {
                differentNum++;
            }
            current++;
            if (differentNum == 2 &&
                    (startArr[current - differentNum] == 'L'
                            && startArr[current - differentNum + 1] == 'X'
                            ||
                            startArr[current - differentNum] == 'X'
                                    && startArr[current - differentNum + 1] == 'R')) {
                char temp = startArr[current - differentNum];
                startArr[current - differentNum] = startArr[current - differentNum + 1];
                startArr[current - differentNum + 1] = temp;
                differentNum = 0;
            }

        }

        current = 0;
        while (current < startArr.length) {
            if (startArr[current] != endArr[current]) {
                return false;
            }
            current++;
        }

        return true;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
//        String start = "RXXLRXRXL";
//        String end = "XRLXXRRLX";

        String start = "RXXLRXRXL";
        String end = "XRLXXRRLX";
        boolean res = solution.canTransform(start, end);
        System.out.println(res);
    }
}
