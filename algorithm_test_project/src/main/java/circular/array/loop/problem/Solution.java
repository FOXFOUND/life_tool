package circular.array.loop.problem;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Solution {

    public boolean circularArrayLoop(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            if (check(nums, i)) {
                return true;
            }
        }
        return false;

    }

    private static boolean check(int[] nums, int i) {
        Set<Integer> arriveSet = new HashSet<>();
        int start = i;
        arriveSet.add(i);
        while (true) {
            //下一步索引的位置
            int next = start + nums[start];
            if (next > 0) {
                next = next % nums.length;
            }
            if (next < 0) {
                next = next + nums.length;
            }
            if (arriveSet.contains(next)) {
                //所有 nums[seq[j]] 应当不是 全正 就是 全负
                Iterator<Integer> integerIterator = arriveSet.iterator();
                while (integerIterator.hasNext()) {
                    if (nums[integerIterator.next().intValue()] * nums[0] < 0) {
                        return false;
                    }
                }

                if (next == i) {
                    return true;
                }
                return false;


            } else {
                arriveSet.add(next);
                start = next;
            }
            if (arriveSet.size() == nums.length) {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {-1, -2, -3, -4, -5, 6};
        Solution solution = new Solution();
        boolean res = solution.circularArrayLoop(nums);
        System.out.println(res);
    }
}
