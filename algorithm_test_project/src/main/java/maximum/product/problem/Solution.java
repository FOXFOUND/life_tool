package maximum.product.problem;

public class Solution {

    /**
     * @param nums
     * @param k
     * @return
     */
    public int maximumProduct(int[] nums, int k) {
        int res = 1;
        int mod = 1000000007;
        int numLength = nums.length % 2 == 0 ? nums.length /2  : 1;
        for (int j = 0; j < k; j++) {
            //构建小顶堆 ,i代表当前的父节点, 2i代表左节点, 2i+1代表右节点
            for (int i =numLength; i >= 0; i--) {
                //选择左右节点的最小值
                int minPost = nums[2 * i + 1] < nums[2 * i + 2] ? 2 * i + 1 : 2 * i + 2;
                if (nums[minPost] < nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[minPost];
                    nums[minPost] = temp;
                }
            }
            nums[0]++;
        }
        for (int i = 0; i < nums.length; i++) {
            res *= nums[i];
        }
        return res % mod;

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        int [] nums = {6,3,3,2};
//        int res= solution.maximumProduct(nums,2);

        int[] nums = {9, 7, 8};
        int res = solution.maximumProduct(nums, 9);
        System.out.println(res);
    }

}
