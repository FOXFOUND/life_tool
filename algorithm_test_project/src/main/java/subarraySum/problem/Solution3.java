package subarraySum.problem;

import java.util.HashMap;
import java.util.Map;

class Solution3 {
    public int findMaxLength(int[] nums) {
        int maxLength = 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int counter = 0;
        //利用-1,将最开始的位置进行了标记
        map.put(counter, -1);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            if (num == 1) {
                counter++;
            } else {
                counter--;
            }
            if (map.containsKey(counter)) {
                int prevIndex = map.get(counter);
                maxLength = Math.max(maxLength, i - prevIndex);
            } else {
                map.put(counter, i);
            }
        }
        return maxLength;
    }
}

//作者：LeetCode-Solution
//        链接：https://leetcode.cn/problems/A1NYOS/solution/0-he-1-ge-shu-xiang-tong-de-zi-shu-zu-by-xbyt/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
