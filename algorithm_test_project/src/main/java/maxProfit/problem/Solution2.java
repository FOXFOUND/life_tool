package maxProfit.problem;

class Solution2 {
    public int maxProfit(int[] prices, int fee) {
        return dfs(prices, 0, 0, fee);
    }

    private int dfs(int[] prices, int index, int status, int fee) {
        if (index == prices.length) {
            return 0;
        }
        //定义三个变量，分别记录[不动]、[买]、[卖]
        int a = 0, b = 0, c = 0;
        a = dfs(prices, index + 1, status, fee);
        if (status == 1) {
            //递归处理卖的情况，卖的时候会有一个手续费
            b = dfs(prices, index + 1, 0, fee) + prices[index] - fee;
        } else {
            //递归处理买的情况
            c = dfs(prices, index + 1, 1, fee) - prices[index];
        }
        //最终结果就是三个变量中的最大值
        return Math.max(Math.max(a, b), c);
    }
}

//作者：wang_ni_ma
//        链接：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/solution/si-chong-shi-xian-tu-jie-714-mai-mai-gu-piao-de-zu/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
