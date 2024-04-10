package maximumSwap.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 给定一个非负整数，你至多可以交换一次数字中的任意两位。返回你能得到的最大值。
 */
public class Solution {
    public int maximumSwap(int num) {
        List<Integer> arr = new ArrayList<>();
        HashMap<Integer, List<Integer>> numPositionMap = new HashMap<>();
        //获取每一位的数
        int location = 0;
        int length = (num + "").length() - 1;
        while (num != 0) {

            arr.add(num % 10);

            List<Integer> numList = numPositionMap.get(num % 10);
            if (numList == null) {
                numList = new ArrayList<>();
            }
            numList.add(length - location);
            numPositionMap.put(num % 10, numList);
            location++;
            num = num / 10;
        }
        int[] arrNum = new int[arr.size()];
        for (int i = 0; i < arrNum.length; i++) {
            arrNum[i] = arr.get(length - i);
        }
        int[] exChange = Arrays.copyOf(arrNum, arr.size());
        Arrays.sort(exChange);
        int left = 0, right = exChange.length - 1;
        while (left < right) {
            int temp = exChange[left];
            exChange[left] = exChange[right];
            exChange[right] = temp;
            left++;
            right--;
        }
        int exChangeTime = 0;

        List<Position> positionList = new ArrayList<>();

        for (int i = 0; i < exChange.length; i++) {
            if (exChange[i] != arrNum[i] && exChangeTime < 1) {
                Position position = new Position();
                position.setLocation(i);
                position.setNum(exChange[i]);
                exChangeTime++;
                positionList.add(position);
            }
            if (exChangeTime >= 1) {
                break;
            }
        }

        for (int i = 0; i < positionList.size(); i++) {

            Position position = positionList.get(i);
            int locationNumLeft = position.getLocation();
            List<Integer> locationList = numPositionMap.get(position.getNum());
            int locationNumRight = locationList.get(0);
            int temp = arrNum[locationNumLeft];
            arrNum[locationNumLeft] = arrNum[locationNumRight];
            arrNum[locationNumRight] = temp;
        }

        int res = 0;
        for (int i = arrNum.length - 1; i >= 0; i--) {
            res += Math.pow(10, length - i) * arrNum[i];
        }

        return res;

    }



    class Position {
        private int num;
        private int location;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int res = solution.maximumSwap(9973);
        //int res = solution.maximumSwap(2736);
        System.out.println(res);
    }
}
