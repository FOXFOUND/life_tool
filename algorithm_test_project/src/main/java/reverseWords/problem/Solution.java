package reverseWords.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        //String s = "the sky is blue";
        String s = "a good   example";
        String res = solution.reverseWords(s);
        System.out.println(res);
    }

    public String reverseWords(String s) {

        String[] arr = s.trim().split(" ");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("")) {
                continue;
            }
            list.add(arr[i].trim());
        }
        String[] arrPure = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arrPure[i] = list.get(i);
        }
        //System.out.println(JSON.toJSONString(list));

        int n = arrPure.length;
        for (int i = 0; i < arrPure.length / 2; i++) {
            String temp = arrPure[i];
            arrPure[i] = arrPure[n - 1 - i];
            arrPure[n - 1 - i] = temp;
        }
        String res = String.join(" ", arrPure);
        return res;
    }
}
