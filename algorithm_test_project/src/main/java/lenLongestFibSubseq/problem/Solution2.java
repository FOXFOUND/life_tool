package lenLongestFibSubseq.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Solution2 {

    public int lenLongestFibSubseq(int[] arr) {

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i);
        }

        //dfs
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(arr[i]);
            //设置开始位置
            int res = lenLongestFibSubseqSub(arr, i, i, 0, map, arrayList);
            if (res > max) {
                max = res;
            }

        }
        return max;

    }

    private int lenLongestFibSubseqSub(int[] arr, int index, int pre, int num, Map<Integer, Integer> map, ArrayList<Integer> arrayList) {

        if (index >= arr.length) {
            return num;
        }

        int max = num, res = num;
        for (int j = index; j < arr.length; j++) {
            //问题: 没有利用 当前j必须是前两个元素相加的,可以引入数组进行缓存

            if (arrayList.size() >= 2
                    && (arrayList.get(arrayList.size() - 1).intValue() + arrayList.get(arrayList.size() - 2).intValue()) != arr[j]) {
                continue;
            }

            if (map.get(arr[pre] + arr[j]) != null) {
                int nextNum = 0;
                if (num == 0) {
                    if (arr[pre] == arr[j]) {
                        nextNum = 0;
                    } else {
                        nextNum = 3;
                    }
                } else {
                    nextNum = num + 1;
                }
                ArrayList arrayList1 = new ArrayList();
                for (int i = 0; i < arrayList.size(); i++) {
                    arrayList1.add(arrayList.get(i));
                }
                arrayList1.add(arr[j]);
                System.out.println(arr[pre] + "_" + arr[j] + "_" + nextNum);
                System.out.println(arrayList1);
                res = lenLongestFibSubseqSub(arr, map.get(arr[j] + arr[pre]), j, nextNum, map, arrayList1);
                if (res > max) {
                    max = res;
                }
            }
        }
        return max;
    }


    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        //int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8};  //5
        //int[] arr = new int[]{1, 3, 7, 11, 12, 14, 18}; //3
        //int[] arr = new int[]{2, 4, 5, 6, 7, 8, 11, 13, 14, 15, 21, 22, 34};  //5
        int[] arr = new int[]{2, 4, 7, 8, 9, 10, 14, 15, 18, 23, 32, 50};//5
        //int[] arr = new int[]{2392,2545,2666,5043,5090,5869,6978,7293,7795};//0
        int res = solution.lenLongestFibSubseq(arr);
        System.out.println(res);
    }
}
