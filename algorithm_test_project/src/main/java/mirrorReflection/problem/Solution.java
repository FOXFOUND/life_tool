package mirrorReflection.problem;

import java.util.HashMap;
import java.util.Map;

/**
 * 有一个特殊的正方形房间，每面墙上都有一面镜子。除西南角以外，每个角落都放有一个接受器，编号为 0， 1，以及 2。
 * <p>
 * 正方形房间的墙壁长度为 p，一束激光从西南角射出，首先会与东墙相遇，入射点到接收器 0 的距离为 q 。
 * <p>
 * 返回光线最先遇到的接收器的编号（保证光线最终会遇到一个接收器）。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/mirror-reflection
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int mirrorReflection(int p, int q) {

        Map<String, Integer> point = new HashMap<>();
        point.put(p + "_" + 0, 0);
        point.put(p + "_" + p, 1);
        point.put(0 + "_" + p, 2);

        int currentX= p, currentY = q;
        int k = q;
        int b = currentY - k * currentX;
        int nextX = q, nextY = -k * currentX + b;


        return 0;

    }
}
