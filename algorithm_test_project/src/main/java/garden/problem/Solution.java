package garden.problem;


class Solution {
    public int minTaps(int n, int[] ranges) {
        int cur = -1, rightmost = 0;
        int[] choices = new int[(n + 1) / 2];

        for (int i = 0; i <= n; i++) {
            //如果水龙头范围为0，直接跳过
            if (ranges[i] == 0) {
                continue;
            }
            int right = Math.min(ranges[i] + i, n);
            //如果水龙头扩大不了范围，直接跳过
            if (right <= rightmost) {
                continue;
            }
            int left = Math.max(i - ranges[i], 0);
            //如果水龙头连接不起来，直接跳过
            if (left > rightmost) {
                continue;
            }
            //此时 left < rightMost<= right
            //如果水龙头能浇灌到0，则清空已选
            //说明此时是最大的覆盖边界
            if (left == 0) {
                cur = -1;
            } else {
                //如果已经选择了两个以上的水龙头了，根据能否与已选择的倒数第二个水龙头连起来，判断是否要舍弃倒数第一个水龙头，并循环
                while ((cur >= 1 && left <= choices[cur - 1])) {
                    cur--;
                }
            }
            //把当前水龙头加入已选择
            cur++;
            choices[cur] = right;
            rightmost = right;
        }

        //没选出水龙头，或者浇灌长度不够返回-1
        if (cur == -1 || rightmost < n) {
            return -1;
        }

        return cur + 1;
    }

    /**
     *
     作者：ms100
     链接：https://leetcode.cn/problems/minimum-number-of-taps-to-open-to-water-a-garden/solution/d-by-ms100-jynp/
     来源：力扣（LeetCode）
     著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}


