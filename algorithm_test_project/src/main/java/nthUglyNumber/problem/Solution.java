package nthUglyNumber.problem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int res = solution.nthUglyNumber(27);
        System.out.println(res);
    }

    public int nthUglyNumber(int n) {

        Set<Integer> uglySet = new HashSet<>();
        uglySet.add(1);

        int[] nth = new int[]{2, 3, 5};
        //5的5次方
        for (int i = 0; i < 13; i++) {
            List<Integer> temp = new ArrayList<>();
            uglySet.forEach(v -> {
                temp.add(v);
            });

            int te1 = -1;
            for (int k = 0; k < temp.size(); k++) {
                for (int j = 0; j < 3; j++) {
                    uglySet.add(temp.get(k) * nth[j]);
                }
            }
        }


        List<Integer> res = new ArrayList<>();
        uglySet.forEach(v -> {
            res.add(v);
        });
        res.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        for (int i = 0; i < res.size(); i++) {
            if (i == n) {
                return res.get(i - 1);
            }
        }

        return -1;

    }
}
