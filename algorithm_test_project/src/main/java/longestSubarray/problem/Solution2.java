package longestSubarray.problem;

import java.util.ArrayList;
import java.util.List;

public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        //int[] nums = new int[]{0, 1, 1, 1, 0, 1, 1, 0, 1};
        //int[] nums = new int[]{1, 1, 0, 1};
        //int[] nums = new int[]{1, 0, 0, 0, 0};
        int[] nums = new int[]{0,0,1,1};
        int res = solution.longestSubarray(nums);
        System.out.println(res);
    }

    public int longestSubarray(int[] nums) {

        List<Integer[]> exist = new ArrayList<>();


        int left = -1;
        int right = -1;

        int numOfOne = 0;
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == 1) {
                numOfOne++;
                if (left == -1 && right == -1) {
                    left = i;
                    right = i;
                }


                if (left < i) {
                    right++;
                }

                if (i == nums.length - 1) {
                    Integer[] arr = new Integer[2];
                    arr[0] = left;
                    arr[1] = right;
                    exist.add(arr);
                }


            } else {

                if (right != -1) {
                    Integer[] arr = new Integer[2];
                    arr[0] = left;
                    arr[1] = right;
                    exist.add(arr);
                }

                left = -1;
                right = -1;


            }

        }

        //不存在0
        if (numOfOne == nums.length) {
            return nums.length - 1;
        }


        int max = 0;
        for (int i = 0; i < exist.size(); i++) {
            Integer[] temp = exist.get(i);
            max = Math.max(temp[1] - temp[0] + 1, max);

            if (i + 1 < exist.size() && exist.get(i + 1)[0] - exist.get(i)[1] == 2) {
                max = Math.max(exist.get(i + 1)[1] - exist.get(i)[0], max);
            }
        }
        return max;
    }
}
