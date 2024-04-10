package smallestChair.problem;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 有 n 个朋友在举办一个派对，这些朋友从 0 到 n - 1 编号。派对里有 无数 张椅子，编号为 0 到 infinity 。当一个朋友到达派对时，他会占据 编号最小 且未被占据的椅子。
 * <p>
 * 比方说，当一个朋友到达时，如果椅子 0 ，1 和 5 被占据了，那么他会占据 2 号椅子。
 * 当一个朋友离开派对时，他的椅子会立刻变成未占据状态。如果同一时刻有另一个朋友到达，可以立即占据这张椅子。
 * <p>
 * 给你一个下标从 0 开始的二维整数数组 times ，其中 times[i] = [arrivali, leavingi] 表示第 i 个朋友到达和离开的时刻，同时给你一个整数 targetFriend 。所有到达时间 互不相同 。
 * <p>
 * 请你返回编号为 targetFriend 的朋友占据的 椅子编号 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/the-number-of-the-smallest-unoccupied-chair
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

public class Solution {

    public int smallestChair(int[][] times, int targetFriend) {


        int[] position = times[targetFriend];

        Arrays.sort(times, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        //空闲椅子
        PriorityQueue<Integer> idleChartQueue = new PriorityQueue();
        //最大椅子集合
        int maxChart = 0;

        List<PersonChart> personList = new ArrayList<>();

        int friend = 0;
        for (int i = 0; i < times.length; i++) {
            //由于没有两个时刻同时到达的,因此可以在i到达时候,对i-1做释放检查

            Iterator<PersonChart> personChartIterator = personList.iterator();
            while (personChartIterator.hasNext()){
                PersonChart personChart =  personChartIterator.next();
                //应该被释放
                if (personChart.getTimes()[1] <= times[i][0]) {
                    idleChartQueue.add(personChart.getChart());
                    personChartIterator.remove();
                }
            }


            // 空闲椅子为空,使用新椅子
            if (idleChartQueue.isEmpty()) {
                PersonChart personChart = new PersonChart();
                personChart.setNum(i);
                personChart.setChart(maxChart);
                personChart.setTimes(times[i]);
                personList.add(personChart);
                maxChart++;
            } else {
                //空闲椅子不为空,使用空闲椅子
                PersonChart personChart = new PersonChart();
                personChart.setNum(i);
                //取队首
                personChart.setChart(idleChartQueue.poll());
                personChart.setTimes(times[i]);
                personList.add(personChart);
            }


            //target位置
            if (position[0] == times[i][0]) {
                //对应的椅子的位置
                int chart = personList.get(personList.size() - 1).getChart();
                return chart;
            }

        }

        return -1;
    }

    class PersonChart {
        private int num;

        private int chart;

        private int[] times;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getChart() {
            return chart;
        }

        public void setChart(int chart) {
            this.chart = chart;
        }

        public int[] getTimes() {
            return times;
        }

        public void setTimes(int[] times) {
            this.times = times;
        }
    }
}
