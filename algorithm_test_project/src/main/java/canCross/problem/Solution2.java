package canCross.problem;

public class Solution2 {

    public boolean canCross(int[] stones) {

        int right = 1;
        int i = 0;
        return canCrossSon(stones, i, right);
    }

    // 问题: 最多跳k步, 但是题目要求只能 k-1,k,k+1 三种可能
    private boolean canCrossSon(int[] stones, int i, int right) {
        //返回条件
        if (stones[stones.length - 1] <= right) {
            return true;
        }


        boolean res = false;
        //当前位置
        int current = i;
        //下一个位置
        i++;
        while (i < stones.length && stones[i] <= right) {

            int step = stones[i] - stones[current];

            int nextRight = stones[i] + step + 1;
            //将下一个位置进行模拟
            res = res || canCrossSon(stones, i, nextRight);
            //当前位置可能,走到的所有可能性
            i++;
        }
        return res;

    }

    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        //int[] stones = new int[]{0, 1, 3, 5, 6, 8, 12, 17};  //true 0,1,3,5,8,12,17
        //int[] stones = new int[]{0, 1, 2, 3, 4, 8, 9, 11};  // false
        int[] stones = new int[]{0,1,3,6,7};  // false
        boolean res = solution.canCross(stones);
        System.out.println(res);
    }
}
