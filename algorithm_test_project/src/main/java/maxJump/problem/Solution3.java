package maxJump.problem;

class Solution3 {
    public int maxJump(int[] stones) {
        int cost = stones[1] - stones[0];
        int n = stones.length;
        for (int i = 2; i < n; i++) {
            cost = Math.max(cost, stones[i] - stones[i - 2]);
        }
        return cost;
    }
}

//作者：stormsunshine
//        链接：https://leetcode.cn/problems/frog-jump-ii/solution/by-stormsunshine-vpde/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。