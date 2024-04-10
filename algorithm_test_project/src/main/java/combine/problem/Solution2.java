package combine.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution2 {
    List<List<Integer>> ret;
    List<Integer> cache;
    int n, k;

    public List<List<Integer>> combine(int n, int k) {
        ret = new ArrayList<>();
        cache = new ArrayList<>();
        this.n = n;
        this.k = k;
        backtrack(1);
        return ret;
    }

    public void backtrack(int start) {
        if (cache.size() == k) {
            ret.add(new ArrayList<>(cache));
            return;
        }
        for (int i = start; i <= n; i++) {
            // 剪枝：如果剩余的个数不够填充list了，直接返回即可
            if (n - i + 1 < k - cache.size()) {
                return;
            }

            cache.add(i);
            backtrack(i + 1);
            cache.remove(cache.size() - 1);
        }
    }
}

//作者：jian-shi
//        链接：https://leetcode.cn/problems/uUsW3B/solution/javahui-su-chao-guo-by-jian-shi-d0tr/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
