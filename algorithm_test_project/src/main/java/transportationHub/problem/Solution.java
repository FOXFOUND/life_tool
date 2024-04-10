package transportationHub.problem;

import java.util.HashSet;
import java.util.Set;

/**
 * 为了缓解「力扣嘉年华」期间的人流压力，组委会在活动期间开设了一些交通专线。path[i] = [a, b] 表示有一条从地点 a通往地点 b 的 单向 交通专线。
 * 若存在一个地点，满足以下要求，我们则称之为 交通枢纽：
 * <p>
 * 所有地点（除自身外）均有一条 单向 专线 直接 通往该地点；
 * 该地点不存在任何 通往其他地点 的单向专线。
 * 请返回交通专线的 交通枢纽。若不存在，则返回 -1。
 * <p>
 * 注意：
 * <p>
 * 对于任意一个地点，至少被一条专线连通。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/D9PW8w
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {

    public int transportationHub(int[][] path) {

        int[] numDegree = new int[1001];
        Set<Integer> numExistSet = new HashSet<>();
        for (int i = 0; i < path.length; i++) {
            numExistSet.add(path[i][0]);
            numExistSet.add(path[i][1]);
            numDegree[path[i][1]]++;
            numDegree[path[i][0]]--;
        }
        int numExist = numExistSet.size() - 1;
        for (int i = 0; i < numDegree.length; i++) {
            if (numDegree[i] == numExist) {
                return i;
            }
        }
        return -1;

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        //int[][] arr = new int[][]{{0, 1}, {0, 3}, {1, 3}, {2, 0}, {2, 3}}; // 3
        int[][] arr = new int[][]{{0, 3}, {1, 0}, {1, 3}, {2, 0}, {3, 0}, {3, 2}}; // -1
        int res = solution.transportationHub(arr);
        System.out.println(res);
    }
}
