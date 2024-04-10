package buildMatrix.problem;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一个 正 整数 k ，同时给你：
 * <p>
 * 一个大小为 n 的二维整数数组 rowConditions ，其中 rowConditions[i] = [abovei, belowi] 和
 * 一个大小为 m 的二维整数数组 colConditions ，其中 colConditions[i] = [lefti, righti] 。
 * 两个数组里的整数都是 1 到 k 之间的数字。
 * <p>
 * 你需要构造一个 k x k 的矩阵，1 到 k 每个数字需要 恰好出现一次 。剩余的数字都是 0 。
 * <p>
 * 矩阵还需要满足以下条件：
 * <p>
 * 对于所有 0 到 n - 1 之间的下标 i ，数字 abovei 所在的 行 必须在数字 belowi 所在行的上面。
 * 对于所有 0 到 m - 1 之间的下标 i ，数字 lefti 所在的 列 必须在数字 righti 所在列的左边。
 * 返回满足上述要求的 任意 矩阵。如果不存在答案，返回一个空的矩阵。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/build-a-matrix-with-conditions
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {

    class Tree {
        private Integer value;
        private List<Tree> nextList = new ArrayList<>();

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public List<Tree> getNextList() {
            return nextList;
        }

        public void setNextList(List<Tree> nextList) {
            this.nextList = nextList;
        }
    }

    /**
     * 分析 :
     * 1. k*k的矩阵,说明无论从行和列来看,每行每列都只会有1个数
     * 2. rowConditions ,colConditions 可以作为优化条件
     * 3. 为了加快速度, rowConditions ,colConditions 可以先校验合法性
     * 4. 题目是8皇后问题的扩展
     * 5. rowConditions ,colConditions 可以先校验合法性 和合法性的校验依据是 不存在 a[i] > a[j] && a[i] < a[j]
     * 需要注意的是传递性
     *
     * @param k
     * @param rowConditions
     * @param colConditions
     * @return
     */
    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {

        return null;
        //1.合法性校验 ,判断图中是否有环 , 将二位数组转换为图


        //2.递归

        //3.减枝

        //4.返回结果
    }
}
