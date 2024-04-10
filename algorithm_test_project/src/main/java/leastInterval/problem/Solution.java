package leastInterval.problem;

public class Solution {
    /**
     * 给你一个用字符数组 tasks 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。在任何一个单位时间，CPU 可以完成一个任务，或者处于待命状态。
     * <p>
     * 然而，两个 相同种类 的任务之间必须有长度为整数 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
     * <p>
     * 你需要计算完成所有任务所需要的 最短时间
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/task-scheduler
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {
        /**
         * 由于采用for循环,导致排在后边的任务存在积压,导致了最后执行的时候出现等待期
         */

        int res = 0;
        //A 65
        int[] waitTime = new int[24];
        int[] job = new int[24];

        for (int i = 0; i < tasks.length; i++) {
            int index = tasks[i] - 'A';
            job[index]++;
        }
        int doJob = 0;

        while (true) {

            boolean select = false;

            for (int i = 0; i < job.length; i++) {
                //空任务跳过
                if (job[i] == 0) {
                    continue;
                }
                //处于等待期的任务跳过
                if (waitTime[i] != 0) {
                    continue;
                }

                //立即执行
                if (job[i] != 0 && waitTime[i] == 0) {
                    int o = i+ 65;
                    System.out.println(o);

                    //有可以执行的任务
                    select = true;
                    //已经完成的任务数
                    doJob++;
                    //任务数组
                    job[i]--;
                    //时间周期
                    res++;
                    waitTime[i] = n;
                    //其他的任务,减少一个时间单位
                    for (int j = 0; j < waitTime.length; j++) {
                        if (waitTime[j] != 0 && i != j) {

                            waitTime[j]--;
                        }
                    }
                    break;
                }

            }

            if (doJob == tasks.length) {
                break;
            }

            //所有任务,都处于冷却器
            if (!select) {
                System.out.println("wait");
                res++;
                for (int j = 0; j < waitTime.length; j++) {
                    if (waitTime[j] != 0) {
                        waitTime[j]--;
                    }
                }
            }
        }
        return res;


    }


    public static void main(String[] args) {
        //char[] tasks = new char[]{'A', 'A', 'A', 'B', 'B', 'B'};
        //char[] tasks = new char[]{'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[] tasks = new char[]{'A','A','A','B','B','B', 'C','C','C', 'D', 'D', 'E'};
        /**
         * 65
         * 66
         * 67
         * 65
         * 66
         * 67
         * 65
         * 66
         * 67
         * 68
         * 69
         * wait
         * 68
         */
        int n = 2;
        Solution solution = new Solution();
        int res = solution.leastInterval(tasks, n);
        System.out.println(res);
    }
}
