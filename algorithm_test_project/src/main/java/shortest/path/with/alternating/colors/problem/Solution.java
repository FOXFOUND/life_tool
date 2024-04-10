package shortest.path.with.alternating.colors.problem;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        int[] answers = new int[n];
        Map<String, Integer> redMap = new HashMap<>();
        Map<String, Integer> blueMap = new HashMap<>();
        for (int i = 0; i < redEdges.length; i++) {
            String redMapKey = redEdges[i][0] + "_" + redEdges[i][1];
            redMap.put(redMapKey, 1);
        }

        if (redMap.get("0_1") == null) {
            Arrays.fill(answers, -1);
            answers[0] = 0;
            return answers;
        }
        answers[0] = 0;
        answers[1] = 1;

        for (int i = 0; i < blueEdges.length; i++) {
            String blueMapKey = blueEdges[i][0] + "_" + blueEdges[i][1];
            blueMap.put(blueMapKey, 1);
        }


        for (int i = 2; i < n; i++) {

            //假设存在从0到n的边
            String redKey = 0 +"_" + i;
            if(redMap.get(redKey) != null){
                answers[i] = 1;
                continue;
            }

            boolean caseRedFlag = false;
            boolean caseBlueFlag = false;
            //假设n-1到n为红边,并且n-2到n-1为蓝边
            String blueKey = (i - 2) + "_" + (i - 1);
            redKey = (i - 1) + "_" + i;
            if (blueMap.get(blueKey) != null && redMap.get(redKey) != null) {
                answers[i] = answers[i - 1] + 1;
                caseRedFlag = true;
                continue;

            }

            //假设n-1到n为蓝边,并且n-2到n-1为红边
            blueKey = (i - 1) + "_" + i;
            redKey = (i - 2) + "_" + (i - 1);
            if (blueMap.get(blueKey) != null && redMap.get(redKey) != null) {
                answers[i] = answers[i - 1] + 1;
                caseBlueFlag = true;
                continue;
            }

            if (!caseRedFlag && !caseBlueFlag) {
                Arrays.fill(answers, i, n, -1);
            }
        }

        return answers;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[][] redEdges = {{0, 1}};
//        int[][] blueEdges = {{1, 2}};

        int[][] redEdges = {{0, 1}, {0, 2}};
        int[][] blueEdges = {{1, 0}};
        int[] res = solution.shortestAlternatingPaths(3, redEdges, blueEdges);
        System.out.println(JSON.toJSONString(res));
    }
}
