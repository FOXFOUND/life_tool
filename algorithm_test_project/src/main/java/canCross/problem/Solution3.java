package canCross.problem;

public class Solution3 {

    public boolean canCross(int[] stones) {

        int right = 1;
        int i = 0;
        return canCrossSon(stones, i, right, 0);
    }


    private boolean canCrossSon(int[] stones, int i, int right, int lastStep) {

        boolean res = false;
        //当前位置
        int current = i;
        //下一个位置
        i++;
        while (i < stones.length && stones[i] <= right) {

            int step = stones[i] - stones[current];
            //只有满足 k-1 ,k,k+1 才算
            if (step < lastStep - 1 || step > lastStep + 1) {
                return false;
            }
            int nextRight = stones[i] + step + 1;
            //将下一个位置进行模拟
            res = res || canCrossSon(stones, i, nextRight, step);
            //当前位置可能,走到的所有可能性
            i++;
        }
        //返回条件
        if (stones[stones.length - 1] <= right) {
            return true;
        }
        return res;

    }

    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        //int[] stones = new int[]{0, 1, 3, 5, 6, 8, 12, 17};  //true 0,1,3,5,8,12,17
        //int[] stones = new int[]{0, 1, 2, 3, 4, 8, 9, 11};  // false
        //int[] stones = new int[]{0, 1, 3, 6, 7};  // false
        int[] stones = new int[]{0, 1, 3, 6, 10, 15, 16, 21};  // true
        boolean res = solution.canCross(stones);
        System.out.println(res);
    }
}
