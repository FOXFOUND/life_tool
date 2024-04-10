package leastInterval.problem;

class Solution2 {
    public int leastInterval(char[] tasks, int n) {
        int[] cnts = new int[26];
        for (char c : tasks) cnts[c - 'A']++;
        int max = 0, tot = 0;
        for (int i = 0; i < 26; i++) max = Math.max(max, cnts[i]);
        for (int i = 0; i < 26; i++) tot += max == cnts[i] ? 1 : 0;
        return Math.max(tasks.length, (n + 1) * (max - 1) + tot);
    }
}
//
//作者：AC_OIer
//        链接：https://leetcode.cn/problems/task-scheduler/solution/by-ac_oier-3560/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。