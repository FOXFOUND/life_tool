package findDifference.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Solution {


    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums1 = new int[]{1, 2, 3};
        int[] nums2 = new int[]{2, 4, 6};
        solution.findDifference(nums1, nums2);
    }

    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {

        Set<Integer> num1Set = new HashSet<>();
        Set<Integer> num2Set = new HashSet<>();



        for (int i = 0; i < nums1.length; i++) {
            num1Set.add(nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            num2Set.add(nums2[i]);
        }
        Set<Integer> num1SetTemp = new HashSet<>(num1Set);
        Set<Integer> num2SetTemp = new HashSet<>(num2Set);

        List<List<Integer>> res = new ArrayList<>();

        num1SetTemp.removeAll(num2Set);
        num2SetTemp.removeAll(num1Set);


        List<Integer> res0 = new ArrayList<>();
        Iterator<Integer> num1Integer = num1SetTemp.iterator();
        while (num1Integer.hasNext()) {
            res0.add(num1Integer.next());
        }

        List<Integer> res1 = new ArrayList<>();
        Iterator<Integer> num2Integer = num2SetTemp.iterator();
        while (num2Integer.hasNext()) {
            res1.add(num2Integer.next());
        }

        res.add(res0);
        res.add(res1);
        return res;
    }
}
