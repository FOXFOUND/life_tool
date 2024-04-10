package subarrayLCM.problem;

class Solution2 {
    public int subarrayLCM(int[] nums, int k) {
        int ans = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int temp = nums[i];
            for (int j = i; j < n; j++) {
                //每次向后一个位置并判断当前子数组的最小公倍数是不是k

                //求temp和nums[j]的最大公约数
                int maxGCM = gcd(temp, nums[j]);

                // lcm(m,n) = maxGCM * k1 * k2 ,其中 m =  maxGCM * k1  , n =  maxGCM  * k2  =
                // lcm(m,n) =  m * k2 =  m * n/maxGCM;
                temp = temp * nums[j] / maxGCM;

                //如果最小公倍数是k,符合条件结果加1
                if (temp == k) {
                    ans++;
                } else if (temp > k) {
                    //如果temp大于k,说明包含当前数的数组的最小公倍数一定大于k,所以break从下一个位置开始搜索
                    break;
                }
            }
        }
        return ans;
    }

    /**
     * 数组的最小公倍数是能被数组中所有数都整除的最小整数
     * gcm是最大公约数,lcm是最小公倍数
     *
     * @param a
     * @param b
     * @return
     */
    public int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }


    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        //int k = 6;
//        int[] nums = new int[]{5, 1, 1, 1, 2};  //6
//        int k = 1;

        int[] nums = new int[]{3, 6, 2, 7, 1};  //4
        int k = 6;
        solution2.subarrayLCM(nums, k);
        System.out.println(solution2.gcd(10, 3));
    }
}

//作者：Luna_
//        链接：https://leetcode.cn/problems/number-of-subarrays-with-lcm-equal-to-k/solution/2470zui-xiao-gong-bei-shu-by-luna_-1w7v/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。