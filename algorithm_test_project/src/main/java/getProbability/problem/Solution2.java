package getProbability.problem;

import java.util.Arrays;

class Solution2 {
    long[][] combinations;
    int k;
    double totalProbability = 0;

    public double getProbability(int[] balls) {
        this.k = balls.length;
        int totalBalls = Arrays.stream(balls).sum();
        combinations = new long[totalBalls + 1][totalBalls + 1];
        combinations[0][0] = 1;
        for (int i = 1; i <= totalBalls; i++) {
            combinations[i][0] = 1;
            combinations[i][i] = 1;
            for (int j = 1; j < i; j++) {
                combinations[i][j] = combinations[i - 1][j - 1] + combinations[i - 1][j];
            }
        }
        int[] firstBox = new int[k];
        int[] secondBox = new int[k];
        System.arraycopy(balls, 0, firstBox, 0, k);
        backtrack(firstBox, secondBox, 0);
        return totalProbability;
    }


    public void backtrack(int[] firstBox, int[] secondBox, int index) {
        //所有颜色小球分完,开始计算是否符合题意
        if (index == k) {
            int firstSum = 0, secondSum = 0;
            int firstColors = 0, secondColors = 0;
            //计算小球总数和颜色总数
            for (int i = 0; i < k; i++) {
                if (firstBox[i] > 0) {
                    firstSum += firstBox[i];
                    firstColors++;
                }
                if (secondBox[i] > 0) {
                    secondSum += secondBox[i];
                    secondColors++;
                }
            }
            //符合题意的计算概率
            if (firstSum == secondSum && firstColors == secondColors) {
                double probability = 1;
                for (int i = 0; i < k; i++) {
                    probability *= combinations[firstBox[i] + secondBox[i]][firstBox[i]];
                }
                probability /= combinations[firstSum + secondSum][firstSum];
                totalProbability += probability;
            }
        } else {
            /**
             * 将所有的球,放到第一个小盒中,通过每次从第一个小盒向第二个小盒挪1个,dfs进行枚举
             */
            int firstCurr = firstBox[index], secondCurr = secondBox[index];
            for (int i = 0; i <= firstCurr; i++) {
                //从第一个小盒向第二个小盒挪动1个同颜色的小球
                firstBox[index] = firstCurr - i;
                secondBox[index] = secondCurr + i;
                //dfs换一个颜色的小球
                backtrack(firstBox, secondBox, index + 1);
            }
            //下层遍历完,把小球还回去,保证上层的for循环可以继续分 ,
            // 例如 1 2 1 2(i) , 1 2 0 2(i) ,如果不重置,当 1 2 1 之后, 1 2 0 2 就无法继续分了,就会变成 1 2 0 0
            firstBox[index] = firstCurr;
            secondBox[index] = secondCurr;
        }
    }


    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int [] balls = new int[]{1,2,1,2};
        //int [] balls = new int[]{1,1};
        double res =  solution.getProbability(balls);
        System.out.println(res);
    }
}

//作者：stormsunshine
//        链接：https://leetcode.cn/problems/probability-of-a-two-boxes-having-the-same-number-of-distinct-balls/solution/by-stormsunshine-qa0i/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
