package arth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    class Staff {
        int s, e;

        public Staff(int s, int e) {
            this.s = s;
            this.e = e;
        }
    }

    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        final int MODULO = 1000000007;
        List<Staff> list = new ArrayList<Staff>();
        PriorityQueue<Staff> queue = new PriorityQueue<Staff>(new Comparator<Staff>() {
            public int compare(Staff staff1, Staff staff2) {
                return staff1.s - staff2.s;
            }
        });
        for (int i = 0; i < n; ++i) {
            list.add(new Staff(speed[i], efficiency[i]));
        }
        Collections.sort(list, new Comparator<Staff>() {
            public int compare(Staff staff1, Staff staff2) {
                return staff2.e - staff1.e;
            }
        });
        long ans = 0, sum = 0;
        for (int i = 0; i < n; ++i) {
            Staff staff = list.get(i);
            long minE = staff.e;
            long sumS = sum + staff.s;
            ans = Math.max(ans, sumS * minE);
            queue.offer(staff);
            sum += staff.s;
            if (queue.size() == k) {
                sum -= queue.poll().s;
            }
        }
        return (int) (ans % MODULO);
    }

    /**
     * 作者：LeetCode-Solution
     *         链接：https://leetcode.cn/problems/maximum-performance-of-a-team/solution/zui-da-de-tuan-dui-biao-xian-zhi-by-leetcode-solut/
     *         来源：力扣（LeetCode）
     *         著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}

