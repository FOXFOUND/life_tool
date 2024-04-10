package possibleToStamp.problem;


import java.util.ArrayList;
import java.util.List;

/**
 * 给你一个 m x n 的二进制矩阵 grid ，每个格子要么为 0 （空）要么为 1 （被占据）。
 * <p>
 * 给你邮票的尺寸为 stampHeight x stampWidth 。我们想将邮票贴进二进制矩阵中，且满足以下 限制 和 要求 ：
 * <p>
 * 覆盖所有 空 格子。
 * 不覆盖任何 被占据 的格子。
 * 我们可以放入任意数目的邮票。
 * 邮票可以相互有 重叠 部分。
 * 邮票不允许 旋转 。
 * 邮票必须完全在矩阵 内 。
 * 如果在满足上述要求的前提下，可以放入邮票，请返回 true ，否则返回 false 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/stamping-the-grid
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {

    public boolean possibleToStamp(int[][] grid, int stampHeight, int stampWidth) {
        int[] index = grid[0];
        List<Integer> fillIndexList = new ArrayList<>();
        for (int i = 0; i < index.length; i++) {
            if (index[i] == 1) {
                //记录1的位置
                fillIndexList.add(i);
            }
        }
        int current = 0, next = 0;
        int currentIndex = 0, nextIndex = 0;
        if (fillIndexList.size() >= 2) {
            nextIndex = 1;
        }
        current = fillIndexList.get(currentIndex);
        next = fillIndexList.get(nextIndex);


        if (currentIndex != nextIndex) {
            int stampWidthSum = 0;
            for (int i = 0; i < currentIndex; i++) {

            }
        }

        return true;

    }
}
