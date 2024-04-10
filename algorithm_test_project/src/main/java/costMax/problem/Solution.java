package costMax.problem;


/**
 * 题目
 * 双十一众多商品进行打折销售，小明想购买一些自己心仪的商品，
 * 但由于受购买资金限制，所以他决定从众多心意商品中购买 3 件，
 * 而且想尽可能的花完资金， 现在请你设计一个程序帮助小明计算尽可能花费的最大资金额。
 * <p>
 * 输入
 * 第一行为整型数组M，数组长度小于100，数组元素记录单个商品的价格；
 * 单个商品价格小于1000； 第二行输入为购买资金的额度R； R < 100000。
 * <p>
 * 输出
 * 输出为满足上述条件的最大花费额度 如果不存在满足上述条件的商品请返回-1
 */
public class Solution {

    //问题 : 忽略了从当前元素开始计算
    public int costMax(int[] costs) {

        int n = costs.length;

        //用来记录购买行为,
        // 第一维度是对于第n个商品的决策,
        // 第二个维度是买或不买的维度, 0代表不买, 1代表买
        // 第三个维度是剩余购买次数
        int[][] buyActor = new int[n][2];

        // 第三个维度是剩余购买次数
        int[][][] reHave = new int[n][2][1];
        buyActor[0][0] = 0;
        reHave[0][0][1] = 3;

        buyActor[0][1] = costs[0];
        reHave[0][1][1] = 2;


        for (int i = 1; i < n; i++) {

            //没买
            int flayNoBuy = 0;
            int tempNoBuy = 0;
            if (buyActor[i - 1][0] > buyActor[i - 1][1]) {
                flayNoBuy = 1;
                tempNoBuy = buyActor[i - 1][0];
            } else {
                flayNoBuy = 0;
                tempNoBuy = buyActor[i - 1][1];
            }
            buyActor[i][0] = tempNoBuy;
            if (flayNoBuy == 1) {
                reHave[i][0][1] = reHave[i - 1][0][1];
            } else {
                reHave[i][0][1] = reHave[i - 1][0][1];
            }

            //买了
            if (buyActor[i - 1][0] > buyActor[i - 1][1]) {
                if (reHave[i - 1][0][1] > 0) {
                    reHave[i][0][1] = reHave[i - 1][0][1] - 1;
                    buyActor[i][1] = buyActor[i - 1][0] + costs[i];
                }
            } else {

                //case 1: 之前已经把购买次数用光了
                if (reHave[i - 1][0][1] == 0) {
                    if (buyActor[i - 1][0] + costs[i] > buyActor[i - 1][1]
                            && reHave[i - 1][0][1] > 0) {
                        reHave[i][1][1] = reHave[i - 1][0][1] - 1;
                        buyActor[i][1] = buyActor[i - 1][0] + costs[i];
                    } else {
                        buyActor[i][1] = buyActor[i - 1][1];
                        reHave[i][1][1] = 0;
                    }
                } else {
                    //case2 : 现在还可以买
                    buyActor[i][1] = buyActor[i - 1][1] + costs[i];
                    reHave[i][1][1] = reHave[i - 1][1][1] - 1;
                }
            }

        }

        return Math.max(buyActor[n - 1][0], buyActor[n - 1][1]);
    }
}
