package successfulPairs.problem;

import java.util.Arrays;

class Solution2 {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int[] ans = new int[spells.length];
        Arrays.sort(potions);
        long[] arr = new long[potions.length];//必须要转发为long类型，不然超范围
        for (int i = 0; i < arr.length; i++) {
            arr[i] = potions[i];
        }
        for (int i = 0; i < spells.length; i++) {
            //二分查找arr[index] * spell[i]>=target的第一个数据
            int index = search(arr, spells[i], success);
            ans[i] = potions.length - index;
        }

        return ans;
    }

    //二分查找arr[index] * spell[i]>=target的第一个数据
    private int search(long[] arr, long x, long target) {
        if (arr[arr.length - 1] * x < target) return arr.length;//对边界进行处理
        int l = 0, r = arr.length - 1;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (arr[mid] * x >= target) r = mid;
            else l = mid + 1;
        }
        return r;
    }
}

//作者：shou-hu-zhe-t
//        链接：https://leetcode.cn/problems/successful-pairs-of-spells-and-potions/solution/by-shou-hu-zhe-t-9jhd/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
