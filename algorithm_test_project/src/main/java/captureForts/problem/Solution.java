package captureForts.problem;


/**
 * 给你一个长度为 n ，下标从 0 开始的整数数组 forts ，表示一些城堡。forts[i] 可以是 -1 ，0 或者 1 ，其中：
 * <p>
 * -1 表示第 i 个位置 没有 城堡。
 * 0 表示第 i 个位置有一个 敌人 的城堡。
 * 1 表示第 i 个位置有一个你控制的城堡。
 * 现在，你需要决定，将你的军队从某个你控制的城堡位置 i 移动到一个空的位置 j ，满足：
 * <p>
 * 0 <= i, j <= n - 1
 * 军队经过的位置 只有 敌人的城堡。正式的，对于所有 min(i,j) < k < max(i,j) 的 k ，都满足 forts[k] == 0 。
 * 当军队移动时，所有途中经过的敌人城堡都会被 摧毁 。
 * <p>
 * 请你返回 最多 可以摧毁的敌人城堡数目。如果 无法 移动你的军队，或者没有你控制的城堡，请返回 0 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/maximum-enemy-forts-that-can-be-captured
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int captureForts(int[] forts) {
        //请数组中 1和-1之间0的最大个数

        int sum = 0;
        int max = 0;
        int beTween = 0;
        int preBeTween = 0;
        for (int i = 0; i < forts.length; i++) {
            preBeTween = beTween;
            beTween += forts[i];
            if (beTween <= -2) {
                beTween = -1;
                sum = 0;
                continue;
            }

            if (beTween >= 2) {
                beTween = 1;
                sum = 0;
                continue;
            }

            if (beTween != 0 && forts[i] == 0) {
                sum++;
            }
            if (beTween == 0) {
                if (preBeTween != 0) {
                    beTween = forts[i];
                }
                max = max > sum ? max : sum;
                sum = 0;
            }
        }

        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] forts = new int[]{1, 0, 0, -1, 0, 0, 0, 0, 1};  // 4
        //int[] forts = new int[]{0, 0, 1, -1};  // 0
        //int[] forts = new int[]{1, -1, -1, 1, 1};  // 0
        //int[] forts = new int[]{0, -1, -1, 0, -1};  // 0
        //int[] forts = new int[]{-1, 0, -1, 0, 1, 1, 1, -1, -1, -1};  // 1
        //int[] forts = new int[]{1, 1, 1, 0, 1, 0, -1};  // 1

        int res = solution.captureForts(forts);
        System.out.println(res);
    }
}
