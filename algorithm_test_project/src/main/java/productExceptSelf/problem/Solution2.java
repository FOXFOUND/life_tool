package productExceptSelf.problem;

public class Solution2 {
    class Solution {
        public int[] productExceptSelf(int[] nums) {
            int[] res = new int[nums.length];
            int p = 1, q = 1;
            for (int i = 0; i < nums.length; i++) {
                res[i] = p;
                p *= nums[i];
            }
            for (int i = nums.length - 1; i > 0 ; i--) {
                q *= nums[i];
                res[i - 1] *= q;
            }
            return res;
        }
    }

//    作者：jyd
//    链接：https://leetcode.cn/problems/product-of-array-except-self/solution/product-of-array-except-self-shang-san-jiao-xia-sa/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
