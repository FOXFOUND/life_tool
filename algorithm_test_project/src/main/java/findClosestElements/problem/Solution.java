package findClosestElements.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个 排序好 的数组 arr ，两个整数 k 和 x ，从数组中找到最靠近 x（两数之差最小）的 k 个数。返回的结果必须要是按升序排好的。
 * <p>
 * 整数 a 比整数 b 更接近 x 需要满足：
 * <p>
 * |a - x| < |b - x| 或者
 * |a - x| == |b - x| 且 a < b
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/find-k-closest-elements
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {

        int xIndex = -1, left = 0, right = arr.length -1 , middle = -1;
        while (left < right) {
            middle = (left + right +1) / 2;
            if (arr[middle] == x) {
                xIndex = middle;
                break;
            }
            if (arr[middle] > x) {
                right = middle;
                right--;
                continue;
            }
            left = middle;
            left++;
        }
        //数组中不存在x,从最接近x的位置开始
        if (xIndex == -1) {
            xIndex = left;
        }
        //双指针
        left = xIndex ;
        right = xIndex + 1;


        while ((right - left) < k) {

            //异常情况
            if (left == 0) {
                right++;
                continue;
            }
            if (right == arr.length - 1) {
                left--;
                continue;
            }

            //判断规则
            if (Math.abs(arr[left] - x) <= Math.abs(arr[right] - x)) {
                left--;
                continue;
            }
            right++;
        }

        List<Integer> res = new ArrayList<>();
        for (int i = left; i < right; i++) {
            res.add(arr[i]);
        }

        return res;

    }


    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[] arr = new int[]{1, 2, 3, 4, 5};
//        //int k = 4 ,x = 3; // [1,2,3,4]
//        int k = 4, x = -1; // [1,2,3,4]

        int[] arr = new int[]{1, 1, 1, 10, 10, 10};
        int k = 1, x = 9;  //10


//        int[] arr = new int[]{0, 0, 0, 1, 3, 5, 6, 7, 8, 8};
//        int k = 2, x = 2;   //[1,3]
        List<Integer> res = solution.findClosestElements(arr, k, x);
        System.out.println(JSON.toJSONString(res));
    }
}
